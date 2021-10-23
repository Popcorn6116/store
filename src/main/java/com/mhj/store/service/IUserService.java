package com.mhj.store.service;

import com.mhj.store.element.User;

/*interface of user module on service layer*/
public interface IUserService {
    void reg(User user);

    //返回一个用户（当前匹配的用户数据），如果没有返回null
    User login(String username, String password);

    void  changePassword(Integer uid,
                         String username,
                         String oldPassword,
                         String newPassword);

    User getByUid(Integer uid);

    void changeInfo(Integer uid, String username, User user);

    /**
     * change the avatar of user
     * @param uid  user id
     * @param avatar  avatar path
     * @param username  username
     */
    void changeAvatar(Integer uid, String avatar, String username);
}
