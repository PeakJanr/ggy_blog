package com.peak.pojo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import lombok.Data;

/**
 * t_blog
 * @author 
 */
@Data
public class Blog implements Serializable {
    /**
     * 博客id
     */
    private Long id;

    /**
     * 博客标题
     */
    private String title;

    /**
     * 博客内容
     */
    private String content;

    /**
     * 博客主图
     */
    private String firstPicture;

    /**
     * 博客标记
     */
    private String flag;

    /**
     * 博客浏览数
     */
    private Integer views;

    /**
     * 博客赞赏
     */
    private Boolean apperation;

    /**
     * 版权开启
     */
    private Boolean shareStatement;

    /**
     * 评论开启
     */
    private Boolean commentalbled;

    /**
     * 发布
     */
    private Boolean published;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 博客的分类
     */
    private Type type;

    /**
     * 博客的用户
     */
    private User user;

    /**
     * 包含的所有标签id
     */
    private List<Tag> tags;

    //这个属性用来mybaits每个博客的标签
    private String tagIds;
    //这个属性用来在mybatis中进行连接查询的
    private Long typeId;

    private String description;
    private static final long serialVersionUID = 1L;
}