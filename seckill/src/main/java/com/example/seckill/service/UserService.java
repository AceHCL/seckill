package com.example.seckill.service;

import com.example.seckill.dao.UserDao;
import com.example.seckill.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 描述:
 *
 * @author ace-huang
 * @create 2019-12-22 4:35 PM
 */
@Service
public class UserService {
    @Autowired
    private UserDao userDao;

    public User getById(int id){
        return userDao.getById(id);
    }
}