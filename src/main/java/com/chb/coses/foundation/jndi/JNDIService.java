package com.chb.coses.foundation.jndi;

import javax.naming.Context;
import javax.naming.NamingException;
import java.util.Hashtable;

/**
 * JNDI Service class for JNDI operations
 */
public class JNDIService {

    private static Context context;
    private static final String DEFAULT_INITIAL_CONTEXT_FACTORY = "weblogic.jndi.WLInitialContextFactory";
    private static final String DEFAULT_PROVIDER_URL = "t3://localhost:7001";

    /**
     * Initialize JNDI context
     * 
     * @throws NamingException if initialization fails
     */
    public static void initialize() throws NamingException {
        if (context == null) {
            Hashtable<String, String> env = new Hashtable<>();
            env.put(Context.INITIAL_CONTEXT_FACTORY, DEFAULT_INITIAL_CONTEXT_FACTORY);
            env.put(Context.PROVIDER_URL, DEFAULT_PROVIDER_URL);
            context = new javax.naming.InitialContext(env);
        }
    }

    /**
     * Initialize JNDI context with custom settings
     * 
     * @param initialContextFactory initial context factory
     * @param providerUrl           provider URL
     * @throws NamingException if initialization fails
     */
    public static void initialize(String initialContextFactory, String providerUrl) throws NamingException {
        if (context == null) {
            Hashtable<String, String> env = new Hashtable<>();
            env.put(Context.INITIAL_CONTEXT_FACTORY, initialContextFactory);
            env.put(Context.PROVIDER_URL, providerUrl);
            context = new javax.naming.InitialContext(env);
        }
    }

    /**
     * Get JNDI context
     * 
     * @return JNDI context
     * @throws NamingException if context is not initialized
     */
    public static Context getContext() throws NamingException {
        if (context == null) {
            initialize();
        }
        return context;
    }

    /**
     * Lookup object by name
     * 
     * @param name JNDI name
     * @return looked up object
     * @throws NamingException if lookup fails
     */
    public static Object lookup(String name) throws NamingException {
        return getContext().lookup(name);
    }

    /**
     * Lookup object by name with type cast
     * 
     * @param name  JNDI name
     * @param clazz expected class
     * @param <T>   type
     * @return looked up object
     * @throws NamingException if lookup fails
     */
    @SuppressWarnings("unchecked")
    public static <T> T lookup(String name, Class<T> clazz) throws NamingException {
        Object obj = lookup(name);
        if (clazz.isInstance(obj)) {
            return (T) obj;
        }
        throw new NamingException("Object is not of expected type: " + clazz.getName());
    }

    /**
     * Bind object to JNDI name
     * 
     * @param name JNDI name
     * @param obj  object to bind
     * @throws NamingException if bind fails
     */
    public static void bind(String name, Object obj) throws NamingException {
        getContext().bind(name, obj);
    }

    /**
     * Rebind object to JNDI name
     * 
     * @param name JNDI name
     * @param obj  object to rebind
     * @throws NamingException if rebind fails
     */
    public static void rebind(String name, Object obj) throws NamingException {
        getContext().rebind(name, obj);
    }

    /**
     * Unbind object from JNDI name
     * 
     * @param name JNDI name
     * @throws NamingException if unbind fails
     */
    public static void unbind(String name) throws NamingException {
        getContext().unbind(name);
    }

    /**
     * Close JNDI context
     */
    public static void close() {
        if (context != null) {
            try {
                context.close();
            } catch (NamingException e) {
                System.err.println("Error closing JNDI context: " + e.getMessage());
            } finally {
                context = null;
            }
        }
    }

    /**
     * Get singleton instance
     * 
     * @return JNDIService instance
     */
    public static JNDIService getInstance() {
        return new JNDIService();
    }

    private JNDIService() {
        // Utility class - prevent instantiation
    }
}