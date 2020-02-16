package cn.cl.love.manager.service.impl;

import cn.cl.love.VO.Data;
import cn.cl.love.bean.Permission;
import cn.cl.love.bean.Role;
import cn.cl.love.bean.User;
import cn.cl.love.exception.LoginFailException;
import cn.cl.love.manager.dao.UserMapper;
import cn.cl.love.manager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 标题：用户业务实现类
 * 作者：何处是归程
 * 时间：2020/1/28 - 12:02
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    /**
     * @功能: 验证登录
     * @作者: 高志红
     */
    @Override
    public User login(Map<String, Object> paramMap) {
        User user = userMapper.queryUserlogin(paramMap);
        if (user == null) {
            throw new LoginFailException("用户账号或密码不正确！");
        }
        return user;
    }

    /**
     * @功能: 注册保存
     * @作者: 高志红
     */
    @Override
    public void insertUser(User user) {
        userMapper.insert(user);
    }

    /**
     * @功能: 查询全部用户
     * @作者: 高志红
     */
    @Override
    public List<User> queryUserAll() {
        List<User> users = userMapper.selectAll();
        return users;
    }

    /**
     * @功能: 根据姓名模糊查询
     * @作者: 高志红
     */
    @Override
    public List<User> queryUserSelect(Map<String, String> map) {
        List<User> users = null;
        if (map.size() == 0 || map == null) {
            users = userMapper.selectAll();
        } else {
            users = userMapper.queryUserSelect(map);
        }
        return users;
    }

    /**
     * @功能: 根据ID查询单个用户
     * @作者: 高志红
     */
    @Override
    public User getUserByid(Integer id) {
        return userMapper.selectByPrimaryKey(id);
    }

    /**
     * @功能: 选择修改
     * @作者: 高志红
     */
    @Override
    public void updateByPrimaryKeySelerct(User user) {
        userMapper.updateByPrimaryKeySelerct(user);
    }

    /**
     * @功能: 删除用户
     * @作者: 高志红
     */
    @Override
    public void delUser(Integer id) {
        userMapper.deleteByPrimaryKey(id);
    }

    /**
     * @功能: 批量删除
     * @作者: 高志红
     */
    @Override
    public void delBatchUser(List<Integer> idList) {
        if (idList != null) {
            for (Integer id : idList) {
                userMapper.deleteByPrimaryKey(id);
            }
        }
    }

    /**
     * @功能: 批量删除VO版
     * @作者: 高志红
     */
    @Override
    public void delBatchUserVO(Data data) {
        userMapper.delBatchUserVO(data.getUsers());
    }

    /**
     * @功能: 查询所有角色
     * @作者: 高志红
     */
    @Override
    public List<Role> queryAllRole() {
        return userMapper.queryAllRole();
    }

    /**
     * @功能: 查询指定用户的角色ID
     * @作者: 高志红
     */
    @Override
    public List<Integer> queryRoleByUserId(Integer id) {
        return userMapper.queryRoleByUserId(id);
    }

    /**
     * @return
     * @功能: 分配角色
     * @作者: 高志红
     */
    @Override
    public int assigRole(Integer userid, Data data) {
        return  userMapper.assigRole(userid, data);
    }

    /**
     * @return
     * @功能: 收回角色
     * @作者: 高志红
     */
    @Override
    public int revokeRole(Integer userid, Data data) {
        return userMapper.revokeRole(userid, data);
    }

    /**
     * @功能: 根据用户id查询权限
     * @作者: 高志红
    */
    @Override
    public List<Permission> getPermissionByUserId(Integer id) {
        return userMapper.getPermission(id);
    }
}
