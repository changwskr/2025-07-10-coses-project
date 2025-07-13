package com.skcc.oversea.framework.transaction.dao;

import com.skcc.oversea.framework.business.dao.IDAO;
import com.skcc.oversea.framework.business.dao.AbstractDAOFactory;

/**
 * =============================================================================
 * 프로그램 설명:
 * =============================================================================
 *
 *
 * =============================================================================
 * 변경내역 정보:
 * =============================================================================
 *  2004년 03월 16일 1차버전 release
 *
 *
 * =============================================================================
 *                                                        @author : 장우승(WooSungJang)
 *                                                        @company: IMS SYSTEM
 *                                                        @email  : changwskr@yahoo.co.kr
 *                                                        @version 1.0
 *  =============================================================================
 */

public class TransactionControlDAOFactory extends AbstractDAOFactory
{
    private static TransactionControlDAOFactory daoFactory = new TransactionControlDAOFactory();

    public static TransactionControlDAOFactory getInstance()
    {
        return daoFactory;
    }

    public IDAO getDAO()
    {
        return getDelegateDAO(getDBMSType());
    }

    public TransactionControlDAO getDelegateDAO(int type)
    {
        if (type == DBMS_TYPE_ORACLE) {
            return new TransactionControlDAO();
        }
        String err = "'" + type + "' type is not supported";
        throw new IllegalArgumentException(err);
    }
}



