package com.chb.coses.eplatonFWK.business.helper.logej;

import java.io.StringWriter;
import java.io.PrintWriter;
import org.apache.log4j.helpers.LogLog;
import org.apache.log4j.Layout;

public class FILELINE implements java.io.Serializable {

  /**
     Caller's line number.
     */
  transient String lineNumber;
  /**
     Caller's file name.
     */
  transient String fileName;
  /**
     Caller's fully qualified class name.
     */
  transient String className;
  /**
     Caller's method name.
     */
  transient String methodName;
  /**
     All available caller information, in the format
     <code>fully.qualified.classname.of.caller.methodName(Filename.java:line)</code>
     */
  public String fullInfo;

  private static StringWriter sw = new StringWriter();
  private static PrintWriter pw = new PrintWriter(sw);

  public FILELINE(Throwable t, String fqnOfCallingClass) {
    if(t == null)
      return;

    String s;
    // Protect against multiple access to sw.
    synchronized(sw) {
      t.printStackTrace();

      System.out.println("pw:------"+pw.toString() );
      t.printStackTrace(pw);
      s = sw.toString();

      System.out.println("s:------"+sw.toString() );

      sw.getBuffer().setLength(0);
    }
    System.out.println("s is ["+s+"]"+fqnOfCallingClass);
    int ibegin, iend;


  }

}