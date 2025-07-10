package com.ims.oversea.common.business.rule;

import com.ims.oversea.framework.transaction.bean.AbstractBIZBCF;
import com.chb.coses.framework.transfer.*;
import com.chb.coses.cosesFramework.exception.CosesAppException;
import com.ims.oversea.common.business.thing.model.EPLcommonDDTO;
import com.ims.oversea.foundation.logej.LOGEJ;
import com.ims.oversea.common.business.rule.helper.*;
import com.ims.oversea.eplatonframework.business.thing.spcommo.*;

public class CommonRuleSBBean extends AbstractBIZBCF
{
  public IDTO getHotCardInfo(IDTO hotCardDDTO) throws CosesAppException
  {
    printf(1,"CommonRuleSBBean call start =====================");

    try{

      ////////////////////////////////////////////////////////////////////////////////////
      //1.dtoconverter
      EPLcommonDDTO ddto = (EPLcommonDDTO)hotCardDDTO;

      //2.rule
      SPcommoSB  remote = EJBUtilRule.getISPcommoSB();
      ddto = (EPLcommonDDTO)remote.execute(JNDINamesRule.COMMO_SB_BEAN.getName(),
                                           JNDINamesRule.COMMO_SB_BEAN_CALLMETHOD01,
                                           (IDTO)ddto,
                                           getEvent());

      ////////////////////////////////////////////////////////////////////////////////////
    }
    catch(Exception ex){
      ex.printStackTrace();
    }

    printf(1,"CommonRuleSBBean call end =======================");


    return (IDTO)hotCardDDTO;
  }

  public IDTO setHotCardInfo(IDTO hotCardDDTO) throws CosesAppException
  {
    printf(1,"CommonRuleSBBean call start =====================");

    try{

      ////////////////////////////////////////////////////////////////////////////////////
      //1.dtoconverter
      EPLcommonDDTO ddto = (EPLcommonDDTO)hotCardDDTO;

      //2.rule
      SPcommoSB  remote = EJBUtilRule.getISPcommoSB();
      ddto = (EPLcommonDDTO)remote.execute(JNDINamesRule.COMMO_SB_BEAN.getName(),
                                           JNDINamesRule.COMMO_SB_BEAN_CALLMETHOD01,
                                           (IDTO)ddto,
                                           getEvent());

      ////////////////////////////////////////////////////////////////////////////////////
    }
    catch(Exception ex){
      ex.printStackTrace();
    }

    printf(1,"CommonRuleSBBean call end =======================");


    return (IDTO)hotCardDDTO;
  }

}