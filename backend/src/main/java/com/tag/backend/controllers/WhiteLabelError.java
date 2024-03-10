package com.tag.backend.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Controller
public class WhiteLabelError implements ErrorController {

    @RequestMapping("/error")
    public String handleError() {
        return "error";
    }

    @GetMapping("/")
    public String home() {
        return "index";
    }
}
