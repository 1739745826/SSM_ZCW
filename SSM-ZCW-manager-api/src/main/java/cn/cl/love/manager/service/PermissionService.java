package cn.cl.love.manager.service;

import cn.cl.love.bean.Permission;

import java.util.List;

/**
 * 标题：
 * 作者：何处是归程
 * 时间：2020/1/31 - 9:21
 */
public interface PermissionService {
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
     * @功能: 查询所有节点
     * @作者: 高志红
     */
    List<Permission> getPermissionsAll();

    /**
     * @功能: 添加许可
     * @作者: 高志红
     */
    void savePermission(Permission permission);

    /**
     * @功能: 根据ID查询许可对象
     * @作者: 高志红
     */
    Permission getPermissionById(Integer id);

    /**
     * @功能: 修改许可
     * @作者: 高志红
     */
    void updatePermission(Permission permission);

    /**
     * @功能: 根据id删除许可
     * @作者: 高志红
    */
    void delPermissionById(Integer id);

    /**
     * @功能: 根据id查询已经具有的权限
     * @作者: 高志红
    */
    List<Integer> permissionIdsForRoleid(Integer roleid);
}
