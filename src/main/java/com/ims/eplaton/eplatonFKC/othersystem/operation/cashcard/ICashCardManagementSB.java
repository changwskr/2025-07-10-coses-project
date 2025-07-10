package com.ims.eplaton.eplatonFKC.othersystem.operation.cashcard;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

import java.rmi.RemoteException;

import com.chb.coses.cosesFramework.exception.CosesAppException;
import com.chb.coses.cosesFramework.transfer.CosesCommonDTO;
import com.chb.coses.cosesFramework.transfer.BatchJobProcessorResultDTO;
import com.chb.coses.cashCard.transfer.*;

import com.ims.eplaton.eplatonFWK.transfer.*;

public interface ICashCardManagementSB
{
    public EPlatonEvent queryForRegisterCashCard(EPlatonEvent eplatonevent)
        throws CosesAppException, RemoteException;

    public CashCardCDTO queryForRegisterCashCard(CashCardCDTO cashCardCDTO,
            CosesCommonDTO commonDTO) throws CosesAppException, RemoteException;

    public CashCardCDTO registerCashCard(CashCardCDTO cashCardCDTO,
            CosesCommonDTO commonDTO) throws CosesAppException, RemoteException;

    public CashCardCDTO modifyCashCard(CashCardCDTO cashCardCDTO,
            CosesCommonDTO commonDTO) throws CosesAppException, RemoteException;

}
