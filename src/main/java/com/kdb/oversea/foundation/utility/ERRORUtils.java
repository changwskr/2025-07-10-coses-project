package com.kdb.oversea.foundation.utility;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

import java.util.*;
import com.kdb.oversea.eplatonframework.transfer.EPlatonEvent;

public class ERRORUtils
{
    private static ERRORUtils instance;
    private Map contextMap;

    /**
     * A private constructor
     *
     */
    private ERRORUtils()
    {
    }

    /**
     * <p>자기 자신의 Instance가 이미 생성되어 있는지 검사한 후, 생성 되어 있지 않으면</p>
     * <p>Instance를 생성한 후, 반환한다.</p>
     *
     * @return ERRORUtils 생성된 자기 자신의 Instance
     */
    public static synchronized ERRORUtils getInstance()
    {
        if (instance == null)
        {
            instance = new ERRORUtils();
        }
        return instance;
    }

    public EPlatonEvent ERRORadd(EPlatonEvent event,String errorcode,String message)
    {

      switch( event.getTPSVCINFODTO().getErrorcode().charAt(0) )
      {
        case 'I' :
          event.getTPSVCINFODTO().setErrorcode(errorcode);
          event.getTPSVCINFODTO().setError_message(message);
        case 'E' :
          errorcode = errorcode+"|"+event.getTPSVCINFODTO().getErrorcode();
          event.getTPSVCINFODTO().setErrorcode(errorcode);
          event.getTPSVCINFODTO().setError_message(message);
      }

      return event;
    }

}
