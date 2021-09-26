package com.peak.pojo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;

/**
 * t_tag
 * @author 
 */
@Data
public class Tag implements Serializable {
    /**
     * 标签id
     */
    private Long id;

    /**
     * 标签名字
     */
    private String name;

    /**
     * 用來統計個數
     */
    private List<Blog> blogs = new ArrayList<>();

    private static final long serialVersionUID = 1L;
}