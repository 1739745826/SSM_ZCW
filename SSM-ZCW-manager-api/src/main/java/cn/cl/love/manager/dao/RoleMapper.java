package cn.cl.love.manager.dao;

import cn.cl.love.bean.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface RoleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Role record);

    Role selectByPrimaryKey(Integer id);

    List<Role> selectAll();

    int updateByPrimaryKey(Role record);

    /**
     * @功能: 模糊查询
     * @作者: 高志红
     */
    List<Role> selectAllSelect(Map<String, String> map);

    /**
     * @功能: 批量删除
     * @作者: 高志红
     */
    void delBatchRole(@Param("ids") List<Integer> ids);

    /**
     * @功能: 根据roleid删除权限
     * @作者: 高志红
     */
    void delPermissionByRoleId(@Param("roleid") Integer roleid);

    /**
     * @功能: 分配权限
     * @作者: 高志红
     */
    void assignPermission(@Param("roleid") Integer roleid, @Param("ids") List<Integer> ids);

}