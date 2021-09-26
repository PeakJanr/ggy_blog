package com.peak.service.imp;

import com.peak.dao.BlogDao;
import com.peak.pojo.Blog;
import com.peak.pojo.BlogAndTag;
import com.peak.pojo.Tag;
import com.peak.service.BlogService;
import com.peak.util.MarkdownUtils;
import com.peak.web.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @PROJECT_NAME: ggy_Blog
 * @DESCRIPTION:
 * @USER: Peak_GGY
 * @DATE: 2021/9/7 8:19
 */
@Service
public class BlogServiceImpl implements BlogService {

    @Autowired
    BlogDao blogDao;
    @Override
    public Blog getBlogByid(Long id) {
        return blogDao.getBlogById(id);
    }

    @Override
    public List<Blog> getAllBlog() {
        return blogDao.getAllBlog();
    }

    @Override
    public List<Blog> searchAllBlogs(Blog blog) {
        return blogDao.searchAllBlogs(blog);
    }

    /**
     * 保存博客
     * @param blog
     */
    @Override
    public void saveBlog(Blog blog) {
        blog.setCreateTime(new Date());
        blog.setUpdateTime(new Date());
        blog.setViews(0);
        blogDao.insert(blog);
        Long blogId = blog.getId();
        List<Tag> tags = blog.getTags();
        BlogAndTag blogAndTag =null;
        for (Tag tag: tags) {
            blogAndTag = new BlogAndTag(blogId,tag.getId());
            blogDao.saveBlogAndTag(blogAndTag);
        }
    }

    /**
     * 修改博客
     * @param blog
     */
    @Override
    public void updateBlog(Blog blog) {
        blog.setUpdateTime(new Date());
        //将标签的数据存到t_blogs_tag表中
        List<Tag> tags = blog.getTags();
        //刪除中間表關係
        blogDao.deleteBlogAndTag(blog.getId());
        BlogAndTag blogAndTag = null;
        for (Tag tag : tags) {
            blogAndTag = new BlogAndTag(blog.getId(), tag.getId());
            blogDao.saveBlogAndTag(blogAndTag);
        }
        blogDao.updateBlog(blog);
    }

    /**
     * 刪除博客
     * @param id
     */
    @Override
    public void deleteBlog(Long id) {
        blogDao.deleteBlog(id);
    }


    /**
     * 首頁頁面博客信息
      * @return
     */
    public List<Blog> getIndexBlog()
    {
        return blogDao.getIndexBlog();
    }

    @Override
    public int getBlogCount() {
        return blogDao.getBlogCount();
    }

    /**
     * 得到搜索的Blog
     * @param query
     * @return
     */
    @Override
    public List<Blog> getSearchBlog(String query) {
        return blogDao.getSearchBlog(query);
    }

    /**
     * 得到blog的詳情
     * @param id
     * @return
     */
    @Override
    public Blog getDetailedBlog(Long id) {


        Blog blog = blogDao.getDetailedBlog(id);
        if (blog == null) {
            throw new NotFoundException("该博客不存在");
        }
        String content = blog.getContent();
        blog.setContent(MarkdownUtils.markdownToHtmlExtensions(content));  //将Markdown格式转换成html
        return blog;
    }

    @Override
    public List<Blog> getBlogByTypeId(Long id) {
        return blogDao.getBlogByTypeId(id);
    }

    @Override
    public List<Blog> getBlogByTagId(Long id) {
        return blogDao.getBlogByTagId(id);
    }

}
