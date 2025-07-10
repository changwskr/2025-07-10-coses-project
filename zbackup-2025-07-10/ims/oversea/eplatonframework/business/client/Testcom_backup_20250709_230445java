package com.ims.oversea.eplatonframework.business.client;

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

import com.ims.oversea.eplatonframework.transfer.EPlatonEvent;



public class Test {
  public static String BIZDELEGATE_CALL_PARAMETER_TYPE_NAME=EPlatonEvent.class.getName();
  public Test() {
  }
  public static void main(String[] args) {
/*
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
    */
    EVENT event1 = new EVENT();
    event1.printf();

    CALLEVENT cevent = new CALLEVENT();
    cevent.setprintf(event1);

    event1.printf();

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


class EVENT {
  public String v1 = "111";
  public String v2 = "111";

  public void printf(){
    System.out.print("v1["+v1+"]");
    System.out.println("v2["+v2+"]");
  }
}

class CALLEVENT {
  public void setprintf(EVENT event){
    event.v1 = "222";
  }
}
