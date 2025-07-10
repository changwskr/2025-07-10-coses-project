package com.chb.coses.eplatonFWK.business.helper.logej;

import java.io.*;
import java.rmi.*;
import java.util.*;
import java.net.*;
import com.chb.coses.eplatonFWK.business.helper.CommonUtil;
import com.chb.coses.eplatonFWK.business.helper.logej.*;
import com.chb.coses.eplatonFWK.business.helper.FILEapi;
import com.chb.coses.eplatonFWK.transfer.EPlatonEvent;

/**
 * <PRE>
 * Class    : LOGEJ
 * Function :
 * Comment  : Log File Writing <BR>
 *            <BR>
 *            <BR>
 * </PRE>
 * @version : 1.0
 * @author  : 장  우  승
 */


public class LOGEJ {
  private static LOGEJ instance;
  public static String hostname=CommonUtil.GetHostName();
  public static String Logej_Level_Info_Filename = "/home/coses/env/logej.properties";
/*
#######################################
# NOLOG   : 4
# INFO    : 1
# DEBUG   : 2
# FATAL   : 10
#
#######################################
LOGGING_LEVEL_MODE=1
LOGGING_DEBUG_MODE=4
*/
  /*
  public static long omode = FILEapi.FILElastmodified(Logej_Level_Info_Filename );;
  public static int pring_offset_mode = CommonUtil.Str2Int(LOGEJ.getInstance().GetEnvValue("LOGGING_LEVEL_MODE"));
  public static int debug_offset_mode = CommonUtil.Str2Int(LOGEJ.getInstance().GetEnvValue("LOGGING_DEBUG_MODE"));
  */

  public LOGEJ() throws IOException,NotBoundException {
  }

  public static synchronized LOGEJ getInstance() {
    if (instance == null) {
      try{
        instance = new LOGEJ();
        }catch(Exception igex){}
    }
    return instance;
  }

  public synchronized String GetEnvValue(String tmp)
  {
    String ConfigPath=null;

    Properties p = new Properties();
    String tmpv = null;
    try {
      p.load(new FileInputStream(Logej_Level_Info_Filename));
      tmpv=p.getProperty(tmp);
    }
    catch (IOException e) {
      System.out.println("GetEnvValue() 에서 환경셋팅시12 예외발생" );
      e.printStackTrace();
    }
    return tmpv;
  }

/*
  public synchronized static void printf(int mode,String contentToWrite) {
    String LoggingFileName = null;

    long nmode = FILEapi.FILElastmodified(Logej_Level_Info_Filename);
    if( nmode != LOGEJ.getInstance().omode ){
      LOGEJ.getInstance().pring_offset_mode = CommonUtil.Str2Int(LOGEJ.getInstance().GetEnvValue("LOGGING_LEVEL_MODE"));
      LOGEJ.getInstance().omode = FILEapi.FILElastmodified(Logej_Level_Info_Filename);
    }
    if( !(mode >= LOGEJ.getInstance().pring_offset_mode) ){
      return;
    }
    try{
        LoggingFileName = CommonUtil.GetHostName() + "." + CommonUtil.GetSysDate();
    }
    catch(Exception ex){
    }

    FileOutputStream fos = null;
    PrintStream ps = null;

    try {
      fos = new FileOutputStream(LoggingFileName, true);
      ps = new PrintStream(fos);
      contentToWrite = CommonUtil.getCurrentDate() +
          CommonUtil.GetSysTime().substring(0,8)  +
          "-" + "|" + contentToWrite;
      ps.println(contentToWrite);
      ps.flush();
    }catch(Exception e){
      e.printStackTrace();
    }
    finally{
      try{
        if( ps != null )
          ps.close();
        if( fos != null )
          fos.close();
      }catch(Exception ex){}
    }
    return;
  }
*/

  public synchronized void printf(String systemname,String eventno,String key,String contentToWrite) {
    String LoggingFileName = "/home/coses/log/outlog/" + systemname + "." +
                             CommonUtil.GetHostName() + "." + CommonUtil.GetSysDate();

    FileOutputStream fos = null;
    PrintStream ps = null;

    try {
      fos = new FileOutputStream(LoggingFileName, true);
      ps = new PrintStream(fos);
      contentToWrite = CommonUtil.GetSysTime().substring(0,8)  +
                 "-" + eventno  +
                 "-" + key + "|" + contentToWrite;

      ps.println(contentToWrite);
      ps.flush();
    }catch(Exception e){
      e.printStackTrace();
    }
    finally{
      try{
        if( ps != null )
          ps.close();
        if( fos != null )
          fos.close();
      }catch(Exception ex){}
    }
    return;
  }

  public synchronized void printf(EPlatonEvent event,String contentToWrite)
  {
    if ( ((EPlatonEvent)event).getTPSVCINFODTO().getSystem_name() == null )
      ((EPlatonEvent)event).getTPSVCINFODTO().setSystem_name("NONSYSTEM");
    if( ((EPlatonEvent)event).getTPSVCINFODTO().getOrgseq() == null )
      ((EPlatonEvent)event).getTPSVCINFODTO().setOrgseq("********");

    String LoggingFileName = "/home/coses/log/outlog/" + ((EPlatonEvent)event).getTPSVCINFODTO().getSystem_name()  + "." +
                             CommonUtil.GetHostName() + "." + CommonUtil.GetSysDate();
    FileOutputStream fos = null;
    PrintStream ps = null;

    try {
      fos = new FileOutputStream(LoggingFileName, true);
      ps = new PrintStream(fos);
      contentToWrite = CommonUtil.GetSysTime().substring(0,8)  +
                       "-" + ((EPlatonEvent)event).getCommon().getEventNo()  +
                       "-" + ((EPlatonEvent)event).getTPSVCINFODTO().getOrgseq() + "|" + contentToWrite;
      ps.println(contentToWrite);
      ps.flush();
    }catch(Exception e){
      e.printStackTrace();
    }
    finally{
      try{
        if( ps != null )
          ps.close();
        if( fos != null )
          fos.close();
      }catch(Exception ex){}
    }
    return;
  }

  public synchronized void txprintf(EPlatonEvent event,String contentToWrite)
  {
    if ( ((EPlatonEvent)event).getTPSVCINFODTO().getSystem_name() == null )
      ((EPlatonEvent)event).getTPSVCINFODTO().setSystem_name("NONSYSTEM");
    if( ((EPlatonEvent)event).getTPSVCINFODTO().getOrgseq() == null )
      ((EPlatonEvent)event).getTPSVCINFODTO().setOrgseq("********");

    String LoggingFileName = "/home/coses/log/outlog/tpmtxlog"   + "." +
                             CommonUtil.GetHostName() + "." + CommonUtil.GetSysDate();
    FileOutputStream fos = null;
    PrintStream ps = null;

    try {

      fos = new FileOutputStream(LoggingFileName, true);
      ps = new PrintStream(fos);
      contentToWrite = CommonUtil.GetSysTime().substring(0,8)  +
                       "-" + ((EPlatonEvent)event).getCommon().getEventNo()  +
                       "-" + ((EPlatonEvent)event).getTPSVCINFODTO().getOrgseq() + "|" + contentToWrite;
      ps.println(contentToWrite);
      ps.flush();
    }catch(Exception e){
      e.printStackTrace();
    }
    finally{
      try{
        if( ps != null )
          ps.close();
        if( fos != null )
          fos.close();
      }catch(Exception ex){}
    }
    return;
  }

  public synchronized void tcf_txprintf(EPlatonEvent event,String contentToWrite)
  {
    if ( ((EPlatonEvent)event).getTPSVCINFODTO().getSystem_name() == null )
      ((EPlatonEvent)event).getTPSVCINFODTO().setSystem_name("NONSYSTEM");
    if( ((EPlatonEvent)event).getTPSVCINFODTO().getOrgseq() == null )
      ((EPlatonEvent)event).getTPSVCINFODTO().setOrgseq("********");

    String LoggingFileName = "/home/coses/log/outlog/TPMTRACE"   + "." +
                             CommonUtil.GetHostName() + "." + CommonUtil.GetSysDate();
    FileOutputStream fos = null;
    PrintStream ps = null;

    try {
      StringTokenizer operation_name = new StringTokenizer(((EPlatonEvent)event).getTPSVCINFODTO().getOperation_name(),".");
      String opn = null;
      while( operation_name.hasMoreElements()){
        opn =(String)operation_name.nextElement();
      }
      operation_name = new StringTokenizer(((EPlatonEvent)event).getTPSVCINFODTO().getAction_name(),".");
      String apn = null;
      while( operation_name.hasMoreElements()){
        apn =(String)operation_name.nextElement();
      }
      fos = new FileOutputStream(LoggingFileName, true);
      ps = new PrintStream(fos);
      contentToWrite = CommonUtil.GetHostName() + ":" +CommonUtil.GetSysTime().substring(0,8)  + ":" + ((EPlatonEvent)event).getTPSVCINFODTO().getOrgseq() +
                       "!" + CommonUtil.PSpaceToStr((( EPlatonEvent)event).getTPSVCINFODTO().getSystem_name(),20) +
                       ":" + CommonUtil.PSpaceToStr(apn,20) +
                       ":" + CommonUtil.PSpaceToStr(opn,20) +
                       "!" + contentToWrite;
      ps.println(contentToWrite);
      ps.flush();
    }catch(Exception e){
      e.printStackTrace();
    }
    finally{
      try{
        if( ps != null )
          ps.close();
        if( fos != null )
          fos.close();
      }catch(Exception ex){}
    }
    return;
  }

  public synchronized void ftxprintf(EPlatonEvent event,String contentToWrite)
  {
    if ( ((EPlatonEvent)event).getTPSVCINFODTO().getSystem_name() == null )
      ((EPlatonEvent)event).getTPSVCINFODTO().setSystem_name("NONSYSTEM");
    if( ((EPlatonEvent)event).getTPSVCINFODTO().getOrgseq() == null )
      ((EPlatonEvent)event).getTPSVCINFODTO().setOrgseq("********");

    String LoggingFileName = "/home/coses/log/outlog/tpmtxlog"   + "." +
                             CommonUtil.GetHostName() + "." + CommonUtil.GetSysDate();
    FileOutputStream fos = null;
    PrintStream ps = null;

    try {
      fos = new FileOutputStream(LoggingFileName, true);
      ps = new PrintStream(fos);
      contentToWrite = "\n" + CommonUtil.GetSysTime().substring(0,8)  +
                       "-" + ((EPlatonEvent)event).getCommon().getEventNo()  +
                       "-" + ((EPlatonEvent)event).getTPSVCINFODTO().getOrgseq() + "|" + contentToWrite;
      ps.println(contentToWrite);
      ps.flush();
    }catch(Exception e){
      e.printStackTrace();
    }
    finally{
      try{
        if( ps != null )
          ps.close();
        if( fos != null )
          fos.close();
      }catch(Exception ex){}
    }
    return;
  }

  public synchronized void nohead_printf(EPlatonEvent event,String contentToWrite)
  {
    if ( ((EPlatonEvent)event).getTPSVCINFODTO().getSystem_name() == null )
      ((EPlatonEvent)event).getTPSVCINFODTO().setSystem_name("NONSYSTEM");
    if( ((EPlatonEvent)event).getTPSVCINFODTO().getOrgseq() == null )
      ((EPlatonEvent)event).getTPSVCINFODTO().setOrgseq("********");

    String LoggingFileName = "/home/coses/log/outlog/" + ((EPlatonEvent)event).getTPSVCINFODTO().getSystem_name()  + "." +
                             CommonUtil.GetHostName() + "." + CommonUtil.GetSysDate();
    FileOutputStream fos = null;
    PrintStream ps = null;

    try {
      fos = new FileOutputStream(LoggingFileName, true);
      ps = new PrintStream(fos);
      ps.println(contentToWrite);
      ps.flush();
    }catch(Exception e){
      e.printStackTrace();
    }
    finally{
      try{
        if( ps != null )
          ps.close();
        if( fos != null )
          fos.close();
      }catch(Exception ex){}
    }
    return;
  }

  public synchronized void nohead_txprintf(EPlatonEvent event,String contentToWrite)
  {
    if ( ((EPlatonEvent)event).getTPSVCINFODTO().getSystem_name() == null )
      ((EPlatonEvent)event).getTPSVCINFODTO().setSystem_name("NONSYSTEM");
    if( ((EPlatonEvent)event).getTPSVCINFODTO().getOrgseq() == null )
      ((EPlatonEvent)event).getTPSVCINFODTO().setOrgseq("********");

    String LoggingFileName = "/home/coses/log/outlog/tpmtxlog"   + "." +
                             CommonUtil.GetHostName() + "." + CommonUtil.GetSysDate();
    FileOutputStream fos = null;
    PrintStream ps = null;

    try {
      fos = new FileOutputStream(LoggingFileName, true);
      ps = new PrintStream(fos);
      ps.println(contentToWrite);
      ps.flush();
    }catch(Exception e){
      e.printStackTrace();
    }
    finally{
      try{
        if( ps != null )
          ps.close();
        if( fos != null )
          fos.close();
      }catch(Exception ex){}
    }
    return;
  }

  public synchronized void print(EPlatonEvent event,String contentToWrite)
  {
    if ( ((EPlatonEvent)event).getTPSVCINFODTO().getSystem_name() == null )
      ((EPlatonEvent)event).getTPSVCINFODTO().setSystem_name("NONSYSTEM");
    if( ((EPlatonEvent)event).getTPSVCINFODTO().getOrgseq() == null )
      ((EPlatonEvent)event).getTPSVCINFODTO().setOrgseq("********");

    String LoggingFileName = "/home/coses/log/outlog/" + ((EPlatonEvent)event).getTPSVCINFODTO().getSystem_name()  + "." +
                             CommonUtil.GetHostName() + "." + CommonUtil.GetSysDate();
    FileOutputStream fos = null;
    PrintStream ps = null;

    try {
      fos = new FileOutputStream(LoggingFileName, true);
      ps = new PrintStream(fos);
      contentToWrite = CommonUtil.GetSysTime().substring(0,8)  +
                       "-" + ((EPlatonEvent)event).getCommon().getEventNo()  +
                       "-" + ((EPlatonEvent)event).getTPSVCINFODTO().getOrgseq() + "|" + contentToWrite;
      ps.print(contentToWrite);
      ps.flush();
    }catch(Exception e){
      e.printStackTrace();
    }
    finally{
      try{
        if( ps != null )
          ps.close();
        if( fos != null )
          fos.close();
      }catch(Exception ex){}
    }
    return;
  }

  public synchronized void txprint(EPlatonEvent event,String contentToWrite)
  {
    if ( ((EPlatonEvent)event).getTPSVCINFODTO().getSystem_name() == null )
      ((EPlatonEvent)event).getTPSVCINFODTO().setSystem_name("NONSYSTEM");
    if( ((EPlatonEvent)event).getTPSVCINFODTO().getOrgseq() == null )
      ((EPlatonEvent)event).getTPSVCINFODTO().setOrgseq("********");

    String LoggingFileName = "/home/coses/log/outlog/tpmtxlog"   + "." +
                             CommonUtil.GetHostName() + "." + CommonUtil.GetSysDate();
    FileOutputStream fos = null;
    PrintStream ps = null;

    try {
      fos = new FileOutputStream(LoggingFileName, true);
      ps = new PrintStream(fos);
      contentToWrite = CommonUtil.GetSysTime().substring(0,8)  +
                       "-" + ((EPlatonEvent)event).getCommon().getEventNo()  +
                       "-" + ((EPlatonEvent)event).getTPSVCINFODTO().getOrgseq() + "|" + contentToWrite;
      ps.print(contentToWrite);
      ps.flush();
    }catch(Exception e){
      e.printStackTrace();
    }
    finally{
      try{
        if( ps != null )
          ps.close();
        if( fos != null )
          fos.close();
      }catch(Exception ex){}
    }
    return;
  }

/*
  public synchronized static void eprintf(int mode,Exception pex) {
    String thrname = Thread.currentThread().getName();
    String LoggingFileName = null;
    long nmode = FILEapi.FILElastmodified(Logej_Level_Info_Filename);

    if( nmode != LOGEJ.getInstance().omode ){
      LOGEJ.getInstance().pring_offset_mode = CommonUtil.Str2Int(LOGEJ.getInstance().GetEnvValue("LOGGING_LEVEL_MODE"));
      LOGEJ.getInstance().omode = FILEapi.FILElastmodified(Logej_Level_Info_Filename);
    }

    if( !(mode >= LOGEJ.getInstance().pring_offset_mode) ){
      return;
    }

    LoggingFileName = "Logejb" + "." +
                  CommonUtil.GetHostName() + "." +
                  CommonUtil.GetSysDate();

    FileOutputStream fos = null;
    PrintStream ps = null;

    try {
      fos = new FileOutputStream(LoggingFileName, true);
      ps = new PrintStream(fos);
      String contentToWrite = "";
      contentToWrite = CommonUtil.GetSysTime().substring(0,8)  +
                       "-" +
                       contentToWrite;
      ps.println(contentToWrite);
      pex.printStackTrace(ps);
      ps.flush();
    }catch(Exception e){
      e.printStackTrace();
      System.out.println("appendFileWrite Exception");
    }
    finally{
      try{
        if( ps != null )
          ps.close();
        if( fos != null )
          fos.close();
        }catch(Exception ex){}
    }
    return;
  }
*/

  public synchronized void eprintf(String systemname,String key,Exception pex) {
    String thrname = Thread.currentThread().getName();
    String LoggingFileName = "/home/coses/log/outlog/" + systemname + "." +
                      CommonUtil.GetHostName() + "." +
                      CommonUtil.GetSysDate();
    FileOutputStream fos = null;
    PrintStream ps = null;

    try {
      fos = new FileOutputStream(LoggingFileName, true);
      ps = new PrintStream(fos);
      String contentToWrite = "";
      contentToWrite = CommonUtil.GetSysTime().substring(0,8)  +
                       "-" + key + "|" +
                       contentToWrite;
      ps.println(contentToWrite);
      pex.printStackTrace(ps);
      ps.flush();
    }catch(Exception e){
      e.printStackTrace();
      System.out.println("appendFileWrite Exception");
    }
    finally{
      try{
        if( ps != null )
          ps.close();
        if( fos != null )
          fos.close();
        }catch(Exception ex){}
    }
    return;
  }

  public synchronized void eprintf(EPlatonEvent event , Exception pex)
  {
    if ( ((EPlatonEvent)event).getTPSVCINFODTO().getSystem_name() == null )
      ((EPlatonEvent)event).getTPSVCINFODTO().setSystem_name("NONSYSTEM");
    if( ((EPlatonEvent)event).getTPSVCINFODTO().getOrgseq() == null )
      ((EPlatonEvent)event).getTPSVCINFODTO().setOrgseq("********");

    String LoggingFileName = "/home/coses/log/outlog/" + ((EPlatonEvent)event).getTPSVCINFODTO().getSystem_name() + "." +
                      CommonUtil.GetHostName() + "." +
                      CommonUtil.GetSysDate();
    FileOutputStream fos = null;
    PrintStream ps = null;

    try {

      fos = new FileOutputStream(LoggingFileName, true);
      ps = new PrintStream(fos);
      String contentToWrite = "";
      contentToWrite = CommonUtil.GetSysTime().substring(0,8)  +
                       "-" + ((EPlatonEvent)event).getCommon().getEventNo()  +
                       "-" + ((EPlatonEvent)event).getTPSVCINFODTO().getOrgseq() + "|" +
                       contentToWrite;
      ps.println(contentToWrite);
      pex.printStackTrace(ps);
      ps.flush();
    }catch(Exception e){
      e.printStackTrace();
      System.out.println("appendFileWrite Exception");
    }
    finally{
      try{
        if( ps != null )
          ps.close();
        if( fos != null )
          fos.close();
        }catch(Exception ex){}
    }
    return;
  }

}
