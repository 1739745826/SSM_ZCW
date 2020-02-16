package cn.cl.love.manager.controller;

import cn.cl.love.bean.Permission;
import cn.cl.love.manager.service.PermissionService;
import cn.cl.love.util.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 标题：
 * 作者：何处是归程
 * 时间：2020/1/31 - 9:17
 */
@Controller
@RequestMapping("/permission")
public class PermissionController {
    @Autowired
    PermissionService permissionService;

    @RequestMapping("/toIndex")
    public String toIndex() {
        return "permission/index";
    }

    @RequestMapping("/toAdd")
    public String toAdd() {
        return "permission/add";
    }

    @RequestMapping("/toUpdate")
    public String toUpfate(Integer id, Model model) {
        Permission permission = permissionService.getPermissionById(id);
        model.addAttribute("permission", permission);
        return "permission/update";
    }

    /**
     * @功能: Demo模拟数据从生成树
     * @作者: 高志红
     */
    /*    @ResponseBody
    @RequestMapping("/loadData")
    public Object loadData() {
        AjaxResult result = new AjaxResult();
        try {
            List<Permission> root=  new ArrayList<Permission>();
            // 父
            Permission permission = new Permission();
            permission.setName("李白");
            // 子
            List<Permission> childern = new ArrayList<Permission>();
            Permission permission1 = new Permission();
            permission1.setName("将进酒");
            Permission permission2 = new Permission();
            permission2.setName("梦游天姥吟留别");
            childern.add(permission1);
            childern.add(permission2);
            permission.setChildren(childern);

            root.add(permission);

            result.setData("root", root);
            result.setSuccess(true);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage("加载许可树失败");
            e.printStackTrace();
        }
        return result;
    }*/


    /**
     * @功能: 从数据表t_permission查询数据，显示许可树（利用递归查询）
     * @作者: 高志红
     */
    /*    @ResponseBody
    @RequestMapping("/loadData")
    public Object loadData() {
        AjaxResult result = new AjaxResult();
        try {
            // 父
            Permission permission = permissionService.getRootPsrmission();
            permission.setOpen(true);
            queryChildPermissions(permission);
            List<Permission> root = new ArrayList<Permission>();
            root.add(permission);
            result.setData("root", root);
            result.setSuccess(true);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage("加载许可树失败");
            e.printStackTrace();
        }
        return result;
    }
    */
    /*
    // 递归查询
    private void queryChildPermissions(Permission permission) {
        // 子
        List<Permission> childern = permissionService.getChildrenPermissionByPid(permission.getId());
        // 设置父子关系
        permission.setChildren(childern);
        // 孙子
        for (Permission child : childern) {
            queryChildPermissions(child);
        }
    }*/

    /**
     * @功能: 一次性加载所有数据进行判断减少与数据库交互的次数
     * @作者: 高志红
     */
  /*  @ResponseBody
    @RequestMapping("/loadData")
    public Object loadData() {
        try {
            List<Permission> permissionsAll = permissionService.getPermissionsAll();
            List<Permission> root = new ArrayList<Permission>();
            for (Permission permission : permissionsAll) {
                Permission child = permission;
                if (child.getPid() == null) {
                    root.add(permission);
                } else {
                    for (Permission innerPermission : permissionsAll) {
                        if (child.getPid() == innerPermission.getId()) {
                            Permission parent = innerPermission;
                            parent.getChildren().add(child);
                            break;
                        }
                    }
                }
            }
            return AjaxResult.success("root", root);
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.fail("获取许可树失败");
        }
    }*/

    /**
     * @功能: 用Map集合来查找父，来组合父子关系，减少循环次数
     * @作者: 高志红
     */
    @ResponseBody
    @RequestMapping("/loadData")
    public Object loadData() {
        try {
            List<Permission> permissionsAll = permissionService.getPermissionsAll();
            List<Permission> root = new ArrayList<Permission>();
            Map<Integer, Permission> map = new HashMap<Integer, Permission>();
            for (Permission innerPermission : permissionsAll) {
                map.put(innerPermission.getId(), innerPermission);
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
            return AjaxResult.success("root", root);
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.fail("获取许可树失败");
        }
    }

    /**
     * @功能: 添加许可
     * @作者: 高志红
     */
    @ResponseBody
    @RequestMapping("add")
    public Object add(Permission permission) {
        try {
            permissionService.savePermission(permission);
            return AjaxResult.success();
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.fail("添加许可失败");
        }
    }

    /**
     * @功能: 修改许可
     * @作者: 高志红
    */
    @ResponseBody
    @RequestMapping("update")
    public Object update(Permission permission) {
        try {
            permissionService.updatePermission(permission);
            return AjaxResult.success();
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.fail("修改许可失败");
        }
    }

    /**
     * @功能: 删除许可
     * @作者: 高志红
     */
    @ResponseBody
    @RequestMapping("/delete")
    public Object toDelete(Integer id) {
        try {
            permissionService.delPermissionById(id);
            return AjaxResult.success();
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.fail("删除许可失败");
        }
    }
}
