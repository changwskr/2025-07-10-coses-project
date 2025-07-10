package com.chb.coses.eplatonFramework.business.tcf;

/**
 * Test TCF class for legacy compatibility
 */
public class TCF_test {

    private String testName;
    private String testDescription;

    public TCF_test() {
        this.testName = "Default Test";
        this.testDescription = "Default test description";
    }

    public TCF_test(String testName, String testDescription) {
        this.testName = testName;
        this.testDescription = testDescription;
    }

    /**
     * Get test name
     * 
     * @return test name
     */
    public String getTestName() {
        return testName;
    }

    /**
     * Set test name
     * 
     * @param testName test name
     */
    public void setTestName(String testName) {
        this.testName = testName;
    }

    /**
     * Get test description
     * 
     * @return test description
     */
    public String getTestDescription() {
        return testDescription;
    }

    /**
     * Set test description
     * 
     * @param testDescription test description
     */
    public void setTestDescription(String testDescription) {
        this.testDescription = testDescription;
    }

    /**
     * Execute test
     * 
     * @return test result
     */
    public boolean executeTest() {
        // Default implementation - always returns true
        return true;
    }
}