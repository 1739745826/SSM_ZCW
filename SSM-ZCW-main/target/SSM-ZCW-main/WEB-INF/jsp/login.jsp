<%--
  User: 何处是归程
  Time: 10:20
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="keys" content="">
    <meta name="author" content="">
    <title>登录界面</title>
    <link rel="stylesheet" href="${APP_PATH}/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="${APP_PATH}/css/font-awesome.min.css">
    <link rel="stylesheet" href="${APP_PATH}/css/login.css">
    <style>

    </style>
</head>
<body>
<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
    <div class="container">
        <div class="navbar-header">
            <div><a class="navbar-brand" href="index.html" style="font-size:32px;">尚筹网-创意产品众筹平台</a></div>
        </div>
    </div>
</nav>

<div class="container">
    <form id="loginForm" action="doLogin.do" method="post" class="form-signin" role="form">
        <h2 class="form-signin-heading"><i class="glyphicon glyphicon-log-in"></i> 用户登录</h2>
        <div class="form-group has-success has-feedback">
            <input type="text" class="form-control" id="floginacct" placeholder="请输入登录账号"
                   autofocus>
            <span class="glyphicon glyphicon-user form-control-feedback"></span>
        </div>
        <div class="form-group has-success has-feedback">
            <input type="text" class="form-control" id="fuserpswd" placeholder="请输入登录密码"
                   style="margin-top:10px;">
            <span class="glyphicon glyphicon-lock form-control-feedback"></span>
        </div>
        <div class="form-group has-success has-feedback">
            <input type="text" class="form-control" id="checkcode_input" placeholder="请输入验证码"
                   style="margin-top:10px; width: 150px; display: inline-block">
            <img id="checkcode_img" src="checkCode.do?" alt="点击更换验证码" width="140" height="36"
                 style="height:43px; cursor:pointer; margin-bottom: 5px; margin-left: 5px">
        </div>
        <div class="form-group has-success has-feedback">
            <select class="form-control" name="type" id="ftype">
                <option value="member" selected>会员</option>
                <option value="user">管理</option>
            </select>
        </div>
        <%--<h5 class="text-center">${exception.message}</h5>--%>
        <h5 id="loginMsg" style="display: none; margin-bottom: 13px"></h5>
        <div class="checkbox">
            <label><input type="checkbox" value="remember-me" id="remember-me"> 记住我（2周）</label>
            <label style="float:right">
                <a href="${APP_PATH}/reg.htm">我要注册</a>
            </label>
        </div>
        <a class="btn btn-lg btn-success btn-block" onclick="dologin()"> 登录</a>
    </form>
</div>
<script type="text/javascript" src="${APP_PATH}/jquery/jquery-2.1.1.min.js"></script>
<script type="text/javascript" src="${APP_PATH}/jquery/layer/layer.js"></script>
<script type="text/javascript" src="${APP_PATH}/bootstrap/js/bootstrap.min.js"></script>
<script>
    function dologin() {
        // 同步
        // $("#loginForm").submit();
        // 异步
        let floginacct = $("#floginacct").val();
        let fuserpswd = $("#fuserpswd").val();
        let checkcoed_input = $("#checkcode_input").val();
        let ftype = $("#ftype").val();
        let flag = $("#remember-me")[0].checked ? 1 : 0;
        // 校验 （对于表单数据而言我们不能用null进行判断）
        if ($.trim(checkcoed_input) === "") {
            errorMsg("#checkcoed_input", "验证码不能为空，请输入！");
            return false;
        }
        if ($.trim(floginacct) === "") {
            errorMsg("#floginacct", "登录账号不能为空，请输入！");
            return false;
        }
        if ($.trim(fuserpswd) === "") {
            errorMsg("#fuserpswd", "登录密码不能为空，请输入！");
            return false;
        }
        $.ajax({
            url: "${APP_PATH}/doLogin.do",
            //$.ajax()函数中{}里的属性名称可以不用引号引起来.
            data: {
                "loginacct": floginacct,
                "userpswd": fuserpswd,
                "checkcode_input": checkcoed_input,
                "remember_me": flag,
                "type": ftype
            },
            type: "POST",
            //将服务器端返回的JSON格式字符串转换为JSON,然后通过JS进行解析.
            success: function (result) {
                if (result.success) {
                    if ("member" === result.data.type) {
                        // 跳转主页面
                        window.location.href = "${APP_PATH}/member.htm";
                    } else if ("user" === result.data.type) {
                        // 跳转主页面
                        window.location.href = "${APP_PATH}/main.htm";
                    } else {
                        layer.msg("登录的类型不合法", {time: 1000, icon: 5, shift: 6});
                    }
                } else {
                    layer.msg(result.message, {time: 1000, icon: 5, shift: 6});
                }
            }
        });
    }

    // 点击切换验证码
    $("#checkcode_img").click(function () {
        $(this).prop("src", $(this).prop("src") + new Date());
    });

    // 提示信息封装
    function errorMsg(element, msg) {
        layer.msg(msg, {time: 1000, icon: 5, shift: 6});
        $(element).val("");
        $(element).focus(); // 获取焦点
    }
</script>
</body>
</html>