package com.lsy.test.spring.jpa.service.impl;

import com.lsy.test.spring.jpa.bean.TUser;
import com.lsy.test.spring.jpa.repository.TUserRepository;
import com.lsy.test.spring.jpa.service.TUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by lisiyu on 2020/4/8.
 */
@Service
public class TUserServiceImpl implements TUserService {
    @Resource
    private TUserRepository tUserRepository;

    @Override
    public List<TUser> getAllUser() {
        return tUserRepository.findAll();
    }
}
