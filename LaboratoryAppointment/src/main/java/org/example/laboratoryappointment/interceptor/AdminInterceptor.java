package org.example.laboratoryappointment.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.example.laboratoryappointment.dox.User;
import org.example.laboratoryappointment.exception.Code;
import org.example.laboratoryappointment.exception.MyException;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@Component
public class AdminInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if(request.getMethod().equals("OPTIONS")){
            response.setStatus(HttpServletResponse.SC_OK);
            return true;
        }
        if(!User.admin.equals(request.getAttribute("role")))
        {
            throw MyException.builder()
                    .code(Code.NO_PERMISSION)
                    .build();
        }
        return true;
    }
}
