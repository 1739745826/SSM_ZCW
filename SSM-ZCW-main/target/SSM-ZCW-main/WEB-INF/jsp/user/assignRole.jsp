<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%session.setAttribute("APP-PATH", request.getPathInfo());%>
<!DOCTYPE html>
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
    <title>用户角色分配</title>
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
                <li class="active">分配角色</li>
            </ol>
            <div class="panel panel-default">
                <div class="panel-body">
                    <form role="form" class="form-inline">
                        <div class="form-group">
                            <label for="leftList">未分配角色列表</label><br>
                            <select class="form-control text-left" multiple size="10"
                                    style="width:250px;overflow-y:auto;"
                                    id="leftList">
                            </select>
                        </div>
                        <div class="form-group">
                            <ul>
                                <li class="btn btn-default glyphicon glyphicon-chevron-right" id="leftToRightBtn"></li>
                                <br>
                                <li class="btn btn-default glyphicon glyphicon-chevron-left" id="rightToLeftBtn"
                                    style="margin-top:20px;"></li>
                            </ul>
                        </div>
                        <div class="form-group" style="margin-left:40px;">
                            <label for="rightList">已分配角色列表</label><br>
                            <select class="form-control text-left" multiple size="10"
                                    style="width:250px;overflow-y:auto;"
                                    id="rightList">
                            </select>
                        </div>
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
        getRole();
    });

    // 分配角色
    $("#leftToRightBtn").click(function () {
        let selectedOptions = $("#leftList option:selected");
        selectedOptions.appendTo($("#rightList")).prop("selected", false);
        let JsonObj = {
            "userid": "${id}"
        };
        $.each(selectedOptions, function (index, option) {
            JsonObj["ids[" + index + "]"] = this.value;
        });
        $.ajax({
            url: "${APP_PATH}/user/assignRole.do",
            type: "POST",
            data: JsonObj,
            success: function (result) {
                if (result.success) {
                    layer.msg("角色分配成功", {time: 1000, icon: 6});
                } else {
                    layer.msg(result.message, {time: 1000, icon: 5, shift: 6});
                }
            }
        });
    });

    // 收回角色
    $("#rightToLeftBtn").click(function () {
        let selectedOptions = $("#rightList option:selected");
        selectedOptions.appendTo("#leftList").prop("selected", false);
        let JsonObj = {
            userid: "${id}"
        }
        $.each(selectedOptions, function (index, option) {
            JsonObj["ids[" + index + "]"] = this.value;
        });
        $.ajax({
            url: "${APP_PATH}/user/revokeRole.do",
            type: "POST",
            data: JsonObj,
            success: function (result) {
                if (result.success) {
                    layer.msg("角色收回成功", {time: 1000, icon: 6});
                } else {
                    layer.msg(result.message, {time: 1000, icon: 5, shift: 6});
                }
            }
        });
    });

    //获取角色
    function getRole() {
        $.ajax({
            url: "${APP_PATH}/user/getRole.do",
            data: {id: "${id}"},
            type: "POST",
            success: function (result) {
                if (result.success) {
                    getSelect(result);
                } else {
                    layer.msg(result.message, {time: 1000, icon: 5, shift: 6});
                }
            }
        })
    }

    // 回显用户的角色
    function getSelect(result) {
        $.each(result.data.leftRoleList, function (index, role) {
            $("#leftList").append('<option value="' + role.id + '">' + role.name + '</option>');
        });
        $.each(result.data.rightRoleList, function (index, role) {
            $("#rightList").append('<option value="' + role.id + '">' + role.name + '</option>');
        });
    }
</script>
</body>
</html>
