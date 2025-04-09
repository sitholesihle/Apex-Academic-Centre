package com.example.demo.controller;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;



@Controller
public class CustomErrorController implements ErrorController {

    @GetMapping("/error")
    public String handleError() {
        // Provide a custom error page
        return "errorPage.html";  // This will return a view (customErrorPage.html or customErrorPage.jsp)
    }

    
    public String getErrorPath() {
        return "/error";  // This is the path for the error page
    }
}
