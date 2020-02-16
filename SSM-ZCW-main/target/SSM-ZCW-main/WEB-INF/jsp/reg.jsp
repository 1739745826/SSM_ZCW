<%--
  User: 何处是归程
  Time: 0:15
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
    <title>注册页面</title>
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
    <form class="form-signin" method="post" role="form">
        <h2 class="form-signin-heading"><i class="glyphicon glyphicon-log-in"></i> 用户注册</h2>
        <div class="form-group has-success has-feedback">
            <input type="text" class="form-control" id="floginacct" name="loginacct" placeholder="请输入登录账号" >
            <span class="glyphicon glyphicon-user form-control-feedback"></span>
        </div>
        <div class="form-group has-success has-feedback">
            <input type="text" class="form-control" id="fuserpswd" name="userpswd" placeholder="请输入登录密码"
                   style="margin-top:10px;">
            <span class="glyphicon glyphicon-lock form-control-feedback"></span>
        </div>
        <div class="form-group has-success has-feedback">
            <input type="text" class="form-control" id="fusername" name="username" placeholder="请输入真实姓名"
                   style="margin-top:10px;">
            <span class="glyphicon glyphicon-qrcode form-control-feedback"></span>
        </div>
        <div class="form-group has-success has-feedback">
            <input type="text" class="form-control" id="femail" name="email" placeholder="请输入邮箱地址"
                   style="margin-top:10px;">
            <span class="glyphicon glyphicon glyphicon-envelope form-control-feedback"></span>
        </div>
        <div class="form-group has-success has-feedback">
            <input type="text" class="form-control" id="checkcode_input" value=""
                   name="check_Code" placeholder="请输入验证码"
                   style="margin-top:10px; width: 150px; display: inline-block">
            <img id="checkcode_img" src="checkCode.do?" alt="点击更换验证码" width="140" height="36"
                 style="height:43px; cursor:pointer; margin-bottom: 5px; margin-left: 5px">
        </div>
        <h5 id="regMsg" style="display: none; margin-bottom: 13px"></h5>
        <div class="checkbox">
            <label>
                忘记密码
            </label>
            <label style="float:right">
                <a href="${APP_PATH}/login.htm">我有账号</a>
            </label>
        </div>
        <a class="btn btn-lg btn-success btn-block" href="javascript:;" onclick="regUser()"> 注册</a>
    </form>
</div>
<script src="${APP_PATH}/jquery/jquery-2.1.1.min.js"></script>
<script type="text/javascript" src="${APP_PATH}/jquery/layer/layer.js"></script>
<script src="${APP_PATH}/bootstrap/js/bootstrap.min.js"></script>
<script>
    /*<a class="btn btn-lg btn-success btn-block" href="javascript:;" onclick="regUser()"> 注册</a>*/
    function regUser() {
        let date = new Date();
        let y = date.getFullYear();
        let m = date.getMonth() + 1;
        m = m < 10 ? ('0' + m) : m;
        let d = date.getDate();
        d = d < 10 ? ('0' + d) : d;
        let createtime = y + '-' + m + '-' + d;
        let floginacct = $("#floginacct").val();
        let fuserpswd = $("#fuserpswd").val();
        let fusername = $("#fusername").val();
        let femail = $("#femail").val();
        let checkcoed_input = $("#checkcode_input").val();
        // 验证各项是否为空
        if ($.trim(checkcoed_input) === "") {
            errorMsg("#checkcoed_input", "验证码不能为空， 请输入！");
            return false;
        }
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
            url: "${APP_PATH}/regUser.do",
            //$.ajax()函数中{}里的属性名称可以不用引号引起来.
            data: {
                "loginacct": floginacct,
                "userpswd": fuserpswd,
                "username": fusername,
                "email": femail,
                "createtime": createtime,
                "checkcode_input": checkcoed_input,
            },
            type: "POST",
            success: function (result) {
                if (result.success) {
                    // 跳转登录页面
                    $("#loginMsg").fadeOut("show");
                    window.location.href = "${APP_PATH}/login.htm";
                } else {
                    errorMsg(null, result.message );
                }
            }
        });
    }

    // 提示信息封装
    function errorMsg (element, msg) {
        layer.msg(msg, {time: 1000, icon: 5, shift: 6});
        $(element).val("");
        $(element).focus(); // 获取焦点
    }

    // 点击切换验证码
    $("#checkcode_img").click(function () {
        $(this).prop("src", $(this).prop("src") + new Date());
    });
</script>
</body>
</html>