package cn.cl.love.manager.service.impl;

import cn.cl.love.bean.Permission;
import cn.cl.love.manager.dao.PermissionMapper;
import cn.cl.love.manager.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 标题：
 * 作者：何处是归程
 * 时间：2020/1/31 - 9:22
 */
@Service
public class PermissionServiceImpl implements PermissionService {
    @Autowired
    private PermissionMapper permissionMapper;

    /**
     * @功能: 查询root节点
     * @作者: 高志红
     */
    @Override
    public Permission getRootPsrmission() {
        return permissionMapper.getRootPsrmission();
    }

    /**
     * @功能: 查询子节点
     * @作者: 高志红
     */
    @Override
    public List<Permission> getChildrenPermissionByPid(Integer id) {
        return permissionMapper.getChildrenPermissionByPid(id);
    }

    /**
     * @功能: 查询所有节点
     * @作者: 高志红
     */
    @Override
    public List<Permission> getPermissionsAll() {
        return permissionMapper.selectAll();
    }

    /**
     * @功能: 增加许可
     * @作者: 高志红
     */
    @Override
    public void savePermission(Permission permission) {
        permissionMapper.insert(permission);
    }

    /**
     * @功能: 根据ID查询许可对象
     * @作者: 高志红
     */
    @Override
    public Permission getPermissionById(Integer id) {
        return permissionMapper.selectByPrimaryKey(id);
    }

    /**
     * @功能: 修改许可
     * @作者: 高志红
     */
    @Override
    public void updatePermission(Permission permission) {
        permissionMapper.updateByPrimaryKey(permission);
    }

    /**
     * @功能: 根据id删除许可
     * @作者: 高志红
     */
    @Override
    public void delPermissionById(Integer id) {
        permissionMapper.deleteByPrimaryKey(id);
    }

    /**
     * @功能: 根据角色id查询该角色具有的权限
     * @作者: 高志红
    */
    @Override
    public List<Integer> permissionIdsForRoleid(Integer roleid) {
        return permissionMapper.permissionIdsForRoleid(roleid);
    }
}
