package ru.kpfu.itis.group_804.project.controllers;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Controller
public class CustomErrorController implements ErrorController {

    private static final int code404 = HttpStatus.NOT_FOUND.value();
    private static final int code403 = HttpStatus.FORBIDDEN.value();

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (status != null) {

            int statusCode = Integer.parseInt(status.toString());

            if(statusCode == code404) {
                return "errors/404";
            }
            if(statusCode == code403){
                return "errors/403";
            }
        }
        return "errors/default_error";
    }

    @Override
    public String getErrorPath()
    {
        return "/error";
    }

}
