package com.mhj.store.service;

import com.mhj.store.element.User;
import com.mhj.store.mapper.UserMapper;
import com.mhj.store.service.ex.ServiceException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.xml.ws.Service;

// @SpringBootTest:Indicates that the current class is a test class and will not be packaged with the project
@SpringBootTest
// @RunWith: start this test class
@RunWith(SpringRunner.class)

public class UserServiceTests {

    @Autowired
    private IUserService userService;

    @Test
    public void reg(){
        try {
            User user = new User();
            user.setUsername("mhj02");
            user.setPassword("123");
            userService.reg(user);
            System.out.println("OK");
        } catch (ServiceException e) {
            System.out.println(e.getClass().getSimpleName());
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    @Test
    public void login(){
        User user = userService.login("test01","123");
        System.out.println(user);
    }

    @Test
    public void changePassword(){
        userService.changePassword(17,"test07","123","321");
    }

    @Test
    public void getByUid(){
        System.err.println(userService.getByUid(17));
    }

    @Test
    public void changeInfo(){
        User user = new User();
        user.setPhone("13838383838");
        user.setEmail(("emial@qq.cpm"));
        user.setGender(0);
        userService.changeInfo(17,"cyj07",user);
    }

    @Test
    public void changeAvatar(){
        userService.changeAvatar(17,"/upload/test.png","baba");
    }
}
