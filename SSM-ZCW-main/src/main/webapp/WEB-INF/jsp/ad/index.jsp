<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%session.setAttribute("APP_PATH", request.getPathInfo());%>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    <title>广告首页</title>
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
</head>

<body>

<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
    <div class="container-fluid">
        <div class="navbar-header">
            <div><a class="navbar-brand" style="font-size:32px;" href="#">众筹平台 - 广告管理</a></div>
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
                                <input class="form-control has-success" id="queryInput" type="text"
                                       placeholder="">
                            </div>
                        </div>
                        <button type="button" class="btn btn-warning" id="queryBtn"><i
                                class="glyphicon glyphicon-search"></i> 查询
                        </button>
                    </form>
                    <button type="button" class="btn btn-danger" style="float:right;margin-left:10px;" id="delPoleAll">
                        <i class=" glyphicon glyphicon-remove"></i> 删除
                    </button>
                    <button type="button" class="btn btn-primary" style="float:right;"
                            onclick="window.location.href='${APP_PATH}/ad/toAdd.do'">
                        <i class="glyphicon glyphicon-plus"></i> 新增
                    </button>
                    <br>
                    <hr style="clear:both;">
                    <div class="table-responsive">
                        <table class="table  table-bordered" id="ad_teable">
                            <thead>
                            <tr>
                                <th width="30"><input type="checkbox" id="checkAll"></th>
                                <th>编号</th>
                                <th>广告名称</th>
                                <th>审核状态</th>
                                <th>创建人</th>
                                <th width="100">操作</th>
                            </tr>
                            </thead>
                            <tbody>
                            </tbody>
                            <tfoot>
                            <tr>
                                <td colspan="6" align="center" id="page_nav"></td>
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
<script src="${APP_PATH}/script/menu.js"></script>
<script type="text/javascript" src="${APP_PATH}/jquery/layer/layer.js"></script>
<script type="text/javascript">
    let page;
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
        // 跳转首页
        toPage(1);
    });

    // 跳转页面
    function toPage(begin) {
        $.ajax({
            url: "${APP_PATH}/ad/index.do",
            data: {"begin": begin},
            type: "POST",
            success: function (result) {
                if (result.success) {
                    adTable(result);
                    adPage(result);
                } else {
                    layer.msg(result.message, {time: 1000, icon: 5, shift: 6});
                }
            }
        });
    }

    // 获取数据库中的数据
    function adTable(result) {
        let data = result.data.info.list;
        let content = "";
        $.each(data, function (index, ad) {
            content += '<tr>';
            content += '	<td><input type="checkbox" class="checkDel" index=' + ad.id + '></td>';
            content += '	<td>' + ad.id + '</td>';
            content += '	<td>' + ad.name + '</td>';
            content += '	<td>' + (ad.status > 2 ? "已审核" : "未审核") + '</td>';
            content += '	<td>' + ad.userid + '</td>';
            content += '	<td>';
            content += '		<button type="button" class="btn btn-success btn-xs"><i class=" glyphicon glyphicon-check"></i></button>';
            content += '		<button type="button" class="btn btn-primary btn-xs editUserBtn" onclick="toUpdate(' + ad.id + ')"><i class=" glyphicon glyphicon-pencil"></i></button>';
            content += '		<button type="button" class="btn btn-danger btn-xs delUserBtn" onclick="del(' + ad.id + ',\'' + ad.name + '\')" ><i class=" glyphicon glyphicon-remove"></i></button>';
            content += '	</td>';
            content += '</tr>';
        });
        $("#ad_teable tbody").html(content);
    }

    // 分页功能
    function adPage(result) {
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


    // 删除单个广告
    function del(id, name) {
        layer.confirm("您确定要删除广告[" + name + "]吗？", {icon: 3, title: '提示'}, function (cindex) {
            layer.close(cindex);
            $.ajax({
                url: "${APP_PATH}/ad/del.do",
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

    // 完成全选全不全功能
    $("#checkAll").click(function () {
        // attr获取checked是undefined
        $(".checkDel").prop("checked", $(this).prop("checked"));
    });
    $(document).on("click", ".checkDel", function () {
        // 判断当前选中的元素是否是8个
        if ($(".checkDel:checked").length === $(".checkDel").length) {
            $("#checkAll").prop("checked", true);
        } else {
            $("#checkAll").prop("checked", false);
        }
    });

    // 批量删除
    $("#delPoleAll").click(function () {
        let roles = $(".checkDel:checked");
        if (roles === null || roles.length === 0) {
            layer.msg("请选择要删除的广告", {time: 1000, icon: 5, shift: 6});
            return false;
        }
        layer.confirm("您确定要删除这些广告吗？", {icon: 3, title: '提示'}, function (cindex) {
            layer.close(cindex);
            let jsonObj = {};
            $.each(roles, function (inedx, ad) {
                jsonObj["ids[" + inedx + "]"] = $(ad).attr("index");
            });
            $.ajax({
                url: "${APP_PATH}/ad/delBatchAd.do",
                data: jsonObj,
                type: "POST",
                success: function (result) {
                    if (result.success) {
                        toPage(page);
                        layer.msg("删除成功", {time: 1000, icon: 6});
                    } else {
                        alert(jsonObj);
                        layer.msg(result.message, {time: 1000, icon: 5, shift: 6});
                    }
                }
            });
        }, function (cindex) {
            layer.close(cindex);
            return false;
        });
    });

    // 跳往修改页面
    function toUpdate(id) {
        window.location.href = "${APP_PATH}/ad/toUpdate.htm?id=" + id;
    }

</script>
<script src="${APP_PATH}/script/docs.min.js"></script>
</body>
</html>
