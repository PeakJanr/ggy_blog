package com.peak.web.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.peak.pojo.Blog;
import com.peak.pojo.Tag;
import com.peak.service.BlogService;
import com.peak.service.TagsService;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @PROJECT_NAME: ggy_Blog
 * @DESCRIPTION:
 * @USER: Peak_GGY
 * @DATE: 2021/9/10 19:26
 */
@Controller
public class TagShowController {

    @Autowired
    TagsService tagsService;

    @Autowired
    BlogService blogService;

    @GetMapping("/tag/{id}")
    public String toTag(@RequestParam(required = false,defaultValue = "1",value = "pageNum") Integer pageNum, @PathVariable Long id, Model model)
    {
        PageHelper.startPage(pageNum,8);
        List<Tag> tags = tagsService.getBlogTag();
        if(id == -1)
        {
            id = tags.get(0).getId();
        }
        List<Blog> blogs = blogService.getBlogByTagId(id);

        PageInfo pageInfo =new PageInfo(blogs);
        model.addAttribute("pageInfo",pageInfo);
        model.addAttribute("tags", tags);
        model.addAttribute("id",id);
        return "/tags";
    }

}
