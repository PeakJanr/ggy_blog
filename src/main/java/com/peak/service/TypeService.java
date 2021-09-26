package com.peak.service;

import com.peak.pojo.Blog;
import com.peak.pojo.Type;


import java.util.List;

/**
 * @PROJECT_NAME: ggy_Blog
 * @DESCRIPTION:
 * @USER: Peak_GGY
 * @DATE: 2021/9/6 15:30
 */
public interface TypeService {

    List<Type> getIndexTypes();

    void saveType(Type type);

    Type getType(Long id);

    List<Type> getAllType();

    void updateType(Type type);

    void deleteType(Long id);

    List<Type> getBlogType();  //获得主页分页栏列表信息

}
