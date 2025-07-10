package com.ims.eplaton.foundation.helper.logej;

import java.io.*;
import java.rmi.*;
import java.util.*;
import java.net.*;
import com.ims.eplaton.foundation.helper.CommonUtil;
import com.ims.eplaton.foundation.helper.FILEapi;
import com.ims.eplaton.eplatonFWK.transfer.EPlatonEvent;

/**
 * <PRE>
 * Class    : LOGJ
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
  public static String sHostName=CommonUtil.GetHostName();
  public static String sLog_file_name="/home/coses/env/logej.properties";
  public static long omode = FILEapi.FILElastmodified(sLog_file_name );;
  public static int pint = CommonUtil.Str2Int(LOGEJ.getInstance().GetEnvValue("LOGGING_LEVEL_MODE"));
  public static int dint = CommonUtil.Str2Int(LOGEJ.getInstance().GetEnvValue("LOGGING_DEBUG_MODE"));

  public LOGEJ() throws IOException,NotBoundException {

    if( ! FILEapi.FILEexist(sLog_file_name) ) {
      println(sLog_file_name,"#######################################\n"+
                             "# NOLOG   : 4                          \n"+
                             "# INFO    : 1                          \n"+
                             "# DEBUG   : 2                          \n"+
                             "# FATAL   : 10                         \n"+
                             "#######################################\n"+
                             "LOGGING_LEVEL_MODE=1\n"+
                             "LOGGING_DEBUG_MODE=4\n");
    }
  }

  public static synchronized LOGEJ getInstance() {
    if (instance == null) {
      try{
        instance = new LOGEJ();
      }
      catch(Exception igex){
        igex.printStackTrace();
      }
    }
    return instance;
  }

  public synchronized static String GetEnvValue(String tmp)
  {
    String ConfigPath=null;
    Properties p = new Properties();
    String tmpv = null;
    try {
      p.load(new FileInputStream(sLog_file_name));
      tmpv=p.getProperty(tmp);
    }
    catch (IOException e) {
      System.out.println("GetEnvValue() 에서 환경셋팅시12 예외발생" );
      e.printStackTrace();
    }
    return tmpv;
  }

  public synchronized void printf(int mode,
                                  String systemname,
                                  String eventno,
                                  String key,
                                  String contentToWrite)
  {
    String LoggingFileName = "/home/coses/log/outlog/" +
                             systemname + "." +
                             sHostName + "." +
                             CommonUtil.GetSysDate();

    FileOutputStream fos = null;
    PrintStream ps = null;

    try {
      long nmode = FILEapi.FILElastmodified(sLog_file_name);
      if( nmode != LOGEJ.getInstance().omode ){
        LOGEJ.getInstance().dint = CommonUtil.Str2Int(LOGEJ.getInstance().GetEnvValue("JTMDEBUGIN_MODE"));
        LOGEJ.getInstance().omode = FILEapi.FILElastmodified(sLog_file_name);
      }
      if( !(mode >= LOGEJ.getInstance().pint) ){
        return;
      }

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

  public synchronized void printf(int mode,EPlatonEvent event,String contentToWrite)
  {
    if ( ((EPlatonEvent)event).getTPSVCINFODTO().getSystem_name() == null )
      ((EPlatonEvent)event).getTPSVCINFODTO().setSystem_name("NONSYSTEM");
    if( ((EPlatonEvent)event).getTPSVCINFODTO().getOrgseq() == null )
      ((EPlatonEvent)event).getTPSVCINFODTO().setOrgseq("********");

    String LoggingFileName = "/home/coses/log/outlog/" +
                             ((EPlatonEvent)event).getTPSVCINFODTO().getSystem_name()  + "." +
                             sHostName + "." +
                             CommonUtil.GetSysDate();
    FileOutputStream fos = null;
    PrintStream ps = null;

    try {
      long nmode = FILEapi.FILElastmodified(sLog_file_name);
      if( nmode != LOGEJ.getInstance().omode ){
        LOGEJ.getInstance().dint = CommonUtil.Str2Int(LOGEJ.getInstance().GetEnvValue("JTMDEBUGIN_MODE"));
        LOGEJ.getInstance().omode = FILEapi.FILElastmodified(sLog_file_name);
      }
      if( !(mode >= LOGEJ.getInstance().pint) ){
        return;
      }

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
                             sHostName + "." +
                             CommonUtil.GetSysDate();
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

  public synchronized void nohead_printf(int mode,EPlatonEvent event,String contentToWrite)
  {
    if ( ((EPlatonEvent)event).getTPSVCINFODTO().getSystem_name() == null )
      ((EPlatonEvent)event).getTPSVCINFODTO().setSystem_name("NONSYSTEM");
    if( ((EPlatonEvent)event).getTPSVCINFODTO().getOrgseq() == null )
      ((EPlatonEvent)event).getTPSVCINFODTO().setOrgseq("********");

    String LoggingFileName = "/home/coses/log/outlog/" +
                             ((EPlatonEvent)event).getTPSVCINFODTO().getSystem_name()  + "." +
                             sHostName + "." +
                             CommonUtil.GetSysDate();
    FileOutputStream fos = null;
    PrintStream ps = null;

    try {
      long nmode = FILEapi.FILElastmodified(sLog_file_name);
      if( nmode != LOGEJ.getInstance().omode ){
        LOGEJ.getInstance().dint = CommonUtil.Str2Int(LOGEJ.getInstance().GetEnvValue("JTMDEBUGIN_MODE"));
        LOGEJ.getInstance().omode = FILEapi.FILElastmodified(sLog_file_name);
      }
      if( !(mode >= LOGEJ.getInstance().dint) ){
        return;
      }

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

  public synchronized void print(int mode,EPlatonEvent event,String contentToWrite)
  {
    if ( ((EPlatonEvent)event).getTPSVCINFODTO().getSystem_name() == null )
      ((EPlatonEvent)event).getTPSVCINFODTO().setSystem_name("NONSYSTEM");
    if( ((EPlatonEvent)event).getTPSVCINFODTO().getOrgseq() == null )
      ((EPlatonEvent)event).getTPSVCINFODTO().setOrgseq("********");

    String LoggingFileName = "/home/coses/log/outlog/" + ((EPlatonEvent)event).getTPSVCINFODTO().getSystem_name()  + "." +
                             sHostName + "." + CommonUtil.GetSysDate();
    FileOutputStream fos = null;
    PrintStream ps = null;

    try {

      long nmode = FILEapi.FILElastmodified(sLog_file_name);
      if( nmode != LOGEJ.getInstance().omode ){
        LOGEJ.getInstance().dint = CommonUtil.Str2Int(LOGEJ.getInstance().GetEnvValue("JTMDEBUGIN_MODE"));
        LOGEJ.getInstance().omode = FILEapi.FILElastmodified(sLog_file_name);
      }
      if( !(mode >= LOGEJ.getInstance().pint) ){
        return;
      }

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

  public synchronized void eprintf(int mode,String systemname,String key,Exception pex) {
    String thrname = Thread.currentThread().getName();
    String LoggingFileName = "/home/coses/log/outlog/" +
                             systemname + "." +
                             sHostName + "." +
                             CommonUtil.GetSysDate();
    FileOutputStream fos = null;
    PrintStream ps = null;

    try {

      long nmode = FILEapi.FILElastmodified(sLog_file_name);
      if( nmode != LOGEJ.getInstance().omode ){
        LOGEJ.getInstance().dint = CommonUtil.Str2Int(LOGEJ.getInstance().GetEnvValue("JTMDEBUGIN_MODE"));
        LOGEJ.getInstance().omode = FILEapi.FILElastmodified(sLog_file_name);
      }
      if( !(mode >= LOGEJ.getInstance().dint) ){
        return;
      }

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

  public synchronized void eprintf(int mode,EPlatonEvent event , Exception pex)
  {
    if ( ((EPlatonEvent)event).getTPSVCINFODTO().getSystem_name() == null )
      ((EPlatonEvent)event).getTPSVCINFODTO().setSystem_name("NONSYSTEM");
    if( ((EPlatonEvent)event).getTPSVCINFODTO().getOrgseq() == null )
      ((EPlatonEvent)event).getTPSVCINFODTO().setOrgseq("********");

    String LoggingFileName = "/home/coses/log/outlog/" +
                             ((EPlatonEvent)event).getTPSVCINFODTO().getSystem_name() + "." +
                             sHostName + "." +
                             CommonUtil.GetSysDate();
    FileOutputStream fos = null;
    PrintStream ps = null;

    try {

      long nmode = FILEapi.FILElastmodified(sLog_file_name);
      if( nmode != LOGEJ.getInstance().omode ){
        LOGEJ.getInstance().dint = CommonUtil.Str2Int(LOGEJ.getInstance().GetEnvValue("JTMDEBUGIN_MODE"));
        LOGEJ.getInstance().omode = FILEapi.FILElastmodified(sLog_file_name);
      }
      if( !(mode >= LOGEJ.getInstance().dint) ){
        return;
      }

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
      }
      catch(Exception ex){}
    }
    return;
  }

  public void println(String filename,String contentToWrite)
  {
    String LoggingFileName = filename;
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


}