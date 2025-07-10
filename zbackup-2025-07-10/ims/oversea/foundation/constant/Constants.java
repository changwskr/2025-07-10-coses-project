package com.ims.oversea.foundation.constant;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class Constants {
  public static String CONFIG_FILE_NAME=
      "/weblogic/bea/wlserver6.1/config/coses_US/applicationConfig/EPLconfig.xml";
  public static String call_host_ip="21.101.3.47";
  public static String call_host_port="7001";
  public static String call_url="t3://"+call_host_ip+":"+call_host_port;
  public static String log_directory_path = "/home/coses/log/outlog/";
  public static String log_level_filep_path="/home/coses/env/logej.properties";
  public static final String BIZDELEGATE_TAG = "bizaction-map-filename";
}