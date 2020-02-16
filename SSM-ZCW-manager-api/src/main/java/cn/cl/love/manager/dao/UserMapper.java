package cn.cl.love.manager.dao;

import cn.cl.love.VO.Data;
import cn.cl.love.bean.Permission;
import cn.cl.love.bean.Role;
import cn.cl.love.bean.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    User selectByPrimaryKey(Integer id);

    List<User> selectAll();

    int updateByPrimaryKey(User record);

    /**
     * @功能: 登录
     * @作者: 高志红
     */
    User queryUserlogin(Map<String, Object> paramMap);

    /**
     * @功能: 模糊查询
     * @作者: 高志红
     */
    List<User> queryUserSelect(Map<String, String> map);

    /**
     * @功能: 条件添加
     * @作者: 高志红
     */
    void updateByPrimaryKeySelerct(User user);

    /**
     * @功能: 批量删除
     * @作者: 高志红
     */
    void delBatchUserVO(List<User> users);

    /**
     * @功能: 查询全部角色
     * @作者: 高志红
     */
    List<Role> queryAllRole();

    /**
     * @功能: 查询指定用户的角色
     * @作者: 高志红
     */
    List<Integer> queryRoleByUserId(Integer id);

    /**
     * @功能: 分配角色
     * @作者: 高志红
     */
    int assigRole(@Param("userid") Integer userid, @Param("data") Data data);

    /**
     * @功能: 收回角色
     * @作者: 高志红
     */
    int revokeRole(@Param("userid") Integer userid, @Param("data") Data data);

    /**
     * @功能: 根据ID查询用户权限
     * @作者: 高志红
    */
    List<Permission> getPermission(@Param("id") Integer id);
}