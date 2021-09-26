package com.peak.web.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.peak.pojo.Blog;
import com.peak.pojo.Tag;
import com.peak.pojo.Type;
import com.peak.service.BlogService;
import com.peak.service.TagsService;
import com.peak.service.TypeService;
import org.apache.ibatis.plugin.Interceptor;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.lang.annotation.Retention;
import java.util.List;

/**
 * @PROJECT_NAME: ggy_Blog
 * @DESCRIPTION:
 * @USER: Peak_GGY
 * @DATE: 2021/9/10 10:20
 */
@Controller
public class IndexController {

    @Autowired
    BlogService blogService;
    @Autowired
    TagsService tagsService;
    @Autowired
    TypeService typeService;

    /**
     * 挑战到主页
     * @param pagenum
     * @param model
     * @return
     */
    @GetMapping("/")
    public String toIndex(@RequestParam(required = false, defaultValue = "1",value = "pagenum")int pagenum, Model model)
    {
        PageHelper.startPage(pagenum,8);
       List<Blog> allblog = blogService.getIndexBlog();
       List<Type> types = typeService.getIndexTypes();
       List<Tag>  tags = tagsService.getIndexTags();
        int count = blogService.getBlogCount();
        PageInfo pageInfo =new PageInfo(allblog);
        model.addAttribute("tags",tags);
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("types",types);
        model.addAttribute("count",count);
        return "index";
    }

    /**
     * 关键字搜索blog
     * @param pagenum
     * @param model
     * @param query
     * @return
     */
    @PostMapping("/search")
    public String search(@RequestParam(required = false,defaultValue = "1",value = "pagenum")int pagenum,Model model,@RequestParam String query)
    {
        PageHelper.startPage(pagenum,8);
        List<Blog> blogs =blogService.getSearchBlog(query);
        PageInfo pageInfo = new PageInfo(blogs);
        model.addAttribute("pageInfo",pageInfo);
        model.addAttribute("query",query);
        return "search";

    }

    /**
     *跳轉到blog詳情頁面
     * @param id
     * @return
     */
    @GetMapping("/blog/{id}")
    public String Toblog(@PathVariable Long id,Model model)
    {

        Blog blog = blogService.getDetailedBlog(id);
        model.addAttribute("blog",blog);
        return "blog";
    }

}
