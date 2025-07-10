package com.kdb.oversea.framework.transaction.helper;

import com.kdb.oversea.eplatonframework.transfer.*;


/**
 * =============================================================================
 * 프로그램 설명:
 * =============================================================================
 *
 *
 * =============================================================================
 * 변경내역 정보:
 * =============================================================================
 *  2004년 03월 16일 1차버전 release
 *
 *
 * =============================================================================
 *                                                        @author : 장우승(WooSungJang)
 *                                                        @company: IMS SYSTEM
 *                                                        @email  : changwskr@yahoo.co.kr
 *                                                        @version 1.0
 *  =============================================================================
 */
public class Convert {

  public static EPlatonEvent createEPlatonEvent(EPlatonEvent pevent)
  {
    EPlatonEvent transfer_event_object = new EPlatonEvent();
    EPlatonCommonDTO transfer_commonDTO = transfer_event_object.getCommon();
    TPSVCINFODTO transfer_tpsvcinfoDTO = transfer_event_object.getTPSVCINFODTO();

    EPlatonCommonDTO commonDTO = pevent.getCommon();
    TPSVCINFODTO tpsvcinfoDTO = pevent.getTPSVCINFODTO();

    transfer_commonDTO.setBaseCurrency(commonDTO.getBaseCurrency());
    transfer_commonDTO.setBankCode(commonDTO.getBankCode());
    transfer_commonDTO.setBranchCode(commonDTO.getBranchCode());
    transfer_commonDTO.setBusinessDate(commonDTO.getBusinessDate());
    transfer_commonDTO.setChannelType(commonDTO.getChannelType());
    transfer_commonDTO.setEventNo(commonDTO.getEventNo());
    transfer_commonDTO.setFxRateCount(commonDTO.getFxRateCount());
    transfer_commonDTO.setGlPostBranchCode(commonDTO.getGlPostBranchCode());
    transfer_commonDTO.setNation(commonDTO.getNation());
    transfer_commonDTO.setRegionCode(commonDTO.getRegionCode());
    transfer_commonDTO.setReqName(commonDTO.getReqName());
    transfer_commonDTO.setTerminalID(commonDTO.getTerminalID());
    transfer_commonDTO.setTerminalType(commonDTO.getTerminalType());
    transfer_commonDTO.setTimeZone(commonDTO.getTimeZone());
    transfer_commonDTO.setSystemDate(commonDTO.getSystemDate());
    transfer_commonDTO.setSystemInTime(commonDTO.getSystemInTime());
    transfer_commonDTO.setTransactionNo(commonDTO.getTransactionNo());
    transfer_commonDTO.setSystemOutTime(commonDTO.getSystemOutTime());
    transfer_commonDTO.setUserID(commonDTO.getUserID());
    transfer_commonDTO.setXmlSeq(commonDTO.getXmlSeq());
    transfer_commonDTO.setUserLevel(commonDTO.getUserLevel());
    transfer_commonDTO.setMultiPL(commonDTO.getMultiPL());
    transfer_commonDTO.setgetIPAddress(commonDTO.getIPAddress());

    transfer_tpsvcinfoDTO.setWeb_timeout(tpsvcinfoDTO.getWeb_timeout());
    transfer_tpsvcinfoDTO.setWeb_outtime(tpsvcinfoDTO.getWeb_outtime());
    transfer_tpsvcinfoDTO.setWeb_intime(tpsvcinfoDTO.getWeb_intime());
    transfer_tpsvcinfoDTO.setTx_timer(tpsvcinfoDTO.getTx_timer());
    transfer_tpsvcinfoDTO.setTrclass(tpsvcinfoDTO.getTrclass());
    transfer_tpsvcinfoDTO.setTpmsvcinfolist(tpsvcinfoDTO.getTpmsvcinfolist());
    transfer_tpsvcinfoDTO.setTpfq(tpsvcinfoDTO.getTpfq());
    transfer_tpsvcinfoDTO.setSystemOutTime(tpsvcinfoDTO.getSystemOutTime());
    transfer_tpsvcinfoDTO.setSystemInTime(tpsvcinfoDTO.getSystemInTime());
    transfer_tpsvcinfoDTO.setSystem_name(tpsvcinfoDTO.getSystem_name());
    transfer_tpsvcinfoDTO.setSystem_date(tpsvcinfoDTO.getSystem_date());
    transfer_tpsvcinfoDTO.setOrgseq(tpsvcinfoDTO.getOrgseq());
    transfer_tpsvcinfoDTO.setOperation_name(tpsvcinfoDTO.getOperation_name());
    transfer_tpsvcinfoDTO.setHostseq(tpsvcinfoDTO.getHostseq());
    transfer_tpsvcinfoDTO.setErrorcode(tpsvcinfoDTO.getErrorcode());
    transfer_tpsvcinfoDTO.setError_message(tpsvcinfoDTO.getError_message());
    transfer_tpsvcinfoDTO.setCdto_name(tpsvcinfoDTO.getCdto_name());
    transfer_tpsvcinfoDTO.setAction_name(tpsvcinfoDTO.getAction_name());
    transfer_tpsvcinfoDTO.setLogic_level(tpsvcinfoDTO.getLogic_level());
    transfer_tpsvcinfoDTO.setSTF_outtime(tpsvcinfoDTO.getSTF_outtime());
    transfer_tpsvcinfoDTO.setSTF_intime(tpsvcinfoDTO.getSTF_intime());
    transfer_tpsvcinfoDTO.setETF_outtime(tpsvcinfoDTO.getETF_outtime());
    transfer_tpsvcinfoDTO.setETF_intime(tpsvcinfoDTO.getETF_intime());
    transfer_tpsvcinfoDTO.setBTF_outtime(tpsvcinfoDTO.getBTF_outtime());
    transfer_tpsvcinfoDTO.setBTF_intime(tpsvcinfoDTO.getBTF_intime());
    transfer_tpsvcinfoDTO.setOperation_method(tpsvcinfoDTO.getOperation_method());
    transfer_tpsvcinfoDTO.setReqName(tpsvcinfoDTO.getReqName());
    transfer_tpsvcinfoDTO.setBp_sequence(tpsvcinfoDTO.getBp_sequence());

    transfer_event_object.setAction(pevent.getAction());
    transfer_event_object.setRequest(pevent.getRequest() );
    transfer_event_object.setResponse(pevent.getResponse() );

    return transfer_event_object;
  }
}