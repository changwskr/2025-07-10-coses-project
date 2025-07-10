package com.banking.eplaton.tcf;

import com.banking.eplaton.transfer.EPlatonEvent;
import com.banking.eplaton.transfer.EPlatonCommonDTO;
import com.banking.eplaton.transfer.TPSVCINFODTO;
import com.banking.eplaton.exception.EPlatonException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * TCF (Transaction Control Framework) Implementation
 * 
 * Main transaction control framework that orchestrates STF, BTF, and ETF.
 * This replaces the legacy TCF with Spring Boot features.
 */
@Component
@Transactional
public class TCF extends AbstractTCF implements ITCF {

    private static final Logger logger = LoggerFactory.getLogger(TCF.class);

    @Autowired
    private STF stf;

    @Autowired
    private BTF btf;

    @Autowired
    private ETF etf;

    private EPlatonEvent currentEvent;

    public TCF() {
        super();
    }

    @Override
    public EPlatonEvent execute(EPlatonEvent event) {
        try {
            currentEvent = event;

            logger.info("TCF execution started for event: {}", event.getEventId());
            logger.info("UserTransaction Start for event: {}", event.getEventId());

            // Initialize TCF if not ready
            if (!isReady()) {
                initialize();
            }

            // Execute STF (Service Transaction Framework)
            logger.info("STF execution started for event: {}", event.getEventId());
            event = stf.execute(event);

            // Check STF result and route accordingly
            char errorCode = event.getTpsvcInfo().getErrorCode().charAt(0);

            switch (errorCode) {
                case 'e':
                case 's':
                case 'E':
                case 'S':
                    logger.warn("STF error detected for event: {}", event.getEventId());
                    logger.info("STF execution ended with error for event: {}", event.getEventId());
                    break;

                case 'I':
                    logger.info("STF success detected for event: {}", event.getEventId());
                    logger.info("STF execution ended successfully for event: {}", event.getEventId());

                    // Execute BTF (Business Transaction Framework)
                    logger.info("BTF execution started for event: {}", event.getEventId());
                    event = btf.execute(event);

                    if (isError(event)) {
                        logger.warn("BTF error detected for event: {}", event.getEventId());
                    } else {
                        logger.info("BTF success detected for event: {}", event.getEventId());
                    }

                    logger.info("BTF execution ended for event: {}", event.getEventId());
                    break;

                case '*':
                default:
                    logger.error("Unknown error code: {} for event: {}", errorCode, event.getEventId());
                    TCF_SPerror("EFWK0034", this.getClass().getName() + ".execute():error code not set");
                    break;
            }

            // Execute ETF (End Transaction Framework)
            logger.info("ETF execution started for event: {}", event.getEventId());
            event = etf.execute(event);
            logger.info("ETF execution ended for event: {}", event.getEventId());

            logger.info("TCF execution completed for event: {}", event.getEventId());

            return event;

        } catch (Exception e) {
            logger.error("TCF execution failed for event: {}", event.getEventId(), e);
            handleTCFError(event, e);
            return event;
        }
    }

    @Override
    public EPlatonEvent getEPlatonEvent() {
        return currentEvent;
    }

    @Override
    public boolean isReady() {
        return initialized && stf != null && btf != null && etf != null;
    }

    @Override
    public void initialize() {
        try {
            logger.info("Initializing TCF components");

            // Initialize sub-components
            if (stf != null) {
                stf.initialize();
            }
            if (btf != null) {
                btf.initialize();
            }
            if (etf != null) {
                etf.initialize();
            }

            initialized = true;
            logger.info("TCF components initialized successfully");

        } catch (Exception e) {
            logger.error("TCF initialization failed", e);
            throw new RuntimeException("TCF initialization error", e);
        }
    }

    @Override
    public void cleanup() {
        try {
            logger.info("Cleaning up TCF components");

            // Cleanup sub-components
            if (stf != null) {
                stf.cleanup();
            }
            if (btf != null) {
                btf.cleanup();
            }
            if (etf != null) {
                etf.cleanup();
            }

            initialized = false;
            logger.info("TCF components cleaned up successfully");

        } catch (Exception e) {
            logger.error("TCF cleanup failed", e);
            throw new RuntimeException("TCF cleanup error", e);
        }
    }

    /**
     * Check if event has error
     */
    private boolean isError(EPlatonEvent event) {
        char errorCode = event.getTpsvcInfo().getErrorCode().charAt(0);

        switch (errorCode) {
            case 'e':
            case 's':
            case 'E':
            case 'S':
                return true;
            case 'I':
                return false;
            case '*':
            default:
                logger.error("Unknown error code: {} for event: {}", errorCode, event.getEventId());
                TCF_SPerror("EFWK0033", this.getClass().getName() + ".isError():error code not set");
                return true;
        }
    }

    /**
     * Handle TCF error
     */
    private void handleTCFError(EPlatonEvent event, Exception e) {
        logger.error("Handling TCF error for event: {}", event.getEventId(), e);

        TCF_SPerror("EFWK0032", this.getClass().getName() + ".execute():" + e.toString());
        logger.error("TCF_execute() exception:[EFWK032] for event: {}", event.getEventId());

        // Set rollback only
        event.getTpsvcInfo().setStatus("ROLLBACK_ONLY");
        event.getCommon().setStatus("ERROR");
    }

    /**
     * TCF SP error handling
     */
    private void TCF_SPerror(String errorCode, String message) {
        if (currentEvent == null || currentEvent.getTpsvcInfo() == null) {
            logger.error("Cannot set error - event or TPSVC info is null");
            return;
        }

        char currentErrorCode = currentEvent.getTpsvcInfo().getErrorCode().charAt(0);

        switch (currentErrorCode) {
            case 'I':
                currentEvent.getTpsvcInfo().setErrorCode(errorCode);
                currentEvent.getTpsvcInfo().setErrorMessage(message);
                break;
            case 'E':
                errorCode = errorCode + "|" + currentEvent.getTpsvcInfo().getErrorCode();
                currentEvent.getTpsvcInfo().setErrorCode(errorCode);
                currentEvent.getTpsvcInfo().setErrorMessage(message);
                break;
            default:
                logger.warn("Unknown error code for TCF_SPerror: {}", currentErrorCode);
                break;
        }
    }

    /**
     * Get STF instance
     */
    public STF getStf() {
        return stf;
    }

    /**
     * Get BTF instance
     */
    public BTF getBtf() {
        return btf;
    }

    /**
     * Get ETF instance
     */
    public ETF getEtf() {
        return etf;
    }

    @Override
    protected void doInitialize() throws Exception {
        logger.info("Initializing TCF base components");
        // TCF-specific initialization logic is handled in initialize()
    }

    @Override
    protected void doCleanup() throws Exception {
        logger.info("Cleaning up TCF base components");
        // TCF-specific cleanup logic is handled in cleanup()
    }
}