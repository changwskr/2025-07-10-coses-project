/*
* @(#)WLMinitor.java.         1.0   04/02/19
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

public class WLMonitor
{

  public Sample  sample;
  public Display display;

  public static void main(String[] args)
  {
    new WLMonitor(args);
  }

  public WLMonitor(String[] args)
  {
    boolean Error;
    int     interval;
    display = new Display();
    sample  = new Sample();
    argRead( args );

    Config config = new Config(sample);
    interval = sample.propSamplePeriod * 1000;
    config.mBean.setContext(config, sample, true);
    display.clearScreen();

    int i =0;
    while (true)
    {
      Error = false;
      try
      {
        config.mBean.getSample();
        display.display(sample);
      }catch(Exception e){
        e.printStackTrace();
        display.displayConnectError();
        Error = true;
      }
      try
      {
        Thread.sleep(interval);
        if(Error){
          sample.previousRequests=0;
          config.mBean.setContext(config, sample, false);
          Thread.sleep(1000);
          display.clearScreen();
        }
        } catch (InterruptedException ie) {}
        i++;
    }
  }


/*
  // 여러 managed서버를 동시에 모니터링 하기 위한 메소드
  // 향후 업글때 적용예정
    public WLMonitor_org(String[] args)
    {
        Display display = new Display();

        for(int i=0; i<serverCount; i++)
        {
         sample = new Sample();
         samples.addElement(sample);
  Config config = new Config(i,sample);
  config.mBean.setContext(config, sample);
  configs.addElement(config);

        }

        int i =0;
        while (true)
        {
     for(int j=0; j < serverCount ; j++)
     {
   sample = (Sample) samples.elementAt(j);
   Config config = (Config) configs.elementAt(j);

          try
          {
           config.mBean.getSample();
           display.display(sample);
          }catch(Exception e){   }
     }

            try
            {
                int interval = sample.propSamplePeriod * 1000;
                Thread.sleep(interval);
            } catch (InterruptedException ie) {}
            i++;
        }
    }
*/

  private void argRead(String[] args)
  {
    try
    {
      if( args.length !=1 ){
        usage();
      }
      else {
        sample.propServerName = args[0];
        System.out.println("\nServerName : " + sample.propServerName);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void usage()
  {
    System.out.println("\nKipoNET WLMonitor version : " + sample.version);
    System.out.println("\nUsage: java org.kipo.wlmonitor.WLMonitor <server name>\n");
    System.exit(0);
  }


}