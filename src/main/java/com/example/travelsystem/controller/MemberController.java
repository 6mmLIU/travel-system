package com.example.travelsystem.controller;

import com.example.travelsystem.model.Member;
import com.example.travelsystem.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/members")
public class MemberController {

    @Autowired
    private MemberService memberService;

    /**
     * 显示会员列表（支持分页）。
     */
    @GetMapping
    public String listMembers(@RequestParam(defaultValue = "1") int page,
                              @RequestParam(defaultValue = "10") int size,
                              Model model) {
        List<Member> members = memberService.getAllMembers(page, size);
        int totalMembers = memberService.getTotalCount();
        int totalPages = (int) Math.ceil((double) totalMembers / size);

        model.addAttribute("members", members);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        return "auth/list";
    }

    /**
     * 搜索会员（支持按姓名、邮箱查询）。
     */
    @GetMapping("/search")
    public String searchMembers(@RequestParam String keyword, Model model) {
        List<Member> members = memberService.searchMembers(keyword);
        model.addAttribute("members", members);
        model.addAttribute("keyword", keyword);
        return "auth/list";
    }

    /**
     * 显示新增会员表单。
     */
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("member", new Member());
        return "auth/add";
    }

    /**
     * 处理新增会员的表单提交。
     */
    @PostMapping("/add")
    public String addMember(@ModelAttribute Member member) {
        memberService.addMember(member);
        return "redirect:/members";
    }

    /**
     * 显示编辑会员表单。
     */
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Integer id, Model model) {
        Member member = memberService.getMemberById(id);
        if (member == null) {
            model.addAttribute("error", "会员不存在！");
            return "auth/error";
        }
        model.addAttribute("member", member);
        return "auth/edit";
    }

    /**
     * 处理会员信息更新的表单提交。
     */
    @PostMapping("/edit/{id}")
    public String editMember(@PathVariable Integer id, @ModelAttribute Member member) {
        member.setId(id);
        memberService.updateMember(member);
        return "redirect:/members";
    }

    /**
     * 删除会员。
     */
    @PostMapping("/delete/{id}")
    public String deleteMember(@PathVariable Integer id, Model model) {
        try {
            memberService.deleteMember(id);
        } catch (Exception e) {
            model.addAttribute("error", "删除失败：" + e.getMessage());
            return "auth/error";
        }
        return "redirect:/members";
    }
}
