package com.peak.web.controller.admin;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.peak.pojo.Blog;
import com.peak.pojo.User;
import com.peak.service.BlogService;
import com.peak.service.TagsService;
import com.peak.service.TypeService;
import com.peak.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @PROJECT_NAME: ggy_Blog
 * @DESCRIPTION:
 * @USER: Peak_GGY
 * @DATE: 2021/9/6 9:11
 */
@Controller
@RequestMapping("/admin")
public class BlogController {

    /**
     * 跳转到BLog管理页面
     * @return
     */

    @Autowired
    BlogService blogService;
    @Autowired
    TagsService tagsService;
    @Autowired
    TypeService typeService;
    public void setTypeAndTag(Model model) {
        model.addAttribute("types", typeService.getAllType());
        model.addAttribute("tags", tagsService.getAllTag());

    }

    /**
     * 查询所有
     * @param pagenum
     * @param model
     * @return
     */
    @GetMapping("/blogs")
    public String toBlog(@RequestParam(required = false,defaultValue = "1",value = "pagenum") int pagenum, Model model)
    {
        PageHelper.startPage(pagenum,5);
        List<Blog> blogs = blogService.getAllBlog();
        PageInfo pageInfo = new PageInfo(blogs);
        model.addAttribute("pageInfo",pageInfo);
        setTypeAndTag(model);  //查询类型和标签
        return "/admin/blogs";
    }

    /**
     * 条件查询
     * @param blog
     * @param pagenum
     * @param model
     * @return
     */
    @PostMapping("/blogs/search")
    public String SearchBlogs(Blog blog,@RequestParam(required = false,defaultValue = "1", value = "pagenum") int pagenum,Model model)
    {
        PageHelper.startPage(pagenum,5);
        List<Blog> blogs = blogService.searchAllBlogs(blog);
        PageInfo pageInfo =new PageInfo(blogs);
        model.addAttribute("pageInfo",pageInfo);
        setTypeAndTag(model);
        return "/admin/blogs";
    }

    @GetMapping("/blogs/input")
    public String Toaddblog(Model model)
    {
        model.addAttribute("blog", new Blog());  //返回一个blog对象给前端th:object
        setTypeAndTag(model);
        return "/admin/blogs-input";
    }

    @PostMapping("/blogs/save")
    public String addBlog(Blog blog, HttpSession session, RedirectAttributes attributes)
    {
        blog.setType(typeService.getType(blog.getType().getId()));
        blog.setUser((User) session.getAttribute("user"));
        blog.setTags(tagsService.getTagByString(blog.getTagIds()));
        System.out.println(blog);
        if (blog.getId() == null) {   //id为空，则为新增
            blogService.saveBlog(blog);
            attributes.addFlashAttribute("msg","新增成功");
        } else {
            blogService.updateBlog(blog);
            attributes.addFlashAttribute("msg","修改成功");
        }
        return "redirect:/admin/blogs";
    }

    @GetMapping("/blogs/delete/{id}")
    public String deleteBlog(@PathVariable("id") Long id,RedirectAttributes attributes)
    {
        try {
            blogService.deleteBlog(id);
            attributes.addFlashAttribute("msg","刪除成功");
        }catch (Exception e)
        {
            attributes.addFlashAttribute("msg","刪除失敗");
        }
        return "redirect:/admin/blogs";
    }

    @GetMapping("/blogs/update/{id}")
    public String toUpdate(@PathVariable("id") Long id,Model model)
    {
        setTypeAndTag(model);
        Blog blog =blogService.getBlogByid(id);
        model.addAttribute("blog",blog);
        return "/admin/blogs-input";
    }


}
