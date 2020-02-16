package cn.cl.love.manager.service;

import cn.cl.love.VO.Data;
import cn.cl.love.bean.Role;

import java.util.List;
import java.util.Map;

/**
 * 标题：
 * 作者：何处是归程
 * 时间：2020/1/31 - 18:01
 */
public interface RoleService {
    /**
     * @功能: 查询全部 + 条件查询
     * @作者: 高志红
    */
    List<Role> getRoleSelect(Map<String, String> map);

    /**
     * @功能: 添加角色
     * @作者: 高志红
    */
    void saveRole(Role role);

    /**
     * @功能: 根据id查询角色
     * @作者: 高志红
    */
    Role getRoleById(Integer id);

    /**
     * @功能: 修改角色
     * @作者: 高志红
    */
    void updateRole(Role role);

    /**
     * @功能: 删除角色
     * @作者: 高志红
    */
    void delRole(Integer id);

    /**
     * @功能: 批量删除
     * @作者: 高志红
    */
    void delBatchRole(Data data);

    /**
     * @功能: 根据roleid删除权限
     * @作者: 高志红
    */
    void delPermissionByRoleId(Integer roleid);

    /**
     * @功能: 分配权限
     * @作者: 高志红
    */
    void assignPermission(Integer roleid, Data data);
}
