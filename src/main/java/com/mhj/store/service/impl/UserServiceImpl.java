package com.mhj.store.service.impl;


import com.mhj.store.element.User;
import com.mhj.store.mapper.UserMapper;
import com.mhj.store.service.IUserService;
import com.mhj.store.service.ex.*;
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

    @Override
    public void changePassword(Integer uid, String username, String oldPassword, String newPassword) {
        User result = userMapper.findByUid(uid);
        if(result == null || result.getIsDelete() == 1){
            throw new UserNotFoundException("User data does not exist.");
        }

        String oldMd5Password = getMD5(oldPassword,result.getSalt());
        if(!result.getPassword().equals(oldMd5Password)){
            throw new PasswordNotMatchException("The password is wrong.");
        }

        String newMd5Password = getMD5(newPassword,result.getSalt());
        Integer rows = userMapper.updatePasswordByUid(uid,newMd5Password,username,new Date());

        if(rows != 1){
            throw new UpdateException("An unknown exception occurred when the password was changing.");
        }

    }

    @Override
    public User getByUid(Integer uid) {
        User result = userMapper.findByUid(uid);
        if(result==null || result.getIsDelete()==1){
            throw new UserNotFoundException("The user does not exist.");
        }

        User user = new User();
        user.setUsername(result.getUsername());
        user.setPhone(result.getPhone());
        user.setEmail(result.getEmail());
        user.setGender(result.getGender());

        return user;
    }

    @Override
    public void changeInfo(Integer uid, String username, User user) {
        User result = userMapper.findByUid(uid);
        if(result == null || result.getIsDelete() == 1 ){
            throw new UserNotFoundException("The user does not exist.");
        }
        user.setUid(uid);
//        user.setUsername(username);
        user.setModifiedUser(username);
        user.setModifiedTime(new Date());

        Integer rows = userMapper.updateInfoByUid(user);
        if(rows != 1){
            throw new UpdateException("An unknown exception occurred during data update!");
        }
    }

    @Override
    public void changeAvatar(Integer uid, String avatar, String username) {
        User result = userMapper.findByUid(uid);
        if(result == null || result.getIsDelete() == 1 ){
            throw new UserNotFoundException("The user does not exist.");
        }

        Integer rows = userMapper.updateAvatarByUid(uid,avatar,username,new Date());
        if(rows != 1){
            throw new UpdateException("An unknown exception occurred during updating avatar!");
        }
    }


    /** Md5 encryption algorithm */
    private String getMD5(String password, String salt){
        for(int i=0;i<3;i++){
            password = DigestUtils.md5DigestAsHex((salt+password+salt).getBytes()).toUpperCase();
        }
        return password;
    }
}
