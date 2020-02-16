package cn.cl.love.manager.controller;

import cn.cl.love.VO.Data;
import cn.cl.love.bean.Role;
import cn.cl.love.bean.User;
import cn.cl.love.manager.service.UserService;
import cn.cl.love.util.AjaxResult;
import cn.cl.love.util.MD5Util;
import cn.cl.love.util.StringUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 标题：用户处理器
 * 作者：何处是归程
 * 时间：2020/1/29 - 11:41
 */
@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * @功能: 跳转到用户主页
     * @作者: 高志红
     */
    @RequestMapping("/toIndex")
    public String toIndex() {
        return "user/index";
    }

    /**
     * @功能: 跳转到添加用户
     * @作者: 高志红
     */
    @RequestMapping("/toAdd")
    public String toAdd() {
        return "user/add";
    }

    /**
     * @功能: 跳转到修改
     * @作者: 高志红
     */
    @RequestMapping("/toUpdate")
    public String toUpdate(Integer id, Model model) {
        User user = userService.getUserByid(id);
        model.addAttribute("user", user);
        return "user/update";
    }

    /**
     * @功能: 跳到角色管理页面
     * @作者: 高志红
     */
    @RequestMapping("/toAssignRole")
    public String toAssignRole(Integer id, Model model) {
        model.addAttribute("id", id);
        return "user/assignRole";
    }

    /**
     * @功能: 查询全部 + 条件查询 + 分页查询
     * @作者: 高志红
     */
    @ResponseBody
    @RequestMapping("/index")
    public Object index(@RequestParam(value = "begin", defaultValue = "1") Integer begin, String queryInput) {
        AjaxResult result = new AjaxResult();
        try {
            Map<String, String> map = new HashMap<String, String>();
            // 判断是否有模糊查询的条件
            if (StringUtil.isNotEmpty(queryInput)) {
                // 为模糊查询拼串
                queryInput = "%" + queryInput + "%";
                map.put("username", queryInput);
            }
            // 分页查询（只对下面紧跟的一条查询生效）
            PageHelper.startPage(begin, 10);
            List<User> userList = userService.queryUserSelect(map);
            // 将查询结果放在PageInfo中，就可以使用pageInfo实现更多功能，第二个参数传入连续显示的页码
            PageInfo<User> teacherPageInfo = new PageInfo<User>(userList, 10);
            result.setSuccess(true);
            result.setPageInfo(teacherPageInfo);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage("查询数据失败");
            e.printStackTrace();
        }
        return result;
    }


    /**
     * @功能: 添加用户
     * @作者: 高志红
     */
    @ResponseBody
    @RequestMapping("/addUser")
    public Object addUser(@Valid User user, BindingResult bindingResult) {
        AjaxResult result = new AjaxResult();
        try {
            // 加密密码
            user.setUserpswd(MD5Util.digest(user.getUserpswd()));
            // 校验用户的各个字段
            if (bindingResult.hasErrors()) {
                // 校验失败
                List<FieldError> errors = bindingResult.getFieldErrors();
                result.setSuccess(false);
                result.setMessage(errors.get(0).getDefaultMessage());
                return result;
            } else {
                result.setSuccess(true);
                userService.insertUser(user);
                return result;
            }
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage("添加失败");
            e.printStackTrace();
            return result;
        }
    }

    /**
     * @功能: 更新用户
     * @作者: 高志红
     */
    @ResponseBody
    @RequestMapping("/updateUser")
    public Object updateUser(@Valid User user, BindingResult bindingResult) {
        AjaxResult result = new AjaxResult();
        try {
            // 加密密码
            user.setUserpswd(MD5Util.digest(user.getUserpswd()));
            // 校验用户的各个字段
            if (bindingResult.hasErrors()) {
                // 校验失败
                List<FieldError> errors = bindingResult.getFieldErrors();
                result.setSuccess(false);
                result.setMessage(errors.get(0).getDefaultMessage());
                return result;
            } else {
                result.setSuccess(true);
                userService.updateByPrimaryKeySelerct(user);
                return result;
            }
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage("修改失败");
            e.printStackTrace();
            return result;
        }
    }

    /**
     * @功能: 删除单个用户
     * @作者: 高志红
     */
    @ResponseBody
    @RequestMapping("/delUser")
    public Object delUser(@RequestParam("id") Integer id) {
        AjaxResult result = new AjaxResult();
        try {
            userService.delUser(id);
            result.setSuccess(true);
            return result;
        } catch (NumberFormatException e) {
            result.setSuccess(false);
            result.setMessage("删除失败");
            e.printStackTrace();
            return result;
        }
    }

    /**
     * @功能: 批量删除
     * @作者: 高志红
     */
    @ResponseBody
    @RequestMapping("/delBatchUser")
    public Object delBatchUser(Data data) {
        AjaxResult result = new AjaxResult();
        try {
            if (data != null) {
                userService.delBatchUserVO(data);
                result.setSuccess(true);
                return result;
            } else {
                result.setSuccess(false);
                result.setMessage("删除失败");
                return result;
            }
        } catch (NumberFormatException e) {
            result.setSuccess(false);
            result.setMessage("删除失败");
            e.printStackTrace();
            return result;
        }
    }

    /**
     * @功能: 查询回显角色的信息
     * @作者: 高志红
     */
    @ResponseBody
    @RequestMapping("/getRole")
    public Object getRole(Integer id) {
        AjaxResult result = new AjaxResult();
        try {
            List<Role> roles = userService.queryAllRole();
            List<Integer> roleIds = userService.queryRoleByUserId(id);
            List<Role> leftRoleList = new ArrayList<Role>();
            List<Role> rightRoleList = new ArrayList<Role>();
            for (Role role : roles) {
                if (roleIds.contains(role.getId())) {
                    rightRoleList.add(role);
                } else {
                    leftRoleList.add(role);
                }
            }
            result.setData("leftRoleList", leftRoleList);
            result.setData("rightRoleList", rightRoleList);
            result.setSuccess(true);
            return result;
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage("获取用户权限失败");
            e.printStackTrace();
            return result;
        }
    }


    /**
     * @功能: 分配角色
     * @作者: 高志红
     */
    @ResponseBody
    @RequestMapping("/assignRole")
    public Object assignRole(Integer userid, Data data) {
        try {
            if (userid != null && data.getIds().size() <= 0) {
                return AjaxResult.fail("获取角色信息失败");
            } else {
                userService.assigRole(userid, data);
                return AjaxResult.success();
            }
        } catch (Exception e) {
            return AjaxResult.fail("分配角色失败");
        }
    }

    /**
     * @功能: 收回角色
     * @作者: 高志红
     */
    @ResponseBody
    @RequestMapping("/revokeRole")
    public Object revokeRole(Integer userid, Data data) {
        try {
            if (userid != null && data.getIds().size() <= 0) {
                return AjaxResult.fail("获取角色信息失败");
            } else {
                userService.revokeRole(userid, data);
                return AjaxResult.success();
            }
        } catch (Exception e) {
            return AjaxResult.fail("收回角色失败");
        }
    }
}