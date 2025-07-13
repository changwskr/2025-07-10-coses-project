package com.skcc.oversea.eplatonframework.business.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import com.skcc.oversea.foundation.logej.LOGEJ;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Business Event Publisher for SKCC Oversea
 * 
 * Publishes business events to registered listeners.
 */
@Service
public class BusinessEventPublisher {

    private static final Logger logger = LoggerFactory.getLogger(BusinessEventPublisher.class);

    private final List<BusinessEventListener<?>> listeners = new CopyOnWriteArrayList<>();

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    /**
     * Register event listener
     */
    public void registerListener(BusinessEventListener<?> listener) {
        listeners.add(listener);
        logger.info("Registered business event listener: {} for event type: {}",
                listener.getClass().getSimpleName(), listener.getSupportedEventType());
    }

    /**
     * Unregister event listener
     */
    public void unregisterListener(BusinessEventListener<?> listener) {
        listeners.remove(listener);
        logger.info("Unregistered business event listener: {}", listener.getClass().getSimpleName());
    }

    /**
     * Publish business event
     */
    public void publishEvent(BusinessEvent event) {
        try {
            logger.debug("Publishing business event: {}", event);

            // Validate event
            if (!event.isValid()) {
                logger.warn("Invalid business event: {}", event);
                return;
            }

            // Publish to Spring ApplicationEventPublisher
            applicationEventPublisher.publishEvent(event);

            // Notify registered listeners
            notifyListeners(event);

            // Log event
            LOGEJ.logBusinessEvent("EVENT_PUBLISHED", event.getEventType(),
                    String.format("EventId: %s, Source: %s", event.getEventId(), event.getSource()));

            logger.info("Business event published successfully: {}", event.getEventId());

        } catch (Exception e) {
            logger.error("Error publishing business event: {}", e.getMessage(), e);
            LOGEJ.logError("EEVT001", "Failed to publish business event: " + e.getMessage(),
                    "BusinessEventPublisher", e);
        }
    }

    /**
     * Notify all registered listeners
     */
    @SuppressWarnings("unchecked")
    private void notifyListeners(BusinessEvent event) {
        for (BusinessEventListener<?> listener : listeners) {
            try {
                if (listener.canHandle(event)) {
                    // Cast and handle the event
                    ((BusinessEventListener<BusinessEvent>) listener).handleEvent(event);

                    logger.debug("Event handled by listener: {}", listener.getClass().getSimpleName());
                }
            } catch (Exception e) {
                logger.error("Error handling event with listener {}: {}",
                        listener.getClass().getSimpleName(), e.getMessage(), e);
            }
        }
    }

    /**
     * Get registered listeners count
     */
    public int getListenersCount() {
        return listeners.size();
    }

    /**
     * Get listeners for specific event type
     */
    public List<BusinessEventListener<?>> getListenersForEventType(String eventType) {
        return listeners.stream()
                .filter(listener -> listener.getSupportedEventType().equals(eventType))
                .sorted((l1, l2) -> Integer.compare(l1.getPriority(), l2.getPriority()))
                .toList();
    }
}