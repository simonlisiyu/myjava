package com.lsy.test.spring.jpa.service;


import com.lsy.test.spring.jpa.bean.TUser;

import java.util.List;

/**
 * Created by lisiyu on 2020/4/8.
 */
public interface TUserService {

    /**
     * get all user
     * @return
     */
    public List<TUser> getAllUser();

}
