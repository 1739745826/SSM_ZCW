package cn.cl.love.manager.service.impl;

import cn.cl.love.VO.Data;
import cn.cl.love.bean.Advertisement;
import cn.cl.love.manager.dao.AdvertisementMapper;
import cn.cl.love.manager.service.AdvertisementServlet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 标题：
 * 作者：何处是归程
 * 时间：2020/2/1 - 21:42
 */
@Service
public class AdvertisementServletImpl implements AdvertisementServlet {
    @Autowired
    private AdvertisementMapper advertisementMapper;

    // 查询全部广告
    @Override
    public List<Advertisement> getAdAll() {
        return advertisementMapper.selectAll();
    }

    // 添加广告
    @Override
    public void saveAd(Advertisement ad) {
        advertisementMapper.insert(ad);
    }

    // 根据id查询广告
    @Override
    public Advertisement getAdById(Integer id) {
        return advertisementMapper.selectByPrimaryKey(id);
    }

    // 修改广告
    @Override
    public void updateAd(Advertisement ad) {
        advertisementMapper.updateByPrimaryKey(ad);
    }

    // 删除广告
    @Override
    public void delAd(Integer id) {
        advertisementMapper.deleteByPrimaryKey(id);
    }

    // 批量删除
    @Override
    public void delBatchAd(Data data) {
        advertisementMapper.delBatchAd(data.getIds());
    }
}
