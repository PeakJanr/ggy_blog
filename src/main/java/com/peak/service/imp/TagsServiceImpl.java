package com.peak.service.imp;

import com.peak.dao.TagDao;
import com.peak.pojo.Tag;
import com.peak.service.TagsService;
import org.omg.IOP.TAG_ORB_TYPE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @PROJECT_NAME: ggy_Blog
 * @DESCRIPTION:
 * @USER: Peak_GGY
 * @DATE: 2021/9/6 20:14
 */
@Service
public class TagsServiceImpl implements TagsService {

    @Autowired
    TagDao tagDao;

    @Override
    public void saveTag(Tag tag) {
       tagDao.insert(tag);
    }

    @Override
    public Tag getTag(Long id) {
        return tagDao.selectByPrimaryKey(id);
    }

    @Override
    public List<Tag> getAllTag() {
        return tagDao.findAll();
    }

    @Override
    public void updateTag(Tag tag) {
        tagDao.updateByPrimaryKey(tag);
    }

    @Override
    public void deleteTag(Long id) {
        tagDao.deleteByPrimaryKey(id);
    }

    @Override
    public List<Tag> getTagByString(String tagIds) {
        List<Long> ids_list = convertToList(tagIds);
        List<Tag> tags = new ArrayList<>();
        for (Long id:ids_list) {
          tags.add(tagDao.selectByPrimaryKey(id));
        }
        return tags;
    }

    @Override
    public List<Tag> getIndexTags() {
        return tagDao.getIndexTags();
    }

    @Override
    public List<Tag> getBlogTag() {
        return tagDao.getBlogTag();
    }

    private List<Long> convertToList(String ids) {  //把前端的tagIds字符串转换为list集合
        List<Long> list = new ArrayList<>();
        if (!"".equals(ids) && ids != null) {
            String[] idarray = ids.split(",");
            for (int i=0; i < idarray.length;i++) {
                list.add(new Long(idarray[i]));
            }
        }
        return list;
    }


}
