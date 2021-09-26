package com.peak.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @PROJECT_NAME: ggy_Blog
 * @DESCRIPTION:
 * @USER: Peak_GGY
 * @DATE: 2021/9/11 18:14
 */
@Controller
public class AboutShowController {

    @GetMapping("/about")
    public String about(){
        return "about";
    }
}
