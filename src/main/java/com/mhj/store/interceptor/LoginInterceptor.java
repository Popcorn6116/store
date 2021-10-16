package com.mhj.store.interceptor;


import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*Define an interceptor */
/*the interceptor need be registered by class which is implemented WebMvcConfigure*/
public class LoginInterceptor implements HandlerInterceptor {


/*Check whether there is UID data in the session object. If there is uid data in the session object, the user is allowed. If there is no UID data in the session object, the user is redirected to the login page.*/
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // by HttpServletRequest object to get session object
        Object obj = request.getSession().getAttribute("uid");
        if (obj == null) {
            response.sendRedirect("/web/login.html");
            return false;
        }
        return true;
    }
}
