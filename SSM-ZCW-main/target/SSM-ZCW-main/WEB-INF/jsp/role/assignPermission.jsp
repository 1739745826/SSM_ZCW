<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%session.setAttribute("APP_PATH", request.getPathInfo());%>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <link rel="stylesheet" href="${APP_PATH}/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="${APP_PATH}/css/font-awesome.min.css">
    <link rel="stylesheet" href="${APP_PATH}/css/main.css">
    <link rel="stylesheet" href="${APP_PATH}/css/doc.min.css">
    <link rel="stylesheet" href="${APP_PATH}/ztree/zTreeStyle.css">
    <style>
        .tree li {
            list-style-type: none;
            cursor: pointer;
        }
    </style>
    <title>分配权限页面</title>
</head>

<body>

<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
    <div class="container-fluid">
        <div class="navbar-header">
            <div><a class="navbar-brand" style="font-size:32px;" href="#">众筹平台 - 角色维护</a></div>
        </div>
        <jsp:include page="/WEB-INF/jsp/common/heade.jsp"/>
    </div>
</nav>

<div class="container-fluid">
    <div class="row">
        <div class="col-sm-3 col-md-2 sidebar">
            <jsp:include page="/WEB-INF/jsp/common/menu.jsp"/>
        </div>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">

            <div class="panel panel-default">
                <div class="panel-heading"><i class="glyphicon glyphicon-th-list"></i> 权限分配列表
                    <div style="float:right;cursor:pointer;" data-toggle="modal" data-target="#myModal"><i
                            class="glyphicon glyphicon-question-sign"></i></div>
                </div>
                <div class="panel-body">
                    <button class="btn btn-success" id="assignPermissionBtn">分配许可</button>
                    <br><br>
                    <ul id="treeDemo" class="ztree"></ul>
                </div>
            </div>
        </div>
    </div>
</div>
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span
                        class="sr-only">Close</span></button>
                <h4 class="modal-title" id="myModalLabel">帮助</h4>
            </div>
        </div>
    </div>
</div>
<script src="${APP_PATH}/jquery/jquery-2.1.1.min.js"></script>
<script src="${APP_PATH}/bootstrap/js/bootstrap.min.js"></script>
<script src="${APP_PATH}/script/docs.min.js"></script>
<script type="text/javascript" src="${APP_PATH}/jquery/layer/layer.js"></script>
<script src="${APP_PATH}/ztree/jquery.ztree.all-3.5.min.js"></script>
<script type="text/javascript">
    $(function () {
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

        // 异步加载
        let setting = {
            check: {
                enable: true // 显示复选框
            },
            view: {
                selectedMulti: false,
                addDiyDom: function (treeId, treeNode) {
                    var icoObj = $("#" + treeNode.tId + "_ico"); // tId = permissionTree_1, $("#permissionTree_1_ico")
                    if (treeNode.icon) {
                        icoObj.removeClass("button ico_docu ico_open").addClass(treeNode.icon).css("background", "");
                    }
                },
            },
            async: {
                enable: true, // 采用异步开发
                url: "${APP_PATH}/role/loadDataAsync.do?roleid=${param.id}",
                autoParam: ["id", "name=n", "level=lv"]
            },
            callback: {
                onClick: function (event, treeId, json) {
                }
            }
        };
        $.fn.zTree.init($("#treeDemo"), setting); //异步访问数据
    });

    // 分配权限按钮的点击事件
    $("#assignPermissionBtn").click(function () {
        let jsonObj = {
            roleid: "${param.id}"
        };
        // 获取整个树
        let tree = $.fn.zTree.getZTreeObj("treeDemo");
        // 获取数中被选中的节点
        let checkTree = tree.getCheckedNodes(tree);
        // 往json中插入数据
        $.each(checkTree, function (index, ele) {
            jsonObj["ids[" + index + "]"] = ele.id;
        });
        if (checkTree.length === 0) {
            layer.msg("请至少选择一个要分配的权限", {time: 1000, icon: 5, shift: 6});
            return false;
        }
        $.ajax({
            url: "${APP_PATH}/role/assignPermission.do",
            type: "POST",
            data: jsonObj,
            success: function (result) {
                console.log(result);
                if (result.success) {
                    layer.msg("分配权限成功", {time: 1000, icon: 6});
                    window.location.href = "${APP_PATH}/role/toIndex.htm";
                } else {
                    layer.msg(result.message, {time: 1000, icon: 5, shift: 6});
                }
            }
        });
    });
</script>
</body>
</html>
