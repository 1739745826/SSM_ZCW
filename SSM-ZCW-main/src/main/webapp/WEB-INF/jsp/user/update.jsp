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
            <div><a class="navbar-brand" style="font-size:32px;" href="user.html">众筹平台 - 用户维护</a></div>
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
                    <form role="form" id="updateUserForm">
                        <div class="form-group">
                            <label for="floginacct">登陆账号</label>
                            <input type="text" disabled class="form-control" value="${user.loginacct}" id="floginacct"
                                   placeholder="请输入登录账号">
                        </div>
                        <div class="form-group">
                            <label for="fuserpswd">登陆密码</label>
                            <input type="text" class="form-control" value="${user.userpswd}" id="fuserpswd"
                                   placeholder="请输入登录密码"
                                   style="margin-top:10px;">
                        </div>
                        <div class="form-group">
                            <label for="fuserpswd">用户姓名</label>
                            <input type="text" class="form-control" value="${user.username}" id="fusername"
                                   placeholder="请输入真实姓名"
                                   style="margin-top:10px;">
                        </div>
                        <div class="form-group">
                            <label for="femail">邮箱地址</label>
                            <input type="text" class="form-control" value="${user.email}" id="femail"
                                   placeholder="请输入邮箱地址"
                                   style="margin-top:10px;">
                            <p class="help-block label label-warning">请输入合法的邮箱地址, 格式为： xxxx@xxxx.com</p>
                        </div>
                        <button type="button" class="btn btn-success" onclick="updateUser()"><i
                                class="glyphicon glyphicon-edit"></i> 修改
                        </button>
                        <button type="button" class="btn btn-danger" onclick="reset()"><i class="glyphicon glyphicon-refresh"></i> 重置
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
            <div class="modal-body">
                <div class="bs-callout bs-callout-info">
                    <h4>测试标题1</h4>
                    <p>测试内容1，测试内容1，测试内容1，测试内容1，测试内容1，测试内容1</p>
                </div>
                <div class="bs-callout bs-callout-info">
                    <h4>测试标题2</h4>
                    <p>测试内容2，测试内容2，测试内容2，测试内容2，测试内容2，测试内容2</p>
                </div>
            </div>
            <!--
            <div class="modal-footer">
              <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
              <button type="button" class="btn btn-primary">Save changes</button>
            </div>
            -->
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
    function updateUser() {
        let floginacct = $("#floginacct").val();
        let fuserpswd = $("#fuserpswd").val();
        let fusername = $("#fusername").val();
        let femail = $("#femail").val();
        let fid = $("#fid").val();
        if ($.trim(floginacct) === "") {
            errorMsg("#floginacct", "登录账号不能为空， 请输入！");
            return false;
        }
        if ($.trim(fuserpswd) === "") {
            errorMsg("#fuserpswd", "登录密码不能为空， 请输入！");
            return false;
        }
        if ($.trim(fusername) === "") {
            errorMsg("#fusername", "真实姓名不能为空， 请输入！");
            return false;
        }
        if ($.trim(femail) === "") {
            errorMsg("#femail", "用户邮箱不能为空， 请输入！");
            return false;
        }
        $.ajax({
            url: "${APP_PATH}/user/updateUser.do",
            //$.ajax()函数中{}里的属性名称可以不用引号引起来.
            data: {
                "loginacct": floginacct,
                "userpswd": fuserpswd,
                "username": fusername,
                "email": femail,
                "id": "${user.id}"
            },
            type: "POST",
            success: function (result) {
                if (result.success) {
                    // 跳转登录页面
                    window.location.href = "${APP_PATH}/user/toIndex.do";
                } else {
                    errorMsg(null, result.message);
                }
            }
        });
    }

    // 提示信息封装
    function errorMsg(element, msg) {
        layer.msg(msg, {time: 1000, icon: 5, shift: 6});
        $(element).val("");
        $(element).focus(); // 获取焦点
    }

    // 重置表单
    function reset() {
        $("#updateUserForm")[0].reset();
    }
</script>
</body>
</html>
