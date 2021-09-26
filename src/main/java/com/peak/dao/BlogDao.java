package com.peak.dao;

import com.peak.pojo.Blog;
import com.peak.pojo.BlogAndTag;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface BlogDao {
    int deleteBlog(Long id);

    int insert(Blog record);

    Blog getBlogById(Long id);

    int updateBlog(Blog record);

    List<Blog> getAllBlog();

    List<Blog> searchAllBlogs(Blog blog);

    void saveBlogAndTag(BlogAndTag blogAndTag);


    List<Blog> getIndexBlog();  //主頁博客展示

    int getBlogCount();  //得到博客縂的個數

    void deleteBlogAndTag(Long id); //刪除博客中間表關係

    List<Blog> getSearchBlog(String query); //根據query搜查博客

    Blog getDetailedBlog(Long id); //得到博客詳情信息

    List<Blog> getBlogByTypeId(Long id);

    List<Blog> getBlogByTagId(Long id);
}