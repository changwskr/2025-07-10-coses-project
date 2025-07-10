package com.chb.coses.eplatonFWK.business.helper.logej;

import java.io.StringWriter;
import java.io.PrintWriter;
import org.apache.log4j.helpers.LogLog;
import org.apache.log4j.Layout;


/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class LocationInfoTest
{

  public LocationInfoTest()
  {
  }
  public void exec(){
    FILELINE li  = new FILELINE(new Exception(), this.getClass().getName());
  }
  public static void main(String[] args)
  {
    LocationInfoTest locationInfoTest1 = new LocationInfoTest();
    locationInfoTest1.exec();
  }
}



