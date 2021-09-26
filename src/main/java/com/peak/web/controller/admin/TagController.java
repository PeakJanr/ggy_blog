package com.peak.web.controller.admin;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.peak.pojo.Tag;

import com.peak.service.TagsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 * @PROJECT_NAME: ggy_Blog
 * @DESCRIPTION:
 * @USER: Peak_GGY
 * @DATE: 2021/9/6 15:58
 */
@Controller
@RequestMapping("/admin")
public class TagController {
    @Autowired
    private TagsService tagsService;

    @GetMapping("/tags")
    public String tags(@RequestParam(required = false,defaultValue = "1",value = "pagenum")int pagenum, Model model){
        PageHelper.startPage(pagenum, 5);
        List<Tag> alltag = tagsService.getAllTag();
        //得到分页结果对象
        PageInfo<Tag> pageInfo = new PageInfo<>(alltag);
        model.addAttribute("pageInfo", pageInfo);
        return "admin/tags";
    }

    /**
     * 跳转到添加页面
     * @return
     */
    @GetMapping("/tags/input")
    public String toAddtag(Model model)
    {
        model.addAttribute("tag",new Tag());
        return "/admin/tags-input";
    }

    @GetMapping("/tags/input/{id}")
    public String toEdittag(@PathVariable Long id, Model model){
        model.addAttribute("tag", tagsService.getTag(id));
        return "admin/tags-input";
    }

    /**
     * 添加类型信息
     * @param tag
     * @param attributes
     * @return
     */
    @PostMapping("/tags/insert")
    public String addtag(Tag tag, RedirectAttributes attributes)
    {
        try {
            tagsService.saveTag(tag);
            attributes.addFlashAttribute("msg","添加成功");
        }catch (Exception e)
        {
            attributes.addFlashAttribute("msg","添加失败");
            return "redirect:/admin/tags/input";
        }
        return "redirect:/admin/tags";
    }

    /**
     * 删除用户信息
     * @param id
     * @param attributes
     * @return
     */
    @GetMapping("/tags/delete/{id}")
    public String deletetag(@PathVariable("id") Long id,RedirectAttributes attributes) {
        try {
            tagsService.deleteTag(id);
            attributes.addFlashAttribute("msg", "删除成功");
            return "redirect:/admin/tags";
        } catch (Exception e) {
            attributes.addFlashAttribute("msg", "删除失败");
            return "redirect:/admin/tags";
        }
    }

    /**
     * 修改类型
     */
    @PostMapping("/tags/update/{id}")
    public String updatetag(@PathVariable("id") Long id, Tag tag, RedirectAttributes attributes)
    {
            tag.setId(id);
        try {
            tagsService.updateTag(tag);
            attributes.addFlashAttribute("msg","更新成功");
            return "redirect:/admin/tags";
        }catch (Exception e)
        {
            attributes.addFlashAttribute("msg","更新失败");
            return "redirect:/admin/tags";
        }
    }



}
