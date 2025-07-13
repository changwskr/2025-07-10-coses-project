package com.skcc.oversea.framework.transaction.dao;

import java.math.BigDecimal;
import com.skcc.oversea.framework.business.dao.IDAO;
import com.skcc.oversea.eplatonframework.transfer.EPlatonEvent;

/**
 * Transactin Contron Framework DAO(Data Access Object) business interface이다.
 *
 * @version 1.0, 2002/10/08
 */
public interface ITransactionControlDAO extends IDAO
{
    public boolean DB_INSERTinlog(EPlatonEvent logDDTO)
            throws DAOException;
    public boolean DB_INSERToutlog(EPlatonEvent logDDTO)
            throws DAOException;
}



