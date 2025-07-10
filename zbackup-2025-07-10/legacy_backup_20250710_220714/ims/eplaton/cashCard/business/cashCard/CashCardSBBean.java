package com.ims.eplaton.cashCard.business.cashCard;
// 1 TEST ADD
// 2 TEST ADD
import javax.ejb.*;

import com.chb.coses.foundation.log.Log;
import com.chb.coses.cosesFramework.transfer.CosesCommonDTO;
import com.chb.coses.cosesFramework.exception.CosesAppException;
import com.chb.coses.cosesFramework.exception.CosesExceptionDetail;

import com.chb.coses.common.business.constants.CommonErrorMessageConstants;

import com.ims.eplaton.cashCard.business.cashCard.model.*;
import com.ims.eplaton.cashCard.business.cashCard.helper.*;
import com.ims.eplaton.cashCard.business.cashCard.entity.*;
import com.ims.eplaton.cashCard.business.cashCardRule.helper.IOBoundCardRegister;

public class CashCardSBBean extends com.chb.coses.framework.business.AbstractSessionBean
        implements ICashCardSB
{
    public void ejbCreate() throws CreateException {
        /**@todo Complete this method*/
    }
//=========================== Private Method Area =================================//
    private CashCardEB findByCashCardEB(CashCardDDTO cashCardDDTO)
            throws CosesAppException
    {
        CashCardEBHome cashCardEBHome = EJBUtilThing.getCashCardEBHome();

        CashCardEB cashCardEB = null;
        if(Log.SDBLogger.isDebugEnabled())
        {
            Log.SDBLogger.debug("SequenceNo : " + cashCardDDTO.getSequenceNo());
            Log.SDBLogger.debug("CardNumber : " + cashCardDDTO.getCardNumber());
            Log.SDBLogger.debug("BankCode : " + cashCardDDTO.getBankCode());
            Log.SDBLogger.debug("PrimaryAccountNo : " + cashCardDDTO.getPrimaryAccountNo());
        }

        try
        {
            cashCardEB = cashCardEBHome.findByPrimaryKey(
                    new CashCardEBPK(cashCardDDTO.getSequenceNo(), cashCardDDTO.getCardNumber(),
                    cashCardDDTO.getBankCode(), cashCardDDTO.getPrimaryAccountNo()));
        }
        catch (FinderException fEx)
        {
            CosesExceptionDetail detail = new CosesExceptionDetail(
                    CommonErrorMessageConstants.ERR_0125_ACCOUNT_NUMBER_DOES_NOT_EXIST);
            detail.addMessage("PrimaryAccountNo", cashCardDDTO.getPrimaryAccountNo());
            detail.addArgument("CashCard System");
            detail.addArgument("findByCashCardEB()");
            throw new CosesAppException(detail);
        }
        return cashCardEB;
    }

    private HotCardEB findByHotCardEB(HotCardDDTO hotCardDDTO, CosesCommonDTO commonDTO)
            throws CosesAppException
    {
        HotCardEBHome hotCardEBHome = EJBUtilThing.getHotCardEBHome();

        HotCardEB hotCardEB = null;

        try
        {
            hotCardEB = hotCardEBHome.findByPrimaryKey(
                    new HotCardEBPK(hotCardDDTO.getSequenceNo(),
                    hotCardDDTO.getCardNumber()));
        }
        catch (FinderException fEx)
        {
            CosesExceptionDetail detail = new CosesExceptionDetail(
                    CommonErrorMessageConstants.ERR_0125_ACCOUNT_NUMBER_DOES_NOT_EXIST);
            detail.addMessage("CardNumber", hotCardDDTO.getCardNumber());
            detail.addArgument("CashCard System");
            detail.addArgument("findByHotCardEB()");
            throw new CosesAppException(detail);
        }
        return hotCardEB;
    }
//=========================== Private Method Area =================================//

    public CashCardDDTO getCashCardInfo(CashCardDDTO cashCardDDTO,
            CosesCommonDTO commonDTO) throws CosesAppException
    {
        CashCardEB cashCardEB = findByCashCardEB(cashCardDDTO);

        cashCardDDTO = DTOConverter.getCashCardDDTO(cashCardEB, cashCardDDTO);

        return cashCardDDTO;
    }

    public CashCardDDTO makeCashCard(CashCardDDTO cashCardDDTO,
            CosesCommonDTO commonDTO) throws CosesAppException
    {
        CashCardEBHome cashCardEBHome = EJBUtilThing.getCashCardEBHome();
        CashCardEB cashCardEB = null;
        try
        {
            cashCardEB = cashCardEBHome.create(cashCardDDTO.getBankType(),
                    cashCardDDTO.getBankCode(), cashCardDDTO.getPrimaryAccountNo(),
                    cashCardDDTO.getSequenceNo(), cashCardDDTO.getCardNumber(),
                    cashCardDDTO.getBranchCode(), cashCardDDTO.getType());

            DTOConverter.setCashCardDDTO(cashCardDDTO, cashCardEB);
        }
        catch (DuplicateKeyException dke)
        {
            CosesExceptionDetail detail = new CosesExceptionDetail(
                    CommonErrorMessageConstants.ERR_0182_ALREADY_EXISTING);
            detail.addMessage("PrimaryAccountNo", cashCardDDTO.getPrimaryAccountNo());
            detail.addArgument("CashCard System");
            detail.addArgument("makeCashCard()");
            throw new CosesAppException(detail);
        }
        catch (CreateException ex)
        {
            throw new EJBException(ex);
        }

        IOBoundCardRegister.getInstance().execute(
                cashCardDDTO.getPrimaryAccountNo(),
                cashCardDDTO.getCIFName(), cashCardDDTO.getBranchCode(),
                cashCardDDTO.getCardNumber());

        return cashCardDDTO;
    }

    public CashCardDDTO findCashCardInfoByCardNo(CashCardDDTO cashCardDDTO,
            CosesCommonDTO commonDTO) throws CosesAppException
    {
        CashCardEBHome cashCardEBHome = EJBUtilThing.getCashCardEBHome();
        CashCardEB cashCardEB = null;
        try
        {
            cashCardEB = cashCardEBHome.findByCardNumber(commonDTO.getBankCode(),
                    cashCardDDTO.getCardNumber());
        }
        catch (ObjectNotFoundException one)
        {
            CosesExceptionDetail detail = new CosesExceptionDetail(
                    CommonErrorMessageConstants.ERR_0100_ACCOUNT_DOES_NOT_EXIST);
            detail.addMessage("CardNumber", cashCardDDTO.getCardNumber());
            detail.addArgument("CashCard System");
            detail.addArgument("findCashCardInfoByCardNo()");
            throw new CosesAppException(detail);
        }
        catch (FinderException ex)
        {
            throw new EJBException(ex);
        }
        cashCardDDTO = DTOConverter.getCashCardDDTO(cashCardEB, cashCardDDTO);

        return cashCardDDTO;
    }

    public CashCardDDTO setCashCard(CashCardDDTO cashCardDDTO,
            CosesCommonDTO commonDTO) throws CosesAppException
    {
        if(Log.SDBLogger.isDebugEnabled())
        {
            Log.SDBLogger.debug("InvalideAttemptCount : " + cashCardDDTO.getInvalidAttemptCnt());
        }

        CashCardEB cashCardEB = findByCashCardEB(cashCardDDTO);

        if(Log.SDBLogger.isDebugEnabled())
        {
            Log.SDBLogger.debug("InvalideAttemptCount with Entity : " + cashCardEB.getInvalidAttemptCnt());
        }

        DTOConverter.setCashCardDDTO(cashCardDDTO, cashCardEB);

        return cashCardDDTO;
    }

    public HotCardDDTO getHotCardInfo(HotCardDDTO hotCardDDTO,
            CosesCommonDTO commonDTO) throws CosesAppException
    {
        HotCardEB hotCardEB = findByHotCardEB(hotCardDDTO, commonDTO);

        hotCardDDTO = DTOConverter.getHotCardCDTO(hotCardEB, hotCardDDTO);

        return hotCardDDTO;
    }

    public HotCardDDTO makeHotCard(HotCardDDTO hotCardDDTO,
            CosesCommonDTO commonDTO) throws CosesAppException
    {
        HotCardEBHome hotCardEBHome = EJBUtilThing.getHotCardEBHome();
        HotCardEB hotCardEB = null;
        try
        {
            hotCardEB = hotCardEBHome.create(hotCardDDTO.getCardNumber(),
                    hotCardDDTO.getSequenceNo());

            DTOConverter.setHotCardDDTO(hotCardDDTO, hotCardEB);
        }
        catch (DuplicateKeyException dke)
        {
            CosesExceptionDetail detail = new CosesExceptionDetail(
                    CommonErrorMessageConstants.ERR_0182_ALREADY_EXISTING);
            detail.addMessage("PrimaryAccountNo", hotCardDDTO.getPrimaryAccountNo());
            detail.addArgument("CashCard System");
            detail.addArgument("makeCashCard()");
            throw new CosesAppException(detail);
        }
        catch (CreateException ex)
        {
            throw new EJBException(ex);
        }
        return hotCardDDTO;
    }

    public HotCardDDTO releaseHotCard(HotCardDDTO hotCardDDTO,
            CosesCommonDTO commonDTO) throws CosesAppException
    {
        HotCardEB hotCardEB = findByHotCardEB(hotCardDDTO, commonDTO);

        setReleaseHotCard(hotCardDDTO, hotCardEB);

        return hotCardDDTO;
    }

    private void setReleaseHotCard(HotCardDDTO hotCardDDTO, HotCardEB hotCardEB)
    {
        hotCardEB.setIncidentCode(hotCardDDTO.getIncidentCode());
        hotCardEB.setStatus(hotCardDDTO.getStatus());
        hotCardEB.setReleasedDate(hotCardDDTO.getReleasedDate());
        hotCardEB.setReleasedTime(hotCardDDTO.getReleasedTime());
        hotCardEB.setReleasedBy(hotCardDDTO.getReleasedBy());
        hotCardEB.setRemark(hotCardDDTO.getRemark());
    }
}
