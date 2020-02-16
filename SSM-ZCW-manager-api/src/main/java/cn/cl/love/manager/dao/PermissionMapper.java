package cn.cl.love.manager.dao;

import cn.cl.love.bean.Permission;

import java.util.List;

public interface PermissionMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Permission record);

    Permission selectByPrimaryKey(Integer id);

    List<Permission> selectAll();

    int updateByPrimaryKey(Permission record);

    /**
     * @功能: 查询root节点
     * @作者: 高志红
     */
    Permission getRootPsrmission();

    /**
     * @功能: 查询子节点
     * @作者: 高志红
     */
    List<Permission> getChildrenPermissionByPid(Integer id);


    /**
     * @功能: 根据角色id查询该角色具有的权限
     * @作者: 高志红
     */
    List<Integer> permissionIdsForRoleid(Integer roleid);
}