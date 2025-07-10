/*
 * @(#)Sample.java.         1.0   04/02/19
 *
 * -----------------------------------------------------------------------------
 *                         MODIFICATION     LOG
 *
 *     DATE                AUTHOR                        DESCRIPTION
 *   --------           -------------         --------------------------------
 *   ???                freelowder.org               Initial Release
 *   2004/02/19         sukwoolee                    Update
 * -----------------------------------------------------------------------------
 */

package com.ims.oversea.wlmonitor.org.kipo.wlmonitor;

import java.util.Vector;

public class Sample
{
    public static final String version = "1.0";

    public final int JDBC_CNT=10;
    public  int      JDBC_POOL_CNT;
    public final int ARRAY_CNT=11;

    public Vector    conPool = new Vector();
    //public int leakPool;

    public boolean    alarm=false;

    public String     propMachine;
    public String     propHostName;
    public int        propPort;
    public int        propSamplePeriod;
    public String     propPassword;
    public String     propServerName;
    public String     propDomain;
    public String     propQueueName;
    public String     propMBeanClassName;
    public String     propDetailView;

    public String     PoolName;

    // JVMRuntime
    public int HeapFreeCurrent[];
    public int HeapSizeCurrent[];


    // ExecuteQueueRuntime
    public int ExecuteThreadCurrentIdleCount[];
    public int PendingRequestCurrentCount[];
    public int ServicedRequestTotalCount[];

    public int previousRequests;    // 이전 request수
    public int Requests[];          // previousRequests - ServicedRequestTotalCount
    public int RequestPerSecond[];  // Requests / samplePeriod

    // ServerRuntime
    public int OpenSocketsCurrentCount[];


    // JDBCConnectionPoolRuntime
    public String Name[]                                ;
    public int    MaxCapacity[][]                       ;
    public int    ConnectionsTotalCount[][]             ;
    public int    ActiveConnectionsCurrentCount[][]     ;
    public int    ActiveConnectionsHighCount[][]        ;
    public int    ConnectionDelayTime[][]               ;
    public int    ConnectionLeakProfileCount[][]        ;
    public int    FailuresToReconnectCount[][]          ;
    public int    LeakedConnectionCount[][]             ;
    public int    PrepStmtCacheHitCount[][]             ;
    public int    PrepStmtCacheMissCount[][]            ;
    public int    PreparedStatementCacheProfileCount[][];
    public int    StatementProfileCount[][]             ;
    public int    WaitSecondsHighCount[][]              ;
    public int    WaitingForConnectionCurrentCount[][]  ;
    public int    WaitingForConnectionHighCount[][]     ;


    Sample(){


    	previousRequests = 0;

    	// JVMRuntime
    	HeapFreeCurrent                       = new int[ARRAY_CNT];
    	HeapSizeCurrent                       = new int[ARRAY_CNT];

        // ExecuteQueueRuntime
        ExecuteThreadCurrentIdleCount         = new int[ARRAY_CNT];
        PendingRequestCurrentCount            = new int[ARRAY_CNT];
        ServicedRequestTotalCount             = new int[ARRAY_CNT];

        Requests                              = new int[ARRAY_CNT];
        RequestPerSecond                      = new int[ARRAY_CNT];

        // ServerRuntime
        OpenSocketsCurrentCount               = new int[ARRAY_CNT];

        // JDBCConnectionPoolRuntime
        Name                                  = new String[10];
        MaxCapacity                           = new int[JDBC_CNT][ARRAY_CNT];
        ConnectionsTotalCount                 = new int[JDBC_CNT][ARRAY_CNT];
        ActiveConnectionsCurrentCount         = new int[JDBC_CNT][ARRAY_CNT];
        ActiveConnectionsHighCount            = new int[JDBC_CNT][ARRAY_CNT];
        ConnectionDelayTime                   = new int[JDBC_CNT][ARRAY_CNT];
        ConnectionLeakProfileCount            = new int[JDBC_CNT][ARRAY_CNT];
        FailuresToReconnectCount              = new int[JDBC_CNT][ARRAY_CNT];
        LeakedConnectionCount                 = new int[JDBC_CNT][ARRAY_CNT];
        PrepStmtCacheHitCount                 = new int[JDBC_CNT][ARRAY_CNT];
        PrepStmtCacheMissCount                = new int[JDBC_CNT][ARRAY_CNT];
        PreparedStatementCacheProfileCount    = new int[JDBC_CNT][ARRAY_CNT];
        StatementProfileCount                 = new int[JDBC_CNT][ARRAY_CNT];
        WaitSecondsHighCount                  = new int[JDBC_CNT][ARRAY_CNT];
        WaitingForConnectionCurrentCount      = new int[JDBC_CNT][ARRAY_CNT];
        WaitingForConnectionHighCount         = new int[JDBC_CNT][ARRAY_CNT];
    }
   public void shiftSample(){
   	int i;
   	for( i=ARRAY_CNT - 2; i>0 ; i-- ){
             // JVMRuntime
             HeapFreeCurrent[i] = HeapFreeCurrent[i-1];
             HeapSizeCurrent[i] = HeapSizeCurrent[i-1];

             // ExecuteQueueRuntime
             ExecuteThreadCurrentIdleCount[i] = ExecuteThreadCurrentIdleCount[i-1];
             PendingRequestCurrentCount[i]    = PendingRequestCurrentCount[i-1];
             ServicedRequestTotalCount[i]     = ServicedRequestTotalCount[i-1];
             Requests[i]                      = Requests[i-1];
             RequestPerSecond[i]              = RequestPerSecond[i-1];

             // ServerRuntime
             OpenSocketsCurrentCount[i] = OpenSocketsCurrentCount[i-1];
        }
   }

   public void shiftJdbcSample(int jdbcPosition){
   	int i;
   	for( i=ARRAY_CNT - 2; i>0 ; i-- ){
             // JDBCConnectionPoolRuntime
             MaxCapacity[jdbcPosition][i]                        = MaxCapacity[jdbcPosition][i-1];
             ConnectionsTotalCount[jdbcPosition][i]              = ConnectionsTotalCount[jdbcPosition][i-1];
             ActiveConnectionsCurrentCount[jdbcPosition][i]      = ActiveConnectionsCurrentCount[jdbcPosition][i-1];
             ActiveConnectionsHighCount[jdbcPosition][i]         = ActiveConnectionsHighCount[jdbcPosition][i-1];
             ConnectionDelayTime[jdbcPosition][i]                = ConnectionDelayTime[jdbcPosition][i-1];
             ConnectionLeakProfileCount[jdbcPosition][i]         = ConnectionLeakProfileCount[jdbcPosition][i-1];
             FailuresToReconnectCount[jdbcPosition][i]           = FailuresToReconnectCount[jdbcPosition][i-1];
             LeakedConnectionCount[jdbcPosition][i]              = LeakedConnectionCount[jdbcPosition][i-1];
             PrepStmtCacheHitCount[jdbcPosition][i]              = PrepStmtCacheHitCount[jdbcPosition][i-1];
             PrepStmtCacheMissCount[jdbcPosition][i]             = PrepStmtCacheMissCount[jdbcPosition][i-1];
             PreparedStatementCacheProfileCount[jdbcPosition][i] = PreparedStatementCacheProfileCount[jdbcPosition][i-1];
             StatementProfileCount[jdbcPosition][i]              = StatementProfileCount[jdbcPosition][i-1];
             WaitSecondsHighCount[jdbcPosition][i]               = WaitSecondsHighCount[jdbcPosition][i-1];
             WaitingForConnectionCurrentCount[jdbcPosition][i]   = WaitingForConnectionCurrentCount[jdbcPosition][i-1];
             WaitingForConnectionHighCount[jdbcPosition][i]      = WaitingForConnectionHighCount[jdbcPosition][i-1];
        }
    }
}
