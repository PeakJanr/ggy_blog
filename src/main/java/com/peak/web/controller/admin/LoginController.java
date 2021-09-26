package com.peak.web.controller.admin;

import com.peak.pojo.User;
import com.peak.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

/**
 * @PROJECT_NAME: ggy_Blog
 * @DESCRIPTION:
 * @USER: Peak_GGY
 * @DATE: 2021/9/5 14:27
 * 登录
 */
@Controller
@RequestMapping("/admin")
public class LoginController {


    @Autowired
    UserService userService;
    /**
     * 跳转到后台登录页面
     * @return
     */
    @GetMapping({"/",""})
    public String loginPage()
    {
        return "admin/login";
    }

    /*
    登录功能实现
     */
    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, HttpSession session, RedirectAttributes attributes)
    {
        User user =userService.cheakUser(username,password);
        if(user != null)
        {
            user.setPassword(null);
            session.setAttribute("user",user);
            return "admin/index";
        }else
        {
            attributes.addFlashAttribute("msg","用户名和密码错误");
            return "redirect:/admin";
        }
    }

    /**
     * 退出
     */

    @GetMapping("/logout")
    public String logout(HttpSession session )
    {
        session.removeAttribute("user");
        return "redirect:/admin";
    }
}
