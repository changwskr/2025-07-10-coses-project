package com.ims.oversea.framework.transaction.txmonitor.business.facade.helper;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */
import com.chb.coses.reference.business.facade.ReferenceManagementSBHome;
import com.ims.oversea.framework.transaction.txmonitor.business.rule.SPtxmonitorRuleSBHome;

public class JNDINamesFacade
{
    // SPtxmonitor Rule
    public static final Class SPTXMONITOR_RULE_HOME = SPtxmonitorRuleSBHome.class;
    // Reference Facade
    public static final Class REFERENCE_MANAGEMENT_HOME = ReferenceManagementSBHome.class;
}