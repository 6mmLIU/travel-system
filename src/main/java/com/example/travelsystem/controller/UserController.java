package com.example.travelsystem.controller;

import com.example.travelsystem.model.TourLine;
import com.example.travelsystem.model.User;
import com.example.travelsystem.service.TourLineService;
import com.example.travelsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.stream.Collectors;

/**
 * UserController 负责用户的注册/登录/个人中心/首页（含搜索）/订单等页面及其表单提交逻辑
 */
@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private TourLineService tourLineService;


    // —— 登录页 —— //
    @GetMapping("/login")
    public String loginPage() {
        return "auth/login";
    }

    // —— 注册页 —— //
    @GetMapping("/register")
    public String registerPage() {
        return "auth/register";
    }

    // —— 处理注册 —— //
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

    // —— 首页：展示已发布线路，支持搜索 —— //
    @GetMapping({"/index", "/"})
    public String showIndex(@RequestParam(value = "keyword", required = false) String keyword,
                            Model model) {
        List<TourLine> publishedLines;
        if (keyword != null && !keyword.trim().isEmpty()) {
            // 先按关键字搜索，再过滤出已发布的
            publishedLines = tourLineService
                    .searchTourLines(keyword.trim())
                    .stream()
                    .filter(TourLine::isPublished)
                    .collect(Collectors.toList());
        } else {
            // 无关键字，直接拿所有已发布
            publishedLines = tourLineService.getAllPublishedTourLines();
        }
        model.addAttribute("publishedLines", publishedLines);
        model.addAttribute("keyword", keyword);
        return "auth/index";
    }

    // —— 个人信息页面 —— //
    @GetMapping("/profile")
    public String profilePage(Model model) {
        User current = getCurrentUser();
        model.addAttribute("user", current);
        return "auth/profile";
    }

    // —— 更新个人信息 —— //
    @PostMapping("/profile")
    public String updateProfile(@ModelAttribute User form,
                                RedirectAttributes ra) {
        User current = getCurrentUser();
        form.setId(current.getId());
        userService.updateUserInfo(form);
        ra.addFlashAttribute("msg", "个人信息更新成功");
        return "redirect:/users/profile";
    }


    // —— 提交修改密码 —— //
    @PostMapping("/password")
    public String changePassword(@RequestParam String oldPassword,
                                 @RequestParam String newPassword,
                                 RedirectAttributes ra) {
        User current = getCurrentUser();
        try {
            userService.changePassword(current.getId(), oldPassword, newPassword);
            ra.addFlashAttribute("msg", "密码修改成功，请重新登录");
            return "redirect:/logout";
        } catch (IllegalArgumentException ex) {
            ra.addFlashAttribute("error", ex.getMessage());
            return "redirect:/users/password";
        }
    }




    /** 工具：从 SecurityContext 拿当前完整 User 对象 */
    private User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return userService.findByUsername(auth.getName());
    }
}
