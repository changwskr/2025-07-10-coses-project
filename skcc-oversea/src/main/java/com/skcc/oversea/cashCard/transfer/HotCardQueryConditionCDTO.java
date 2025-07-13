package com.skcc.oversea.cashCard.transfer;

import com.skcc.oversea.framework.constants.Constants;

import com.skcc.oversea.cashCard.transfer.CardParentQueryConditionCDTO;

public class HotCardQueryConditionCDTO extends CardParentQueryConditionCDTO
{
    private String primaryAccountNo = Constants.BLANK;

    public HotCardQueryConditionCDTO()
    {
        super("HOT_CARD", "CARD_NUMBER", "ASC",
              "CARD_NUMBER, PRIMARY_ACCOUNT_NO, CIF_NAME, INCIDENT_CODE, " +
              "STATUS, REGISTER_DATE, RELEASED_DATE");
    }

    public HotCardQueryConditionCDTO(String orderByColumn)
    {
        super("HOT_CARD", "CARD_NUMBER", orderByColumn,
              "CARD_NUMBER, PRIMARY_ACCOUNT_NO, CIF_NAME, INCIDENT_CODE, " +
              "STATUS, REGISTER_DATE, RELEASED_DATE");
    }

    // Getter Method
    public String getPrimaryAccountNo()
    {
        return primaryAccountNo;
    }

    // Setter Method
    public void setPrimaryAccountNo(String primaryAccountNo)
    {
        this.primaryAccountNo = primaryAccountNo;
    }
}

