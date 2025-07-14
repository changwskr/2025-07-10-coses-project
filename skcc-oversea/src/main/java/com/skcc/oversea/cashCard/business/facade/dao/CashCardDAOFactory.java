package com.skcc.oversea.cashCard.business.facade.dao;

import com.skcc.oversea.framework.business.dao.AbstractDAOFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CashCardDAOFactory extends AbstractDAOFactory {

    @Autowired
    private CashCardDAO cashCardDAO;

    public CashCardDAO getCashCardDAO() {
        return cashCardDAO;
    }

    public void setCashCardDAO(CashCardDAO cashCardDAO) {
        this.cashCardDAO = cashCardDAO;
    }
}
