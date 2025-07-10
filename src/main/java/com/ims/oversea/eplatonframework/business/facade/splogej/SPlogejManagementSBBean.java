package com.ims.oversea.eplatonframework.business.facade.splogej;

import java.io.*;
import java.rmi.*;
import java.sql.*;
import java.net.*;
import java.text.*;
import java.util.*;
import javax.naming.*;
import javax.ejb.*;
import javax.transaction.*;
import java.math.BigDecimal;
import javax.rmi.PortableRemoteObject;
// import weblogic.transaction.TxHelper; // WebLogic specific - commented out

import com.chb.coses.foundation.log.*;
import com.chb.coses.foundation.base.*;
import com.chb.coses.foundation.config.*;
import com.chb.coses.foundation.jndi.*;
import com.chb.coses.foundation.utility.*;
import com.chb.coses.foundation.db.DBService;

import com.ims.oversea.framework.transfer.*;
import com.ims.oversea.foundation.config.Config;
import com.ims.oversea.framework.transaction.constant.TCFConstants;
import com.ims.oversea.framework.transaction.constant.TCFConstantErrcode;
import com.ims.oversea.eplatonframework.transfer.*;
import com.ims.oversea.eplatonframework.business.delegate.action.*;
import com.ims.oversea.foundation.utility.*;
import com.ims.oversea.foundation.logej.LOGEJ;
import com.ims.oversea.framework.transaction.tcf.STF;
import com.ims.oversea.framework.transaction.model.TransactionLogDDTO;
import com.ims.oversea.framework.transaction.dao.*;

import com.ims.oversea.eplatonframework.transfer.EPlatonEvent;
import com.ims.oversea.eplatonframework.transfer.*;
import com.ims.oversea.eplatonframework.transfer.EPlatonCommonDTO;
import com.ims.oversea.framework.transaction.tcf.*;
import com.ims.oversea.eplatonframework.business.facade.cashCard.ICashCardManagementSB;
import com.ims.oversea.foundation.utility.*;
import com.ims.oversea.foundation.logej.*;

/**
 * =============================================================================
 * 프로그램 설명:
 * =============================================================================
 * 실제 업무 시스템의 facade단이 정의되는 부분이다.
 * 현재 이 각 업무시스템의 facade단을 직접가지고 감으로서 모든 시스템을 하나로 로직을 관리
 * 하고 트랜잭션을 일률적으로 관리하기 위한 로직이다.
 * 실제 호출되는 세션빈에 대한 메소드는 execute()이다.
 * 이 메소드내에서 각 업무시스템과의 연계를 위한 하나의 메소드가 정의 된다.
 *
 * =============================================================================
 * 변경내역 정보:
 * =============================================================================
 * 2004년 03월 16일 1차버전 release
 *
 *
 * =============================================================================
 * 
 * @author : 장우승(WooSungJang)
 * @company: IMS SYSTEM
 * @email : changwskr@yahoo.co.kr
 * @version 1.0
 *          =============================================================================
 */

public class SPlogejManagementSBBean extends com.chb.coses.framework.business.AbstractSessionBean
    implements SessionBean {
  SessionContext sessionContext;

  public void ejbCreate() throws CreateException {
    /** @todo Complete this method */
  }

  public com.ims.oversea.eplatonframework.transfer.EPlatonEvent execute(EPlatonEvent event, char type) {

    TransactionControlDAO dao = new TransactionControlDAO();

    try {
      /**
       * 트랜잭션의 정보를 주요정보만 데이타베이스에 남긴다.
       * 최초의 200인 클라이언트의 경우 not support인 biz delegate을 타므로서
       * 무조건 최초의 패킷은 저장된다.
       * 하지만 100인 서버끼리의 연동은 required이므로 정상일경우만 처리된다.
       * 그러나 파일정보는 남을 것이다.
       */
      switch (type) {
        case 'i':
          if (!dao.DB_INSERT_tpminlog(event)) {
            LOGEJ.getInstance().printf(TCFConstants.GENERAL_LOG_LEVEL, event, "TPSDBrecv() 에러");
          } else {
            LOGEJ.getInstance().printf(TCFConstants.GENERAL_LOG_LEVEL, event, "TPSDBrecv() 성공");
          }

          break;
        case 'o':
          if (!dao.DB_INSERTtpmoutlog(event, event.getTPSVCINFODTO().getLdumy01())) {
            LOGEJ.getInstance().printf(TCFConstants.GENERAL_LOG_LEVEL, event, "TPSDBsend() 에러");
          } else {
            LOGEJ.getInstance().printf(TCFConstants.GENERAL_LOG_LEVEL, event, "TPSDBsend() 성공");
          }
          break;
      }

    } catch (Exception ex) {
      LOGEJ.getInstance().eprintf(TCFConstants.FATAL_LOG_LEVEL, (EPlatonEvent) event, ex);
      ex.printStackTrace();
    }
    return event;
  }
}