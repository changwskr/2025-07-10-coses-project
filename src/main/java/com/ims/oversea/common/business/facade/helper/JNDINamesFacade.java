package com.ims.oversea.common.business.facade.helper;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

import com.ims.oversea.eplatonframework.business.rule.spcommoRule.SPcommoRuleSBHome;
import com.ims.oversea.common.business.facade.CommonManagementSBean;
import com.ims.oversea.common.business.rule.CommonRuleSBBean;

public class JNDINamesFacade
{
    // SPtxmonitor Rule
    public static final Class SPCOMMO_RULE_HOME = SPcommoRuleSBHome.class;
    public static final Class COMMO_RULE_SB_BEAN = CommonRuleSBBean.class;
    public static final String METHOD_GETHOSTCARD = "getHotCardInfo";

}