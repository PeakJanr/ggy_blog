package com.peak.service;

import com.peak.pojo.Blog;

import java.util.List;

/**
 * @PROJECT_NAME: ggy_Blog
 * @DESCRIPTION:
 * @USER: Peak_GGY
 * @DATE: 2021/9/7 8:15
 */
public interface BlogService {
    /**
     *
     */

     Blog getBlogByid(Long id); //通過id查詢博客

     List<Blog> getAllBlog();  //獲取所有博客信息

     List<Blog> searchAllBlogs(Blog blog);//根据条件查询博客信息

     void saveBlog(Blog blog);

     void updateBlog(Blog blog);

     void deleteBlog(Long id);


     /*
     首頁博客信息
      */
    List<Blog> getIndexBlog();


    int getBlogCount(); //獲得blog的縂的個數

    List<Blog> getSearchBlog(String query);

    Blog getDetailedBlog(Long id);

    List<Blog> getBlogByTypeId(Long id);  //根据博客的

    List<Blog> getBlogByTagId(Long id);   //根據博客的tagid查詢博客列表信息
}
