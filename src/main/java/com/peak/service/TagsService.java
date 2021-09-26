package com.peak.service;

import com.peak.pojo.Tag;

import java.util.List;

/**
 * @PROJECT_NAME: ggy_Blog
 * @DESCRIPTION:
 * @USER: Peak_GGY
 * @DATE: 2021/9/6 15:30
 */
public interface TagsService {

    void saveTag(Tag Tag);

    Tag getTag(Long id);

    List<Tag> getAllTag();

    void updateTag(Tag Tag);

    void deleteTag(Long id);

    List<Tag> getTagByString(String tagIds);  //

    List<Tag> getIndexTags();

    List<Tag> getBlogTag();   //得到博客的标签信息
}
