package com.banking.neweplatonframework.business.tcf;

import com.banking.neweplatonframework.transfer.NewEPlatonEvent;
import com.banking.neweplatonframework.exception.NewEPlatonException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * New TCF (Transaction Control Framework) Implementation
 * 
 * Main transaction control framework that orchestrates STF, BTF, and ETF for
 * New EPlaton.
 * This replaces the legacy TCF with Spring Boot features.
 */
@Component
@Transactional
public class NewTCF implements INewTCF {

    private static final Logger logger = LoggerFactory.getLogger(NewTCF.class);

    @Autowired
    private NewSTF stf;

    @Autowired
    private NewBTF btf;

    @Autowired
    private NewETF etf;

    private NewEPlatonEvent currentEvent;
    private boolean initialized = false;

    public NewTCF() {
    }

    @Override
    public NewEPlatonEvent execute(NewEPlatonEvent event) {
        try {
            currentEvent = event;

            logger.info("New TCF execution started for event: {}", event.getEventId());
            logger.info("UserTransaction Start for event: {}", event.getEventId());

            // Initialize TCF if not ready
            if (!isReady()) {
                initialize();
            }

            // Execute STF (Service Transaction Framework)
            logger.info("New STF execution started for event: {}", event.getEventId());
            event = stf.execute(event);

            // Check STF result and route accordingly
            char errorCode = event.getTpsvcInfo().getErrorCode().charAt(0);

            switch (errorCode) {
                case 'e':
                case 's':
                case 'E':
                case 'S':
                    logger.warn("New STF error detected for event: {}", event.getEventId());
                    logger.info("New STF execution ended with error for event: {}", event.getEventId());
                    break;

                case 'I':
                    logger.info("New STF success detected for event: {}", event.getEventId());
                    logger.info("New STF execution ended successfully for event: {}", event.getEventId());

                    // Execute BTF (Business Transaction Framework)
                    logger.info("New BTF execution started for event: {}", event.getEventId());
                    event = btf.execute(event);

                    if (isError(event)) {
                        logger.warn("New BTF error detected for event: {}", event.getEventId());
                    } else {
                        logger.info("New BTF success detected for event: {}", event.getEventId());
                    }

                    logger.info("New BTF execution ended for event: {}", event.getEventId());
                    break;

                case '*':
                default:
                    logger.error("Unknown error code: {} for event: {}", errorCode, event.getEventId());
                    newTCF_SPerror("EFWK0034", this.getClass().getName() + ".execute():error code not set");
                    break;
            }

            // Execute ETF (End Transaction Framework)
            logger.info("New ETF execution started for event: {}", event.getEventId());
            event = etf.execute(event);
            logger.info("New ETF execution ended for event: {}", event.getEventId());

            logger.info("New TCF execution completed for event: {}", event.getEventId());

            return event;

        } catch (Exception e) {
            logger.error("New TCF execution failed for event: {}", event.getEventId(), e);
            handleNewTCFError(event, e);
            return event;
        }
    }

    @Override
    public NewEPlatonEvent getNewEPlatonEvent() {
        return currentEvent;
    }

    @Override
    public boolean isReady() {
        return initialized && stf != null && btf != null && etf != null;
    }

    @Override
    public void initialize() {
        try {
            logger.info("Initializing New TCF components");

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
            logger.info("New TCF components initialized successfully");

        } catch (Exception e) {
            logger.error("New TCF initialization failed", e);
            throw new RuntimeException("New TCF initialization error", e);
        }
    }

    @Override
    public void cleanup() {
        try {
            logger.info("Cleaning up New TCF components");

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
            logger.info("New TCF components cleaned up successfully");

        } catch (Exception e) {
            logger.error("New TCF cleanup failed", e);
            throw new RuntimeException("New TCF cleanup error", e);
        }
    }

    /**
     * Check if event has error
     */
    private boolean isError(NewEPlatonEvent event) {
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
                newTCF_SPerror("EFWK0033", this.getClass().getName() + ".isError():error code not set");
                return true;
        }
    }

    /**
     * Handle New TCF error
     */
    private void handleNewTCFError(NewEPlatonEvent event, Exception e) {
        logger.error("Handling New TCF error for event: {}", event.getEventId(), e);

        newTCF_SPerror("EFWK0032", this.getClass().getName() + ".execute():" + e.toString());
        logger.error("New TCF_execute() exception:[EFWK032] for event: {}", event.getEventId());

        // Set rollback only
        event.getTpsvcInfo().setStatus("ROLLBACK_ONLY");
        event.getCommon().setStatus("ERROR");
    }

    /**
     * New TCF SP error handling
     */
    private void newTCF_SPerror(String errorCode, String message) {
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
                logger.warn("Unknown error code for New TCF_SPerror: {}", currentErrorCode);
                break;
        }
    }

    /**
     * Get STF instance
     */
    public NewSTF getStf() {
        return stf;
    }

    /**
     * Get BTF instance
     */
    public NewBTF getBtf() {
        return btf;
    }

    /**
     * Get ETF instance
     */
    public NewETF getEtf() {
        return etf;
    }
}