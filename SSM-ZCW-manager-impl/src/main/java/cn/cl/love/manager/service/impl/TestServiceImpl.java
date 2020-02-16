package cn.cl.love.manager.service.impl;


import cn.cl.love.manager.dao.TestDao;
import cn.cl.love.manager.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * 标题：
 * 作者：何处是归程
 * 时间：2020/1/27 - 21:26
 */
@Service
public class TestServiceImpl implements TestService {
    @Autowired
    private TestDao testDao;

    @Override
    public void insert() {
        Map map = new HashMap();
        map.put("name", "高");
        testDao.insert(map);
    }
}
