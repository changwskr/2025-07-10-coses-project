package com.skcc.oversea.cashCard.transfer;

import java.util.List;

import com.chb.coses.framework.transfer.PageDTO;

public class HotCardPageCDTO extends PageDTO
{
    public HotCardPageCDTO(List pageItems, int pageNumber,
                               int totalLineCount)
    {
        super(pageItems, pageNumber, totalLineCount);
    }

    public HotCardPageCDTO(List pageItems, int pageNumber,
                               int totalLineCount, int linePerPage)
    {
        super(pageItems, pageNumber, totalLineCount, linePerPage);
    }
}
