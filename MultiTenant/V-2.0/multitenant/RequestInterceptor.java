package com.krish.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

@Component
public class RequestInterceptor extends HandlerInterceptorAdapter {
    
	@Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object) throws Exception {
        String tenantID = request.getHeader("tenantID");
        System.out.println("RequestURI::" + request.getRequestURI() +" || Search for tenantID  :: " + tenantID);
        if (null == tenantID) {
            response.getWriter().write("tenantID not present in the Request Header");
            response.setStatus(400);
            return false;
        }
        
        TenantContext.setCurrentTenant(tenantID);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        TenantContext.clear();
    }

}
