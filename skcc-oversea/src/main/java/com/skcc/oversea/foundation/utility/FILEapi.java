package com.skcc.oversea.foundation.utility;

import java.io.*;
import java.text.*;
import java.util.*;

/**
 * <PRE>
 * Class    : CifFILEapi
 * Function :
 * Comment  : Log File Writing <BR>
 *            <BR>
 *            <BR>
 * </PRE>
 * @version : 1.0
 * @author  : 장  우  승
 */

public class FILEapi {


  public static final String FILEabsolutpath(String fname)
  {
    String strtmp=null;

    try {
      File f = new File(fname);

      if(f.exists());
      else
        throw new IOException(fname + "Not Found");

      strtmp = f.getAbsolutePath();

    }
    catch(Exception ex) {
      System.out.println("FILEabsolutpath Exception :: " + ex.getMessage() );
      System.out.println("FILEabsolutpath FileName  :: " + fname );
      return null;
    }

    return strtmp;
  }


  // @ Comment : 파일의 상태가 바뀐것을 체킹한다.
  //
  public static final long FILElastmodified ( String fname )
  {

    long strtmp = -1;

    try {

      File f = new File(fname);

      if(f.exists());
      else
        throw new IOException(fname + "Not Found");

      strtmp = f.lastModified();

    }
    catch(Exception ex) {
      System.out.println("FILEabsolutpath Exception :: " + ex.getMessage() );
      System.out.println("FILEabsolutpath FileName  :: " + fname );
      return -1;
    }

    return strtmp;

  }

  public static final boolean DIRmake(String fname)
  {
    try {
      File f = new File(fname);

      if(f.exists()) {
        throw new IOException(fname + "DIR is already existing");
      }
      f.mkdirs();
      if(!f.isDirectory())
        throw new IOException(fname + "DIR does not make dir");
    }
    catch(Exception ex) {
      System.out.println("DIRmake Exception :: " + ex.getMessage() );
      System.out.println("DIRmake FileName  :: " + fname );
      return false;
    }
    return true;

  }


  public static final long FILEsize(String fname)
  {
    long strtmp=-1;

    try {
      File f = new File(fname);

      if(f.exists());
      else
        throw new IOException(fname + "Not Found");
      strtmp=f.length();
    }
    catch(Exception ex) {
      System.out.println("FILEabsolutpath Exception :: " + ex.getMessage() );
      System.out.println("FILEabsolutpath FileName  :: " + fname );
      return -1;
    }
    return strtmp;
  }


  public static final boolean FILEexist(String fname)
  {
    try {
      File f = new File(fname);

      if(f.exists());
      else
        return false;
    }
    catch(Exception ex) {
      System.out.println("FILEexist Exception :: " + ex.getMessage() );
      System.out.println("FILEexit FileName  :: " + fname );
      return false;
    }
    return true;
  }


  public static final boolean FILEdel(String fname)
  {
    try {
      File f = new File(fname);

      if(f.exists());
      else
        return false;

      f.delete();
    }
    catch(Exception ex) {
      System.out.println("FILEdel Exception :: " + ex.getMessage() );
      System.out.println("FILEdel FileName  :: " + fname );
      return false;
    }
    return true;
  }


  public static final void FILEinfo (File f)
  {
    System.out.println(
        " Absolute path : " + f.getAbsolutePath() +
        "\n     Can read : " + f.canRead() +
        "\n    Can write : " + f.canWrite() +
        "\n      getName : " + f.getName() +
        "\n    getParent : " + f.getParent() +
        "\n      getPath : " + f.getPath() +
        "\n       length : " + f.length() +
        "\n lastModified : " + f.lastModified());

    if(f.isFile())
      System.out.println("it's a file");
    else if(f.isDirectory())
      System.out.println("it's a directory");
  }

  public static final boolean FILErename(String oldfn,String newfn)
  {

    try {

      File old = new File(oldfn), rname = new File(newfn);

      old.renameTo(rname);

    }
    catch(Exception ex) {

      System.out.println("FILErename Exception :: " + ex.getMessage() );
      System.out.println("FILErename FileName  :: " + oldfn + " " + newfn );
      return false;

    }

    return true;

  }

  public static final boolean FILEcopy(String oldfn,String newfn)
  {

    try {
      BufferedReader in = new BufferedReader(new FileReader(oldfn));
      PrintWriter out = new PrintWriter(
          new FileOutputStream(newfn));

      String strtmp;
      while( (strtmp=in.readLine()) != null ) {
        //L.p(strtmp);
        out.println(strtmp);
      }

      in.close();
      out.close();
    }
    catch(Exception ex) {

      System.out.println("FILEcopy Exception :: " + ex.getMessage() );
      System.out.println("FILEcopy FileName  :: " + oldfn + " " + newfn );
      return false;

    }

    return true;

  }

  private final static String usage =
      "Usage:CifFILEapi path1 ...\n" +
      "Creates each path\n" +
      "Usage:CifFILEapi -d path1 ...\n" +
      "Deletes each path\n" +
      "Usage:CifFILEapi -r path1 path2\n" +
      "Renames from path1 to path2\n";


  private static void usage() {
    System.err.println(usage);
    System.exit(1);
  }


  private static void fileData(File f) {

    System.out.println(
        "Absolute path: " + f.getAbsolutePath() +
        "\n Can read: " + f.canRead() +
        "\n Can write: " + f.canWrite() +
        "\n getName: " + f.getName() +
        "\n getParent: " + f.getParent() +
        "\n getPath: " + f.getPath() +
        "\n length: " + f.length() +
        "\n lastModified: " + f.lastModified());


    if(f.isFile())
      System.out.println("it's a file");
    else if(f.isDirectory())
      System.out.println("it's a directory");
  }

  public synchronized void println(String filename,String contentToWrite)
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


  public static  void main(String[] args) {

    FILEabsolutpath("kkk.in");
    FILEcopy("kkk.in","kkk.in.txt");
    DIRmake("C:\\MYTEST\\CHB\\src\\파일\\aa");
    System.out.println( FILElastmodified("kkk.in"));



    if(args.length < 1) usage();

    if(args[0].equals("-r")) {
      if(args.length != 3) usage();

      File old = new File(args[1]),
      rname = new File(args[2]);

      old.renameTo(rname);
      fileData(old);
      fileData(rname);
      return; // Exit main
    }

    int count = 0;
    boolean del = false;
    if(args[0].equals("-d")) {
      count++;
      del = true;
    }
    for( ; count < args.length; count++) {
      File f = new File(args[count]);
      if(f.exists()) {
        System.out.println(f + " exists");
        if(del) {
          System.out.println("deleting..." + f);
          f.delete();
        }
      }
      else { // Doesn't exist
        if(!del) {
          f.mkdirs();
          System.out.println("created " + f);
        }
      }
      fileData(f);
    }
  }



  public static ArrayList getFileCount( File dir )
  {
    ArrayList ar = new ArrayList();
    File[] files = dir.listFiles();


    for(int i=0; i<files.length; i++)
    {
      File f = files[i];
      if( f.isDirectory() ){

      }
      else{
        ar.add(i,f.getName());
      }
    }
    return ar;
  }

  /*
  public int getFileCount( File dir )
  {
    int count = 0;
    File[] files = dir.listFiles();
    for(int i=0; i<files.length; i++)
    {
      File f = files[i];
      if( f.isDirectory() )
        count += recursiveDirectory(f);
      else
        count++;
    }
    return count;
  }
  */


} ///:~

