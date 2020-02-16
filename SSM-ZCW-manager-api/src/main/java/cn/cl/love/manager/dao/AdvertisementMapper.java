package cn.cl.love.manager.dao;

import cn.cl.love.bean.Advertisement;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AdvertisementMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Advertisement record);

    Advertisement selectByPrimaryKey(Integer id);

    List<Advertisement> selectAll();

    int updateByPrimaryKey(Advertisement record);

    // 批量删除
    void delBatchAd(@Param("ids") List<Integer> ids);
}