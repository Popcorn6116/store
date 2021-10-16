package com.mhj.store.service.impl;


import com.mhj.store.element.User;
import com.mhj.store.mapper.UserMapper;
import com.mhj.store.service.IUserService;
import com.mhj.store.service.ex.InsertException;
import com.mhj.store.service.ex.PasswordNotMatchException;
import com.mhj.store.service.ex.UsernameDuplicatedException;
import com.mhj.store.service.ex.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.Locale;
import java.util.UUID;

/*@service:The objects of the current class are handed over to Spring for management, automatic object creation and maintenance*/
@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public void reg(User user) {
        String username = user.getUsername();
        User result = userMapper.findByUsername(username);
        if(result != null){
            throw new UsernameDuplicatedException("The username is duplicated!");
        }

        String password = user.getPassword();
        String salt = UUID.randomUUID().toString().toUpperCase(Locale.ROOT);
        user.setSalt(salt);
        String md5Password = getMD5(password,salt);
        user.setPassword(md5Password);

        user.setIsDelete(0);
        user.setCreatedUser(user.getUsername());
        user.setModifiedUser(user.getUsername());
        Date date = new Date();
        user.setCreatedTime(date);
        user.setModifiedTime(date);


        Integer rows = userMapper.insert((user));
        if(rows != 1){
            throw new InsertException("An unknown exception occurred in the registration!");
        }
    }

    @Override
    public User login(String username, String password) {
        User result = userMapper.findByUsername(username);
        if(result == null){
            throw new UserNotFoundException("The user does not exist");
        }

        if(result.getIsDelete()==1){
            throw new UserNotFoundException("The user does not exist");
        }

        // match the password
        String salt = result.getSalt();
        String newMd5Password = getMD5(password, salt);
        String oldMd5Password = result.getPassword();
        if(!newMd5Password.equals(oldMd5Password)){
            throw new PasswordNotMatchException("User password error");
        }

        //Data compression improves system performance
        User user = new User();
        user.setUid(result.getUid());
        user.setUsername(result.getUsername());
        user.setAvatar(result.getAvatar());

        return user;
    }

    private String getMD5(String password, String salt){
        for(int i=0;i<3;i++){
            password = DigestUtils.md5DigestAsHex((salt+password+salt).getBytes()).toUpperCase();
        }
        return password;
    }
}
