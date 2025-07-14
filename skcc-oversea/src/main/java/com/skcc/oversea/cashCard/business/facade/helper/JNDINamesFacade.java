package com.skcc.oversea.cashCard.business.facade.helper;

import org.springframework.stereotype.Component;

@Component
public class JNDINamesFacade {

    public static final String CASH_CARD_SERVICE = "java:global/skcc-oversea/CashCardService";
    public static final String HOT_CARD_SERVICE = "java:global/skcc-oversea/HotCardService";
    public static final String DEPOSIT_SERVICE = "java:global/skcc-oversea/DepositService";
    public static final String TELLER_SERVICE = "java:global/skcc-oversea/TellerService";
    public static final String COMMON_SERVICE = "java:global/skcc-oversea/CommonService";
    public static final String REFERENCE_SERVICE = "java:global/skcc-oversea/ReferenceService";
    public static final String USER_SERVICE = "java:global/skcc-oversea/UserService";

    public String getServiceJNDIName(String serviceName) {
        return "java:global/skcc-oversea/" + serviceName;
    }

    public String getLocalServiceJNDIName(String serviceName) {
        return "java:app/skcc-oversea/" + serviceName;
    }
}