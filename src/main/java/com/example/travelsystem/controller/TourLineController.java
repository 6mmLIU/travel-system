package com.example.travelsystem.controller;

import com.example.travelsystem.model.TourLine;
import com.example.travelsystem.service.TourLineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller 用于管理旅游线路相关的功能，包括添加、查询、发布、取消发布等操作。
 */
@Controller
@RequestMapping("/tour-lines")
public class TourLineController {

    @Autowired
    private TourLineService tourLineService;

    /**
     * 跳转到添加旅游线路的页面。
     *
     * @return 添加线路页面的路径。
     */
    @GetMapping("/add")
    public String addTourLinePage() {
        return "auth/lines"; // 返回添加线路页面
    }

    /**
     * 添加新的旅游线路。
     *
     * @param tourLine 旅游线路实体，包含页面提交的表单数据。
     * @param model    用于在页面返回时传递消息。
     * @return 添加成功后的页面路径。
     */
    @PostMapping("/add")
    public String addTourLine(@ModelAttribute TourLine tourLine, Model model) {
        tourLineService.addTourLine(tourLine);
        model.addAttribute("message", "Tour Line added successfully!");
        return "auth/lines";
    }

    /**
     * 根据关键字搜索旅游线路。
     *
     * @param keyword 搜索关键字。
     * @param model   用于在页面返回时传递搜索结果。
     * @return 搜索结果页面的路径。
     */
    @GetMapping("/search")
    public String searchTourLines(@RequestParam String keyword, Model model) {
        List<TourLine> tourLines = tourLineService.searchTourLines(keyword);
        model.addAttribute("tourLines", tourLines);
        return "auth/lines";
    }

    /**
     * 分页获取所有旅游线路。
     *
     * @param page 当前页码，默认值为 1。
     * @param size 每页显示的记录数，默认值为 10。
     * @param model 用于在页面返回时传递分页结果。
     * @return 所有旅游线路列表页面的路径。
     */
    @GetMapping("/all")
    public String getAllTourLines(@RequestParam(defaultValue = "1") int page,
                                  @RequestParam(defaultValue = "10") int size,
                                  Model model) {
        if (page < 1) page = 1;

        int totalRecords = tourLineService.getTotalRecords(); // 新增：获取总记录数
        int totalPages = (totalRecords + size - 1) / size; // 计算总页数

        if (page > totalPages) page = totalPages; // 防止页码超出范围

        List<TourLine> tourLines = tourLineService.getAllTourLines(page, size);

        model.addAttribute("tourLines", tourLines);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages); // 添加总页数
        return "auth/lines";
    }

    /**
     * 发布旅游线路。
     *
     * @param id    要发布的旅游线路 ID。
     * @param model 用于传递发布成功的消息。
     * @return 跳转到管理页面的路径。
     */
    @PostMapping("/publish/{id}")
    public String publishTourLine(@PathVariable Integer id, Model model) {
        tourLineService.publishTourLine(id);
        model.addAttribute("message", "Tour Line published successfully!");
        return "redirect:/tour-lines/all";
    }

    /**
     * 取消发布旅游线路。
     *
     * @param id    要取消发布的旅游线路 ID。
     * @param model 用于传递取消发布成功的消息。
     * @return 跳转到管理页面的路径。
     */
    @PostMapping("/unpublish/{id}")
    public String unpublishTourLine(@PathVariable Integer id, Model model) {
        tourLineService.unpublishTourLine(id);
        model.addAttribute("message", "Tour Line unpublished successfully!");
        return "redirect:/tour-lines/all";
    }

    /**
     * 获取所有已发布的旅游线路。
     *
     * @param model 用于在页面返回时传递已发布线路的列表。
     * @return 已发布旅游线路列表页面的路径。
     */
    @GetMapping("/published")
    public String getPublishedTourLines(Model model) {
        List<TourLine> tourLines = tourLineService.getAllPublishedTourLines();
        model.addAttribute("tourLines", tourLines);
        return "auth/lines";
    }

    /**
     * 根据条件筛选旅游线路。
     *
     * @param destination    目的地（可选）。
     * @param minPrice       最低价格（可选）。
     * @param maxPrice       最高价格（可选）。
     * @param minDuration    最短持续时间（可选）。
     * @param maxDuration    最长持续时间（可选）。
     * @param page           当前页码，默认值为 1。
     * @param size           每页显示的记录数，默认值为 10。
     * @param sortField      排序字段，默认值为 "id"。
     * @param sortDirection  排序方向，默认值为 "asc"。
     * @param model          用于传递筛选后的旅游线路列表。
     * @return 筛选结果页面的路径。
     */
    @GetMapping("/filter")
    public String filterTourLines(@RequestParam(required = false) String destination,
                                  @RequestParam(required = false) Double minPrice,
                                  @RequestParam(required = false) Double maxPrice,
                                  @RequestParam(required = false) Integer minDuration,
                                  @RequestParam(required = false) Integer maxDuration,
                                  @RequestParam(defaultValue = "1") int page,
                                  @RequestParam(defaultValue = "10") int size,
                                  @RequestParam(defaultValue = "id") String sortField,
                                  @RequestParam(defaultValue = "asc") String sortDirection,
                                  Model model) {
        List<TourLine> tourLines = tourLineService.filterTourLines(destination, minPrice, maxPrice,
                minDuration, maxDuration, sortField, sortDirection, page, size);
        model.addAttribute("tourLines", tourLines);
        return "auth/lines";
    }

    /**
     * 获取某条旅游线路的详细信息。
     *
     * @param id    旅游线路的 ID。
     * @param model 用于传递旅游线路的详细信息。
     * @return 旅游线路详情页面的路径，如果未找到，则返回错误页面。
     */
    @GetMapping("/details/{id}")
    public String getTourLineDetail(@PathVariable Integer id, Model model) {
        TourLine tourLine = tourLineService.findById(id);
        if (tourLine == null) {
            model.addAttribute("error", "Tour line not found!");
            return "auth/error";
        }
        model.addAttribute("tourLine", tourLine);
        return "auth/lines";
    }

}
