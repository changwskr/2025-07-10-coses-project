/*
 * @(#)Display.java.         1.0   04/02/19
 *
 * -----------------------------------------------------------------------------
 *                         MODIFICATION     LOG
 *
 *     DATE                AUTHOR                        DESCRIPTION
 *   --------           -------------         --------------------------------
 *   2004/02/19         sukwoolee                    Initial Release
 * -----------------------------------------------------------------------------
 */

package com.ims.oversea.wlmonitor.org.kipo.wlmonitor;

import java.util.*;
import java.io.*;

public class Display
{
    public Sample sample;

    // ANSI CODE DEFINE
    private static final String ANSI_CLEAR                 =  "[2J[1;1f"   ;
    private static final String ANSI_HOME                  =  "[1;1f"         ;
    private static final String ANSI_SET_LINE2             =  "[2;1f"         ;
    private static final String ANSI_SET_132COL            =  "[?3h"          ;
    private static final String ANSI_LIST_HEADER           =  "[0;30;42m"     ;
    private static final String ANSI_RESET                 =  "[0m"           ;
    private static final String ANSI_ALERT                 =  "[7;33;41m"     ;
    private static final String ANSI_WARNING               =  "[34;42m"       ;
    private static final String ANSI_ERASE_EOF             =  "[J"            ;
    private static final String ANSI_TITLE                 =  "[0;37;44m"     ;
    private static final String ANSI_CURRENT               =  "[30;42m"       ;
    private static final String ANSI_TITLE_ALERT_CURRENT   =  "[33;41m"       ;
    private static final String ANSI_TITLE_WARNING_CURRENT =  "[37;41m"       ;
    private static final String ANSI_TITLE_WARNING         =  "[34;43m"       ;
    private static final String ANSI_SERVICE               =  "[7;34;43m"     ;
    private static final String ANSI_HOSTNAME              =  "[36m"          ;
    private static final String ANSI_FORE_YELLOW           =  "[36m"          ;
    private static final String ANSI_FORE_RED              =  "[31m"          ;
    private static final String ANSI_BACK_GREEN            =  "[42m"          ;
    private static final String ANSI_HIGH                  =  "[30;43m"       ;
    private static final String ANSI_LOW                   =  "[33m"          ;
    private static final String ANSI_BLACK_CYAN            =  "[30;46m"       ;
    private static final String ANSI_BLUE_WHITE            =  "[34;47m"       ;
    private static final String ANSI_MAGENTA_WHITE         =  "[35;47m"       ;
    private static final String ANSI_BLACK_BLUE            =  "[30;44m"       ;

    private static final String ANSI_ITEM1                 =  "[33;44m"       ;
    private static final String ANSI_ITEM2                 =  "[37;44m"       ;

    private static final String ANSI_SAVE_CURSOR           =  "7"            ;
    private static final String ANSI_RESTORE_CURSOR        =  "8"            ;


    private static final String SUB_TITLE1 = "[36;44mLimit  OP    0   -1   -2   -3   -4   -5   -6   -7   -8   -9 \n[0m";
    private static final String COMP_GE   = ">=";
    private static final String COMP_LE   = "<=";
    private static final String COMP_EQ   = " =";
    private static final String COMP_NONE = "  ";




    public String padout(String value, int size)
    {
        StringBuffer sb = new StringBuffer(size);
        int limit = size - value.length();

        for (int i = 0; i < limit; i++)
            sb.append(" ");

        sb.append(value);

        return sb.toString();
    }

    public String padout(int value, boolean zeroCheck, boolean alarmCheck)
    {
       return padout( value, zeroCheck, alarmCheck, 1 );
    }

    public String padout(int value, boolean zeroCheck, boolean alarmCheck, int pos)
    {
        StringBuffer sb = new StringBuffer();
        String      tmpStr = new String();
        int         limit=0;
        int         tmpValue;


        tmpValue= value;
        tmpStr = "";

        if( value == 0){
          if(zeroCheck) {
             sb.append("None");
             return( sb.toString());
          }
          else{
            return( "   0");
          }
        }

        if( tmpValue >= 1024) {
            tmpStr = "K";
            tmpValue /= 1024;
        }
        if( tmpValue >= 1024) {
            tmpStr = "M";
            tmpValue /= 1024;
        }
         if( tmpValue >= 1024) {
            tmpStr = "G";
            tmpValue /= 1024;
        }

        limit = 4 - Integer.toString(tmpValue).length() ;
        if(tmpStr != "") limit--;


        for (int i = 0; i < limit; i++)
            sb.append(" ");

        if( alarmCheck ) {
          sb.append( ANSI_ALERT );
          if( pos == 0 ) {
             sample.alarm = true;
          }
        }

        sb.append(Integer.toString(tmpValue));
        sb.append(tmpStr);

        if( alarmCheck )
          sb.append( ANSI_RESET );

        return sb.toString();
    }


    //getDate added
    public String getDate()
    {
		java.text.SimpleDateFormat df = new java.text.SimpleDateFormat("yyyyMMdd/HH:mm:ss");
		java.util.Date now = new java.util.Date();
		return df.format(now);
    }

    public void display(Sample s)
    {
    	sample = s;
    	displayHeader();
    	displayBody();
    	displayBottom();
    }

    public void displayBody()
    {
     StringBuffer ScreenBuffer = new StringBuffer();

//JVMRuntime Display
     // title
     ScreenBuffer.append( ANSI_ITEM1 + "JVMRuntime: " + ANSI_SAVE_CURSOR + ANSI_ITEM2 );
     ScreenBuffer.append(  "             " + SUB_TITLE1);
     ScreenBuffer.append( ANSI_RESTORE_CURSOR + ANSI_ITEM1 );
     ScreenBuffer.append( sample.propServerName + "\n" + ANSI_RESET);

     // HeapSizeCurrent
     ScreenBuffer.append( "HeapSizeCurrent          [");
     ScreenBuffer.append( padout(sample.HeapSizeCurrent[sample.ARRAY_CNT-1], true, false ) + "] " + COMP_NONE );

     for( int i=0;i<10;i++){
        ScreenBuffer.append(" ");
        ScreenBuffer.append( padout(sample.HeapSizeCurrent[i], false, false  ));
     }
     ScreenBuffer.append("\n");

     // HeapFreeCurrent
     ScreenBuffer.append( "HeapFreeCurrent          [");
     ScreenBuffer.append( padout(sample.HeapFreeCurrent[sample.ARRAY_CNT-1], true, false) + "] ");
     if( sample.HeapFreeCurrent[sample.ARRAY_CNT-1] == 0 ) ScreenBuffer.append( COMP_NONE );
     else                                                  ScreenBuffer.append( COMP_LE );

     for( int i=0;i<10;i++){
        ScreenBuffer.append(" ");
        ScreenBuffer.append( padout(sample.HeapFreeCurrent[i], false, (sample.HeapFreeCurrent[i] <= sample.HeapFreeCurrent[sample.ARRAY_CNT-1]), i) );
     }
     ScreenBuffer.append("\n\n");

//ExecuteQueueRuntime
     // title
     ScreenBuffer.append( ANSI_ITEM1 + "ExecuteQueueRuntime      ");
     ScreenBuffer.append( ANSI_ITEM2 + SUB_TITLE1 +ANSI_RESET);

     // ExecuteThreadCurrentIdleCount
     ScreenBuffer.append( "ExecuteThreadCurrentIdle [");
     ScreenBuffer.append( padout(sample.ExecuteThreadCurrentIdleCount[sample.ARRAY_CNT-1], true, false) + "] " + COMP_NONE );

     for( int i=0;i<10;i++){
        ScreenBuffer.append(" ");
        ScreenBuffer.append( padout(sample.ExecuteThreadCurrentIdleCount[i], false, false) );
     }
     ScreenBuffer.append("\n");

     // PendingRequestCurrentCount
     ScreenBuffer.append( "PendingRequestCurrent    [");
     ScreenBuffer.append( padout(sample.PendingRequestCurrentCount[sample.ARRAY_CNT-1], true, false) + "] ");
     if( sample.PendingRequestCurrentCount[sample.ARRAY_CNT-1] == 0 ) ScreenBuffer.append( COMP_NONE );
     else                                                             ScreenBuffer.append( COMP_GE );

     for( int i=0;i<10;i++){
        ScreenBuffer.append(" ");
        ScreenBuffer.append( padout(sample.PendingRequestCurrentCount[i], false,
                              (sample.PendingRequestCurrentCount[i] >= sample.PendingRequestCurrentCount[sample.ARRAY_CNT-1]),i ) );
     }
     ScreenBuffer.append("\n");

     // ServicedRequestTotalCount
     ScreenBuffer.append( "ServicedRequestTotalCount[");
     ScreenBuffer.append( padout(sample.ServicedRequestTotalCount[sample.ARRAY_CNT-1], true, false) + "] " + COMP_NONE );

     for( int i=0;i<10;i++){
        ScreenBuffer.append(" ");
        ScreenBuffer.append( padout(sample.ServicedRequestTotalCount[i], false,  false) );
     }
     ScreenBuffer.append("\n");

     // Requests
     ScreenBuffer.append( "Requests                 [");
     ScreenBuffer.append( padout(sample.Requests[sample.ARRAY_CNT-1], true, false) + "] ");
     if( sample.Requests[sample.ARRAY_CNT-1] == 0 ) ScreenBuffer.append( COMP_NONE );
     else                                           ScreenBuffer.append( COMP_GE );


     for( int i=0;i<10;i++){
        ScreenBuffer.append(" ");
        ScreenBuffer.append( padout(sample.Requests[i], false,
                            (sample.Requests[i] >= sample.Requests[sample.ARRAY_CNT-1]), i) );
     }
     ScreenBuffer.append("\n");

     // RequestPerSecond
     ScreenBuffer.append( "RequestPerSecond         [");
     ScreenBuffer.append( padout(sample.RequestPerSecond[sample.ARRAY_CNT-1], true, false) + "] ");
     if( sample.RequestPerSecond[sample.ARRAY_CNT-1] == 0 ) ScreenBuffer.append( COMP_NONE );
     else                                                   ScreenBuffer.append( COMP_GE );

     for( int i=0;i<10;i++){
        ScreenBuffer.append(" ");
        ScreenBuffer.append( padout(sample.RequestPerSecond[i], false,
                            (sample.RequestPerSecond[i] >= sample.RequestPerSecond[sample.ARRAY_CNT-1]),i) );
     }
     ScreenBuffer.append("\n\n");

//ServerRuntime
     // title
     ScreenBuffer.append( ANSI_ITEM1 + "ServerRuntime            ");
     ScreenBuffer.append( ANSI_ITEM2 + SUB_TITLE1 + ANSI_RESET);

     // OpenSocketsCurrentCount
     ScreenBuffer.append( "OpenSocketsCurrentCount  [");
     ScreenBuffer.append( padout(sample.OpenSocketsCurrentCount[sample.ARRAY_CNT-1], true, false) + "] ");
     if( sample.OpenSocketsCurrentCount[sample.ARRAY_CNT-1] == 0 ) ScreenBuffer.append( COMP_NONE );
     else                                                          ScreenBuffer.append( COMP_GE );

     for( int i=0;i<10;i++){
        ScreenBuffer.append(" ");
        ScreenBuffer.append( padout(sample.OpenSocketsCurrentCount[i], false,
                             (sample.OpenSocketsCurrentCount[i] >= sample.OpenSocketsCurrentCount[sample.ARRAY_CNT-1]),i) );
     }
     ScreenBuffer.append("\n");

// JDBCConnectionPoolRuntime
     for( int loop = 0; loop < sample.JDBC_POOL_CNT ;loop++){
          // title
          ScreenBuffer.append("\n");
          ScreenBuffer.append( ANSI_ITEM1 + "JDBC: " + ANSI_SAVE_CURSOR + ANSI_ITEM2 + "                   ");
          ScreenBuffer.append( SUB_TITLE1 + ANSI_RESTORE_CURSOR + ANSI_ITEM1 );
          ScreenBuffer.append( sample.Name[loop] + "\n" + ANSI_RESET);

          // MaxCapacity
          ScreenBuffer.append( "MaxCapacity              [");
          ScreenBuffer.append( padout(sample.MaxCapacity[loop][sample.ARRAY_CNT-1], true, false) + "] "+ COMP_NONE );

          for( int i=0;i<10;i++){
             ScreenBuffer.append(" ");
             ScreenBuffer.append( padout(sample.MaxCapacity[loop][i], false, false ));
          }
          ScreenBuffer.append("\n");

          // ConnectionsTotalCount
          ScreenBuffer.append( "Connections              [");
          ScreenBuffer.append( padout(sample.ConnectionsTotalCount[loop][sample.ARRAY_CNT-1], true, false) + "] ");
         if( sample.ConnectionsTotalCount[loop][sample.ARRAY_CNT-1] == 0 )   ScreenBuffer.append( COMP_NONE );
         else                                                                ScreenBuffer.append( COMP_GE );


          for( int i=0;i<10;i++){
             ScreenBuffer.append(" ");
             ScreenBuffer.append( padout(sample.ConnectionsTotalCount[loop][i], false,
                                  (sample.ConnectionsTotalCount[loop][i] >= sample.ConnectionsTotalCount[loop][sample.ARRAY_CNT-1]),i) );
          }
          ScreenBuffer.append("\n");

          // ActiveConnectionsCurrentCount
          ScreenBuffer.append( "ActiveConnections        [");
          ScreenBuffer.append( padout(sample.ActiveConnectionsCurrentCount[loop][sample.ARRAY_CNT-1], true, false) + "] ");
         if( sample.ActiveConnectionsCurrentCount[loop][sample.ARRAY_CNT-1] == 0 )   ScreenBuffer.append( COMP_NONE );
         else                                                                  ScreenBuffer.append( COMP_GE );


          for( int i=0;i<10;i++){
             ScreenBuffer.append(" ");
             ScreenBuffer.append( padout(sample.ActiveConnectionsCurrentCount[loop][i], false,
                                  (sample.ActiveConnectionsCurrentCount[loop][i] >= sample.ActiveConnectionsCurrentCount[loop][sample.ARRAY_CNT-1]),i) );
          }
          ScreenBuffer.append("\n");

          // ActiveConnectionsHighCount
          ScreenBuffer.append( "ActiveConnectionsHigh    [");
          ScreenBuffer.append( padout(sample.ActiveConnectionsHighCount[loop][sample.ARRAY_CNT-1], true, false) + "] "+ COMP_NONE );


          for( int i=0;i<10;i++){
             ScreenBuffer.append(" ");
             ScreenBuffer.append( padout(sample.ActiveConnectionsHighCount[loop][i], false, false));
          }
          ScreenBuffer.append("\n");

          // ConnectionDelayTime
          ScreenBuffer.append( "ConnectionDelayTime      [");
          ScreenBuffer.append( padout(sample.ConnectionDelayTime[loop][sample.ARRAY_CNT-1], true, false) + "] ");
          if( sample.ConnectionDelayTime[loop][sample.ARRAY_CNT-1] == 0 )   ScreenBuffer.append( COMP_NONE );
          else                                                        ScreenBuffer.append( COMP_GE );

          for( int i=0;i<10;i++){
             ScreenBuffer.append(" ");
             ScreenBuffer.append( padout(sample.ConnectionDelayTime[loop][i], false,
                                   (sample.ConnectionDelayTime[loop][i] >= sample.ConnectionDelayTime[loop][sample.ARRAY_CNT-1]),i) );
          }
          ScreenBuffer.append("\n");

          // ConnectionLeakProfileCount
          ScreenBuffer.append( "ConnectionLeakProfile    [");
          ScreenBuffer.append( padout(sample.ConnectionLeakProfileCount[loop][sample.ARRAY_CNT-1], true, false) + "] "+ COMP_NONE);

          for( int i=0;i<10;i++){
             ScreenBuffer.append(" ");
             ScreenBuffer.append( padout(sample.ConnectionLeakProfileCount[loop][i], false,
                                   (sample.ConnectionLeakProfileCount[loop][i] >= sample.ConnectionLeakProfileCount[loop][sample.ARRAY_CNT-1]),i) );
          }
          ScreenBuffer.append("\n");

          // FailuresToReconnectCount
          ScreenBuffer.append( "FailuresToReconnect      [");
          ScreenBuffer.append( padout(sample.FailuresToReconnectCount[loop][sample.ARRAY_CNT-1], true, false) + "] ");
          if( sample.FailuresToReconnectCount[loop][sample.ARRAY_CNT-1] == 0 )   ScreenBuffer.append( COMP_NONE );
          else                                                             ScreenBuffer.append( COMP_GE );

          for( int i=0;i<10;i++){
             ScreenBuffer.append(" ");
             ScreenBuffer.append( padout(sample.FailuresToReconnectCount[loop][i], false,
                                   (sample.FailuresToReconnectCount[loop][i] >= sample.FailuresToReconnectCount[loop][sample.ARRAY_CNT-1]),i) );
          }
          ScreenBuffer.append("\n");

          // LeakedConnectionCount
          ScreenBuffer.append( "LeakedConnection         [");
          ScreenBuffer.append( padout(sample.LeakedConnectionCount[loop][sample.ARRAY_CNT-1], true, false) + "] ");
          if( sample.LeakedConnectionCount[loop][sample.ARRAY_CNT-1] == 0 )   ScreenBuffer.append( COMP_NONE );
          else                                                          ScreenBuffer.append( COMP_GE );


          for( int i=0;i<10;i++){
             ScreenBuffer.append(" ");
             ScreenBuffer.append( padout(sample.LeakedConnectionCount[loop][i], false,
                                   (sample.LeakedConnectionCount[loop][i] >= sample.LeakedConnectionCount[loop][sample.ARRAY_CNT-1]),i) );
          }
          ScreenBuffer.append("\n");

          // WaitingForConnectionCurrentCount
          ScreenBuffer.append( "WaitingForConnection     [");
          ScreenBuffer.append( padout(sample.WaitingForConnectionCurrentCount[loop][sample.ARRAY_CNT-1], true, false) + "] ");
          if( sample.WaitingForConnectionCurrentCount[loop][sample.ARRAY_CNT-1] == 0 )   ScreenBuffer.append( COMP_NONE );
          else                                                                           ScreenBuffer.append( COMP_GE );

          for( int i=0;i<10;i++){
             ScreenBuffer.append(" ");
             ScreenBuffer.append( padout(sample.WaitingForConnectionCurrentCount[loop][i], false,
                                  (sample.WaitingForConnectionCurrentCount[loop][i] >= sample.WaitingForConnectionCurrentCount[loop][sample.ARRAY_CNT-1]),i) );

          }
          ScreenBuffer.append("\n");

          if( sample.propDetailView.equals("true")){
                 // PrepStmtCacheHitCount
                 ScreenBuffer.append( "PrepStmtCacheHit         [");
                 ScreenBuffer.append( padout(sample.PrepStmtCacheHitCount[loop][sample.ARRAY_CNT-1], true, false) + "] " + COMP_NONE);


                 for( int i=0;i<10;i++){
                    ScreenBuffer.append(" ");
                    ScreenBuffer.append( padout(sample.PrepStmtCacheHitCount[loop][i], false, false));

                 }
                 ScreenBuffer.append("\n");

                 // PrepStmtCacheMissCount
                 ScreenBuffer.append( "PrepStmtCacheMiss        [");
                 ScreenBuffer.append( padout(sample.PrepStmtCacheMissCount[loop][sample.ARRAY_CNT-1], true, false) + "] " + COMP_NONE);

                 for( int i=0;i<10;i++){
                    ScreenBuffer.append(" ");
                    ScreenBuffer.append( padout(sample.PrepStmtCacheMissCount[loop][i], false, false));

                 }
                 ScreenBuffer.append("\n");

                 // PreparedStatementCacheProfileCount
                 ScreenBuffer.append( "PreparedStatementCache   [");
                 ScreenBuffer.append( padout(sample.PreparedStatementCacheProfileCount[loop][sample.ARRAY_CNT-1], true, false) + "] " + COMP_NONE);

                 for( int i=0;i<10;i++){
                    ScreenBuffer.append(" ");
                    ScreenBuffer.append( padout(sample.PreparedStatementCacheProfileCount[loop][i], false, false));

                 }
                 ScreenBuffer.append("\n");

                 // StatementProfileCount
                 ScreenBuffer.append( "StatementProfile         [");
                 ScreenBuffer.append( padout(sample.StatementProfileCount[loop][sample.ARRAY_CNT-1], true, false) + "] " + COMP_NONE);

                 for( int i=0;i<10;i++){
                    ScreenBuffer.append(" ");
                    ScreenBuffer.append( padout(sample.StatementProfileCount[loop][i], false, false));

                 }
                 ScreenBuffer.append("\n");

                 // WaitSecondsHighCount
                 ScreenBuffer.append( "WaitSecondsHigh          [");
                 ScreenBuffer.append( padout(sample.WaitSecondsHighCount[loop][sample.ARRAY_CNT-1], true, false) + "] " + COMP_NONE);

                 for( int i=0;i<10;i++){
                    ScreenBuffer.append(" ");
                    ScreenBuffer.append( padout(sample.WaitSecondsHighCount[loop][i], false, false));

                 }
                 ScreenBuffer.append("\n");


                 // WaitingForConnectionHighCount
                 ScreenBuffer.append( "WaitingForConnectionHigh [");
                 ScreenBuffer.append( padout(sample.WaitingForConnectionHighCount[loop][sample.ARRAY_CNT-1], true, false) + "] " + COMP_NONE);

                 for( int i=0;i<10;i++){
                    ScreenBuffer.append(" ");
                    ScreenBuffer.append( padout(sample.WaitingForConnectionHighCount[loop][i], false, false));

                 }
                 ScreenBuffer.append("\n");
         }
     }
     // alarm 발생
     if( sample.alarm ){
          alarmSoundPlay();
          sample.alarm = false;
     }

     System.out.println( ScreenBuffer.toString());
    }

    public void alarmSoundPlay(){
     	try {
        DataOutputStream  Dostream = new DataOutputStream ( System.out );
        Dostream.writeInt(0x07 );
        sample.alarm = false;
        }catch(Exception e){
          e.printStackTrace();
        }
    }

     public void displayHeader()
    {
     StringBuffer ScreenBuffer = new StringBuffer();

     // title display
     ScreenBuffer.append( ANSI_HOME + ANSI_TITLE + " " + ANSI_SAVE_CURSOR );
     ScreenBuffer.append("                                                 ");
     ScreenBuffer.append("KipoNET Weblogic Monitoring   V" + sample.version + " ");
     ScreenBuffer.append( ANSI_RESTORE_CURSOR + ANSI_HOSTNAME );

     //System.out.print( "Hostname: " + sample.hostName );

     ScreenBuffer.append( "Domain: " + sample.propDomain );
     ScreenBuffer.append( "  ServerName: " + sample.propServerName + "\n" + ANSI_RESET);

     ScreenBuffer.append( " Server: t3://" + sample.propHostName + ":" + sample.propPort + "  Interval: " + sample.propSamplePeriod+ " Secounds\n");

     System.out.println( ScreenBuffer.toString());

    }

    public void displayBottom()
    {
     StringBuffer ScreenBuffer = new StringBuffer();

     // title display
     ScreenBuffer.append( ANSI_TITLE );
     ScreenBuffer.append(" " + getDate());
     ScreenBuffer.append("                                                               [");
     ScreenBuffer.append( ANSI_SAVE_CURSOR + " ] "  + ANSI_RESTORE_CURSOR + ANSI_RESET );

     System.out.print( ScreenBuffer.toString());

    }

    public void displayConnectError()
    {
     System.out.print( ANSI_CLEAR );
     displayHeader();
     System.out.print( " " + ANSI_ALERT + " Connect Error!!!" + ANSI_RESET + "\n\n" );
     // displayBottom();
     alarmSoundPlay();

    }

     public void clearScreen()
    {
     System.out.println( ANSI_CLEAR );
    }


}
