package com.ims.eplaton.eplatonFWK.business.dao;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

import com.chb.coses.framework.business.dao.IDAO;
import com.chb.coses.framework.business.dao.AbstractDAOFactory;

/**
 * posting DAO Factory을 나타낸다.
 *
 * @author  <a href="mailto:ghyu@imssystem.com">Gwanghyeok Yu</a>
 * @version 1.0, 2002/10/08
 */
public class EPlatonDelegateDAOFactory extends AbstractDAOFactory
{
    private static EPlatonDelegateDAOFactory daoFactory = new EPlatonDelegateDAOFactory();

    public static EPlatonDelegateDAOFactory getInstance()
    {
        return daoFactory;
    }

    public IDAO getDAO()
    {
        return getDelegateDAO(getDBMSType());
    }

    public EPlatonDelegateDAO getDelegateDAO(int type)
    {
        if (type == DBMS_TYPE_ORACLE) {
            return new EPlatonDelegateDAO();
        }
        String err = "'" + type + "' type is not supported";
        throw new IllegalArgumentException(err);
    }
}
