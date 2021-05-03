package com.appirio.service.test.manager;

import com.appirio.tech.core.api.v3.TCID;
import com.appirio.tech.core.auth.AuthUser;

public class Authorization extends AuthUser {

    public Authorization(){
        super();
    }

    protected  void setUserId(TCID userId){
        super.setUserId(userId);
    }
}
