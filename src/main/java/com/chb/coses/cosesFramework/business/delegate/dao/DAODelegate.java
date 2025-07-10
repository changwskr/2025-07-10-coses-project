package com.chb.coses.cosesFramework.business.delegate.dao;

/**
 * DAO Delegate for Coses Framework
 */
public class DAODelegate {

    private String daoName;
    private String dataSource;

    public DAODelegate() {
        this.daoName = "DefaultDAO";
        this.dataSource = "default";
    }

    public DAODelegate(String daoName, String dataSource) {
        this.daoName = daoName;
        this.dataSource = dataSource;
    }

    /**
     * Execute database operation
     * 
     * @param operation  operation name
     * @param parameters operation parameters
     * @return operation result
     */
    public Object executeOperation(String operation, Object... parameters) {
        // Default implementation
        return null;
    }

    public String getDaoName() {
        return daoName;
    }

    public void setDaoName(String daoName) {
        this.daoName = daoName;
    }

    public String getDataSource() {
        return dataSource;
    }

    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
    }
}