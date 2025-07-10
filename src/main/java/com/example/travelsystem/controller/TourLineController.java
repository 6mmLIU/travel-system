package com.example.travelsystem.controller;

import com.example.travelsystem.model.TourLine;
import com.example.travelsystem.model.User;
import com.example.travelsystem.service.FavoriteService;
import com.example.travelsystem.service.TourLineService;
import com.example.travelsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/tour-lines")
public class TourLineController {

    @Autowired
    private TourLineService tourLineService;

    /* 如果已实现收藏功能，注入 FavoriteService；否则可以删掉 */
    @Autowired(required = false)
    private FavoriteService favoriteService;

    /* 用来根据用户名查询用户及其 ID */
    @Autowired
    private UserService userService;

    /* =========== 1. 新增线路 =========== */

    @GetMapping("/add")
    public String addPage(Model model) {
        model.addAttribute("tourLine", new TourLine());
        return "auth/add-tour-line";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute TourLine tourLine, RedirectAttributes ra) {
        tourLineService.addTourLine(tourLine);
        ra.addFlashAttribute("message", "新增线路成功！");
        return "redirect:/tour-lines/all";
    }

    /* =========== 2. 搜索 / 列表 / 分页 =========== */

    @GetMapping("/search")
    public String search(@RequestParam(defaultValue = "") String keyword, Model model) {
        List<TourLine> list = tourLineService.searchTourLines(keyword);
        model.addAttribute("tourLines", list);
        fillPageAttr(model, 1, 1, list.size());
        model.addAttribute("keyword", keyword);
        return "auth/lines";
    }

    @GetMapping("/all")
    public String all(@RequestParam(defaultValue = "1") int page,
                      @RequestParam(defaultValue = "10") int size,
                      Model model) {
        int total  = tourLineService.getTotalRecords();
        int pages  = (total + size - 1) / size;
        page       = Math.min(Math.max(page, 1), pages == 0 ? 1 : pages);

        List<TourLine> list = tourLineService.getAllTourLines(page, size);
        model.addAttribute("tourLines", list);
        fillPageAttr(model, page, pages, size);
        return "auth/lines";
    }

    @GetMapping("/published")
    public String published(Model model) {
        List<TourLine> list = tourLineService.getAllPublishedTourLines();
        model.addAttribute("tourLines", list);
        fillPageAttr(model, 1, 1, list.size());
        return "auth/lines";
    }

    /* =========== 3. 上下架 =========== */

    @PostMapping("/publish/{id}")
    public String publish(@PathVariable Integer id, RedirectAttributes ra) {
        tourLineService.publishTourLine(id);
        ra.addFlashAttribute("message", "线路已上架！");
        return "redirect:/tour-lines/all";
    }

    @PostMapping("/unpublish/{id}")
    public String unpublish(@PathVariable Integer id, RedirectAttributes ra) {
        tourLineService.unpublishTourLine(id);
        ra.addFlashAttribute("message", "线路已下架！");
        return "redirect:/tour-lines/all";
    }

    /* =========== 4. 多条件筛选（含分页） =========== */

    @GetMapping("/filter")
    public String filter(@RequestParam(required = false) String destination,
                         @RequestParam(required = false) Double minPrice,
                         @RequestParam(required = false) Double maxPrice,
                         @RequestParam(required = false) Integer minDuration,
                         @RequestParam(required = false) Integer maxDuration,
                         @RequestParam(defaultValue = "id")   String sortField,
                         @RequestParam(defaultValue = "asc")  String sortDirection,
                         @RequestParam(defaultValue = "1")    int page,
                         @RequestParam(defaultValue = "10")   int size,
                         Model model) {

        List<TourLine> list = tourLineService.filterTourLines(
                destination, minPrice, maxPrice, minDuration, maxDuration,
                sortField, sortDirection, page, size);

        int total  = tourLineService.countFilter(
                destination, minPrice, maxPrice, minDuration, maxDuration);
        int pages  = (total + size - 1) / size;

        model.addAttribute("tourLines", list);
        fillPageAttr(model, page, pages, size);

        model.addAttribute("destination", destination);
        model.addAttribute("minPrice",   minPrice);
        model.addAttribute("maxPrice",   maxPrice);
        model.addAttribute("minDuration",minDuration);
        model.addAttribute("maxDuration",maxDuration);
        model.addAttribute("sortField",  sortField);
        model.addAttribute("sortDirection", sortDirection);
        return "auth/lines";
    }

    /* =========== 5. 线路详情（带收藏状态） =========== */

    @GetMapping("/{id}")
    public String detail(@PathVariable Integer id,
                         Authentication authentication,
                         Model model) {

        TourLine line = tourLineService.findById(id);
        if (line == null) {
            return "error/404";
        }

        boolean favored = false;
        if (authentication != null && favoriteService != null) {
            // 1. 取出用户名
            String username = authentication.getName();
            // 2. 根据用户名加载 User 实体（其中包含 ID）
            User user = userService.findByUsername(username);
            if (user != null) {
                Integer userId = user.getId();
                // 3. 判断是否已收藏
                favored = favoriteService.isFavorite(userId, id);
            }
        }

        model.addAttribute("line",    line);
        model.addAttribute("favored", favored);
        return "auth/tour-line-detail";
    }

    /* =========== 工具：填充分页 =========== */
    private void fillPageAttr(Model m, int current, int pages, int size) {
        m.addAttribute("currentPage", current);
        m.addAttribute("totalPages",  pages == 0 ? 1 : pages);
        m.addAttribute("size", size);
    }
}
