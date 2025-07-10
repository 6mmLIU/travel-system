package com.example.travelsystem.controller;

import com.example.travelsystem.model.TourLine;
import com.example.travelsystem.model.User;
import com.example.travelsystem.service.FavoriteService;
import com.example.travelsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/users/favorites")
public class FavoriteController {

    @Autowired
    private UserService userService;

    @Autowired
    private FavoriteService favoriteService;

    // —— 查看我的收藏 —— //
    @GetMapping
    public String favoritePage(Model model, Authentication auth) {
        User current = userService.findByUsername(auth.getName());
        // 用 FavoriteService 直接拿收藏线路实体列表
        List<TourLine> favs = favoriteService.listFavorites(current.getId());
        model.addAttribute("favorites", favs);
        return "auth/favorites";
    }

    // —— 切换收藏/取消 —— //
    @PostMapping("/{lineId}/toggle")
    public String toggleFavorite(@PathVariable Integer lineId,
                                 Authentication auth,
                                 RedirectAttributes ra) {
        User current = userService.findByUsername(auth.getName());
        Integer uid = current.getId();
        // 调用 FavoriteService
        if (favoriteService.isFavorite(uid, lineId)) {
            favoriteService.removeFavorite(uid, lineId);
            ra.addFlashAttribute("msg", "已取消收藏");
        } else {
            favoriteService.addFavorite(uid, lineId);
            ra.addFlashAttribute("msg", "已加入收藏");
        }
        return "redirect:/users/favorites";
    }
}
