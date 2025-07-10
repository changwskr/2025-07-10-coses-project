package com.ims.oversea.foundation.config;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */
import java.net.*;
import java.io.*;
import java.util.*;
import com.chb.coses.foundation.base.*;

/**
 * 사용예]
 * XMLconfig.getInstance().GetEnvValue("SERVICE","MAX_TIME_OUT")
 * XMLconfig.getInstance().GetEnvValue("SERVICE","SERVICE01","MAX_THR_CNT")
 * 파일예
 *
 <ENVIRONMENT>
   <SERVICE>
                <SERVICECNT>1</SERVICECNT>
                <MAX_TIME_OUT>305000</MAX_TIME_OUT>
                <SERVICE01>
                        <PORT>45530</PORT>
                        <MAX_THR_CNT>20</MAX_THR_CNT>
                        <LOG>/home/cosif/atmppp/log/ATMserver.45530.out</LOG>
                        <BOOTINFO>M</BOOTINFO>
                </SERVICE01>
                <SERVICE02>
                        <PORT>45531</PORT>
                        <MAX_THR_CNT>2</MAX_THR_CNT>
                        <LOG>/home/cosif/atmppp/log/ATMserver.45530.out</LOG>
                        <BOOTINFO>M</BOOTINFO>
                </SERVICE02>
                <SERVICE03>
                        <PORT>45532</PORT>
                        <MAX_THR_CNT>2</MAX_THR_CNT>
                        <LOG>/home/cosif/atmppp/log/ATMserver.45530.out</LOG>
                        <BOOTINFO>M</BOOTINFO>
                </SERVICE03>
        </SERVICE>
 </ENVIRONMENT>
 *
 */

public class XMLconfig {
  private static XMLconfig instance;
  private static String xmlfile = "conf.file";

  public static synchronized XMLconfig getInstance() {
    if (instance == null) {
      try{
        instance = new XMLconfig();
        XMLconfig.xmlfile = System.getProperty(XMLconfig.xmlfile);
        if( XMLconfig.xmlfile == null )
          XMLconfig.xmlfile = com.ims.oversea.foundation.constant.Constants.CONFIG_FILE_NAME ;
        }catch(Exception igex){}
    }
    return instance;
  }

  /**
   * XMLconfig.xmlfile = xml환경 파일명
   *
   * @param ele1 - level 1값
   * @param ele2 - level 2값
   * @return
   */
  public String GetEnvValue(String ele1,String ele2){
    return XMLCache.getInstance().getXML(XMLconfig.xmlfile).getRootElement().getChild(ele1).getChildTextTrim(ele2);
  }

  /**
   * XMLconfig.xmlfile = xml환경 파일명
   *
   * @param ele1 - level 1값
   * @param ele2 - level 2값
   * @param ele3 - level 3값
   * @return
   */

  public String GetEnvValue(String ele1,String ele2,String ele3){
    return XMLCache.getInstance().getXML(XMLconfig.xmlfile).getRootElement().getChild(ele1).getChild(ele2).getChildTextTrim(ele3);
  }

  /**
   * XMLconfig.xmlfile = xml환경 파일명
   * @param ele1
   * @param ele2
   * @param ele3
   * @param ele4
   * @return
   */
  public String GetEnvValue(String ele1,String ele2,String ele3,String ele4){
    return XMLCache.getInstance().getXML(XMLconfig.xmlfile).getRootElement().getChild(ele1).getChild(ele2).getChild(ele3).getChildTextTrim(ele4);
  }


  public XMLconfig() {
  }

}



