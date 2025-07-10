package com.kdb.oversea.cashCard.business.cashCardRule.helper;

import java.io.*;
import java.util.*;
import java.net.*;
import java.util.*;
import java.text.*;

import com.chb.coses.foundation.log.Log;

public class IOBoundCardRegister {
  private static IOBoundCardRegister instance;

  //file 정보
  public String CardFPath = "/home/ftpuser/CARD/";
  public String CardFName = "CardRegister";

  //track #1
  private String SS="%";
  private String FC="B";
  private String PAN;
  private String FS1="^";
  private String Name;
  private String FS2="^";
  private String RegionCode="50";
  private String BankKind="5";
  private String SerialNo1="03";
  private String serialNo2="013";
  private String BankCode="03";
  private String BranchCode;
  private String CardNo;
  private String ES="?";

  private int PAN_Len=16;
  private int Name_Len=26;
  private int BranchCode_Len=4;
  private int CardNo_Len=16;

  //track #2
  private String SS2=";";
  private String PAN2;
  private String FS3="=";
  private String CardNo2;
  private String ES2="?";
  //private String ES2="E";

  public static synchronized IOBoundCardRegister getInstance() {
    if (instance == null) {
      try{
        instance = new IOBoundCardRegister();
        }catch(Exception igex){}
    }
    return instance;
  }

  public void setAccountNo(String acctno){
    PAN="00000"+acctno;
    PAN2="00000"+acctno;
  }

  public void setName(String name){
    Name=PSpaceToStr(name,Name_Len);
  }

  public void setBranchCode(String branch){
    BranchCode=branch;
    if( BranchCode.equals("0238") ){
      serialNo2="013";
      RegionCode="50";
    }
    if( BranchCode.equals("0239") ){
      serialNo2="018";
      RegionCode="10";
    }
  }

  public void setCardNo(String cardno){
    CardNo=cardno;
    CardNo2=CardNo;
  }

  public IOBoundCardRegister(){
  }

  /////////////////////////////////////////////////////////////////////////////
  //                           carddata.yyyymmdd
  /////////////////////////////////////////////////////////////////////////////
  //acctno,name,branch,cashcardno
  //23867039589,PongPong,0238,5030238000000017
  //23867039589,PongPong,0238,5030238000000017
  /////////////////////////////////////////////////////////////////////////////

  public synchronized void execute(String fname)
  {
    String name=null,acctno=null,branch=null,cardno=null;

    try
    {
      BufferedReader ipin = new BufferedReader( new FileReader(fname) );

      for( String ipline; (ipline = ipin.readLine()) != null ; )
      {
        if( ipline.equals("")){
          continue;
        }
        if( ipline.substring(0,1).equals("#") ){
          continue;
        }
        String delim=",";
        StringTokenizer tokenizer1 = new StringTokenizer(ipline, delim);
        if( tokenizer1.countTokens() !=4 ){
          Log.SDBLogger.debug("Number of Tokens: "+tokenizer1.countTokens());
          Log.SDBLogger.debug("["+ipline+"]");
          continue;
        }
        int i=0;
        while(tokenizer1.hasMoreTokens()) {
          String var = tokenizer1.nextToken();
          switch( i ){
            case 0: setAccountNo(var); break;
            case 1: setName(var); break;
            case 2: setBranchCode(var); break;
            case 3: setCardNo(var); break;
          }
          i++;
        }
        Thread.currentThread().sleep(50);

        String CardSeq = getCardSeq();
        this.makefile( null , CardSeq + this.CardNo + Track1_toString() + Track2_toString() );
        Log.SDBLogger.debug(CardSeq + this.CardNo + Track1_toString() + Track2_toString());
        setCardSeq(CardSeq,GetSysDate());
      }
    }
    catch(Exception ex){
      ex.printStackTrace();
    }
  }

  public synchronized void execute(String acctno,String name,String branch,String cardno){

    setName(name);
    setAccountNo(acctno);
    setBranchCode(branch);
    setCardNo(cardno);

    if( BranchCode.equals("0238") ){
    }
    else if( BranchCode.equals("0239") ){
    }
    else {
      Log.SDBLogger.debug("-------------- branchcode not match");
      return;
    }

    String CardSeq = getCardSeq();

    this.makefile( null , CardSeq + this.CardNo + Track1_toString() + Track2_toString() );
    setCardSeq(CardSeq,GetSysDate());
  }

  public String Track2_toString(){
   return ( SS2 +
            PAN2 +
            FS3 +
            CardNo2 +
            ES2 );
  }

  public String Track1_toString(){
   return ( SS +
            FC +
            PAN +
            FS1 +
            Name +
            FS2 +
            RegionCode +
            BankKind +
            SerialNo1 +
            serialNo2 +
            BankCode +
            BranchCode +
            CardNo +
            ES );
  }

  public final String PSpaceToStr(String str1,int size) {
    if( str1==null )
      str1 = " ";

    String str= null;
    if( str1.length() >= size ){
      str = str1.substring(0,size);
    }
    else
      str = str1;

    StringBuffer tt = new StringBuffer();
    for ( int i = 1; i <= size ; i ++ )
    {
      tt.append(' ');
    }
    for( int i=0 ; i<str.length() ; i++)
    {
      tt.setCharAt(i,str.charAt(i));
    }

    return tt.toString();
  }

  public synchronized void makefile(String cardno, String contentToWrite) {
    FileOutputStream fos = null;
    PrintStream ps = null;

    try{

      if( !DIRmake(this.CardFPath) ){
        Log.SDBLogger.debug("Card File Make error");
        return;
      }

      if( !DIRmake(this.CardFPath + GetSysDate()) ){
        Log.SDBLogger.debug("Card File Make error");
        return;
      }

      String CardFName = this.CardFPath  + GetSysDate() + "/" + this.GetHostName()  + "." + this.CardFName + "." + GetSysDate();

      fos = new FileOutputStream(CardFName, true);
      ps = new PrintStream(fos);
      ps.println(contentToWrite);
      ps.flush();
      ps.close();
      fos.close();
    }catch(Exception e){
      try{
        fos.close();
        ps.close();
      }
      catch(Exception ex){}
      e.printStackTrace();
    }

    Fmode(CardFName);

    return;
  }

  public synchronized void makeenv() {
    String CardFName = null;
    FileOutputStream fos = null;
    PrintStream ps = null;

    try{
      CardFName = "/home/ftpuser/CARD/SEQ/CardSeq.INI" ;
      File f = new File(CardFName);

      if(f.exists()){
        return;
      }
      fos = new FileOutputStream(CardFName, true);
      ps = new PrintStream(fos);
      ps.println("LSeq=00000000");
      ps.println("LDate="+this.GetSysDate());
      ps.flush();
      ps.close();
      fos.close();
    }catch(Exception e){
      try{
        fos.close();
        ps.close();
      }
      catch(Exception ex){}
      e.printStackTrace();
    }
    return;
  }

  public static String GetHostName()
  {
    InetAddress Address=null ;
    try{
      Address = InetAddress.getLocalHost();
    } catch (Exception e) {
      Log.SDBLogger.debug(e.getMessage());
      return (null);
    }
    return( Address.getHostName() );
  }

  public static String GetSysDate()
  {
    SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd");
    return fmt.format(new Date()).toString();
  }

  public static String GetSysTime()
  {
    SimpleDateFormat fmt = new SimpleDateFormat("HHmmssSSS");
    return fmt.format(new java.util.Date()).toString();
  }

  public void Fmode(String FName){
    try{
      Runtime runtime = Runtime.getRuntime();
      runtime.exec("/usr/bin/chmod 777 " + FName);
    }
    catch(IOException ex){
      ex.printStackTrace();
    }
  }

  public final boolean DIRmake(String FName)
  {
    try {
      File f = new File(FName);

      if(f.exists()) {
        return true;
      }
      f.mkdirs();
      if(!f.isDirectory())
        throw new IOException(FName + "DIR does not make dir");
    }
    catch(Exception ex) {
      Log.SDBLogger.debug("DIRmake Exception :: " + ex.getMessage() );
      Log.SDBLogger.debug("DIRmake FileName  :: " + FName );
      return false;
    }
    return true;
  }


  public static void main(String args[]){

    IOBoundCardRegister.getInstance().execute("23867039589","PongPong","0238","5030238000000017");
    IOBoundCardRegister.getInstance().execute("23967039589","GongGong","0239","5030239000000017");

  }

  public synchronized String getCardSeq()
  {
    String FName = "/home/ftpuser/CARD/SEQ/CardSeq.INI";
    String LSeq = null;
    String LDate = null;
    try
    {
      makeenv();
      Properties p = new Properties();
      p.load(new FileInputStream(FName));
      LSeq = p.getProperty("LSeq");
      LDate = p.getProperty("LDate");
      if( LDate.equals(GetSysDate()) ){
        int iseq = Integer.valueOf(LSeq).intValue() + 1;
        DecimalFormat fmt = new DecimalFormat("00000000");
        fmt = new DecimalFormat("00000000");
        String sseq = fmt.format(iseq);
        return sseq;
      }
      else{
        setCardSeq("00000001",GetSysDate());
        return "00000001";
      }
    }
    catch(Exception e)
    {
      e.printStackTrace();
      return null;
    }
  }

  public synchronized boolean setCardSeq(String seq,String date)
  {
    String FName = "/home/ftpuser/CARD/SEQ/CardSeq.INI";
    String LSeq = null;
    String LDate = null;
    try
    {
      Properties p = new Properties();
      p.load(new FileInputStream(FName));
      p.setProperty("LSeq", seq);
      p.setProperty("LDate", date);
      p.store(new FileOutputStream(FName), "INI REFERENCE[CardSeq.INI]");
    }
    catch(Exception e)
    {
      e.printStackTrace();
      return false;
    }
    return true;
  }
}