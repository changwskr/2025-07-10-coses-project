package com.ims.oversea.framework.transaction.constant;

import java.io.Serializable;
import java.math.BigDecimal;
import com.ims.oversea.eplatonframework.transfer.EPlatonEvent;
import com.ims.oversea.foundation.utility.CommonUtil;
import com.ims.oversea.framework.transaction.config.TCFConfig;

/**
 * =============================================================================
 * 프로그램 설명:
 * =============================================================================
 *   Transaction Control Framwwork상에서 사용되는 모든 상수값에 대해서 설정하는 클래스
 *
 * =============================================================================
 * 변경내역 정보:
 * =============================================================================
 *  2004년 03월 16일 1차버전 release
 *
 *
 *                                                        @author : WooSungJang
 *                                                        @email  : changwskr@yahoo.co.kr
 *                                                        @version 1.0
 *  =============================================================================
 */


public final class TCFConstants {

  /**
   * eplaton 시스템의 정보관리 파일
   */
  public static final String CONFIG_FILE_NAME=
      "/weblogic/bea/wlserver6.1/config/coses_US/applicationConfig/EPLconfig.xml";

  /**
   * url 정보관리
   * TCFConfig.getInstance().getElement("jndi-server0").getChild("url").getTextTrim();
   */
  public static final String call_url="t3://localhost:10000";
      //TCFConfig.getInstance().getElement("jndi-server0").getChild("url").getTextTrim();

  /**
   * 로깅 및 eplaton 시스템의 서버환경 정보구현 파일
   * "/weblogic/bea/wlserver6.1/config/coses_US/applicationConfig/maps/epllogej.xml";
   */
  public static final String LOGEJ_CONFIG_FILE_NAME="/weblogic/bea/wlserver6.1/config/coses_US/applicationConfig/maps/epllogej.xml";
      //TCFConfig.getInstance().getElement("log-manage-info").getChild("config-file-path").getTextTrim();

  /**
   * 실제사용하지는 않는 파일
   */
  public static String TX_BLOCKING_CONFIG_FILE_NAME = "/weblogic/bea/wlserver6.1/config/coses_US/applicationConfig/EPLtxblocking.xml";

  /**
   * 로깅관련 파일
   * "/home/coses/log/outlog/TPMTRACE";
   */
  public static String TPMTRACE_LoggingFileName = "/home/coses/log/outlog/TPMTRACE";
      //TCFConfig.getInstance().getElement("log-manage-info").getChild("tpmtrace-file-path").getTextTrim();

  /**
   * "/home/coses/log/input/";
   */
  public static String INPUT_LOGFILENAME = "/home/coses/log/input/";
      //TCFConfig.getInstance().getElement("log-manage-info").getChild("input-file-path").getTextTrim();

  /**
   * "/home/coses/log/output/";
   */
  public static String OUTPUT_LOGFILENAME = "/home/coses/log/output/";
      //TCFConfig.getInstance().getElement("log-manage-info").getChild("output-file-path").getTextTrim();

  /**
   * "/home/coses/log/outlog/TPMTRACE_TRANSACTIONINFO";
   */
  public static String TCF_TRANSACTION_INFO_FILE_NAME= "/home/coses/log/outlog/TPMTRACE_TRANSACTIONINFO";
      //TCFConfig.getInstance().getElement("log-manage-info").getChild("transactioninfo-file-path").getTextTrim();

  /**
   * System.getProperties().getProperty("line.separator")<br>
   * 한 줄 내리기
   */
  public static final String LINE_SEPARATOR = System.getProperties().getProperty("line.separator");

  /**
   * ""<br>
   * 빈 문자열
   */
  public static final String BLANK = "";

  /**
   * new BigDecimal("0.0")<br>
   * 값이 0인 BigDecimal
   */
  public static final BigDecimal ZERO = new BigDecimal("0");
  public static final BigDecimal ONE = new BigDecimal("1");
  public static final BigDecimal HUNDRED = new BigDecimal("100");
  public static final BigDecimal ZERO_DOT_ONE = new BigDecimal("0.1");

  /**
   * "bizaction-map-filename"<br>
   * BizAction class에서 사용하는 상수. BizDelegate 환경 설정 파일이름이 있는 config의 tag 이름
   */
  public static final String BIZDELEGATE_TAG = "bizaction-map-filename";

  /**
   * "bizaction-class"<br>
   * BizAction class에서 사용하는 상수. BizAction class 이름을 정의한 tag 이름
   */
  public static final String ACTIONCLASS_TAG = "bizaction-class";

  public static final String TRANSACTIONABLE = "transactionable";

  /**
   * "bizaction-method"<br>
   * BizAction class에서 사용하는 상수.
   * BizAction class에서 불러야 하는 system component의 operation 이름을 정의한 tag 이름
   */
  public static final String METHOD_TAG = "bizaction-method";

  /**
   * "bizaction-parametertype"<br>
   * BizAction class에서 사용하는 상수.
   * BizAction class에서 불러야 하는 system component의 operation이 사용하는
   * CDTO의 type 이름을 정의한 tag 이름
   */
  public static final String TYPE_TAG = "bizaction-parametertype";

  /**
   * "CosesCommonDTO ChannelType"<br>
   * CosesCommonDTO내에 ChannelType에 사용하는 상수(일반 UI화면에서 거래하는 경우).<br>
   */
  public static final String GENERAL_UI_CHANNEL = "01";

  /**
   * "CosesCommonDTO ChannelType"<br>
   * CosesCommonDTO내에 ChannelType에 사용하는 상수(배치프로그램에서 거래하는 경우).<br>
   */
  public static final String BATCH_CHANNEL = "02";

  /**
   * "CosesCommonDTO ChannelType"<br>
   * CosesCommonDTO내에 ChannelType에 사용하는 상수(ATM 거래로 들어오는 경우).<br>
   */
  public static final String ATM_CHANNEL = "03";

  /**
   * "CosesCommonDTO ChannelType"<br>
   * CosesCommonDTO내에 ChannelType에 사용하는 상수(INTERNET 화면에서 거래하는 경우).<br>
   */
  public static final String INTERNET_BANK_CHANNEL = "10";

  /**
   * 이 class는 생성할 수 없다.
   */

  /**
   *
   */
  public static String HOST_IP_PORT = "t3://localhost:10000";
      //TCFConfig.getInstance().getElement("jndi-server0").getChild("url").getTextTrim();
  public static String USER_TRANSACTION_MSG = "usertransaction";
  public static String NO_TRANSACTION_MSG = "no_transaction_type";
  public static String CONTAINER_TRANSACTION_MSG = "container";
  public static String BIZDELEGATE_CALL_METHOD_NAME="execute";
  public static String TCF_TRANSACTION_DEFAULT_TIMEOUT="180";
  public static String TCF_SUCCESS_ERRCODE="IZZ000";
  public static String BIZDELEGATE_CALL_PARAMETER_TYPE_NAME=EPlatonEvent.class.getName();

  // Fields
  public static final String CALL_BEAN_TYPE_TAG = "action-call-bean-type";
  public static final String BEAN_TRANSACTION_TYPE_TAG = "bean-transaction-type";
  public static final String OPERATION_TAG = "operation-class";
  public static final String LOG_SYSTEM_TAG = "epllogej";
  public static final String PRINT_MODE_TAG = "print_mode";
  public static final String ERROR_MODE_TAG = "error_mode";


  //
  public static final String BEAN_TYPE="remote";
  public static final String BEAN_LOCAL_TYPE="local";
  public static final String NO_TX_BEAN_TYPE="no_transaction_type";

  //
  public static final String HOSTSEQ_INITIAL_STR="********";
  public static final String EVENTNO_INITIAL_STR="********";

  // TPFQ 정보
  public static final String SERVER_SERVER_INTERCHANGE_LOC="100";
  public static final String WAF_SERVER_INTERCHANGE_LOC="200";
  public static final String NATIVE_SERVER_INTERCHANGE_LOC="300";
  public static final String ATM_SERVER_INTERCHANGE_LOC="400";
  public static final String INTERNETBANK_SERVER_INTERCHANGE_LOC="500";
  public static final String BATCHCLIENT_SERVER_INTERCHANGE_LOC="600";


  public static final int SERVER_SERVER_INTERCHANGE_LOC_NUM=CommonUtil.STR2INT(TCFConstants.SERVER_SERVER_INTERCHANGE_LOC) ;
  public static final int WAF_SERVER_INTERCHANGE_LOC_NUM=CommonUtil.STR2INT(TCFConstants.WAF_SERVER_INTERCHANGE_LOC);
  public static final int NATIVE_SERVER_INTERCHANGE_LOC_NUM=CommonUtil.STR2INT(TCFConstants.NATIVE_SERVER_INTERCHANGE_LOC);
  public static final int ATM_SERVER_INTERCHANGE_LOC_NUM=CommonUtil.STR2INT(TCFConstants.ATM_SERVER_INTERCHANGE_LOC);
  public static final int BATCHCLIENT_SERVER_INTERCHANGE_LOC_NUM=CommonUtil.STR2INT(TCFConstants.BATCHCLIENT_SERVER_INTERCHANGE_LOC);
  public static final int INTERNETBANK_SERVER_INTERCHANGE_LOC_NUM=CommonUtil.STR2INT(TCFConstants.INTERNETBANK_SERVER_INTERCHANGE_LOC);

  //errorcode
  public static final String SUCCESS_ERRCODE="IZZ000";

  //logging level
  public static int GENERAL_LOG_LEVEL = 1;
  public static int WARN_LOG_LEVEL = 2;
  public static int FATAL_LOG_LEVEL = 10;

  public static int TCF_LOG_LEVEL = 5;
  public static int STF_LOG_LEVEL = 4;
  public static int ETF_LOG_LEVEL = 4;
  public static int BTF_LOG_LEVEL = 5;
  public static int TPM_LOG_LEVEL = 6;

  //timezone
  public static String TIME_ZONE = "GMT+09:00";

  //FX CNT
  public static int FX_RATE_COUNT = 1;
  public static int GLOABL_TX_INITIAL_MODE = 0;
  public static int GLOABL_TX_ALREADY_MODE = 1;

  //TCF 상수값정의
  public static String TCF_STF_INTIME_INITIAL="XXXXXXXX";
  public static String TCF_STF_OUTTIME_INITIAL="XXXXXXXX";
  public static String TCF_BTF_INTIME_INITIAL="XXXXXXXX";
  public static String TCF_BTF_OUTTIME_INITIAL="XXXXXXXX";
  public static String TCF_ETF_INTIME_INITIAL="XXXXXXXX";
  public static String TCF_ETF_OUTTIME_INITIAL="XXXXXXXX";
  public static String TCF_LOGIC_LEVEL="XXXX";
  public static String TCF_BEFORE_MSG="Before";
  public static String TCF_AFTER_MSG="After";

  public static String STF_MSG = "STF";
  public static String BTF_MSG = "BTF";
  public static String ETF_MSG = "ETF";

  /**
   * "machine-service-mode"
   *  "/weblogic/bea/wlserver6.1/config/coses_US/applicationConfig/EPLconfig.xml"
   * 파일에서 현재 머신에서 온라인서버인지 운용서버인지를 구분하는 구분자이다.
   *
   */
  public static final String MACHINE_SERVICE_MODE_TAG = "machine-service-mode";
  public static final String MACHINE_SERVICE_MODE_DEV = "DEV";

  /**
   * WAF-EJB단의 큐잉시간에 대한 지연을 막기위해 설정한 시간
   */
  public static final int WEB_TRANSACTION_MINUS_SECONDS = 4;
  public static final int GLOBAL_TRANSACTION_MINUS_SECONDS = 2;

  /**
   *
   */
  public static final int LINE_COUNT_PER = 2047;

  /**
   * RULE/THING 단의 초기화 값
   */
  public static final String RULE_THING_INITIAL_VAL = "^^^";

}