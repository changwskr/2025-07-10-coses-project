package com.ims.eplaton.eplatonFWK.business.dao;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

import java.math.BigDecimal;
import com.chb.coses.framework.business.dao.IDAO;
import com.ims.eplaton.eplatonFWK.transfer.EPlatonEvent;

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

