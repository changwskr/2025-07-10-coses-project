package com.chb.coses.foundation.base;

/**
 * Base helper class for common base operations
 */
public class BaseHelper {

    /**
     * Get application name
     * 
     * @return application name
     */
    public static String getApplicationName() {
        return "EPlaton Banking System";
    }

    /**
     * Get application version
     * 
     * @return application version
     */
    public static String getApplicationVersion() {
        return "1.0.0";
    }

    /**
     * Get application description
     * 
     * @return application description
     */
    public static String getApplicationDescription() {
        return "Banking system with legacy code migration";
    }

    /**
     * Get system information
     * 
     * @return system information
     */
    public static String getSystemInfo() {
        return System.getProperty("os.name") + " " + System.getProperty("os.version");
    }

    /**
     * Get Java version
     * 
     * @return Java version
     */
    public static String getJavaVersion() {
        return System.getProperty("java.version");
    }

    /**
     * Get Java vendor
     * 
     * @return Java vendor
     */
    public static String getJavaVendor() {
        return System.getProperty("java.vendor");
    }

    /**
     * Get user home directory
     * 
     * @return user home directory
     */
    public static String getUserHome() {
        return System.getProperty("user.home");
    }

    /**
     * Get current working directory
     * 
     * @return current working directory
     */
    public static String getCurrentDirectory() {
        return System.getProperty("user.dir");
    }

    /**
     * Get file separator
     * 
     * @return file separator
     */
    public static String getFileSeparator() {
        return System.getProperty("file.separator");
    }

    /**
     * Get path separator
     * 
     * @return path separator
     */
    public static String getPathSeparator() {
        return System.getProperty("path.separator");
    }

    /**
     * Get line separator
     * 
     * @return line separator
     */
    public static String getLineSeparator() {
        return System.getProperty("line.separator");
    }

    private BaseHelper() {
        // Utility class - prevent instantiation
    }
}