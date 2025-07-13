package com.skcc.oversea.cashCard.transfer;

import java.util.List;

import com.chb.coses.framework.transfer.PageDTO;

public class CashCardPageCDTO extends PageDTO
{

    public CashCardPageCDTO(List pageItems, int pageNumber,
                               int totalLineCount)
    {
        super(pageItems, pageNumber, totalLineCount);
    }

    public CashCardPageCDTO(List pageItems, int pageNumber,
                               int totalLineCount, int linePerPage)
    {
        super(pageItems, pageNumber, totalLineCount, linePerPage);
    }
}
