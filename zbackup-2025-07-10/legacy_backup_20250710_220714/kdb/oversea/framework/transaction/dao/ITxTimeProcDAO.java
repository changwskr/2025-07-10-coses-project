package com.kdb.oversea.framework.transaction.dao;


import java.math.BigDecimal;
import com.chb.coses.framework.business.dao.IDAO;
import com.kdb.oversea.eplatonframework.transfer.EPlatonEvent;

/**
 * Delegate DAO(Data Access Object) business interface이다.
 *
 * @author  <a href="mailto:ghyu@imssystem.com">Gwanghyeok Yu</a>
 * @version 1.0, 2002/10/08
 */
public interface ITxTimeProcDAO extends IDAO
{
    public boolean TRANSACTION_INFO(EPlatonEvent logDDTO)
            throws DAOException;
}

