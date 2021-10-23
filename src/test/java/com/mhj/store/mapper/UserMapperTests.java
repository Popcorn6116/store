package com.mhj.store.mapper;

import com.mhj.store.element.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

// @SpringBootTest:Indicates that the current class is a test class and will not be packaged with the project
@SpringBootTest
// @RunWith: start this test class
@RunWith(SpringRunner.class)

public class UserMapperTests {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void insert(){
        User user = new User();
        user.setUsername("mhjj");
        user.setPassword("1234");
        Integer rows = userMapper.insert(user);
        System.out.println(rows);
    }

    @Test
    public void findByUsername(){
        User user = userMapper.findByUsername("mhjj");
        System.out.println(user);
    }

    @Test
    public void findByUid(){
        System.out.println(userMapper.findByUid(3));
    }

    @Test
    public void updatePasswordByUid(){
        userMapper.updatePasswordByUid(3,"321","tester", new Date());
    }

    @Test
    public void updateInfoByUid(){
        User user = new User();
        user.setUid(17);
        user.setPhone("123456789");
        user.setEmail("123456789@qq.com");
        user.setGender(1);
        userMapper.updateInfoByUid(user);
    }

    @Test
    public void updateAvatarByUid(){
        userMapper.updateAvatarByUid(
                18,
                "/upload/avatar.png",
                "cyj01",
                new Date());
    }

}
