<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    <title>修改许可</title>
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
</head>

<body>

<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
    <div class="container-fluid">
        <div class="navbar-header">
            <div><a class="navbar-brand" style="font-size:32px;" href="index.jsp">众筹平台 - 许可修改</a></div>
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
                    <form role="form" id="updatePermission">
                        <div class="form-group">
                            <label for="fname">许可名称</label>
                            <input type="text" class="form-control" value="${permission.name}" id="fname"
                                   placeholder="请输入许可名称">
                        </div>
                        <div class="form-group">
                            <label for="furl">许可URL</label>
                            <input type="text" class="form-control" id="furl" value="${permission.url}" placeholder="请输入许可URL"
                                   style="margin-top:10px;">
                        </div>
                        <button type="button" class="btn btn-success" onclick="updatePermission()"><i
                                class="glyphicon glyphicon-plus"></i> 修改
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
<script type="text/javascript" src="${APP_PATH}/jquery/layer/layer.js"></script>
<script src="${APP_PATH}/script/docs.min.js"></script>

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

    function updatePermission() {
        let fname = $("#fname").val();
        let furl = $("#furl").val();
        /*  if ($.trim(floginacct) === "") {
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
              errorMsg("#femail", "许可邮箱不能为空， 请输入！");
              return false;
          }*/
        $.ajax({
            url: "${APP_PATH}/permission/update.do",
            //$.ajax()函数中{}里的属性名称可以不用引号引起来.
            data: {
                "name": fname,
                "url": furl,
                "id": "${permission.id}"
            },
            type: "POST",
            success: function (result) {
                if (result.success) {
                    // 跳转许可页面
                    window.location.href = "${APP_PATH}/permission/toIndex.htm";
                    layer.msg("许可修改成功", {time: 1000, icon: 6});
                } else {
                    layer.msg(result.message, {time: 1000, icon: 5, shift: 6});
                }
            }
        });
    }

    // 重置表单
    function reset() {
        $("#updatePermission")[0].reset();
    }
</script>
</body>
</html>
