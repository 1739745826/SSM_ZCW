package cn.cl.love.manager.controller;

import cn.cl.love.VO.Data;
import cn.cl.love.bean.Permission;
import cn.cl.love.bean.Role;
import cn.cl.love.manager.service.PermissionService;
import cn.cl.love.manager.service.RoleService;
import cn.cl.love.util.AjaxResult;
import cn.cl.love.util.StringUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 标题：角色控制器
 * 作者：何处是归程
 * 时间：2020/1/31 - 18:00
 */
@Controller
@RequestMapping("/role")
public class RoleController {
    @Autowired
    RoleService roleService;

    @Autowired
    PermissionService permissionService;

    @RequestMapping("/toIndex")
    public String toIndex() {
        return "role/index";
    }

    @RequestMapping("/toAdd")
    public String toAdd() {
        return "role/add";
    }

    @RequestMapping("/toUpdate")
    public String toUpdate(Integer id, Model model) {
        Role role = roleService.getRoleById(id);
        model.addAttribute("role", role);
        return "role/update";
    }

    /**
     * @功能: 查询全部 & 模糊查询
     * @作者: 高志红
     */
    @ResponseBody
    @RequestMapping("/index")
    public Object index(@RequestParam(value = "begin", defaultValue = "1") Integer begin, String queryInput) {
        try {
            Map<String, String> map = new HashMap<String, String>();
            if (StringUtil.isNotEmpty(queryInput)) {
                queryInput = "%" + queryInput + "%";
                map.put("name", queryInput);
            }
            PageHelper.startPage(begin, 10);
            List<Role> roles = roleService.getRoleSelect(map);
            PageInfo<Role> info = new PageInfo<Role>(roles, 10);
            return AjaxResult.success("info", info);
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.fail("获取角色信息失败");
        }
    }

    /**
     * @功能: 添加角色
     * @作者: 高志红
     */
    @ResponseBody
    @RequestMapping("/add")
    public Object add(Role role) {
        try {
            roleService.saveRole(role);
            return AjaxResult.success();
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.fail("添加角色失败");
        }
    }

    /**
     * @功能: 修改角色
     * @作者: 高志红
     */
    @ResponseBody
    @RequestMapping("/update")
    public Object update(Role role) {
        try {
            roleService.updateRole(role);
            return AjaxResult.success();
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.fail("修改角色失败");
        }
    }

    /**
     * @功能: 删除角色
     * @作者: 高志红
     */
    @ResponseBody
    @RequestMapping("/del")
    public Object del(Integer id) {
        try {
            roleService.delRole(id);
            return AjaxResult.success();
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.fail("删除角色失败");
        }
    }

    /**
     * @功能: 批量删除角色
     * @作者: 高志红
     */
    @ResponseBody
    @RequestMapping("/delBatchRole")
    public Object delBatchRole(Data data) {
        System.out.println(data);
        try {
            roleService.delBatchRole(data);
            return AjaxResult.success();
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.fail("删除角色失败");
        }
    }

    /**
     * @功能: 跳转分配权限页面
     * @作者: 高志红
     */
    @RequestMapping("/toAssignPermission")
    public String toAssignPermission() {
        return "role/assignPermission";
    }

    /**
     * @功能: 查询所有的权限
     * @作者: 高志红
     */
    @ResponseBody
    @RequestMapping("loadDataAsync")
    public Object loadDataAsync(Integer roleid) {
        try {
            List<Permission> permissionsAll = permissionService.getPermissionsAll();
            List<Permission> root = new ArrayList<Permission>();
            // 根据角色id查询该角色之前分配的许可
            List<Integer> permissionIdsForRoleid = permissionService.permissionIdsForRoleid(roleid);

            Map<Integer, Permission> map = new HashMap<Integer, Permission>();
            for (Permission innerPermission : permissionsAll) {
                map.put(innerPermission.getId(), innerPermission);
                // 如果查询出来的id和本来的id一致就打上对勾
                if (permissionIdsForRoleid.contains(innerPermission.getId())) {
                    innerPermission.setChecked(true);
                }
            }
            for (Permission permission : permissionsAll) {
                Permission child = permission;
                if (child.getPid() == null) {
                    root.add(permission);
                } else {
                    // 父节点
                    Permission parent = map.get(child.getPid());
                    parent.getChildren().add(child);
                }
            }
            return root;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @功能: 分配权限
     * @作者: 高志红
    */
    @ResponseBody
    @RequestMapping("/assignPermission")
    public Object assignPermission(Integer roleid, Data data) {
        try {
            // 先删除所有权限
            roleService.delPermissionByRoleId(roleid);
            // 插入新分配的数据
            roleService.assignPermission(roleid, data);
            return AjaxResult.success();
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.fail("分配权限失败");
        }
    }
}
