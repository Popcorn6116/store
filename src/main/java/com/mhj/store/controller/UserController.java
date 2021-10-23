package com.mhj.store.controller;

import com.mhj.store.controller.ex.*;
import com.mhj.store.element.User;
import com.mhj.store.service.IUserService;
import com.mhj.store.service.ex.InsertException;
import com.mhj.store.service.ex.UsernameDuplicatedException;
import com.mhj.store.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.MultipartFilter;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

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

    @RequestMapping("change_password")
    public JsonResult<Void> changePassword(String oldPassword, String newPassword, HttpSession session){
        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);
        userService.changePassword(uid,username,oldPassword,newPassword);

        return new JsonResult<>(OK);
    }

    @RequestMapping("get_by_uid")
    public JsonResult<User> getByUid(HttpSession session){
        User data = userService.getByUid(getUidFromSession(session));

        return new JsonResult<User>(OK,data);
    }

    @RequestMapping("change_info")
    public JsonResult<Void> changeInfo(User user,HttpSession session){
        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);
        userService.changeInfo(uid,username,user);
        return new JsonResult<>(OK);
    }


    public static final int AVATAR_MAX_SIZE = 10 * 1024 * 1024;

    public static final List<String> AVATAR_TYPE = new ArrayList<>();

    static {
        AVATAR_TYPE.add("image/jpeg");
        AVATAR_TYPE.add("image/png");
        AVATAR_TYPE.add("image/bmp");
        AVATAR_TYPE.add("image/gif");
    }
    /**
     * MultipartFile interface is provided by SpringMVC, which could package all kinds of file type.
     * @param session
     * @param file
     * @return
     * @RequestParam, 表示请求中的参数，将请求中的参数注入请求处理方法的某个参数。如果名称不一致，使用该注解。
     */
    @RequestMapping("change_avatar")
    public JsonResult<String> changeAvatar(@RequestParam("file") MultipartFile file, HttpSession session){
        if(file.isEmpty()){
            throw new FileEmptyException("File is empty! Try again.");
        }
        if(file.getSize() > AVATAR_MAX_SIZE){
            throw new FileSizeException("File size exceeds the limit! Try again.");
        }
        String contentType = file.getContentType();
        if(!AVATAR_TYPE.contains(contentType)){
            throw new FileTypeException("File type is not supported! Try again");
        }

        String parent = session.getServletContext().getRealPath("upload");

        File dir = new File(parent);
        if(!dir.exists()){
            dir.mkdirs();
        }
        //get the name of the file, then get a new string as name of file by UUID
        String originalFilename = file.getOriginalFilename();
        System.out.println(("OriginalFilename=" + originalFilename));

        //get the type of file
        int index = originalFilename.lastIndexOf(".");
        String suffix = originalFilename.substring(index);

        String filename = UUID.randomUUID().toString().toUpperCase() + suffix;

        File dest = new File(dir,filename);   // empty file
        // put data from file to dest
        try {
            file.transferTo(dest);
        } catch (IOException e) {
            throw new FileUploadIOException("An exception occurred during file reading and writing. Procedure!");
        } catch (FileStateException e){
            throw new FileStateException("The file status is abnormal!");
        }

        //change avatar
        Integer uid = getUidFromSession(session);
        String avatar = "/upload/" + filename;
        String username = getUsernameFromSession(session);
        userService.changeAvatar(uid, avatar, username);

        return new JsonResult<>(OK,avatar);
    }
}



