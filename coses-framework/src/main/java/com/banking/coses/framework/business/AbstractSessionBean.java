package com.banking.coses.framework.business;

import com.banking.coses.framework.exception.CosesAppException;
import com.banking.foundation.log.FoundationLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Abstract base class for Session Beans in the COSES Framework
 * 
 * Provides common functionality for session management, security,
 * transaction handling, and logging in Spring Boot environment.
 */
@Transactional
public abstract class AbstractSessionBean {

    @Autowired
    protected ApplicationContext applicationContext;

    protected final FoundationLogger logger = FoundationLogger.getLogger(getClass());

    protected String sessionId;
    protected LocalDateTime sessionStartTime;
    protected String currentUser;
    protected String currentRole;
    protected String clientIp;
    protected String userAgent;

    public AbstractSessionBean() {
        this.sessionId = UUID.randomUUID().toString();
        this.sessionStartTime = LocalDateTime.now();
        initializeSession();
    }

    /**
     * Initialize session information
     */
    protected void initializeSession() {
        try {
            // Get current user from Spring Security
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null && authentication.isAuthenticated()) {
                this.currentUser = authentication.getName();
                if (authentication.getAuthorities() != null && !authentication.getAuthorities().isEmpty()) {
                    this.currentRole = authentication.getAuthorities().iterator().next().getAuthority();
                }
            }

            // Get request information
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder
                    .getRequestAttributes();
            if (attributes != null) {
                HttpServletRequest request = attributes.getRequest();
                this.clientIp = getClientIpAddress(request);
                this.userAgent = request.getHeader("User-Agent");
            }

            logger.info("Session initialized - User: {}, Role: {}, IP: {}",
                    currentUser, currentRole, clientIp);

        } catch (Exception e) {
            logger.warn("Failed to initialize session: {}", e.getMessage());
        }
    }

    /**
     * Get session context information
     */
    public SessionContext getSessionContext() {
        return new SessionContext(sessionId, currentUser, currentRole, clientIp, userAgent, sessionStartTime);
    }

    /**
     * Check if session is valid
     */
    protected boolean isSessionValid() {
        return sessionId != null && currentUser != null;
    }

    /**
     * Get caller principal
     */
    protected String getCallerPrincipal() {
        return currentUser;
    }

    /**
     * Check if caller is in role
     */
    protected boolean isCallerInRole(String role) {
        return role != null && role.equals(currentRole);
    }

    /**
     * Get client IP address from request
     */
    protected String getClientIpAddress(HttpServletRequest request) {
        String xForwardedFor = request.getHeader("X-Forwarded-For");
        if (xForwardedFor != null && !xForwardedFor.isEmpty() && !"unknown".equalsIgnoreCase(xForwardedFor)) {
            return xForwardedFor.split(",")[0];
        }

        String xRealIp = request.getHeader("X-Real-IP");
        if (xRealIp != null && !xRealIp.isEmpty() && !"unknown".equalsIgnoreCase(xRealIp)) {
            return xRealIp;
        }

        return request.getRemoteAddr();
    }

    /**
     * Validate session before operation
     */
    protected void validateSession() throws CosesAppException {
        if (!isSessionValid()) {
            throw new CosesAppException("SESSION_INVALID", "Session is not valid");
        }
    }

    /**
     * Log session activity
     */
    protected void logSessionActivity(String activity, String details) {
        logger.info("Session Activity - User: {}, Activity: {}, Details: {}, SessionId: {}",
                currentUser, activity, details, sessionId);
    }

    /**
     * Get session information as string
     */
    public String getSessionInfo() {
        return String.format("Session[ID=%s, User=%s, Role=%s, IP=%s, StartTime=%s]",
                sessionId, currentUser, currentRole, clientIp, sessionStartTime);
    }

    /**
     * Session context inner class
     */
    public static class SessionContext {
        private final String sessionId;
        private final String user;
        private final String role;
        private final String clientIp;
        private final String userAgent;
        private final LocalDateTime startTime;

        public SessionContext(String sessionId, String user, String role, String clientIp, String userAgent,
                LocalDateTime startTime) {
            this.sessionId = sessionId;
            this.user = user;
            this.role = role;
            this.clientIp = clientIp;
            this.userAgent = userAgent;
            this.startTime = startTime;
        }

        public String getSessionId() {
            return sessionId;
        }

        public String getUser() {
            return user;
        }

        public String getRole() {
            return role;
        }

        public String getClientIp() {
            return clientIp;
        }

        public String getUserAgent() {
            return userAgent;
        }

        public LocalDateTime getStartTime() {
            return startTime;
        }

        @Override
        public String toString() {
            return "SessionContext{" +
                    "sessionId='" + sessionId + '\'' +
                    ", user='" + user + '\'' +
                    ", role='" + role + '\'' +
                    ", clientIp='" + clientIp + '\'' +
                    ", startTime=" + startTime +
                    '}';
        }
    }
}