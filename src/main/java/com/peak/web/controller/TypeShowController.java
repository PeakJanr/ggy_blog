package com.peak.web.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.peak.pojo.Blog;
import com.peak.pojo.Type;
import com.peak.service.BlogService;
import com.peak.service.TypeService;
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
 * @DATE: 2021/9/10 15:47
 */
@Controller

public class TypeShowController {
    @Autowired
    TypeService typeService;

    /**
     * 跳轉到 type的展示頁面
     */
    @Autowired
    BlogService blogService;

    @GetMapping("/type/{id}")
    public String toType(@RequestParam(required = false,defaultValue = "1",value = "pageNum") Integer pageNum, Model model, @PathVariable Long id)
    {
        PageHelper.startPage(pageNum,8);
        List<Type> types= typeService.getBlogType();
        //-1从导航点过来的
        if (id == -1){
            id = types.get(0).getId();
        }
        List<Blog> blogs = blogService.getBlogByTypeId(id);//根据Tag Id 查询相应的博客信息
        PageInfo pageInfo =new PageInfo(blogs);
        model.addAttribute("pageInfo",pageInfo);
        model.addAttribute("types",types);
        model.addAttribute("id",id);
        return "types";
    }

}
