package com.chb.coses.common.transfer;

import com.chb.coses.framework.transfer.DTO;

/**
 * System Parameter DTO for managing system parameters
 */
public class SystemParameterCDTO extends DTO {

    private String parameterCode;
    private String parameterName;
    private String parameterValue;
    private String parameterType;
    private String description;
    private String category;
    private boolean editable;
    private boolean visible;
    private String defaultValue;
    private String validationRule;

    public SystemParameterCDTO() {
        super();
    }

    public SystemParameterCDTO(String parameterCode, String parameterValue) {
        super();
        this.parameterCode = parameterCode;
        this.parameterValue = parameterValue;
    }

    public String getParameterCode() {
        return parameterCode;
    }

    public void setParameterCode(String parameterCode) {
        this.parameterCode = parameterCode;
    }

    public String getParameterName() {
        return parameterName;
    }

    public void setParameterName(String parameterName) {
        this.parameterName = parameterName;
    }

    public String getParameterValue() {
        return parameterValue;
    }

    public void setParameterValue(String parameterValue) {
        this.parameterValue = parameterValue;
    }

    public String getParameterType() {
        return parameterType;
    }

    public void setParameterType(String parameterType) {
        this.parameterType = parameterType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public String getValidationRule() {
        return validationRule;
    }

    public void setValidationRule(String validationRule) {
        this.validationRule = validationRule;
    }

    /**
     * Get parameter value as integer
     * 
     * @param defaultValue default value if conversion fails
     * @return integer value
     */
    public int getIntValue(int defaultValue) {
        try {
            return Integer.parseInt(parameterValue);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    /**
     * Get parameter value as long
     * 
     * @param defaultValue default value if conversion fails
     * @return long value
     */
    public long getLongValue(long defaultValue) {
        try {
            return Long.parseLong(parameterValue);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    /**
     * Get parameter value as boolean
     * 
     * @param defaultValue default value if conversion fails
     * @return boolean value
     */
    public boolean getBooleanValue(boolean defaultValue) {
        if (parameterValue == null) {
            return defaultValue;
        }
        return Boolean.parseBoolean(parameterValue);
    }

    /**
     * Check if parameter is numeric
     * 
     * @return true if numeric, false otherwise
     */
    public boolean isNumeric() {
        if (parameterValue == null) {
            return false;
        }
        try {
            Double.parseDouble(parameterValue);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}