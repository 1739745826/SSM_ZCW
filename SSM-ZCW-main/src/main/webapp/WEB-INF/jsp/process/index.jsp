<%--
  User: 何处是归程
  Time: 18:09
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
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
    <title>流程管理</title>
</head>

<body>

<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
    <div class="container-fluid">
        <div class="navbar-header">
            <div><a class="navbar-brand" style="font-size:32px;" href="#">众筹平台 - 流程管理</a></div>
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
                    <h3 class="panel-title"><i class="glyphicon glyphicon-th"></i> 数据列表</h3>
                </div>
                <div class="panel-body">
                    <form class="form-inline" role="form" style="float:left;">
                        <div class="form-group has-feedback">
                            <div class="input-group">
                                <div class="input-group-addon">查询条件</div>
                                <input class="form-control has-success" type="text" placeholder="请输入查询条件">
                            </div>
                        </div>
                        <button type="button" class="btn btn-warning"><i class="glyphicon glyphicon-search"></i> 查询
                        </button>
                    </form>
                    <button type="button" class="btn btn-primary" style="float:right;" id="upLoadProDefBtn"><i
                            class="glyphicon glyphicon-upload"></i>
                        上传流程定义文件
                    </button>
                    <br>
                    <hr style="clear:both;">
                    <div class="table-responsive">
                        <%--上传流程定义文件--%>
                        <form id="deployForm" action="" style="display: none" method="post"
                              enctype="multipart/form-data">
                            <input id="ProcessDefFile" type="file" name="ProcessDefFile">
                        </form>
                        <table class="table  table-bordered" id="process_teable">
                            <thead>
                            <tr>
                                <th width="100">流程编号</th>
                                <th>流程定义名称</th>
                                <th>流程定义版本</th>
                                <th>流程定义key</th>
                                <th width="150">操作</th>
                            </tr>
                            </thead>
                            <tbody>
                            </tbody>
                            <tfoot>
                            <tr>
                                <td colspan="5" align="center" id="page_nav"></td>
                            </tr>
                            </tfoot>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<script src="${APP_PATH}/jquery/jquery-2.1.1.min.js"></script>
<script src="${APP_PATH}/bootstrap/js/bootstrap.min.js"></script>
<script src="${APP_PATH}/script/docs.min.js"></script>
<script src="${APP_PATH}/script/menu.js"></script>
<script type="text/javascript" src="${APP_PATH}/jquery/layer/layer.js"></script>
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
        toPage(1);
    });

    let page;

    // 跳转页面
    function toPage(begin) {
        $.ajax({
            url: "${APP_PATH }/process/index.do",
            data: {"begin": begin},
            type: "POST",
            success: function (result) {
                if (result.success) {
                    roleTable(result);
                    rolePage(result);
                } else {
                    layer.msg(result.message, {time: 1000, icon: 5, shift: 6});
                }
            }
        });
    }

    // 获取数据库中的数据
    function roleTable(result) {
        let data = result.data.info.list;
        let content = "";
        $.each(data, function (index, process) {
            content += '<tr>';
            content += '	<td>' + process.id + '</td>';
            content += '	<td>' + process.name + '</td>';
            content += '	<td>' + process.version + '</td>';
            content += '	<td>' + process.key + '</td>';
            content += '	<td>';
            content += '		<button type="button" class="btn btn-success btn-xs" onclick="getPng()"><i class=" glyphicon glyphicon-eye-open"></i></button>';
            content += '		<button type="button" class="btn btn-danger btn-xs" onclick="delProDef(\'' + process.id + '\', \'' + process.name + '\')" ><i class=" glyphicon glyphicon-remove"></i></button>';
            content += '	</td>';
            content += '</tr>';
        });
        $("#process_teable tbody").html(content);
    }

    // 分页功能
    function rolePage(result) {
        page = result.data.info.pageNum;
        // 清空分页条
        $("#page_nav").empty();
        // 构建ul元素
        let ul = $("<ul></ul>").addClass("pagination");
        // 构建首页和上一页元素,并判断当前页码是否是第一页
        if (result.data.info.hasPreviousPage) {
            let firstPageLi = $("<li></li>").append($("<a></a>").html("首页").attr("href", "javascript:;"));
            let prePageLi = $("<li></li>").append($("<a></a>").html("&laquo;").attr("href", "javascript:;"));
            // 为首页添加单击事件
            firstPageLi.click(function () {
                toPage(1);
            });
            // 为上一页添加单击事件
            prePageLi.click(function () {
                toPage(result.data.info.pageNum - 1);
            });
            // 将首页和上一页元素添加到ul元素内
            ul.append(firstPageLi).append(prePageLi);
        }
        // 利用循环构建详细页码元素
        $.each(result.data.info.navigatepageNums, function (index, item) {
            let numLi = $("<li></li>").append($("<a></a>").html(item));
            // 为当前页码添加样式
            if (result.data.info.pageNum == item) {
                numLi.addClass("active");
            }
            // 为页码添加单击时间,使其可以分页
            numLi.click(function () {
                toPage(item);
            });
            // 把页码元素添加到ul容器中
            ul.append(numLi);
        });
        // 构建下一页和末页并判断当前页是否是最后一页
        if (result.data.info.hasNextPage) {
            let nextPageLi = $("<li></li>").append($("<a></a>").html("&raquo;").attr("href", "javascript:;"));
            let lastPageLi = $("<li></li>").append($("<a></a>").html("末页").attr("href", "javascript:;"));
            // 为下一页添加点击事件
            nextPageLi.click(function () {
                toPage(result.data.info.pageNum + 1);
            });
            // 为末页添加点击事件
            lastPageLi.click(function () {
                toPage(result.data.info.pages);
            });
            // 将末页和下一页元素添加到ul容器中
            ul.append(nextPageLi).append(lastPageLi);
        }
        // 将ul容器添加到nav容器中
        $("<nav></nav>").append(ul).appendTo($("#page_nav"));
    }

    // 上传流程定义文件
    $("#upLoadProDefBtn").click(function () {
        $("#ProcessDefFile").click(); //标识直接调用
    });

    // 异步提交流程定义文件
    $("#ProcessDefFile").change(function () {
        let options = {
            url: "${APP_PATH}/process/deploy.do",
            beforeSubmit: function () {
                loadingIndex = layer.msg('数据正在部署中', {icon: 6});
                return true; //必须返回true,否则,请求终止.
            },
            success: function (result) {
                layer.close(loadingIndex);
                if (result.success) {
                    layer.msg("部署成功", {time: 1000, icon: 6});
                    toPage(page);
                } else {
                    layer.msg(result.message, {time: 1000, icon: 5, shift: 6});
                }
            }
        };
        $("#deployForm").ajaxSubmit(options); //异步提交
        return;
    });

    // 删除流程定义
    function delProDef(id, name) {
        layer.confirm("您确定要删除流程定义[" + name + "]吗？", {icon: 3, title: '提示'}, function (cindex) {
            layer.close(cindex);
            $.ajax({
                url: "${APP_PATH}/process/del.do",
                data: {"id": id},
                type: "POST",
                success: function (result) {
                    if (result.success) {
                        toPage(page);
                        layer.msg("删除成功", {time: 1000, icon: 6});
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
</script>
</body>
</html>

