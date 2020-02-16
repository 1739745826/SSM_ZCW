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
    <style>
        .tree li {
            list-style-type: none;
            cursor: pointer;
        }
    </style>
    <title>修改界面</title>
</head>

<body>

<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
    <div class="container-fluid">
        <div class="navbar-header">
            <div><a class="navbar-brand" style="font-size:32px;" href="user.html">众筹平台 - 角色维护</a></div>
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
            <ol class="breadcrumb">
                <li><a href="#">首页</a></li>
                <li><a href="#">数据列表</a></li>
                <li class="active">修改</li>
            </ol>
            <div class="panel panel-default">
                <div class="panel-heading">表单数据
                    <div style="float:right;cursor:pointer;" data-toggle="modal" data-target="#myModal"><i
                            class="glyphicon glyphicon-question-sign"></i></div>
                </div>
                <div class="panel-body">
                    <form role="form" id="updateRoleForm">
                        <div class="form-group">
                            <label>编号</label>
                            <input type="text" disabled class="form-control" value="${role.id}">
                        </div>
                        <div class="form-group">
                            <label for="fname">角色名称</label>
                            <input type="text" class="form-control" value="${role.name}" id="fname"
                                   placeholder="请输入角色名称">
                        </div>
                        <button type="button" class="btn btn-success" onclick="updateRole()"><i
                                class="glyphicon glyphicon-edit"></i> 修改
                        </button>
                        <button type="button" class="btn btn-danger" onclick="reset()"><i
                                class="glyphicon glyphicon-refresh"></i> 重置
                        </button>
                    </form>
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
    });

    // 更新用户数据
    function updateRole() {
        let fname = $("#fname").val();
        if ($.trim(fname) === "") {
            layer.msg("登录账号不能为空， 请输入！", {time: 1000, icon: 5, shift: 6});
            return false;
        }
        $.ajax({
            url: "${APP_PATH}/role/update.do",
            //$.ajax()函数中{}里的属性名称可以不用引号引起来.
            data: {
                "name": fname,
                "id": "${role.id}"
            },
            type: "POST",
            success: function (result) {
                if (result.success) {
                    layer.msg("角色修改成功", {time: 1000, icon: 6});
                    window.location.href = "${APP_PATH}/role/toIndex.do";
                } else {
                    layer.msg(result.message, {time: 1000, icon: 5, shift: 6});
                }
            }
        });
    }

    // 重置表单
    function reset() {
        $("#updateRoleForm")[0].reset();
    }
</script>
</body>
</html>
