package com.sf.heros.im.service;

import com.sf.heros.im.common.bean.AuthCheck;

public interface AuthService {

    public AuthCheck check(String userId, String token);

}
