package cn.cl.love.manager.service;

import cn.cl.love.VO.Data;
import cn.cl.love.bean.Advertisement;

import java.util.List;

/**
 * 标题：
 * 作者：何处是归程
 * 时间：2020/2/1 - 21:43
 */
public interface AdvertisementServlet {
    // 获取全部广告
    List<Advertisement> getAdAll();

    // 添加广告
    void saveAd(Advertisement ad);

    // 根据id查询广告
    Advertisement getAdById(Integer id);

    // 修改广告
    void updateAd(Advertisement ad);

    // 删除广告
    void delAd(Integer id);

    // 批量删除
    void delBatchAd(Data data);
}
