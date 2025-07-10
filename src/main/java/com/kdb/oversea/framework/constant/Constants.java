package com.kdb.oversea.framework.constant;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */


/* EPLtxblocking.xml
 <ENVIRONMENT>

        <TRANSACTION-BLOCKING>

                <TXBLOCKCNT>5</TXBLOCKCNT>
                <TPFQ>
                        <EMODE>OFF</EMODE>
                        <EVNTS1>100</EVNTS1>
                        <EVNTE1>100</EVNTE1>
                        <EVNTS2>200</EVNTS2>
                        <EVNTE2>200</EVNTE2>
                        <EVNTS3>300</EVNTS3>
                        <EVNTE3>300</EVNTE3>
                        <EVNTS4>*</EVNTS4>
                        <EVNTE4>*</EVNTE4>
                        <EVNTS5>*</EVNTS5>
                        <EVNTE5>*</EVNTE5>
                </TPFQ>
                <TXCODE>
                        <EMODE>OFF</EMODE>
                        <EVNTS1>80001</EVNTS1>
                        <EVNTE1>80002</EVNTE1>
                        <EVNTS2>80005</EVNTS2>
                        <EVNTE2>80005</EVNTE2>
                        <EVNTS3>*</EVNTS3>
                        <EVNTE3>*</EVNTE3>
                        <EVNTS4>*</EVNTS4>
                        <EVNTE4>*</EVNTE4>
                        <EVNTS5>*</EVNTS5>
                        <EVNTE5>*</EVNTE5>
                </TXCODE>
                <TELLER>
                        <TMODE>OFF</TMODE>
                        <TELLS>NotUse</TELLS>
                        <TELLE>NotUse</TELLE>
                        <TELLS1>0238001</TELLS1>
                        <TELLE1>0238001</TELLE1>
                        <TELLS2>*</TELLS2>
                        <TELLE2>*</TELLE2>
                        <TELLS3>*</TELLS3>
                        <TELLE3>*</TELLE3>
                        <TELLS4>*</TELLS4>
                        <TELLE4>*</TELLE4>
                        <TELLS5>*</TELLS5>
                        <TELLE5>*</TELLE5>
                </TELLER>
                <BRANCH>
                        <BMODE>OFF</BMODE>
                        <BRCHS>NotUse</BRCHS>
                        <BRCHE>NotUse</BRCHE>
                        <BRCHS1>*</BRCHS1>
                        <BRCHE1>*</BRCHE1>
                        <BRCHS2>*</BRCHS2>
                        <BRCHE2>*</BRCHE2>
                        <BRCHS3>*</BRCHS3>
                        <BRCHE3>*</BRCHE3>
                        <BRCHS4>*</BRCHS4>
                        <BRCHE4>*</BRCHE4>
                        <BRCHS5>*</BRCHS5>
                        <BRCHE5>*</BRCHE5>
                </BRANCH>

        </TRANSACTION-BLOCKING>
</ENVIRONMENT>

*/
public class Constants {

  public static String TX_BLOCKING_CONFIG_FILE_NAME=
      "/weblogic/bea/wlserver6.1/config/coses_US/applicationConfig/EPLtxblocking.xml";
  public static String TPMTRACE_LoggingFileName = "/home/coses/log/outlog/TPMTRACE";
  public static String INPUT_LOGFILENAME = "/home/coses/log/input/";
  public static String OUTPUT_LOGFILENAME = "/home/coses/log/output/";

}