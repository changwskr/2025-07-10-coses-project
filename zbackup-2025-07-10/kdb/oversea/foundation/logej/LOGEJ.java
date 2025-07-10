package com.kdb.oversea.foundation.logej;

import java.io.*;
import java.rmi.*;
import java.util.*;
import java.net.*;

import com.chb.coses.cosesFramework.exception.CosesAppException;
import com.kdb.oversea.foundation.utility.CommonUtil;
import com.kdb.oversea.foundation.utility.FILEapi;
import com.kdb.oversea.eplatonframework.transfer.EPlatonEvent;
import com.kdb.oversea.foundation.constant.Constants;
import com.kdb.oversea.framework.transaction.tcf.STF;
import com.chb.coses.foundation.base.*;
import com.chb.coses.foundation.config.Config;

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
  public static String sHostName=CommonUtil.GetHostName();
  public static String sLog_file_name=
      "/weblogic/bea/wlserver6.1/config/coses_US/applicationConfig/maps/epllogej.xml";

  public LOGEJ() throws IOException,NotBoundException {
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


  public synchronized void printf(int mode,
                                  String systemname,
                                  String eventno,
                                  String key,
                                  String contentToWrite)
  {
    String LoggingFileName = Constants.log_directory_path  +
                             systemname + "." +
                             sHostName + "." +
                             CommonUtil.GetSysDate();

    FileOutputStream fos = null;
    PrintStream ps = null;

    String log_system_tag = "epllogej";
    String print_mode_tag = "print-mode";
    String error_mode_tag = "error-mode";
    String key_print_mode_name = null;
    try {
      key_print_mode_name = log_system_tag + "-" +
                            systemname + "." +
                            print_mode_tag;

      int pint = CommonUtil.Str2Int( LOGEJgeterrorloglvel(systemname) );
      if( !(mode >= pint) ){
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

    String LoggingFileName = Constants.log_directory_path +
                             ((EPlatonEvent)event).getTPSVCINFODTO().getSystem_name()  + "." +
                             sHostName + "." +
                             CommonUtil.GetSysDate();

    FileOutputStream fos = null;
    PrintStream ps = null;

    String log_system_tag = "epllogej";
    String print_mode_tag = "print-mode";
    String error_mode_tag = "error-mode";
    String key_print_mode_name = null;

    try {
      key_print_mode_name = log_system_tag + "-" +
                            event.getTPSVCINFODTO().getSystem_name() + "." +
                            print_mode_tag;

      int pint = CommonUtil.Str2Int( LOGEJgetprntloglvel(event.getTPSVCINFODTO().getSystem_name()) );
      if( !(mode >= pint) ){
        return;
      }

      fos = new FileOutputStream(LoggingFileName, true);
      ps = new PrintStream(fos);
      contentToWrite = CommonUtil.GetSysTime().substring(0,8)  +
                     //"-" + ((EPlatonEvent)event).getCommon().getEventNo()  +
                     "-" + ((EPlatonEvent)event).getTPSVCINFODTO().getReqName()  +
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

    String LoggingFileName = Constants.log_directory_path + "tpmtxlog"   + "." +
                             sHostName + "." +
                             CommonUtil.GetSysDate();
    FileOutputStream fos = null;
    PrintStream ps = null;

    try {

      fos = new FileOutputStream(LoggingFileName, true);
      ps = new PrintStream(fos);
      contentToWrite = CommonUtil.GetSysTime().substring(0,8)  +
                     //"-" + ((EPlatonEvent)event).getCommon().getEventNo()  +
                     "-" + ((EPlatonEvent)event).getTPSVCINFODTO().getReqName()  +
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

    String LoggingFileName = com.kdb.oversea.framework.transaction.constant.TCFConstants.TPMTRACE_LoggingFileName  + "." +
                             CommonUtil.GetHostName() + "." + CommonUtil.GetSysDate();
    FileOutputStream fos = null;
    PrintStream ps = null;

    try {
      StringTokenizer operation_name = new StringTokenizer(((EPlatonEvent)event).getTPSVCINFODTO().getOperation_name(),".");
      String opn = null;
      while( operation_name.hasMoreElements()){
        opn =(String)operation_name.nextElement();
      }
      operation_name = new StringTokenizer(((EPlatonEvent)event).getTPSVCINFODTO().getOperation_method(),".");
      String apn = null;
      while( operation_name.hasMoreElements()){
        apn =(String)operation_name.nextElement();
      }
      fos = new FileOutputStream(LoggingFileName, true);
      ps = new PrintStream(fos);
      contentToWrite = CommonUtil.GetHostName() + ":" +
                       CommonUtil.GetSysTime().substring(0,8)  + ":" +
                       ((EPlatonEvent)event).getTPSVCINFODTO().getOrgseq() + "!" +
                       CommonUtil.PSpaceToStr((( EPlatonEvent)event).getTPSVCINFODTO().getSystem_name(),20) + ":" +
                       CommonUtil.PSpaceToStr(apn,20) + ":" +
                       CommonUtil.PSpaceToStr(opn,20) + "!" +
                       contentToWrite;
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

    String LoggingFileName = Constants.log_directory_path +"tpmtxlog"   + "." +
                             CommonUtil.GetHostName() + "." + CommonUtil.GetSysDate();
    FileOutputStream fos = null;
    PrintStream ps = null;

    try {
      fos = new FileOutputStream(LoggingFileName, true);
      ps = new PrintStream(fos);
      contentToWrite = "\n" + CommonUtil.GetSysTime().substring(0,8)  +
                     //"-" + ((EPlatonEvent)event).getCommon().getEventNo()  +
                     "-" + ((EPlatonEvent)event).getTPSVCINFODTO().getReqName()  +
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

    String LoggingFileName = Constants.log_directory_path +
                             ((EPlatonEvent)event).getTPSVCINFODTO().getSystem_name()  + "." +
                             sHostName + "." +
                             CommonUtil.GetSysDate();
    FileOutputStream fos = null;
    PrintStream ps = null;

    String log_system_tag = "epllogej";
    String print_mode_tag = "print-mode";
    String error_mode_tag = "error-mode";
    String key_print_mode_name = null;
    try {
      key_print_mode_name = log_system_tag + "-" +
                            event.getTPSVCINFODTO().getSystem_name() + "." +
                            print_mode_tag;

      int pint = CommonUtil.Str2Int( LOGEJgetprntloglvel(event.getTPSVCINFODTO().getSystem_name()) );
      if( !(mode >= pint) ){
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

    String LoggingFileName = Constants.log_directory_path+"tpmtxlog"   + "." +
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

    String LoggingFileName = Constants.log_directory_path + ((EPlatonEvent)event).getTPSVCINFODTO().getSystem_name()  + "." +
                             sHostName + "." + CommonUtil.GetSysDate();
    FileOutputStream fos = null;
    PrintStream ps = null;

    String log_system_tag = "epllogej";
    String print_mode_tag = "print-mode";
    String error_mode_tag = "error-mode";
    String key_print_mode_name = null;
    try {
      key_print_mode_name = log_system_tag + "-" +
                            event.getTPSVCINFODTO().getSystem_name() + "." +
                            print_mode_tag;

      int pint = CommonUtil.Str2Int( LOGEJgetprntloglvel(event.getTPSVCINFODTO().getSystem_name()) );
      if( !(mode >= pint) ){
        return;
      }

      fos = new FileOutputStream(LoggingFileName, true);
      ps = new PrintStream(fos);
      contentToWrite = CommonUtil.GetSysTime().substring(0,8)  +
                     //"-" + ((EPlatonEvent)event).getCommon().getEventNo()  +
                     "-" + ((EPlatonEvent)event).getTPSVCINFODTO().getReqName()  +
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

    String LoggingFileName = Constants.log_directory_path+"tpmtxlog"   + "." +
                             CommonUtil.GetHostName() + "." + CommonUtil.GetSysDate();
    FileOutputStream fos = null;
    PrintStream ps = null;

    try {
      fos = new FileOutputStream(LoggingFileName, true);
      ps = new PrintStream(fos);
      contentToWrite = CommonUtil.GetSysTime().substring(0,8)  +
                     //"-" + ((EPlatonEvent)event).getCommon().getEventNo()  +
                     "-" + ((EPlatonEvent)event).getTPSVCINFODTO().getReqName()  +
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
    String LoggingFileName = Constants.log_directory_path +
                             systemname + "." +
                             sHostName + "." +
                             CommonUtil.GetSysDate();
    FileOutputStream fos = null;
    PrintStream ps = null;

    String log_system_tag = "epllogej";
    String print_mode_tag = "print-mode";
    String error_mode_tag = "error-mode";
    String key_print_mode_name = null;
    try {
      key_print_mode_name = log_system_tag + "-" +
                            systemname + "." +
                            error_mode_tag;

      int pint = CommonUtil.Str2Int( LOGEJgeterrorloglvel(systemname) );
      if( !(mode >= pint) ){
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

    String LoggingFileName = Constants.log_directory_path +
                             ((EPlatonEvent)event).getTPSVCINFODTO().getSystem_name() + "." +
                             sHostName + "." +
                             CommonUtil.GetSysDate();
    FileOutputStream fos = null;
    PrintStream ps = null;

    String log_system_tag = "epllogej";
    String print_mode_tag = "print-mode";
    String error_mode_tag = "error-mode";
    String key_print_mode_name = null;
    try {
      key_print_mode_name = log_system_tag + "-" +
                            event.getTPSVCINFODTO().getSystem_name() + "." +
                            error_mode_tag;

      int pint = CommonUtil.Str2Int( LOGEJgeterrorloglvel(event.getTPSVCINFODTO().getSystem_name()) );
      if( !(mode >= pint) ){
        return;
      }

      fos = new FileOutputStream(LoggingFileName, true);
      ps = new PrintStream(fos);
      String contentToWrite = "";
      contentToWrite = CommonUtil.GetSysTime().substring(0,8)  +
                     //"-" + ((EPlatonEvent)event).getCommon().getEventNo()  +
                     "-" + ((EPlatonEvent)event).getTPSVCINFODTO().getReqName()  +
                       "-" + ((EPlatonEvent)event).getTPSVCINFODTO().getOrgseq() + "|" +
                       contentToWrite;
      //ps.println(contentToWrite);
      ps.print(contentToWrite);
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

  public synchronized void printf(int mode,EPlatonEvent event , Exception pex)
  {
    if ( ((EPlatonEvent)event).getTPSVCINFODTO().getSystem_name() == null )
      ((EPlatonEvent)event).getTPSVCINFODTO().setSystem_name("NONSYSTEM");
    if( ((EPlatonEvent)event).getTPSVCINFODTO().getOrgseq() == null )
      ((EPlatonEvent)event).getTPSVCINFODTO().setOrgseq("********");

    String LoggingFileName = Constants.log_directory_path +
                             ((EPlatonEvent)event).getTPSVCINFODTO().getSystem_name() + "." +
                             sHostName + "." +
                             CommonUtil.GetSysDate();
    FileOutputStream fos = null;
    PrintStream ps = null;

    String log_system_tag = "epllogej";
    String print_mode_tag = "print-mode";
    String error_mode_tag = "error-mode";
    String key_print_mode_name = null;
    try {
      key_print_mode_name = log_system_tag + "-" +
                            event.getTPSVCINFODTO().getSystem_name() + "." +
                            error_mode_tag;

      int pint = CommonUtil.Str2Int( LOGEJgeterrorloglvel(event.getTPSVCINFODTO().getSystem_name()) );
      if( !(mode >= pint) ){
        return;
      }

      fos = new FileOutputStream(LoggingFileName, true);
      ps = new PrintStream(fos);
      String contentToWrite = "";
      contentToWrite = CommonUtil.GetSysTime().substring(0,8)  +
                     //"-" + ((EPlatonEvent)event).getCommon().getEventNo()  +
                     "-" + ((EPlatonEvent)event).getTPSVCINFODTO().getReqName()  +
                       "-" + ((EPlatonEvent)event).getTPSVCINFODTO().getOrgseq() + "|" +
                       contentToWrite;
//      ps.println(contentToWrite);
      ps.print(contentToWrite);
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


  public synchronized void eprintf(int mode,EPlatonEvent event , CosesAppException pex)
  {
    if ( ((EPlatonEvent)event).getTPSVCINFODTO().getSystem_name() == null )
      ((EPlatonEvent)event).getTPSVCINFODTO().setSystem_name("NONSYSTEM");
    if( ((EPlatonEvent)event).getTPSVCINFODTO().getOrgseq() == null )
      ((EPlatonEvent)event).getTPSVCINFODTO().setOrgseq("********");

    String LoggingFileName = Constants.log_directory_path +
                             ((EPlatonEvent)event).getTPSVCINFODTO().getSystem_name() + "." +
                             sHostName + "." +
                             CommonUtil.GetSysDate();
    FileOutputStream fos = null;
    PrintStream ps = null;

    String log_system_tag = "epllogej";
    String print_mode_tag = "print-mode";
    String error_mode_tag = "error-mode";
    String key_print_mode_name = null;
    try {
      key_print_mode_name = log_system_tag + "-" +
                            event.getTPSVCINFODTO().getSystem_name() + "." +
                            error_mode_tag;

      int pint = CommonUtil.Str2Int( LOGEJgeterrorloglvel(event.getTPSVCINFODTO().getSystem_name()) );
      if( !(mode >= pint) ){
        return;
      }

      fos = new FileOutputStream(LoggingFileName, true);
      ps = new PrintStream(fos);
      String contentToWrite = "";
      contentToWrite = CommonUtil.GetSysTime().substring(0,8)  +
                       //"-" + ((EPlatonEvent)event).getCommon().getEventNo()  +
                       "-" + ((EPlatonEvent)event).getTPSVCINFODTO().getReqName()  +
                       "-" + ((EPlatonEvent)event).getTPSVCINFODTO().getOrgseq() + "|" +
                       contentToWrite;
      //ps.println(contentToWrite);
      ps.print(contentToWrite);
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

  public synchronized void printf(int mode,EPlatonEvent event , CosesAppException pex)
  {
    if ( ((EPlatonEvent)event).getTPSVCINFODTO().getSystem_name() == null )
      ((EPlatonEvent)event).getTPSVCINFODTO().setSystem_name("NONSYSTEM");
    if( ((EPlatonEvent)event).getTPSVCINFODTO().getOrgseq() == null )
      ((EPlatonEvent)event).getTPSVCINFODTO().setOrgseq("********");

    String LoggingFileName = Constants.log_directory_path +
                             ((EPlatonEvent)event).getTPSVCINFODTO().getSystem_name() + "." +
                             sHostName + "." +
                             CommonUtil.GetSysDate();
    FileOutputStream fos = null;
    PrintStream ps = null;

    String log_system_tag = "epllogej";
    String print_mode_tag = "print-mode";
    String error_mode_tag = "error-mode";
    String key_print_mode_name = null;
    try {
      key_print_mode_name = log_system_tag + "-" +
                            event.getTPSVCINFODTO().getSystem_name() + "." +
                            error_mode_tag;

      int pint = CommonUtil.Str2Int( LOGEJgeterrorloglvel(event.getTPSVCINFODTO().getSystem_name()) );
      if( !(mode >= pint) ){
        return;
      }

      fos = new FileOutputStream(LoggingFileName, true);
      ps = new PrintStream(fos);
      String contentToWrite = "";
      contentToWrite = CommonUtil.GetSysTime().substring(0,8)  +
                     //"-" + ((EPlatonEvent)event).getCommon().getEventNo()  +
                     "-" + ((EPlatonEvent)event).getTPSVCINFODTO().getReqName()  +
                       "-" + ((EPlatonEvent)event).getTPSVCINFODTO().getOrgseq() + "|" +
                       contentToWrite;
      //ps.println(contentToWrite);
      ps.print(contentToWrite);
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

  /**
   1.epllogej.xml 파일정보

   <epllogej-common>
        <print-mode>3</print-mode>
        <error-mode>5</error-mode>
   </epllogej-common>

   <epllogej-deposit>
        <print-mode>3</print-mode>
        <error-mode>5</error-mode>
   </epllogej-deposit>

   * @return
   */

  public String LOGEJgetprntloglvel(String psystem_name)
  {
    String configFileName = null;
    String call_operation_tag = null;
    String actionClassName = null;
    String log_system_tag = "epllogej";
    String print_mode_tag = "print-mode";
    String error_mode_tag = "error-mode";
    String key_print_mode_name_value = null;
    String key_error_mode_name_value = null;
    String key_print_mode_name = null;
    String key_error_mode_name = null;
    String system_name = psystem_name;

    try{
      //epllogej.xml파일을 읽기위한 기본준비를 한다.
      configFileName = Config.getInstance().getElement(com.kdb.oversea.foundation.constant.Constants.BIZDELEGATE_TAG).getTextTrim();
      call_operation_tag = log_system_tag + "-" +  system_name;

      key_print_mode_name_value = XMLCache.getInstance().getXML(configFileName).getRootElement().getChild(call_operation_tag).getChildTextTrim(print_mode_tag);
      key_error_mode_name_value = XMLCache.getInstance().getXML(configFileName).getRootElement().getChild(call_operation_tag).getChildTextTrim(error_mode_tag);

      key_print_mode_name = call_operation_tag + "." + print_mode_tag;
      key_error_mode_name = call_operation_tag + "." + error_mode_tag;
      /*
      System.out.println("key_print_mode_name_value " + key_print_mode_name_value + ":" + key_print_mode_name);
      System.out.println("key_error_mode_name_value " + key_error_mode_name_value + ":" + key_error_mode_name);
      */
      return key_print_mode_name_value;
    }
    catch(Exception ex){
      ex.printStackTrace();
    }
    return "1";
  }


  public String LOGEJgeterrorloglvel(String psystem_name)
  {
    String configFileName = null;
    String call_operation_tag = null;
    String actionClassName = null;
    String log_system_tag = "epllogej";
    String print_mode_tag = "print-mode";
    String error_mode_tag = "error-mode";
    String key_print_mode_name_value = null;
    String key_error_mode_name_value = null;
    String key_print_mode_name = null;
    String key_error_mode_name = null;
    String system_name = psystem_name;

    try{
      //epllogej.xml파일을 읽기위한 기본준비를 한다.
      configFileName = Config.getInstance().getElement(Constants.BIZDELEGATE_TAG).getTextTrim();
      call_operation_tag = log_system_tag + "-" +  system_name;

      key_print_mode_name_value = XMLCache.getInstance().getXML(configFileName).getRootElement().getChild(call_operation_tag).getChildTextTrim(print_mode_tag);
      key_error_mode_name_value = XMLCache.getInstance().getXML(configFileName).getRootElement().getChild(call_operation_tag).getChildTextTrim(error_mode_tag);

      key_print_mode_name = call_operation_tag + "." + print_mode_tag;
      key_error_mode_name = call_operation_tag + "." + error_mode_tag;
      /*
      System.out.println("key_print_mode_name_value " + key_print_mode_name_value + ":" + key_print_mode_name);
      System.out.println("key_error_mode_name_value " + key_error_mode_name_value + ":" + key_error_mode_name);
      */
      return key_error_mode_name_value;
    }
    catch(Exception ex){
      ex.printStackTrace();
    }
    return "1";
  }


}