package com.peak.pojo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;

/**
 * t_type
 * @author 
 */
@Data
public class Type implements Serializable {
    /**
     * 类型
     */
    private Long id;

    /**
     * 类型的名字
     */
    private String name;

    /**
     * 爲了 統計type類型 Blog的個數
     */
    private List<Blog> blogs = new ArrayList<>();

    private static final long serialVersionUID = 1L;
}