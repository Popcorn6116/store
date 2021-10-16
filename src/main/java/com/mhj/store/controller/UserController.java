package com.mhj.store.controller;

import com.mhj.store.element.User;
import com.mhj.store.service.IUserService;
import com.mhj.store.service.ex.InsertException;
import com.mhj.store.service.ex.UsernameDuplicatedException;
import com.mhj.store.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

//@Controller
@RestController
@RequestMapping("users")
public class UserController extends BaseController{

    @Autowired
    private IUserService userService;

    @RequestMapping("reg")
    //@ResponseBody
    public JsonResult<Void> reg(User user){
        userService.reg(user);
        return new JsonResult<>(OK);
//        JsonResult<Void> result = new JsonResult<>();
//        try {
//            userService.reg(user);
//            result.setState(200);
//            result.setMessage("registered successfully");
//        } catch (UsernameDuplicatedException e){
//            result.setState(4000);
//            result.setMessage("Username has been duplicated");
//        } catch (InsertException e){
//            result.setState(5000);
//            result.setMessage("An unknown exception occurred during registration");
//        }
//        return  result;
    }

    @RequestMapping("login")
    public  JsonResult<User> login(String username, String password, HttpSession session){
        User data = userService.login(username,password);

        session.setAttribute("uid", data.getUid());
        session.setAttribute("username", data.getUsername());
//        System.out.println(getUidFromSession(session));
//        System.out.println((getUsernameFromSession(session)));

        return new JsonResult<>(OK,data);
    }

}



