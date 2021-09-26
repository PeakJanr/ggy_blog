package com.peak.pojo;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * t_comment
 * @author 
 */
@Data
public class Comment implements Serializable {
    /**
     * 评论id
     */
    private Long id;

    /**
     * 评论昵称
     */
    private String nickname;

    /**
     * 评论邮箱
     */
    private String email;

    /**
     * 评论内容
     */
    private String content;

    /**
     * 评论头像
     */
    private String avatar;

    /**
     * 评论时间
     */
    private Date createTime;

    /**
     * 评论父节点
     */
    private Long parentCommentId;

    /**
     * 是否为管理员评论
     */
    private Boolean admincomment;

    /**
     * 所属评论
     */
    private Blog blog;

    private static final long serialVersionUID = 1L;
}