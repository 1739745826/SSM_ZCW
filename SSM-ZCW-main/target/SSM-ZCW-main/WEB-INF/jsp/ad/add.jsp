<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    <title>添加广告</title>
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
            <div><a class="navbar-brand" style="font-size:32px;" href="index.jsp">众筹平台 - 广告管理</a></div>
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
                <li class="active">新增</li>
            </ol>
            <div class="panel panel-default">
                <div class="panel-heading">表单数据
                    <div style="float:right;cursor:pointer;" data-toggle="modal" data-target="#myModal"><i
                            class="glyphicon glyphicon-question-sign"></i></div>
                </div>
                <div class="panel-body">
                    <form role="form" id="addAdForm" method="post" enctype="multipart/form-data">
                        <div class="form-group">
                            <label for="fname">广告名称</label>
                            <input type="text" class="form-control" id="fname" name="name" placeholder="请输入广告名称">
                            <%--<p class="help-block label label-warning">请输入合法的角色名称, 格式为： PM - 项目经理</p>--%>
                        </div>
                        <div class="form-group">
                            <label for="furl">广告地址</label>
                            <input type="text" class="form-control" name="url" id="furl" placeholder="请输入广告地址">
                            <p class="help-block label label-warning">请输入合法的广告地址, 格式为：
                                http//:localhost:8080/www.baidu.com</p>
                        </div>
                        <div class="form-group">
                            <label for="ficonpath">广告图片</label>
                            <input type="file" class="form-control" id="ficonpath" name="img" placeholder="请选择广告图片">
                        </div>
                        <button type="button" class="btn btn-success" id="addAd"><i
                                class="glyphicon glyphicon-plus"></i> 新增
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
<script src="${APP_PATH }/jquery/jquery-form/jquery-form.min.js"></script>

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

        // 异步提交表单
        $("#addAd").click(function () {
            let options = {
                url: "${APP_PATH}/ad/add.do",
                beforeSubmit: function () {
                    loadingIndex = layer.msg('数据正在保存中', {icon: 6});
                    return true; //必须返回true,否则,请求终止.
                },
                success: function (result) {
                    layer.close(loadingIndex);
                    if (result.success) {
                        layer.msg("广告添加成功", {time: 1000, icon: 6});
                        // 跳转广告页面
                        window.location.href = "${APP_PATH}/ad/toIndex.htm";
                    } else {
                        layer.msg(result.message, {time: 1000, icon: 5, shift: 6});
                    }
                }
            };
            $("#addAdForm").ajaxSubmit(options); //异步提交
            return;
        });
    });

    // 重置表单
    function reset() {
        $("#addAdForm")[0].reset();
    }
</script>
</body>
</html>
