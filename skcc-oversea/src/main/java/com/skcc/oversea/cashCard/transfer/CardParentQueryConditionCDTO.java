package com.skcc.oversea.cashCard.transfer;

import com.skcc.oversea.framework.transfer.DTO;
import com.skcc.oversea.framework.constants.Constants;

public class CardParentQueryConditionCDTO extends DTO
{
    private String bankCode = Constants.BLANK;
    private int pageNumber = 1;
    private int linePerPage;

    private String tableName = Constants.BLANK;
    private String orderByColumn = Constants.BLANK;
    private String orderByMethod = Constants.BLANK;
    private String selectValue = Constants.BLANK;

    public CardParentQueryConditionCDTO()
    {
    }

    public CardParentQueryConditionCDTO(String tableName,
                                        String orderByColumn,
                                        String orderByMethod,
                                        String selectValue){
        this.tableName = tableName;
        this.orderByColumn = orderByColumn;
        this.orderByMethod = orderByMethod;
        this.selectValue = selectValue;
    }

    // Getter Method
    public String getBankCode()
    {
        return bankCode;
    }
    public int getLinePerPage()
    {
        return linePerPage;
    }
    public String getOrderByColumn()
    {
        return orderByColumn;
    }
    public String getOrderByMethod()
    {
        return orderByMethod;
    }
    public int getPageNumber()
    {
        return pageNumber;
    }
    public String getSelectValue()
    {
        return selectValue;
    }
    public String getTableName()
    {
        return tableName;
    }

    // Setter Method
    public void setBankCode(String bankCode)
    {
        this.bankCode = bankCode;
    }
    public void setLinePerPage(int linePerPage)
    {
        this.linePerPage = linePerPage;
    }
    public void setOrderByColumn(String orderByColumn)
    {
        this.orderByColumn = orderByColumn;
    }
    public void setOrderByMethod(String orderByMethod)
    {
        this.orderByMethod = orderByMethod;
    }
    public void setPageNumber(int pageNumber)
    {
        this.pageNumber = pageNumber;
    }
    public void setSelectValue(String selectValue)
    {
        this.selectValue = selectValue;
    }
    public void setTableName(String tableName)
    {
        this.tableName = tableName;
    }
}

