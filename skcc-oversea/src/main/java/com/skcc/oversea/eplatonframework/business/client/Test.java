package com.skcc.oversea.eplatonframework.business.client;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */
import java.lang.reflect.*;
import java.util.*;
import java.rmi.*;

import com.skcc.oversea.eplatonframework.transfer.EPlatonEvent;


public class Test {
  public static String BIZDELEGATE_CALL_PARAMETER_TYPE_NAME=EPlatonEvent.class.getName();
  public Test() {
  }
  public static void main(String[] args) {
    Test test1 = new Test();
    String s1 ="";
    String s2 = "";
    String path_filename = args[0];

    int lastSlash = path_filename.lastIndexOf('-');
    s1 = path_filename.substring(lastSlash+1);
    s2 = path_filename.substring(0, lastSlash);

    System.out.println("s1 "+s1+" s2 "+s2 + " ==" + Test.BIZDELEGATE_CALL_PARAMETER_TYPE_NAME);

    EPlatonEvent event = new EPlatonEvent();
    C1 c1 = new C1();
    main2(c1,event);
  }

  public static void main2(Object target, EPlatonEvent event)
  {
    String methodName = target.getClass().getMethods().toString();

    Method meth[] = target.getClass().getMethods();


    System.out.println("["+methodName+"]");

  }

}


class C1 {
  public String execute(String io){
    return null;
  }
}

