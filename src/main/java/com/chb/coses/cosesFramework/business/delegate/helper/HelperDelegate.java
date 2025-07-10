package com.chb.coses.cosesFramework.business.delegate.helper;

/**
 * Helper Delegate for Coses Framework
 */
public class HelperDelegate {

    private String helperName;
    private String helperType;

    public HelperDelegate() {
        this.helperName = "DefaultHelper";
        this.helperType = "DEFAULT";
    }

    public HelperDelegate(String helperName, String helperType) {
        this.helperName = helperName;
        this.helperType = helperType;
    }

    /**
     * Execute helper operation
     * 
     * @param operation  operation name
     * @param parameters operation parameters
     * @return operation result
     */
    public Object executeHelper(String operation, Object... parameters) {
        // Default implementation
        return null;
    }

    public String getHelperName() {
        return helperName;
    }

    public void setHelperName(String helperName) {
        this.helperName = helperName;
    }

    public String getHelperType() {
        return helperType;
    }

    public void setHelperType(String helperType) {
        this.helperType = helperType;
    }
}