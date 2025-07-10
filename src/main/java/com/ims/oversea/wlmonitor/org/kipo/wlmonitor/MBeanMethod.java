/*
* @(#)MBeanMethod.java.         1.0   04/02/19
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
import javax.naming.*;
import javax.jms.*;

import weblogic.management.MBeanHome;
import weblogic.management.RemoteMBeanServer;
import weblogic.management.Helper;
import weblogic.management.runtime.*;

public class MBeanMethod
{

  private Config  config;
  private Sample  sample;
  private boolean rtn=true;

  private JVMRuntimeMBean jvmBean;
  private ExecuteQueueRuntimeMBean exeBean;
  private ServerRuntimeMBean serverBean;

  //for Connection pool
  private JDBCConnectionPoolRuntimeMBean[] jdbcRunBean;

  private List entityBeans = new ArrayList();
  private List statefulBeans = new ArrayList();

  final static String JNDI_FACTORY="weblogic.jndi.WLInitialContextFactory";

  public void getSample() throws Exception
  {

    // rotate sample variable array
    sample.shiftSample();


    // get JVMRuntime
    sample.HeapSizeCurrent[0]            = (int)(jvmBean.getHeapSizeCurrent());
    sample.HeapFreeCurrent[0]            = (int)(jvmBean.getHeapFreeCurrent());

    // get ExecuteQueueRuntime
    sample.ExecuteThreadCurrentIdleCount[0] = exeBean.getExecuteThreadCurrentIdleCount();
    sample.PendingRequestCurrentCount[0]    = exeBean.getPendingRequestCurrentCount();
    sample.ServicedRequestTotalCount[0]     = exeBean.getServicedRequestTotalCount();

    if( sample.previousRequests <= 0 ) sample.previousRequests = sample.ServicedRequestTotalCount[0];

    sample.Requests[0]         = sample.ServicedRequestTotalCount[0] - sample.previousRequests;
    sample.RequestPerSecond[0] = (int)(sample.Requests[0] / sample.propSamplePeriod);

    sample.previousRequests = sample.ServicedRequestTotalCount[0];

    // get ServerRuntime
    sample.OpenSocketsCurrentCount[0] = serverBean.getOpenSocketsCurrentCount();


    // get JDBCConnectionPoolRuntime
    for(int i=0; i<sample.JDBC_POOL_CNT; i++)
    {
      // rotate sample variable array
      sample.shiftJdbcSample(i);

      sample.Name[i]                                   = jdbcRunBean[i].getName();
      sample.MaxCapacity[i][0]                         = jdbcRunBean[i].getMaxCapacity();
      sample.ConnectionsTotalCount[i][0]               = jdbcRunBean[i].getConnectionsTotalCount();
      sample.ActiveConnectionsCurrentCount[i][0]       = jdbcRunBean[i].getActiveConnectionsCurrentCount();
      sample.ActiveConnectionsHighCount[i][0]          = jdbcRunBean[i].getActiveConnectionsHighCount();
      sample.ConnectionDelayTime[i][0]                 = jdbcRunBean[i].getConnectionDelayTime();
      sample.ConnectionLeakProfileCount[i][0]          = jdbcRunBean[i].getConnectionLeakProfileCount();
      sample.FailuresToReconnectCount[i][0]            = jdbcRunBean[i].getFailuresToReconnectCount();
      sample.LeakedConnectionCount[i][0]               = jdbcRunBean[i].getLeakedConnectionCount();
      sample.PrepStmtCacheHitCount[i][0]               = jdbcRunBean[i].getPrepStmtCacheHitCount();
      sample.PrepStmtCacheMissCount[i][0]              = jdbcRunBean[i].getPrepStmtCacheMissCount();
      sample.PreparedStatementCacheProfileCount[i][0]  = jdbcRunBean[i].getPreparedStatementCacheProfileCount();
      sample.StatementProfileCount[i][0]               = jdbcRunBean[i].getStatementProfileCount();
      sample.WaitSecondsHighCount[i][0]                = jdbcRunBean[i].getWaitSecondsHighCount();
      sample.WaitingForConnectionCurrentCount[i][0]    = jdbcRunBean[i].getWaitingForConnectionCurrentCount();
      sample.WaitingForConnectionHighCount[i][0]       = jdbcRunBean[i].getWaitingForConnectionHighCount();
    }

  }

  public void setContext(Config config, Sample sample, boolean exitCode)
  {
    try
    {
      System.out.println("Connecting to t3://" + sample.propHostName + ":" + sample.propPort + "  at " + sample.propServerName + "  in " + sample.propDomain);
      MBeanHome mbh                = Helper.getMBeanHome("system", sample.propPassword, "t3://"+sample.propHostName+":"+sample.propPort, sample.propServerName);
      RemoteMBeanServer homeServer = (RemoteMBeanServer)mbh.getMBeanServer();
      MBeanHome home               = (MBeanHome)homeServer.getMBeanHome();
      jvmBean                      = (JVMRuntimeMBean) home.getRuntimeMBean(sample.propServerName, "JVMRuntime");
      exeBean                      = (ExecuteQueueRuntimeMBean) home.getRuntimeMBean(sample.propQueueName, "ExecuteQueueRuntime");

      Set mbeans = mbh.getMBeansByType("JDBCConnectionPoolRuntime");

      sample.JDBC_POOL_CNT = mbeans.size();
      jdbcRunBean = new JDBCConnectionPoolRuntimeMBean[sample.JDBC_POOL_CNT];

      RuntimeMBean bean = null;
      StringBuffer sb   = new StringBuffer();

      int i = 0;
      for( Iterator it = mbeans.iterator(); it.hasNext(); )
      {
        bean = (RuntimeMBean)it.next();
        sb.append(bean.getName()).append(",");
        jdbcRunBean[i++] = (JDBCConnectionPoolRuntimeMBean)home.getRuntimeMBean(bean.getName(),"JDBCConnectionPoolRuntime");
      }
      sample.PoolName = sb.toString();
      sb.setLength(0);

      serverBean = (ServerRuntimeMBean) home.getRuntimeMBean(sample.propServerName, "ServerRuntime");
      this.config = config;
      this.sample = sample;

      System.out.println("Connected!!!");

    } catch (javax.management.InstanceNotFoundException ne) {
      System.out.println(ne.getMessage());
    } catch (Exception e) {
      System.out.println("Exception : cannot connect " + "t3://" + sample.propHostName + ":" + sample.propPort + "  at " + sample.propServerName + "  in " + sample.propDomain+"\n");
      if( exitCode ) System.exit(-1);
    }
  }

  public String getDescription()
  {
    return "JMX communication protocol. Requires weblogic.jar to be in the classpath,\n     and wlstat must be able to access the Weblogic Server's JNDI tree.\n     Monitors JVM heap usage, WLS requests per second, execution queue length,\n     active sockets, active entity and session beans, and passivations per minute.";
  }


}