package com.ims.eplaton.framework.tcf;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

import java.rmi.*;
import java.sql.*;
import java.net.*;
import java.text.*;
import java.util.*;

import javax.ejb.*;
import javax.naming.*;

import com.chb.coses.foundation.log.*;
import com.chb.coses.foundation.base.*;
import com.chb.coses.foundation.config.*;
import com.chb.coses.foundation.jndi.*;
import com.chb.coses.foundation.utility.*;

import com.chb.coses.framework.business.*;
import com.chb.coses.framework.business.delegate.IBizDelegate;
import com.chb.coses.framework.transfer.*;
import com.chb.coses.framework.exception.*;
import com.chb.coses.framework.constants.*;

import com.chb.coses.common.business.constants.*;
import com.chb.coses.common.business.facade.*;
import com.chb.coses.common.transfer.*;
import com.chb.coses.user.business.facade.*;
import com.chb.coses.user.transfer.UserCDTO;

import com.chb.coses.reference.business.facade.*;
import com.chb.coses.cosesFramework.action.ActionManager;

import com.ims.eplaton.eplatonFWK.transfer.*;
import com.ims.eplaton.foundation.helper.TPMSVCAPI;
import com.ims.eplaton.foundation.helper.CommonUtil;
import com.ims.eplaton.eplatonFWK.business.operation.IBizOperation;

public class TCF_test extends AbstractTCF implements ITCF_test
{

  public IEvent STF(IEvent event) throws BizDelegateException
  {
    EPlatonEvent eevent = null;
    EPlatonCommonDTO commonDTO = null;
    TPSVCINFODTO tpsvcinfo = null;

    String bankCode = null;
    String branchCode = null;
    String channelType = null;
    String businessDate = null;
    String eventNo = null;
    String transactionNo = null;
    String baseCurrency = null;

    ICommonManagementSB commonManagementSB = null;
    IReferenceManagementSB referenceManagementSB = null;

    try
    {

      /*************************************************************************
       * 기본정보를 가져온다.
       ************************************************************************/
      Log.DelegateLogger.debug("[STF] STF START ==============================");

      Log.DelegateLogger.debug("[STF] 기본정보를 셋팅===========");
      eevent = (EPlatonEvent)event;
      commonDTO = (EPlatonCommonDTO)eevent.getCommon();
      tpsvcinfo = eevent.getTPSVCINFODTO();
      bankCode = commonDTO.getBankCode();
      branchCode = commonDTO.getBranchCode();
      channelType = commonDTO.getChannelType();
      businessDate = commonDTO.getBusinessDate();
      eventNo = commonDTO.getEventNo();

/*
      commonManagementSB = EJBUtils.getCommonEJB();
      referenceManagementSB = EJBUtils.getReferenceEJB();
*/
      /*************************************************************************
       * 시스탬날짜을 설정한다.
       ************************************************************************/
      Log.DelegateLogger.debug("[STF] 시스템일자 셋팅===========");
      TimeProcess timeProcess = TimeProcess.getInstance();
      String systemDateAndTime = timeProcess.getSystemDate(commonDTO.getTimeZone());
      commonDTO.setSystemDate(systemDateAndTime.substring(0, 8));
      /*************************************************************************
       * 시스탬시간을 설정한다.
       ************************************************************************/
      Log.DelegateLogger.debug("[STF] 시스템 트랜잭션 시작시간 셋팅===");
      commonDTO.setSystemInTime(systemDateAndTime.substring(8));
      /*************************************************************************
       * 영업일자을 설정한다.
       ************************************************************************/
      Log.DelegateLogger.debug("[STF] 영업일자 셋팅===========");
      businessDate = commonManagementSB.getBusinessDate(commonDTO.getBankCode());
      commonDTO.setBusinessDate(businessDate);
      /*************************************************************************
       * 클라이언트에서 요청한 트랜잭션 정보를 구한다
       ************************************************************************/
      Log.DelegateLogger.debug("[STF] 트랜잭션 정보를 셋팅===========");
      //int offset; if( TPMSVCAPI.getInstance().TPinfo() ) offset = 1; else offset = 0;
      int offset=0;
      switch (offset)
      {
        case 1 :
          /*********************************************************************
           * 이미 트랜잭션을 시작한 상태이므로 트랜잭션을 시작하지 않고 현재의 트랜잭션
           * 을 유지해 준다
           ********************************************************************/
          break;
        case 0 :
          /*********************************************************************
           * 클라이언트 화면에서 올라온 Transaction Timer (seconds) 값을 가지고
           * UserTransaction을 시작한다.
           ********************************************************************/
          TPMSVCAPI.getInstance().TPbegin(tpsvcinfo.getTx_timer());
          break;
      }
      /*************************************************************************
       * SEQUENCE를 채번한후 거래별 트랜잭션 번호를 셋팅한다.
       ************************************************************************/
      transactionNo = referenceManagementSB.getTransactionNumber(commonDTO.getBankCode(), businessDate);
      commonDTO.setTransactionNo(transactionNo);
      /*************************************************************************
       * 마감전후 구분 필드를 세운다.
       ************************************************************************/

      /*************************************************************************
       * 자동화기기 마감전후를 구분하여 마감구분 필드를 세운다.
       * 토요일 : 13시 * 평  일 : 16시30
       * 향후 이 로직을 위한 추가 로직을 구성한다.
       ***********************************************************************/

      /*************************************************************************
       * 거래제어정보를 관리한다
       * 1. 채널타입별 관리
       * 2. 거래를 제어하기 위한 관리
       * 3. 클라이언트에서의 타임아웃관리
       *    - 만약 현재 트랜잭이 클라이언트에서 셋팅된 시간의 2초보단 작으면 리턴
       * 4. TPMSVCINFO 정보를 셋팅한다.
       *    call_service_name;
       *    call_tpm_in_time;
       *    call_tpm_out_time;
       *    call_tpme_interval;
       *    call_tpm_stf_in_time;
       *    call_tpm_stf_out_time;
       *    call_tpm_etf_in_time;
       *    call_tpm_etf_out_time;
       *    call_tpme_service_interval;
       *    error_code;
       *    call_hostseq;
       *    call_orgseq;
       *   call_location;       *
       ************************************************************************/

      /*************************************************************************
       * 클라이언트의 위치정보를 관리한다.
       * TPFQ :
       * 200 - 온라인 클라이언트
       * 100 - 온라인 서버에서 호출
       * 300 - 대외기관에서 호출
       ************************************************************************/

      /*************************************************************************
       * Base Currency
       ************************************************************************/
      baseCurrency = commonManagementSB.getBaseCurrency(commonDTO.getBankCode());
       commonDTO.setBaseCurrency(baseCurrency);
       /*************************************************************************
        * 공통정보 로깅작을 실시한다.
        ************************************************************************/

       /*************************************************************************
        * 동적 API를 구성한다.
        ************************************************************************/
       stfActive01(eevent);

    }
    catch (Throwable _e)
    {
    }
    return event;
  }

  public IEvent ROUTE(IEvent event) throws BizDelegateException
  {
    EPlatonEvent eevent = null;
    EPlatonCommonDTO commonDTO = null;
    TPSVCINFODTO tpsvcinfo = null;
    EPlatonEvent resevent = null;

    try
    {
      /*************************************************************************
       * 기본정보를 가져온다.
       ************************************************************************/
      Log.DelegateLogger.debug("[ROUTE] ROUTE START ==============================");
      eevent = (EPlatonEvent)event;
      commonDTO = (EPlatonCommonDTO)eevent.getCommon();
      tpsvcinfo = eevent.getTPSVCINFODTO();

      /*************************************************************************
       * operation_name 는 기존시스의 Facade 단의 세션빈의 operation 이름을
       * 말한다 (예, listCashCardNumber.)
       ************************************************************************/

      String operationName = tpsvcinfo.getOperation_name();

      IBizOperation operation = (IBizOperation)(Class.forName(operationName).newInstance());
      resevent = operation.act(eevent);

      /*************************************************************************
       * 동적 API를 구성한다.
       ************************************************************************/
      routeActive01(eevent);

    }
    catch (Throwable _e)
    {
    }
    return resevent;
  }

  public IEvent ETF(IEvent event) throws BizDelegateException
  {

    EPlatonEvent eevent = null;
    EPlatonCommonDTO commonDTO = null;
    TPSVCINFODTO tpsvcinfo = null;

    try
    {

      /*************************************************************************
       * 기본정보를 가져온다.
       ************************************************************************/
      Log.DelegateLogger.debug("[ETF] ETF START ==============================");

      Log.DelegateLogger.debug("[ETF] 기본정보를 셋팅===========");
      eevent = (EPlatonEvent)event;
      commonDTO = (EPlatonCommonDTO)eevent.getCommon();
      tpsvcinfo = (TPSVCINFODTO)eevent.getTPSVCINFODTO();

      /***************************************************************************
       * EJB - WEB 단과의 트랜잭션 TIMEOUT을 관리한다
       **************************************************************************/

      /***************************************************************************
       * 트랜잭션 OUT TIME 셋팅한다.
       **************************************************************************/

      /***************************************************************************
       * 트랜잭션 OUTLOGGING 작업을 실시한다
       **************************************************************************/

      /*************************************************************************
       * 거래제어정보를 관리한다
       * 1. 채널타입별 관리
       * 2. 거래를 제어하기 위한 관리
       * 3. 클라이언트에서의 타임아웃관리
       *    - 만약 현재 트랜잭이 클라이언트에서 셋팅된 시간의 2초보단 작으면 리턴
       * 4. TPMSVCINFO 정보를 셋팅한다.
       *    call_service_name;
       *    call_tpm_in_time;
       *    call_tpm_out_time;
       *    call_tpme_interval;
       *    call_tpm_stf_in_time;
       *    call_tpm_stf_out_time;
       *    call_tpm_etf_in_time;
       *    call_tpm_etf_out_time;
       *    call_tpme_service_interval;
       *    error_code;
       *    call_hostseq;
       *    call_orgseq;
       *   call_location;
       ************************************************************************/

      /***************************************************************************
       * 트랜잭션에 대한 Commit / Rollback 을 관리한다
       **************************************************************************/
      switch( tpsvcinfo.getErrorcode().charAt(0) )
      {
        case 'E' :
          //TPMSVCAPI.getInstance().TProllback();
          break;
        case 'S' :
          //TPMSVCAPI.getInstance().TPcommit();
          break;
      }

      /*************************************************************************
       * 동적 API를 구성한다.
       ************************************************************************/
      etfActive01(eevent);

    }
    catch (Throwable _e)
    {
    }
    return event;
  }

}
