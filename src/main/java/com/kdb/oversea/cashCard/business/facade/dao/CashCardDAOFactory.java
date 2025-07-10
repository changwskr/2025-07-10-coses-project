package com.kdb.oversea.cashCard.business.facade.dao;

import com.chb.coses.framework.business.dao.*;


public class CashCardDAOFactory extends AbstractDAOFactory
{
    private static CashCardDAOFactory singleton = null;

    public CashCardDAOFactory()
    {
        super();
    }

    /*
     * LendingDAOFactory의 instance를 돌려준다
     */
    public static CashCardDAOFactory getInstance()
    {
        if(CashCardDAOFactory.singleton == null){
            CashCardDAOFactory.singleton = new CashCardDAOFactory();
        }
        return CashCardDAOFactory.singleton;
    }

    public IDAO getDAO()
    {
        switch(getDBMSType())
        {
            case DBMS_TYPE_ORACLE :
                return new CashCardDAO();
            /*case DBMS_TYPE_DB2 :
                return new XXXDB2DAO();
            case DBMS_TYPE_SYBASE :
                return new XXXSybaseDAO();*/
            default : throw new IllegalArgumentException();
        }
    }

    public CashCardDAO getCashCardDAO()
    {
        return (CashCardDAO)getDAO();
    }
}