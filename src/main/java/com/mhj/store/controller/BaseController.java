package com.mhj.store.controller;

import com.mhj.store.service.ex.*;
import com.mhj.store.util.JsonResult;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpSession;

public class BaseController {
    public static final int OK = 200;

    @ExceptionHandler(ServiceException.class)
    public JsonResult<Void> handleException(Throwable e){
        JsonResult<Void> result = new JsonResult<>(e);
        if(e instanceof UsernameDuplicatedException){
            result.setState(4000);
            result.setMessage("Username has been duplicated");
        } else if(e instanceof UserNotFoundException){
            result.setState(5001);
            result.setMessage("The user does not exist");
        } else if(e instanceof PasswordNotMatchException){
            result.setState(5002);
            result.setMessage("The user's password is incorrect");
        } else if(e instanceof InsertException){
            result.setState(5000);
            result.setMessage("An unknown exception occurred during inserting data");
        }

        return result;
    }

    protected final Integer getUidFromSession(HttpSession session){
        return Integer.valueOf(session.getAttribute("uid").toString());
    }

    protected final String getUsernameFromSession(HttpSession session){
        return session.getAttribute("username").toString();
    }
}
