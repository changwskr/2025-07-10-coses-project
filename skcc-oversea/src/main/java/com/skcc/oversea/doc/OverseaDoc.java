package com.skcc.oversea.doc;


/**
 * 이 시스템의 구현 특징
 * 1) Business Delegate - action - Facade - tcf - Business native operation - rule - thing - entity
 * 2) 각 시스템 별 구현안
 *    business delegate 단은 세션빈의 형태로 구현한다.
 *                           트랜잭션 관리 형태는 container-not support 타입으로 한다.
 *    action 단은 기본은 logcal / remote facade 세션빈을 호출할 수 있는 구조로 만든다.
 *    Facade 단은 세션빈으로 만들며
 *                트랜잭션 타입은 기본은 container-required타입으로 만든다.
 *                만약 facade단의 세션빈이 usertransaction타입으로 트랜잭션을 관리할 경우
 *                                       bean-usertx타입으로 하며 tcf 호출정보를 넘겨주어야 된다
 *    tcf 단은 java native 타입으로 생성하며
 *            트랜잭션 관리가 bean/container이 동시관리 될수 있도록 한다.
 * ========================================================================================================== *
1) bizaction map 파일을 만드는 쓰레드 구현
   - 웹로직의 StartUP Class을 이용해 웹로직 재기동시 bizactionmap파일 생성 쓰레드
     자동기동
   - 재구성 필요

2) xml 파일 구성 및 추가
   <cashCard-listCashCard>
        <bizaction-class>com.chb.coses.cashCard.business.delegate.action.CashCardBizAction</bizaction-class>
        <transactionable>Y</transactionable>
        <bizaction-method>listCashCard</bizaction-method>
        <bizaction-parametertype>com.chb.coses.cashCard.transfer.CashCardConditionCDTO</bizaction-parametertype>
        * 추가<operation-class>com.kdb.oversea.cashCard.business.facade.CashCardManageMent</operation-class>
  </cashCard-listCashCard>

3) client에서 server로의 시스템호출 방법

   - native client
     가)EPlatonEvent객체생성
     나)EPlatonCommonDTO객체에 기본정보 저장
     나)TPSVCINFODTO객체에 호출할 서비스명 저장
        tpsvcinfoDTO.setReqName("cashCard-listCashCard");
     다)TPCsendrecv객체에 전달
        TPCsendrecv.getInstance("21.101.3.47","7001").callEJB("cashCard-listCashCard","30",event);
     라)EPlatonBizDelegateSB가호출 execute()메소드 호출
       라-1.TPSrecv 메소드
          모든 호출은 클라이언트에서는 request_name만을 호출하고 서버에서 호출할 시스템의
          구체적인 정보를 셋팅하는 것으로 한다.(from bizaction.xml)
         a.호출할 시스템 명
           tpsvcinfoDTO.setSystem_name(TPMSVCAPI.getInstance().TPgetcallsystemname(tpsvcinfo.getReqName() ) );
         b.호출할 시스템으로 라우팅할 action 클래스명
           tpsvcinfoDTO.setAction_name(TPMSVCAPI.getInstance().TPgetactionclassname(event)  );
         c.호출할 시스템 클래스명
           tpsvcinfoDTO.setOperation_name(TPMSVCAPI.getInstance().TPgetoperationclassname(event)  );
         d.호출할 시스템 클래스의 메소드명
           tpsvcinfoDTO.setOperation_method(TPMSVCAPI.getInstance().TPgetinvokemethodname(event)  );
         e.호출할 시스템 클래스의 메소드에 전달할 CDTO 클래스명
           tpsvcinfoDTO.setCdto_name(TPMSVCAPI.getInstance().TPgetparametertypename(event)  );
      라-2.bizAction 메소드
         a.CashCardBizAction(),TellerBizAction(), .... etc
         b.EPlatonBizAction()
           각 시스템 Facade단의 세션빈을 호출, 클라이언트에서 올라온 객체를 Facade단의
           세션빈에게 전달한다
      라-3.Facade Session Bean
         a.TCF 트랜잭션 모듈 호출
           각 빈에서 트랜잭션 관리를 전담할 TCF가 호출된다.
           TCF는 크게 STF,BTF,ETF 3개의 단락으로 구성되며, 업무단의 호출을 위해서 BTF단에서
           호출된다.
         b.BTF에서 tpsvcinfoDTO객체의 operation class을 찾아서
           이것은 인스턴스화 시킨다.
         c.ETF단계를 마치고 Facade Session Bean으로 리턴된다.
      라-4.BizAction 메소드로 리턴
      라-5.EPlatonBizDelegateSB의 execute() 메소드에게로 리턴
    마)클라이언트에게로 리턴된다.

   - server ejb
     가)EPlatonEvent객체생성
     나)EPlatonCommonDTO객체에 기본정보 저장
     나)TPSVCINFODTO객체에 호출할 서비스명 저장
        tpsvcinfoDTO.setReqName("cashCard-listCashCard");
     다)TPSsendrecv객체에 전달
        TPSsendrecv.getInstance().CallEJB("common","cashCard-listCashCard",event);
                                          "원본시스템","호출시스템-메소드","전달객체"
     라)Business Action 호출
       라-1.TPSrecv 메소드
          모든 호출은 클라이언트에서는 request_name만을 호출하고 서버에서 호출할 시스템의
          구체적인 정보를 셋팅하는 것으로 한다.(from bizaction.xml)
         a.호출할 시스템 명
           tpsvcinfoDTO.setSystem_name(TPMSVCAPI.getInstance().TPgetcallsystemname(tpsvcinfo.getReqName() ) );
         b.호출할 시스템으로 라우팅할 action 클래스명
           tpsvcinfoDTO.setAction_name(TPMSVCAPI.getInstance().TPgetactionclassname(event)  );
         c.호출할 시스템 클래스명
           tpsvcinfoDTO.setOperation_name(TPMSVCAPI.getInstance().TPgetoperationclassname(event)  );
         d.호출할 시스템 클래스의 메소드명
           tpsvcinfoDTO.setOperation_method(TPMSVCAPI.getInstance().TPgetinvokemethodname(event)  );
         e.호출할 시스템 클래스의 메소드에 전달할 CDTO 클래스명
           tpsvcinfoDTO.setCdto_name(TPMSVCAPI.getInstance().TPgetparametertypename(event)  );
      라-2.bizAction 메소드
         a.CashCardBizAction(),TellerBizAction(), .... etc
         b.EPlatonBizAction()
           각 시스템 Facade단의 세션빈을 호출, 클라이언트에서 올라온 객체를 Facade단의
           세션빈에게 전달한다
      라-3.Facade Session Bean
         a.TCF 트랜잭션 모듈 호출
           각 빈에서 트랜잭션 관리를 전담할 TCF가 호출된다.
           TCF는 크게 STF,BTF,ETF 3개의 단락으로 구성되며, 업무단의 호출을 위해서 BTF단에서
           호출된다.
         b.BTF에서 tpsvcinfoDTO객체의 operation class을 찾아서
           이것은 인스턴스화 시킨다.
         c.ETF단계를 마치고 Facade Session Bean으로 리턴된다.
      라-4.BizAction 메소드로 리턴
    마)서버클라이언트에게로 리턴된다.

4) loggin 관리를 위한 방안
   가. xml 파일관리 - 파일명 epllogej.xml
        <epllogej-common>
          <print-mode>1</print-mode>
          <error-mode>5</error-mode>
        </epllogej-common>
        <epllogej-lcommon>
          <print-mode>1</print-mode>
          <error-mode>5</error-mode>
        </epllogej-lcommon>
        <epllogej-teller>
          <print-mode>1</print-mode>
          <error-mode>5</error-mode>
        </epllogej-teller>
        <epllogej-cashCard>
          <print-mode>1</print-mode>
          <error-mode>5</error-mode>
        </epllogej-cashCard>
        <epllogej-deposit>
          <print-mode>1</print-mode>
          <error-mode>5</error-mode>
        </epllogej-deposit>
  나. 관리방안
      epllogej.xml파일에 해당 시스템에 대한 mode을 추가한다.
      /weblogic/bea/wlserver6.1/config/coses/applicationConfig/bizaction-map.xml
      파일을 touch해서 epllogej.xml정보가 jdom의 메모리에 반영되도록 한다.
  다. 실제사용방법
      LOGEJ(1,event,"message");
      LOGEJ(1,event,exception);

4) 트랜잭션을 제어하기 위한 구조

  가. 파일명 - epllogej.xml

  <block-txcode>
        <count>3</count>
        <mode>off</mode>
        <s1>1000-1000</s1>
        <s2>1003-1010</s2>
        <s3>1010-2222</s3>
</block-txcode>


<block-system>
        <count>3</count>
        <mode>off</mode>
        <bank>03</bank>
        <s1>common</s1>
        <s2>deposit</s2>
        <s3>cashCard</s3>
</block-system>


<block-bankcode>
        <count>3</count>
        <mode>off</mode>
        <s1>02-02</s1>
        <s2>03-03</s2>
        <s3>04-04</s3>
</block-txcode>


<block-tpfq>
        <count>8</count>
        <mode>off</mode>
        <s1>100-100</s1>
        <s2>200-200</s2>
        <s3>300-300</s3>
        <s4>400-400</s4>
        <s5>500-500</s5>
        <s6>600-600</s6>
        <s7>700-700</s7>
        <s8>800-800</s8>
</block-tpfq>

*
*

////////////////////////////////////////////////////////////////////////////////

drop table TRANSACTION_INPUT;

CREATE TABLE TRANSACTION_INPUT (
         terminalID 			VARCHAR2(100),
         terminalType 		VARCHAR2(100),
         xmlSeq 					VARCHAR2(100),
         bankCode 				VARCHAR2(100),
         branchCode 			VARCHAR2(100),
         glPostBranchCode VARCHAR2(100),
         channelType 			VARCHAR2(100),
         userID 					VARCHAR2(100),
         eventNo 					VARCHAR2(100),
         nation 					VARCHAR2(100),
         regionCode 			VARCHAR2(100),
         timeZone 				VARCHAR2(100),
         fxRateCount 			VARCHAR2(100),
         reqName 					VARCHAR2(100),
         systemDate 			VARCHAR2(100),
         businessDate 		VARCHAR2(100),
         transactionNo 		VARCHAR2(100),
         baseCurrency 		VARCHAR2(100),
         multiPL 					VARCHAR2(100),
         userLevel 				VARCHAR2(100),
         IPAddress 				VARCHAR2(100),
         bp_sequence 				VARCHAR2(100),
         req_name 				VARCHAR2(100),
         system_name 			VARCHAR2(100),
         operation_name 	VARCHAR2(100),
         operation_method VARCHAR2(100),
         cdto_name 				VARCHAR2(100),
         action_name 			VARCHAR2(100),
         hostseq 					VARCHAR2(100),
         orgseq 					VARCHAR2(100),
         tx_timer 				VARCHAR2(100),
         tpfq 						VARCHAR2(100),
         errorcode 				VARCHAR2(100),
         trclass 					VARCHAR2(100),
         web_timeout 			VARCHAR2(100),
         web_intime 			VARCHAR2(100),
         web_outtime 			VARCHAR2(100),
         systemInTime 		VARCHAR2(100),
         systemOutTime 		VARCHAR2(100),
         system_date 			VARCHAR2(100),
         error_message 		VARCHAR2(100),
         logic_level 			VARCHAR2(100),
         STF_intime 			VARCHAR2(100),
         STF_outtime 			VARCHAR2(100),
         BTF_intime 			VARCHAR2(100),
         BTF_outtime 			VARCHAR2(100),
         ETF_intime 			VARCHAR2(100),
         ETF_outtime 			VARCHAR2(100),
         INPUT_DTO        VARCHAR2(4000),
         OUTPUT_DTO       VARCHAR2(4000),
         CONSTRAINT TRANSACTION_INPUT PRIMARY KEY ( hostseq )
         USING INDEX TABLESPACE TSP_CORE_IND )
         TABLESPACE TSP_CORE
;

CREATE SYNONYM TRANSACTION_INPUT FOR CORE.TRANSACTION_INPUT;
grant select,insert,update,delete on TRANSACTION_INPUT to eplaton;


///////////////////////////////////////////////////////////////////////////////
drop table TRANSACTION_OUTPUT;

CREATE TABLE TRANSACTION_OUTPUT (
         terminalID 			VARCHAR2(100),
         terminalType 		VARCHAR2(100),
         xmlSeq 					VARCHAR2(100),
         bankCode 				VARCHAR2(100),
         branchCode 			VARCHAR2(100),
         glPostBranchCode VARCHAR2(100),
         channelType 			VARCHAR2(100),
         userID 					VARCHAR2(100),
         eventNo 					VARCHAR2(100),
         nation 					VARCHAR2(100),
         regionCode 			VARCHAR2(100),
         timeZone 				VARCHAR2(100),
         fxRateCount 			VARCHAR2(100),
         reqName 					VARCHAR2(100),
         systemDate 			VARCHAR2(100),
         businessDate 		VARCHAR2(100),
         transactionNo 		VARCHAR2(100),
         baseCurrency 		VARCHAR2(100),
         multiPL 					VARCHAR2(100),
         userLevel 				VARCHAR2(100),
         IPAddress 				VARCHAR2(100),
         bp_sequence 				VARCHAR2(100),
         req_name 				VARCHAR2(100),
         system_name 			VARCHAR2(100),
          operation_name 	VARCHAR2(100),
         operation_method VARCHAR2(100),
         cdto_name 				VARCHAR2(100),
         action_name 			VARCHAR2(100),
         hostseq 					VARCHAR2(100),
         orgseq 					VARCHAR2(100),
         tx_timer 				VARCHAR2(100),
         tpfq 						VARCHAR2(100),
         errorcode 				VARCHAR2(100),
         trclass 					VARCHAR2(100),
         web_timeout 			VARCHAR2(100),
         web_intime 			VARCHAR2(100),
         web_outtime 			VARCHAR2(100),
         systemInTime 		VARCHAR2(100),
         systemOutTime 		VARCHAR2(100),
         system_date 			VARCHAR2(100),
         error_message 		VARCHAR2(100),
         logic_level 			VARCHAR2(100),
         STF_intime 			VARCHAR2(100),
         STF_outtime 			VARCHAR2(100),
         BTF_intime 			VARCHAR2(100),
         BTF_outtime 			VARCHAR2(100),
         ETF_intime 			VARCHAR2(100),
         ETF_outtime 			VARCHAR2(100),
         INPUT_DTO        VARCHAR2(4000),
         OUTPUT_DTO       VARCHAR2(4000),
         CONSTRAINT TRANSACTION_OUTPUT PRIMARY KEY ( hostseq )
         USING INDEX TABLESPACE TSP_CORE_IND )
         TABLESPACE TSP_CORE
         ;

CREATE SYNONYM TRANSACTION_OUTPUT FOR CORE.TRANSACTION_OUTPUT;
grant select,insert,update,delete on TRANSACTION_OUTPUT to eplaton;

////////////////////////////////////////////////////////////////////////////////

//TRANSACTION_INFO 테이블 정보
// channel_type == *01:General UI, 02:Batch, 03:ATM, 10:Internet Banking

drop table transaction_info;
CREATE TABLE TRANSACTION_INFO(
        transaction_id           VARCHAR2(100)  ,
        host_name                VARCHAR2(100)  ,
        system_name              VARCHAR2(100)  ,
        method_name              VARCHAR2(100)  ,
        bank_code                VARCHAR2(100)  ,
        branch_code              VARCHAR2(100)  ,
        user_id                  VARCHAR2(100)  ,
        channel_type             VARCHAR2(100)  ,
        business_date            VARCHAR2(100)  ,
        register_date            VARCHAR2(100)  ,
        in_time                  VARCHAR2(100)  ,
        event_no                 VARCHAR2(100)  ,
        transaction_no           VARCHAR2(100)  ,
        org_seq                  VARCHAR2(100)  ,
        out_time                 VARCHAR2(100)  ,
        response_time            VARCHAR2(100)  ,
        error_code               VARCHAR2(100)  ,
        ip_address               VARCHAR2(100)  ,
        tpfq                     VARCHAR2(100)  ,
        CONSTRAINT PK_TRANSACTION_INFO PRIMARY KEY (transaction_id)
        USING INDEX TABLESPACE TSP_CORE_IND
)
TABLESPACE TSP_CORE
/

grant select,insert,update,delete on TRANSACTION_INFO to eplaton;
conn eplaton/useplaton
/
CREATE SYNONYM TRANSACTION_INFO FOR CORE.TRANSACTION_INFO;

* ========================================================================================================== *
*/

public class OverseaDoc {
}
