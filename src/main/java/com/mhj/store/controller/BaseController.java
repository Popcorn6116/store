package com.mhj.store.controller;

import com.mhj.store.controller.ex.*;
import com.mhj.store.service.ex.*;
import com.mhj.store.util.JsonResult;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpSession;

public class BaseController {
    public static final int OK = 200;

    @ExceptionHandler({ServiceException.class, FileUploadException.class})
    public JsonResult<Void> handleException(Throwable e){
        JsonResult<Void> result = new JsonResult<>(e);
        if(e instanceof UsernameDuplicatedException){
            result.setState(4000);
            result.setMessage("Username has been duplicated");
        } else if(e instanceof UserNotFoundException){
            result.setState(4001);
            result.setMessage("The user does not exist");
        } else if(e instanceof PasswordNotMatchException){
            result.setState(4002);
            result.setMessage("The user's password is incorrect");
        } else if(e instanceof AddressCountLimitException){
            result.setState(4003);
            result.setMessage("The number of receiving addresses exceeds the upper limit");
        }else if(e instanceof InsertException){
            result.setState(5000);
            result.setMessage("An unknown exception occurred during inserting data");
        } else if(e instanceof UpdateException){
            result.setState(5001);
            result.setMessage("An unknown exception occurred during updating data");
        } else if(e instanceof FileEmptyException){
            result.setState(6000);
        } else if(e instanceof FileSizeException){
            result.setState(6001);
        } else if(e instanceof FileTypeException){
            result.setState(6002);
        } else if(e instanceof FileStateException){
            result.setState(6003);
        } else if(e instanceof FileUploadIOException){
            result.setState(6004);
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
