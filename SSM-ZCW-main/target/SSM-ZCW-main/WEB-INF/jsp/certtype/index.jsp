<%--
  User: 何处是归程
  Time: 11:11
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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

        input[type=checkbox] {
            width: 18px;
            height: 18px;
        }
    </style>
    <title></title>
</head>

<body>
<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
    <div class="container-fluid">
        <div class="navbar-header">
            <div><a class="navbar-brand" style="font-size:32px;" href="#">众筹平台 - 分类管理</a></div>
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
                    <h3 class="panel-title"><i class="glyphicon glyphicon-th"></i> 数据矩阵</h3>
                </div>
                <div class="panel-body">
                    <div class="table-responsive">
                        <table class="table  table-bordered">
                            <thead>
                            <tr>
                                <th>名称</th>
                                <th>商业公司</th>
                                <th>个体工商户</th>
                                <th>个人经营</th>
                                <th>政府及非营利组织</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${queryAllCert}" var="cert">
                                <tr>
                                    <td>${cert.name}</td>
                                    <td><input type="checkbox" certid="${cert.id}" accttype="0"></td>
                                    <td><input type="checkbox" certid="${cert.id}" accttype="1"></td>
                                    <td><input type="checkbox" certid="${cert.id}" accttype="2"></td>
                                    <td><input type="checkbox" certid="${cert.id}" accttype="3"></td>
                                </tr>
                            </c:forEach>
                            </tbody>
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
        /*回显*/
        <c:forEach items="${certaccttype}" var="cert">
        $(":checkbox[certid='${cert.certid }'][accttype='${cert.accttype}']")[0].checked = true;
        </c:forEach>
    });

    $(":checkbox").click(function () {
        var flg = this.checked;
        //通过this.certid能否获取自定义的属性值?
        var certid = $(this).attr("certid");
        var accttype = $(this).attr("accttype");

        if (flg) {
            // 增加账户类型和资质的关系
            $.ajax({
                type: "POST",
                url: "${APP_PATH}/certtype/insertAcctTypeCert.do",
                data: {
                    certid: certid,
                    accttype: accttype
                },
                success: function (result) {
                    if (result.success) {
                        layer.msg("添加账户类型和资质关系成功", {time: 1000, icon: 6});
                    } else {
                        layer.msg(result.message, {time: 1000, icon: 5, shift: 6});
                    }
                }
            });

        } else {
            // 删除账户类型和资质的关系
            $.ajax({
                type: "POST",
                url: "${APP_PATH}/certtype/deleteAcctTypeCert.do",
                data: {
                    certid: certid,
                    accttype: accttype
                },
                success: function (result) {
                    if (result.success) {
                        layer.msg("删除账户类型和资质关系成功", {time: 1000, icon: 6});
                    } else {
                        layer.msg(result.message, {time: 1000, icon: 5, shift: 6});
                    }
                }
            });
        }
    });
</script>
</body>
< /html>
