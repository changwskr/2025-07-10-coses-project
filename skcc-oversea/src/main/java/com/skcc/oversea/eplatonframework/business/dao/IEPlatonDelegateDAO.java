package com.skcc.oversea.eplatonframework.business.dao;


import com.skcc.oversea.framework.business.dao.IDAO;
import com.skcc.oversea.eplatonframework.transfer.EPlatonEvent;

/**
 * Delegate DAO(Data Access Object) business interface이다.
 *
 * @author  <a href="mailto:ghyu@imssystem.com">Gwanghyeok Yu</a>
 * @version 1.0, 2002/10/08
 */
public interface IEPlatonDelegateDAO extends IDAO
{
    public boolean DB_INSERTinlog(EPlatonEvent logDDTO)
            throws DAOException;
    public boolean DB_INSERToutlog(EPlatonEvent logDDTO)
            throws DAOException;
}


