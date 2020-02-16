package cn.cl.love.manager.service.impl;

import cn.cl.love.VO.Data;
import cn.cl.love.bean.Role;
import cn.cl.love.manager.dao.RoleMapper;
import cn.cl.love.manager.service.RoleService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 标题：
 * 作者：何处是归程
 * 时间：2020/1/31 - 18:02
 */
@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    RoleMapper roleMapper;

    /**
     * @功能: 查询全部 + 条件查询
     * @作者: 高志红
     */
    @Override
    public List<Role> getRoleSelect(Map<String, String> map) {
        List<Role> roles = new ArrayList<Role>();
        if (map.size() == 0 || map == null) {
            roles = roleMapper.selectAll();
        } else {
            roles = roleMapper.selectAllSelect(map);
        }
        return roles;
    }

    /**
     * @功能: 添加角色
     * @作者: 高志红
     */
    @Override
    public void saveRole(Role role) {
        roleMapper.insert(role);
    }

    /**
     * @功能: 根据id查询角色
     * @作者: 高志红
     */
    @Override
    public Role getRoleById(Integer id) {
        return roleMapper.selectByPrimaryKey(id);
    }

    /**
     * @功能: 修改角色
     * @作者: 高志红
     */
    @Override
    public void updateRole(Role role) {
        roleMapper.updateByPrimaryKey(role);
    }

    /**
     * @功能: 删除角色
     * @作者: 高志红
     */
    @Override
    public void delRole(Integer id) {
        roleMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void delBatchRole(Data data) {
        roleMapper.delBatchRole(data.getIds());
    }

    /**
     * @功能: 根据roleid删除权限
     * @作者: 高志红
     */
    @Override
    public void delPermissionByRoleId(Integer roleid){
        roleMapper.delPermissionByRoleId(roleid);
    }

    /**
     * @功能: 分配权限
     * @作者: 高志红
     */
    @Override
    public void assignPermission(Integer roleid, Data data) {
        roleMapper.assignPermission(roleid, data.getIds());
    }
}
