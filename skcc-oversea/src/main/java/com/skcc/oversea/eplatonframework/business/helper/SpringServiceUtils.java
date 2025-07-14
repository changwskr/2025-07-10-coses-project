package com.skcc.oversea.eplatonframework.business.helper;

import com.skcc.oversea.eplatonframework.business.service.CommonService;
import com.skcc.oversea.eplatonframework.business.service.ReferenceService;
import com.skcc.oversea.eplatonframework.business.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SpringServiceUtils {

    @Autowired
    private CommonService commonService;

    @Autowired
    private ReferenceService referenceService;

    @Autowired
    private UserService userService;

    public CommonService getCommonService() {
        return commonService;
    }

    public void setCommonService(CommonService commonService) {
        this.commonService = commonService;
    }

    public ReferenceService getReferenceService() {
        return referenceService;
    }

    public void setReferenceService(ReferenceService referenceService) {
        this.referenceService = referenceService;
    }

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}