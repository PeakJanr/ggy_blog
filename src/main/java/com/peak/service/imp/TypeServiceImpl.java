package com.peak.service.imp;

import com.peak.dao.TypeDao;
import com.peak.pojo.Blog;
import com.peak.pojo.Type;
import com.peak.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @PROJECT_NAME: ggy_Blog
 * @DESCRIPTION:
 * @USER: Peak_GGY
 * @DATE: 2021/9/6 15:29
 */
@Service
public class TypeServiceImpl implements TypeService {
    @Autowired
    TypeDao typeDao;

    @Override
    public List<Type> getIndexTypes() {
        return typeDao.getIndexTypes();
    }

    @Override
    public void saveType(Type type) {
        typeDao.insert(type);
    }

    @Override
    public Type getType(Long id) {
        return typeDao.selectByPrimaryKey(id);
    }
    @Override
    public List<Type> getAllType() {
        return typeDao.findAll();
    }

    @Override
    public void updateType(Type type) {
        typeDao.updateByPrimaryKey(type);
    }

    @Override
    public void deleteType(Long id) {
        typeDao.deleteByPrimaryKey(id);
    }

    @Override
    public List<Type> getBlogType() {
        return typeDao.getBlogType();
    }


}
