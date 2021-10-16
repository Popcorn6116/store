package com.mhj.store.service;

import com.mhj.store.element.User;

/*interface of user module on service layer*/
public interface IUserService {
    void reg(User user);

    //返回一个用户（当前匹配的用户数据），如果没有返回null
    User login(String username, String password);
}
