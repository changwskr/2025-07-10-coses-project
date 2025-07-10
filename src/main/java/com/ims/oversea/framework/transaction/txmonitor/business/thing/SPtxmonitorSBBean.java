package com.ims.oversea.framework.transaction.txmonitor.business.thing;

import javax.ejb.*;
import java.util.*;
import com.ims.oversea.framework.transaction.txmonitor.business.thing.model.*;
import com.ims.oversea.framework.transaction.txmonitor.business.thing.entity.transactioninfo.*;
import com.chb.coses.cosesFramework.exception.CosesAppException;
import com.chb.coses.cosesFramework.exception.CosesExceptionDetail;
import com.ims.oversea.framework.transaction.txmonitor.business.thing.helper.EJBUtilThing;
import com.ims.oversea.framework.transaction.txmonitor.business.thing.helper.DTOConverter;
import com.ims.oversea.foundation.logej.LOGEJ;
import com.ims.oversea.eplatonframework.transfer.EPlatonEvent;
import com.ims.oversea.framework.transaction.constant.TCFConstants;

public class SPtxmonitorSBBean implements SessionBean {
  SessionContext sessionContext;
  public void ejbCreate() throws CreateException {
    /**@todo Complete this method*/
  }
  public void ejbRemove() {
    /**@todo Complete this method*/
  }
  public void ejbActivate() {
    /**@todo Complete this method*/
  }
  public void ejbPassivate() {
    /**@todo Complete this method*/
  }
  public void setSessionContext(SessionContext sessionContext) {
    this.sessionContext = sessionContext;
  }
  //-----------------------------------------------------------------------------

  /**
   * 하나의 row에 대한 엔티티빈 리모트 인터페이스를 구한다.
   *
   * @param event
   * @param transactioninfoDDTO
   * @return
   * @throws CosesAppException
   */
  private TransactionInfoEB findByTransactioninfoEB(EPlatonEvent event, TransactionInfoDDTO transactioninfoDDTO) throws CosesAppException {
    TransactionInfoEBHome transactioninfoEBHome = EJBUtilThing.getTransactionInfoEBHome();
    TransactionInfoEB transactioninfoEB = null;

    try
    {
      LOGEJ.getInstance().printf(TCFConstants.GENERAL_LOG_LEVEL,event,"데이타베이스로부터 하나의 레코드를 구해 이것을 엔티티빈에 할당해서 가지고 온다");
      transactioninfoEB = transactioninfoEBHome.findByPrimaryKey(
          new TransactionInfoEBPK(transactioninfoDDTO.getTransaction_id()));
    }
    catch (FinderException fEx)
    {
      LOGEJ.getInstance().printf(TCFConstants.GENERAL_LOG_LEVEL,event,
                                 "findByPrimaryKey()할 경우 에러는 finderException 이발생할 가능성이 있다");
      LOGEJ.getInstance().printf(TCFConstants.GENERAL_LOG_LEVEL,event,
                                 "그러므로 CosesExceptionDetail클래스를 생성하고,addMessage(),addArguement()해서 CosesAppException으로 처리한다->CosesAppException(csdetail)");

      CosesExceptionDetail detail = new CosesExceptionDetail("0125");
      detail.addMessage("Transaction ID", transactioninfoDDTO.getTransaction_id());
      detail.addArgument("Transactioninfo System");
      detail.addArgument("findByTransactioninfoEB()");
      throw new CosesAppException(detail);
    }
    return transactioninfoEB;
  }

  //-----------------------------------------------------------------------------
  public TransactionInfoDDTO getTransactionInfo(EPlatonEvent event, TransactionInfoDDTO transactionDDTO) throws CosesAppException {
    /**@todo Complete this method*/
    TransactionInfoEB transactioninfoEB = findByTransactioninfoEB(event,transactionDDTO);
    transactionDDTO = DTOConverter.getTransactionInfoDDTO(transactioninfoEB);
    return transactionDDTO;
  }

  /**
   * 하나의 레코드를 DB에 생성하는 예이다.
   *
   *
   * @param event
   * @param transactionDDTO
   * @return
   * @throws CosesAppException
   */
  public boolean insertTransactionInfo(EPlatonEvent event, TransactionInfoDDTO transactionDDTO) throws CosesAppException {
    LOGEJ.getInstance().printf(1,event,"1. 엔티티빈에 대한 홈객체을 구한다");
    TransactionInfoEBHome transactionInfoEBHome = EJBUtilThing.getTransactionInfoEBHome();
    TransactionInfoEB transactionInfoEB = null;

    try
    {
      LOGEJ.getInstance().printf(1,event,"2. 엔티티빈의 Home으로부터 리모트객체를 구한다");
      LOGEJ.getInstance().printf(1,event,"   하나의 레코드를 생성하는 과정이므로 홈객체의 create메소드를 통해서 리모트객체를 생성한다");
      LOGEJ.getInstance().printf(1,event,"   그리고 데이타베이스에 하나의 레코드가 생기는 시간은 컨테이너가 commit을 만나면서 ejbStore 메소드를 호출하는 순간이다");

      transactionInfoEB = transactionInfoEBHome.create(
          transactionDDTO.getTransaction_id(),
          transactionDDTO.getHost_name(),
          transactionDDTO.getSystem_name(),
          transactionDDTO.getMethod_name(),
          transactionDDTO.getBank_code(),
          transactionDDTO.getBranch_code(),
          transactionDDTO.getUser_id(),
          transactionDDTO.getChannel_type(),
          transactionDDTO.getBusiness_date(),
          transactionDDTO.getRegister_date(),
          transactionDDTO.getEvent_no(),
          transactionDDTO.getTransaction_no(),
          transactionDDTO.getOrg_seq(),
          transactionDDTO.getIn_time(),
          transactionDDTO.getOut_time(),
          transactionDDTO.getResponse_time(),
          transactionDDTO.getError_code(),
          transactionDDTO.getIp_address(),
          transactionDDTO.getTpfq(),
          transactionDDTO.getStf_intime(),
          transactionDDTO.getStf_outtime(),
          transactionDDTO.getStf_interval(),
          transactionDDTO.getBtf_intime(),
          transactionDDTO.getBtf_outtime(),
          transactionDDTO.getBtf_interval(),
          transactionDDTO.getEtf_intime(),
          transactionDDTO.getEtf_outtime(),
          transactionDDTO.getEtf_interval()
          );
    }
    catch (DuplicateKeyException dke)
    {
      CosesExceptionDetail detail = new CosesExceptionDetail("0125");
      detail.addMessage("TransactionNo", transactionDDTO.getTransaction_no() );
      detail.addArgument("TransactionInfo System");
      detail.addArgument("insertTransactionInfo()");
      throw new CosesAppException(detail);
    }
    catch (CreateException ex)
    {
      throw new EJBException(ex);
    }

    return true;
  }


  public TransactionInfoDDTO findTransactionInfoByHostSeq(String hostseq) throws CosesAppException {
    TransactionInfoEBHome transactionInfoEBHome = EJBUtilThing.getTransactionInfoEBHome();
    TransactionInfoEB transactionInfoEB = null;

    try
    {
      transactionInfoEB = transactionInfoEBHome.findByPrimaryKey(new TransactionInfoEBPK(hostseq));
    }
    catch (ObjectNotFoundException one)
    {
      CosesExceptionDetail detail = new CosesExceptionDetail("100");
      detail.addMessage("transaction id", null);
      detail.addArgument("TransactionInfo System");
      detail.addArgument("findTransactionInfoByHostSeq()");
      throw new CosesAppException(detail);
    }
    catch (FinderException ex)
    {
      throw new EJBException(ex);
    }

    TransactionInfoDDTO inTransactionInfoDDTO =  DTOConverter.getTransactionInfoDDTO(transactionInfoEB);

    return inTransactionInfoDDTO;
  }

  public TransactionInfoListDDTO findTransactionInfoByTime(String intime, String outtime) throws CosesAppException {
    TransactionInfoEBHome transactionInfoEBHome = EJBUtilThing.getTransactionInfoEBHome();
    TransactionInfoEB transactionInfoEB = null;
    TransactionInfoListDDTO al = null;
    try
    {
      al = new TransactionInfoListDDTO();
      Collection transactionInfoEBs = transactionInfoEBHome.findByTime(intime, outtime);

      Iterator iterator = transactionInfoEBs.iterator();
      if (transactionInfoEBs.size() != 0) {
        while(iterator.hasNext()) {
          transactionInfoEB = (TransactionInfoEB)iterator.next();
          al.add(findTransactionInfoByHostSeq(transactionInfoEB.getTransaction_id()));
        }
      }

    }
    catch (ObjectNotFoundException one)
    {
      CosesExceptionDetail detail = new CosesExceptionDetail(
          "0100");
      detail.addMessage("CardNumber", null);
      detail.addArgument("TransactionInfo System");
      detail.addArgument("findTransactionInfoInfoByCardNo()");
      throw new CosesAppException(detail);
    }
    catch (FinderException ex)
    {
      throw new EJBException(ex);
    }

    return al;
  }

  public boolean updateTransactionInfoByHostseq(String hostseq,String hostname) throws CosesAppException {
    TransactionInfoEBHome transactionInfoEBHome = EJBUtilThing.getTransactionInfoEBHome();
    TransactionInfoEB transactionInfoEB = null;

    try
    {
      transactionInfoEB = transactionInfoEBHome.findByPrimaryKey(new TransactionInfoEBPK(hostseq));
      transactionInfoEB.setUser_id("entitybean");
    }
    catch (ObjectNotFoundException one)
    {
      CosesExceptionDetail detail = new CosesExceptionDetail("100");
      detail.addMessage("transaction id", null);
      detail.addArgument("TransactionInfo System");
      detail.addArgument("findTransactionInfoByHostSeq()");
      throw new CosesAppException(detail);
    }
    catch (FinderException ex)
    {
      throw new EJBException(ex);
    }

    /**@todo Complete this method*/
    return true;
  }



}