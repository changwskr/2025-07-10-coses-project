package com.ims.oversea.framework.transaction.constant;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public final class TCFConstantErrcode {

  //ACTION
  public static final String EBDL001 = "EBDL001";
  public static final String EBDL001_MSG = "other exception in bizdelegateSB";


  //ACTION
  public static final String EACT001 = "EACT001";
  public static final String EACT001_MSG = "other exception in eplatonbizaction";

  public static final String EACT002 = "EACT002";
  public static final String EACT002_MSG = "exception in bizaction";

  // TPMAPI
  public static final String ETPM001 = "ETPM001";
  public static final String ETPM001_MSG = "cannot get action_class_name";

  public static final String ETPM002 = "ETPM002";
  public static final String ETPM002_MSG = "cannot get operation_class_name";

  public static final String ETPM003 = "ETPM003";
  public static final String ETPM003_MSG = "cannot get operation_method_name";

  public static final String ETPM004 = "ETPM004";
  public static final String ETPM004_MSG = "cannot get parametertyp";

  public static final String ETPM005 = "ETPM005";
  public static final String ETPM005_MSG = "event no is not set";

  public static final String ETPM006 = "ETPM006";
  public static final String ETPM006_MSG = "tpfq is not set";

  public static final String ETPM007 = "ETPM007";
  public static final String ETPM007_MSG = "DB connection is null or Hostseq is null";

  public static final String ETPM008 = "ETPM008";
  public static final String ETPM008_MSG = "TPSrecv exception";

  public static final String ETPM009 = "ETPM009";
  public static final String ETPM009_MSG = "Hostseq value is abnormal";

  // TPSsendrecv
  public static final String ETPM010 = "ETPM010";
  public static final String ETPM010_MSG = "IDTO is null or blank";

  public static final String ETPM011 = "ETPM011";
  public static final String ETPM011_MSG = "TPSsendrecv Class Not Found Exception";

  public static final String ETPM012 = "ETPM012";
  public static final String ETPM012_MSG = "TPSsendrecv Illegal Access Exception";

  public static final String ETPM013 = "ETPM013";
  public static final String ETPM013_MSG = "TPSsendrecv Instantiation Exception";

  public static final String ETPM014 = "ETPM014";
  public static final String ETPM014_MSG = "TPSsendrecv other Exception";

  public static final String ETPM015 = "ETPM015";
  public static final String ETPM015_MSG = "TPSsendrecv other Exception";

  public static final String ETPM016 = "ETPM016";
  public static final String ETPM016_MSG = "not to get bizdate";

  public static final String ETPM017 = "ETPM017";
  public static final String ETPM017_MSG = "not to get bizdate";

  public static final String ETPM018 = "ETPM018";
  public static final String ETPM018_MSG = "not to get transactionno";

  //TPCsendrecv
  public static final String ETPM019 = "ETPM019";
  public static final String ETPM019_MSG = "this cdto is not the type of IDTO";

  public static final String ETPM020 = "ETPM020";
  public static final String ETPM020_MSG = "exception in TPCsendrecv";

  //TPMDBrecv
  public static final String ETPM021 = "ETPM021";
  public static final String ETPM021_MSG = "fail to suspend transaction";
  public static final String ETPM022 = "ETPM022";
  public static final String ETPM022_MSG = "fail to resume transaction";


  // TCF
  public static final String ETCF001 = "ETCF001";
  public static final String ETCF001_MSG = "UserTransaction object is null";

  public static final String ETCF002 = "ETCF002";
  public static final String ETCF002_MSG = "bean no_transaction_type";

  public static final String ETCF003 = "ETCF003";
  public static final String ETCF003_MSG = "error code not set";

  public static final String ETCF004 = "ETCF004";
  public static final String ETCF004_MSG = "TCF other exception";

  // STF
  public static final String ESTF001 = "ESTF001";
  public static final String ESTF001_MSG = "businessdate  is null";

  public static final String ESTF002 = "ESTF002";
  public static final String ESTF002_MSG = "STFSPinit other exception";

  public static final String ESTF003 = "ESTF003";
  public static final String ESTF003_MSG = "STF.execute other exception";

  public static final String ESTF004 = "ESTF004";
  public static final String ESTF004_MSG = "STF error in tpinfo for checking txmode";

  public static final String ESTF005 = "ESTF005";
  public static final String ESTF005_MSG = "STF error in tpbegin for starting txmode";

  public static final String ESTF006 = "ESTF006";
  public static final String ESTF006_MSG = "STF transaction already marked setrollback only";

  public static final String ESTF007 = "ESTF007";
  public static final String ESTF007_MSG = "not start transactin with tpbegin";

  public static final String ESTF008 = "ESTF008";
  public static final String ESTF008_MSG = "transactin mode is abnormal";

  public static final String ESTF009 = "ESTF009";
  public static final String ESTF009_MSG = "tpfq information is nothing";

  public static final String ESTF010 = "ESTF010";
  public static final String ESTF010_MSG = "other exception in STFmiddle ";

  public static final String ESTF011 = "ESTF011";
  public static final String ESTF011_MSG = "other exception in STFend ";

  public static final String ESTF012 = "ESTF012";
  public static final String ESTF012_MSG = "error code not set";

  public static final String ESTF013 = "ESTF013";
  public static final String ESTF013_MSG = "error in inputting packet to db";

  public static final String ESTF014 = "ESTF014";
  public static final String ESTF014_MSG = "other exception in STF_SPdbInLog";

  public static final String ESTF015 = "ESTF015";
  public static final String ESTF015_MSG = "other exception in STF_SPcommonLog";

  public static final String ESTF016 = "ESTF016";
  public static final String ESTF016_MSG = "other exception in STF_SPtxctl";

  public static final String ESTF017 = "ESTF017";
  public static final String ESTF017_MSG = "other exception in STF_SPtxblocking";

  public static final String ESTF018 = "ESTF018";
  public static final String ESTF018_MSG = "this txcode not service";

  public static final String ESTF019 = "ESTF019";
  public static final String ESTF019_MSG = "other exception in STF_SPtxcodeblock";

  public static final String ESTF020 = "ESTF020";
  public static final String ESTF020_MSG = "this bizsystem not service";

  public static final String ESTF021 = "ESTF021";
  public static final String ESTF021_MSG = "other exception in STF_SPsystemblock";

  public static final String ESTF022 = "ESTF022";
  public static final String ESTF022_MSG = "this bankcode not service";

  public static final String ESTF023 = "ESTF023";
  public static final String ESTF023_MSG = "other exception in STF_SPbankcodeblock";

  public static final String ESTF024 = "ESTF024";
  public static final String ESTF024_MSG = "this tpfq not service";

  public static final String ESTF025 = "ESTF025";
  public static final String ESTF025_MSG = "other exception in STF_SPtpfqblock";

  public static final String ESTF026 = "ESTF026";
  public static final String ESTF026_MSG = "other exception in STF_SPeod";

  public static final String ESTF027 = "ESTF027";
  public static final String ESTF027_MSG = "other exception in STF_SPwebtxtimer";

  public static final String ESTF028 = "ESTF028";
  public static final String ESTF028_MSG = "transaction biztimeout";

  public static final String ESTF029 = "ESTF029";
  public static final String ESTF029_MSG = "other exception in STF_SPgetbusinessdate";

  public static final String ESTF030 = "ESTF030";
  public static final String ESTF030_MSG = "already transaction rolled back in STF tpinfo()";

  // BTF
  public static final String EBTF001 = "EBTF001";
  public static final String EBTF001_MSG = "other exception in BTF main module";

  public static final String EBTF002 = "EBTF002";
  public static final String EBTF002_MSG = "other exception BTF_SPinit in BTF";

  public static final String EBTF003 = "EBTF003";
  public static final String EBTF003_MSG = "other exception BTF_SPmiddle in BTF";

  public static final String EBTF004 = "EBTF004";
  public static final String EBTF004_MSG = "other exception BTF_SPend in BTF";

  public static final String EBTF005 = "EBTF005";
  public static final String EBTF005_MSG = "errcode not set in BTF";


  // ETF
  public static final String EETF001 = "EETF001";
  public static final String EETF001_MSG = "other exception in ETF main module";

  public static final String EETF002 = "EETF002";
  public static final String EETF002_MSG = "other exception ETF_SPinit in ETF";

  public static final String EETF003 = "EETF003";
  public static final String EETF003_MSG = "other exception ETF_SPmiddle in ETF";

  public static final String EETF004 = "EETF004";
  public static final String EETF004_MSG = "other exception ETF_SPend in ETF";

  public static final String EETF005 = "EETF005";
  public static final String EETF005_MSG = "tprollback error in ETF";

  public static final String EETF006 = "EETF006";
  public static final String EETF006_MSG = "tpcommit error in ETF";

  public static final String EETF007 = "EETF007";
  public static final String EETF007_MSG = "error in DB_INSERToutlog in ETF_SPdbOutLog";

  public static final String EETF008 = "EETF008";
  public static final String EETF008_MSG = "other exception in ETF_SPdbOutLog";

  public static final String EETF009 = "EETF009";
  public static final String EETF009_MSG = "error in saving outlog file";

  public static final String EETF010 = "EETF010";
  public static final String EETF010_MSG = "error code not set ";

  public static final String EETF011 = "EETF011";
  public static final String EETF011_MSG = "other exception ETF_SPdbOutLog in ETF_SPwebtxtimer";

  public static final String EETF012 = "EETF012";
  public static final String EETF012_MSG = "transaction biz timeout";

  // Facade Session Bean
  public static final String EFAD001 = "EFAD001";
  public static final String EFAD001_MSG = "exception in Facade SessionBean";

}