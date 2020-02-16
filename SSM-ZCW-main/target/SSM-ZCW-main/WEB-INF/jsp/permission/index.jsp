<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%session.setAttribute("APP_PATH", request.getPathInfo());%>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    <title>用户首页</title>
    <link rel="stylesheet" href="${APP_PATH}/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="${APP_PATH}/css/font-awesome.min.css">
    <link rel="stylesheet" href="${APP_PATH}/css/main.css">
    <link rel="stylesheet" href="${APP_PATH}/ztree/zTreeStyle.css">
    <style>
        .tree li {
            list-style-type: none;
            cursor: pointer;
        }

        table tbody tr:nth-child(odd) {
            background: #F4F4F4;
        }

        table tbody td:nth-child(even) {
            color: #C00;
        }

        th, td {
            text-align: center;
        }
    </style>
</head>

<body>

<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
    <div class="container-fluid">
        <div class="navbar-header">
            <div><a class="navbar-brand" style="font-size:32px;" href="#">众筹平台 - 用户维护</a></div>
        </div>
        <jsp:include page="/WEB-INF/jsp/common/heade.jsp"/>
    </div>
</nav>

<div class="container-fluid">
    <div class="row">
        <div class="col-sm-3 col-md-2 sidebar">
            <div class="tree">
                <jsp:include page="/WEB-INF/jsp/common/menu.jsp"/>
            </div>
        </div>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title"><i class="glyphicon glyphicon-th"></i> 许可树</h3>
                </div>
                <div class="panel-body">
                    <ul id="treeDemo" class="ztree"></ul>
                </div>
            </div>
        </div>
    </div>
</div>

<script src="${APP_PATH}/jquery/jquery-2.1.1.min.js"></script>
<script src="${APP_PATH}/bootstrap/js/bootstrap.min.js"></script>
<script src="${APP_PATH}/script/menu.js"></script>
<script type="text/javascript" src="${APP_PATH}/jquery/layer/layer.js"></script>
<script type="text/javascript" src="${APP_PATH}/ztree/jquery.ztree.all-3.5.min.js"></script>
<script type="text/javascript">
    /*let zNodes = [
        {
            name: "父节点1 - 展开", open: true,
            children: [
                {
                    name: "父节点11 - 折叠",
                    children: [
                        {name: "叶子节点111"},
                        {name: "叶子节点112"},
                        {name: "叶子节点113"},
                        {name: "叶子节点114"}
                    ]
                },
                {
                    name: "父节点12 - 折叠",
                    children: [
                        {name: "叶子节点121"},
                        {name: "叶子节点122"},
                        {name: "叶子节点123"},
                        {name: "叶子节点124"}
                    ]
                },
                {name: "父节点13 - 没有子节点", isParent: true}
            ]
        },
        {
            name: "父节点2 - 折叠",
            children: [
                {
                    name: "父节点21 - 展开", open: true,
                    children: [
                        {name: "叶子节点211"},
                        {name: "叶子节点212"},
                        {name: "叶子节点213"},
                        {name: "叶子节点214"}
                    ]
                },
                {
                    name: "父节点22 - 折叠",
                    children: [
                        {name: "叶子节点221"},
                        {name: "叶子节点222"},
                        {name: "叶子节点223"},
                        {name: "叶子节点224"}
                    ]
                },
                {
                    name: "父节点23 - 折叠",
                    children: [
                        {name: "叶子节点231"},
                        {name: "叶子节点232"},
                        {name: "叶子节点233"},
                        {name: "叶子节点234"}
                    ]
                }
            ]
        },
        {name: "父节点3 - 没有子节点", isParent: true}

    ];*/

    // 从数据库里查询数据并以树的结构显示在页面上
    $.ajax({
        url: "${APP_PATH}/permission/loadData.do",
        type: "post",
        success: function (result) {
            if (result.success) {
                let setting = {
                    // 切换字体图标
                    view: {
                        addDiyDom: function (treeId, treeNode) {
                            let icoObj = $("#" + treeNode.tId + "_ico"); // tId = permissionTree_1, $("#permissionTree_1_ico")
                            if (treeNode.icon) {
                                icoObj.removeClass("button ico_docu ico_open").addClass(treeNode.icon).css("background", "");
                            }
                        },
                        addHoverDom: function (treeId, treeNode) { // 按钮组
                            var aObj = $("#" + treeNode.tId + "_a"); // tId = permissionTree_1, ==> $("#permissionTree_1_a")
                            aObj.attr("href", "#").attr("target", "_parent"); // 取消当前链接的事件
                            if (treeNode.editNameFlag || $("#btnGroup" + treeNode.tId).length > 0) return;
                            var s = '<span id="btnGroup' + treeNode.tId + '">';
                            if (treeNode.level == 0) { // 根节点
                                s += '<a class="btn btn-info dropdown-toggle btn-xs" style="margin-left:10px;padding-top:0px;" href="#"  onclick="window.location.href=\'${APP_PATH}/permission/toAdd.htm?id=' + treeNode.id + '\'">&nbsp;&nbsp;<i class="fa fa-fw fa-plus rbg "></i></a>';
                            } else if (treeNode.level == 1) { // 分支节点
                                s += '<a class="btn btn-info dropdown-toggle btn-xs" style="margin-left:10px;padding-top:0px;"  href="#" title="修改权限信息" onclick="window.location.href=\'${APP_PATH}/permission/toUpdate.htm?id=' + treeNode.id + '\'">&nbsp;&nbsp;<i class="fa fa-fw fa-edit rbg "></i></a>';
                                if (treeNode.children.length == 0) {
                                    s += '<a class="btn btn-info dropdown-toggle btn-xs" style="margin-left:10px;padding-top:0px;" href="#" onclick="del(' + treeNode.id + ',\'' + treeNode.name + '\')">&nbsp;&nbsp;<i class="fa fa-fw fa-times rbg "></i></a>';
                                }
                                s += '<a class="btn btn-info dropdown-toggle btn-xs" style="margin-left:10px;padding-top:0px;" href="#" onclick="window.location.href=\'${APP_PATH}/permission/toAdd.htm?id=' + treeNode.id + '\'" >&nbsp;&nbsp;<i class="fa fa-fw fa-plus rbg "></i></a>';
                            } else if (treeNode.level == 2) { // 叶子节点
                                s += '<a class="btn btn-info dropdown-toggle btn-xs" style="margin-left:10px;padding-top:0px;"  href="#" title="修改权限信息" onclick="window.location.href=\'${APP_PATH}/permission/toUpdate.htm?id=' + treeNode.id + '\'">&nbsp;&nbsp;<i class="fa fa-fw fa-edit rbg "></i></a>';
                                s += '<a class="btn btn-info dropdown-toggle btn-xs" style="margin-left:10px;padding-top:0px;" href="#" onclick="del(' + treeNode.id + ',\'' + treeNode.name + '\')" >&nbsp;&nbsp;<i class="fa fa-fw fa-times rbg "></i></a>';
                            }
                            s += '</span>';
                            aObj.after(s);
                        },
                        removeHoverDom: function (treeId, treeNode) {
                            $("#btnGroup" + treeNode.tId).remove();
                        }
                    }
                };
                let zNodes = result.data.root;
                $.fn.zTree.init($("#treeDemo"), setting, zNodes);
            } else {
                layer.msg(result.message, {time: 1000, icon: 5, shift: 6});
            }
        }
    });

    function del(id, name) {
        layer.confirm("确定要删除许可[" + name + "]吗", {icon: 3, title: '提示'}, function (cindex) {
            layer.close(cindex);
            $.ajax({
                url: "${APP_PATH}/permission/delete.do",
                type: "POST",
                data: {"id": id},
                success: function (result) {
                    if (result.success) {
                        // 跳转许可页面
                        window.location.href = "${APP_PATH}/permission/toIndex.htm";
                        layer.msg("许可删除成功", {time: 1000, icon: 6});
                    } else {
                        layer.msg(result.message, {time: 1000, icon: 5, shift: 6});
                    }
                }
            });
        }, function (cindex) {
            layer.close(cindex);
            return false;
        });
    }

    $(function () {
        showMenu();
        $(".list-group-item").click(function () {
            if ($(this).find("ul")) {
                $(this).toggleClass("tree-closed");
                if ($(this).hasClass("tree-closed")) {
                    $("ul", this).hide("fast");
                } else {
                    $("ul", this).show("fast");
                }
            }
        });
    });
</script>
<script src="${APP_PATH}/script/docs.min.js"></script>
</body>
</html>
