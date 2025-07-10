/*
 * @(#)Config.java.         1.0   04/02/19
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

import java.util.*;
import java.io.*;
import java.net.*;

public class Config  extends Properties
{
    public  MBeanMethod mBean;
    private Sample  sample;

    public Config(Sample sample)
    {
        try {

            this.sample = sample;
            System.out.println( "jres/" + sample.propServerName + ".properties" + " property file reading...");
            FileInputStream fis = new FileInputStream("jres/" + sample.propServerName + ".properties");

            load(fis);
            sample.propMBeanClassName      = getProperty("className");
            sample.propMachine      = getProperty("machine", "local");
            sample.propHostName     = getProperty("host", "localhost");
            sample.propPort         = Integer.parseInt(getProperty("port", "7001"));
            sample.propDomain       = getProperty("domain", "mydomain");
            sample.propPassword     = getProperty("password", "security");
            sample.propSamplePeriod = Integer.parseInt(getProperty("samplePeriod", "5"));
            sample.propServerName   = getProperty("serverName", "myserver");
            sample.propQueueName    = getProperty("queueName", "default");
            sample.propDetailView   = getProperty("detailView", "false");

    	   // JVMRuntime
    	    sample.HeapFreeCurrent[sample.ARRAY_CNT -1 ]                    = Integer.parseInt( getProperty( "alarm.limit.HeapFreeCurrent","0")                    )*1024*1024;
    	    sample.HeapSizeCurrent[sample.ARRAY_CNT -1 ]                    = Integer.parseInt( getProperty( "alarm.limit.HeapSizeCurrent","0")                    )*1024*1024;

           // ExecuteQueueRuntime
            sample.ExecuteThreadCurrentIdleCount[sample.ARRAY_CNT -1 ]      = Integer.parseInt( getProperty( "alarm.limit.ExecuteThreadCurrentIdleCount","0")      );
            sample.PendingRequestCurrentCount[sample.ARRAY_CNT -1 ]         = Integer.parseInt( getProperty( "alarm.limit.PendingRequestCurrentCount","0")         );
            sample.ServicedRequestTotalCount[sample.ARRAY_CNT -1 ]          = Integer.parseInt( getProperty( "alarm.limit.ServicedRequestTotalCount","0")          );

            sample.Requests[sample.ARRAY_CNT -1 ]                           = Integer.parseInt( getProperty( "alarm.limit.sample.Requests","0")                           );
            sample.RequestPerSecond[sample.ARRAY_CNT -1 ]                   = Integer.parseInt( getProperty( "alarm.limit.sample.RequestPerSecond","0")                   );

            // ServerRuntime
            sample.OpenSocketsCurrentCount[sample.ARRAY_CNT -1 ]            = Integer.parseInt( getProperty( "alarm.limit.OpenSocketsCurrentCount","0")            );

            // JDBCConnectionPoolRuntime
            sample.Name[0]                                                  = getProperty( "alarm.limit.Name","0");
            sample.ConnectionsTotalCount[0][sample.ARRAY_CNT-1]             = Integer.parseInt( getProperty( "alarm.limit.ConnectionsTotalCount","0")              );
            sample.ActiveConnectionsCurrentCount[0][sample.ARRAY_CNT-1]     = Integer.parseInt( getProperty( "alarm.limit.ActiveConnectionsCurrentCount","0")      );
            sample.ConnectionDelayTime[0][sample.ARRAY_CNT-1]               = Integer.parseInt( getProperty( "alarm.limit.ConnectionDelayTime","0")                );
            sample.FailuresToReconnectCount[0][sample.ARRAY_CNT-1]          = Integer.parseInt( getProperty( "alarm.limit.FailuresToReconnectCount","0")           );
            sample.LeakedConnectionCount[0][sample.ARRAY_CNT-1]             = Integer.parseInt( getProperty( "alarm.limit.LeakedConnectionCount","0")              );
            sample.WaitingForConnectionCurrentCount[0][sample.ARRAY_CNT-1]  = Integer.parseInt( getProperty( "alarm.limit.WaitingForConnectionCurrentCount","0")   );


            for(int k=1; k < sample.JDBC_CNT; k++){
                sample.ConnectionsTotalCount[k][sample.ARRAY_CNT-1]            =  sample.ConnectionsTotalCount[0][sample.ARRAY_CNT-1]           ;
                sample.ActiveConnectionsCurrentCount[k][sample.ARRAY_CNT-1]    =  sample.ActiveConnectionsCurrentCount[0][sample.ARRAY_CNT-1]   ;
                sample.ConnectionDelayTime[k][sample.ARRAY_CNT-1]              =  sample.ConnectionDelayTime[0][sample.ARRAY_CNT-1]             ;
                sample.FailuresToReconnectCount[k][sample.ARRAY_CNT-1]         =  sample.FailuresToReconnectCount[0][sample.ARRAY_CNT-1]        ;
                sample.LeakedConnectionCount[k][sample.ARRAY_CNT-1]            =  sample.LeakedConnectionCount[0][sample.ARRAY_CNT-1]           ;
                sample.WaitingForConnectionCurrentCount[k][sample.ARRAY_CNT-1] =  sample.WaitingForConnectionCurrentCount[0][sample.ARRAY_CNT-1];
            }

            mBean = (MBeanMethod) Class.forName(sample.propMBeanClassName).newInstance();
        } catch (Exception e) {
        	System.out.println( sample.propServerName + " property file read error!!!\n");
        	e.printStackTrace();
        }
    }
}
