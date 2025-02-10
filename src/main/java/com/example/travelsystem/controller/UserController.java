package com.example.travelsystem.controller;

import com.example.travelsystem.model.TourLine;
import com.example.travelsystem.model.User;
import com.example.travelsystem.service.TourLineService;
import com.example.travelsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * UserController 负责处理用户相关的功能，包括注册、登录、查看信息、更新信息、修改密码等操作。
 */
@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private TourLineService tourLineService;

    // 【登录页】
    @GetMapping("/login")
    public String loginPage() {
        // 返回 Thymeleaf 模板: auth/login.html
        return "auth/login";
    }

    // ========== 注释掉原先的自定义登录方法, 交由 Spring Security 内置流程处理 ==========
    /*
    @PostMapping("/login")
    public String login(@ModelAttribute User user, Model model) {
        User existingUser = userService.findByUsername(user.getUsername());
        if (existingUser == null) {
            model.addAttribute("error", "用户名不存在！");
            return "auth/login";
        }
        if (!passwordEncoder.matches(user.getPassword(), existingUser.getPassword())) {
            model.addAttribute("error", "密码错误！");
            return "auth/login";
        }
        model.addAttribute("user", existingUser);
        model.addAttribute("message", "登录成功！");
        // 这里也会查询已发布线路
        List<TourLine> publishedLines = tourLineService.getAllPublishedTourLines();
        model.addAttribute("publishedLines", publishedLines);
        return "auth/index";
    }
    */

    // 【注册页面】
    @GetMapping("/register")
    public String registerPage() {
        return "auth/register";
    }

    // 【处理注册逻辑】
    @PostMapping("/register")
    public String register(@ModelAttribute User user, Model model) {
        if (userService.findByUsername(user.getUsername()) != null) {
            model.addAttribute("error", "用户名已存在！");
            return "auth/register";
        }
        userService.register(user);
        model.addAttribute("message", "注册成功，请登录！");
        return "auth/login";
    }

    // 【登录成功后访问首页，也可以显示已发布线路】
    @GetMapping("/index")
    public String showIndex(Model model) {
        List<TourLine> publishedLines = tourLineService.getAllPublishedTourLines();
        model.addAttribute("publishedLines", publishedLines);
        return "auth/index";
    }


}
