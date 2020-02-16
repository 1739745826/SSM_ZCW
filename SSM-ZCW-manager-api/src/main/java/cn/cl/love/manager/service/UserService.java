package cn.cl.love.manager.service;


import cn.cl.love.VO.Data;
import cn.cl.love.bean.Permission;
import cn.cl.love.bean.Role;
import cn.cl.love.bean.User;

import java.util.List;
import java.util.Map;

/**
 * 标题：用户业务接口
 * 作者：何处是归程
 * 时间：2020/1/28 - 12:01
 */
public interface UserService {
    /**
     * @功能: 登录
     * @作者: 高志红
     */
    User login(Map<String, Object> paramMap);

    /**
     * @功能: 注册（保存）
     * @作者: 高志红
     */
    void insertUser(User user);

    /**
     * @功能: 查询全部用户
     * @作者: 高志红
     */
    List<User> queryUserAll();

    /**
     * @功能: 根据真实姓名模糊查询
     * @作者: 高志红
     */
    List<User> queryUserSelect(Map<String, String> map);

    /**
     * @功能: 根据ID查询单个员工
     * @作者: 高志红
     */
    User getUserByid(Integer id);

    /**
     * @功能: 修改员工选择条件版
     * @作者: 高志红
     */
    void updateByPrimaryKeySelerct(User user);

    /**
     * @功能: 删除用户
     * @作者: 高志红
     */
    void delUser(Integer id);

    /**
     * @功能: 批量删除
     * @作者: 高志红
     */
    void delBatchUser(List<Integer> idList);

    /**
     * @功能: 批量删除VO版
     * @作者: 高志红
    */
    void delBatchUserVO(Data data);

    /**
     * @功能: 查询所有角色
     * @作者: 高志红
    */
    List<Role> queryAllRole();

    /**
     * @功能: 查询指定用户的角色ID
     * @作者: 高志红
    */
    List<Integer> queryRoleByUserId(Integer id);

    /**
     * @功能: 分配角色
     * @作者: 高志红
     * @return
    */
    int assigRole(Integer userid, Data data);

    /**
     * @功能: 收回角色
     * @作者: 高志红
     * @return
    */
    int revokeRole(Integer userid, Data data);

    /**
     * @功能: 根据角色查询权限
     * @作者: 高志红
    */
    List<Permission> getPermissionByUserId(Integer id);
}
