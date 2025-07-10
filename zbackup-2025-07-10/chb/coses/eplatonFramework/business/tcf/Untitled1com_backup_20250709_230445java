package com.chb.coses.eplatonFramework.business.tcf;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

class sub1 {
  public int sv1=0;
}

class sub2 {
  public int sv2=0;
}

class tpsvcinfo {
  int a1=1;
  public sub1 s1=new sub1();
  public sub2 s2=new sub2();
}


public class Untitled1
{

  public Untitled1()
  {
  }
  public tpsvcinfo execute(tpsvcinfo ti) {
    sub1 s1 = ti.s1;
    sub2 s2 = ti.s2;
    System.out.println("ti.s1.sv1:"+ti.s1.sv1  + " " + "ti.s2.sv2:"+ti.s2.sv2 + " s1.sv1:"+s1.sv1  + " " + "s2.sv2:"+s2.sv2 );

    s1.sv1 = 1;
    s2.sv2 = 1;
    System.out.println("ti.s1.sv1:"+ti.s1.sv1  + " " + "ti.s2.sv2:"+ti.s2.sv2 + " s1.sv1:"+s1.sv1  + " " + "s2.sv2:"+s2.sv2 );

    return ti;

  }
  public static void main(String[] args)
  {
    Untitled1 aa = new Untitled1();
    tpsvcinfo ti = new tpsvcinfo();
    aa.execute(ti);
    System.out.println("ti.s1.sv1:"+ti.s1.sv1  + " " + "ti.s2.sv2:"+ti.s2.sv2 );

  }
}

