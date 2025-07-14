package com.skcc.oversea.cashCard.transfer;

import com.skcc.oversea.framework.transfer.DTO;
import com.skcc.oversea.framework.constants.Constants;

public class CardParentQueryConditionCDTO extends DTO {
    private String bankCode = Constants.BLANK;
    private int pageNumber = 1;
    private int linePerPage;

    private String tableName = Constants.BLANK;
    private String orderByColumn = Constants.BLANK;
    private String orderByMethod = Constants.BLANK;
    private String selectValue = Constants.BLANK;

    public CardParentQueryConditionCDTO() {
    }

    public CardParentQueryConditionCDTO(String tableName,
            String orderByColumn,
            String orderByMethod,
            String selectValue) {
        this.tableName = tableName;
        this.orderByColumn = orderByColumn;
        this.orderByMethod = orderByMethod;
        this.selectValue = selectValue;
    }

    // Getter Method
    public String getBankCode() {
        return bankCode;
    }

    public int getLinePerPage() {
        return linePerPage;
    }

    public String getOrderByColumn() {
        return orderByColumn;
    }

    public String getOrderByMethod() {
        return orderByMethod;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public String getSelectValue() {
        return selectValue;
    }

    public String getTableName() {
        return tableName;
    }

    // Setter Method
    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public void setLinePerPage(int linePerPage) {
        this.linePerPage = linePerPage;
    }

    public void setOrderByColumn(String orderByColumn) {
        this.orderByColumn = orderByColumn;
    }

    public void setOrderByMethod(String orderByMethod) {
        this.orderByMethod = orderByMethod;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public void setSelectValue(String selectValue) {
        this.selectValue = selectValue;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((bankCode == null) ? 0 : bankCode.hashCode());
        result = prime * result + pageNumber;
        result = prime * result + linePerPage;
        result = prime * result + ((tableName == null) ? 0 : tableName.hashCode());
        result = prime * result + ((orderByColumn == null) ? 0 : orderByColumn.hashCode());
        result = prime * result + ((orderByMethod == null) ? 0 : orderByMethod.hashCode());
        result = prime * result + ((selectValue == null) ? 0 : selectValue.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        CardParentQueryConditionCDTO other = (CardParentQueryConditionCDTO) obj;
        if (bankCode == null) {
            if (other.bankCode != null)
                return false;
        } else if (!bankCode.equals(other.bankCode))
            return false;
        if (pageNumber != other.pageNumber)
            return false;
        if (linePerPage != other.linePerPage)
            return false;
        if (tableName == null) {
            if (other.tableName != null)
                return false;
        } else if (!tableName.equals(other.tableName))
            return false;
        if (orderByColumn == null) {
            if (other.orderByColumn != null)
                return false;
        } else if (!orderByColumn.equals(other.orderByColumn))
            return false;
        if (orderByMethod == null) {
            if (other.orderByMethod != null)
                return false;
        } else if (!orderByMethod.equals(other.orderByMethod))
            return false;
        if (selectValue == null) {
            if (other.selectValue != null)
                return false;
        } else if (!selectValue.equals(other.selectValue))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "CardParentQueryConditionCDTO{" +
                "bankCode='" + bankCode + '\'' +
                ", pageNumber=" + pageNumber +
                ", linePerPage=" + linePerPage +
                ", tableName='" + tableName + '\'' +
                ", orderByColumn='" + orderByColumn + '\'' +
                ", orderByMethod='" + orderByMethod + '\'' +
                ", selectValue='" + selectValue + '\'' +
                '}';
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
