<%--
  User: 何处是归程
  Time: 10:51
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
    <link rel="stylesheet" href="${APP_PATH}/css/theme.css">
    <style>
        #footer {
            padding: 15px 0;
            background: #fff;
            border-top: 1px solid #ddd;
            text-align: center;
        }
    </style>
</head>
<body>
<div class="navbar-wrapper">
    <div class="container">
        <jsp:include page="/WEB-INF/jsp/common/memberHeade.jsp"/>
    </div>
</div>

<div class="container theme-showcase" role="main">
    <div class="page-header">
        <h1>实名认证 - 申请</h1>
    </div>

    <ul class="nav nav-tabs" role="tablist">
        <li role="presentation"><a href="#"><span class="badge">1</span> 基本信息</a></li>
        <li role="presentation" class="active"><a href="#"><span class="badge">2</span> 资质文件上传</a></li>
        <li role="presentation"><a href="#"><span class="badge">3</span> 邮箱确认</a></li>
        <li role="presentation"><a href="#"><span class="badge">4</span> 申请确认</a></li>
    </ul>

    <form role="form" style="margin-top:20px;" id="uploadCerForm" method="post" enctype="multipart/form-data">
        <c:forEach items="${certList}" var="cert" varStatus="status">
            <div class="form-group">
                <label for="upLoad">${cert.name}</label>
                <input type="hidden" name="certimgs[${status.index}].certid" value="${cert.id}">
                <input type="file" name="certimgs[${status.index}].fileImg" class="form-control" id="upLoad">
                <br>
                <img src="" style="display: none; width: 200px">
            </div>
        </c:forEach>
        <button type="button"  class="btn btn-default">上一步</button>
        <button type="button" id="nextBtn" class="btn btn-success">下一步</button>
    </form>
    <hr>
</div> <!-- /container -->
<div class="container" style="margin-top:20px;">
    <div class="row clearfix">
        <div class="col-md-12 column">
            <div id="footer">
                <div class="footerNav">
                    <a rel="nofollow" href="http://www.atguigu.com">关于我们</a> | <a rel="nofollow"
                                                                                  href="http://www.atguigu.com">服务条款</a>
                    | <a rel="nofollow" href="http://www.atguigu.com">免责声明</a> | <a rel="nofollow"
                                                                                    href="http://www.atguigu.com">网站地图</a>
                    | <a rel="nofollow" href="http://www.atguigu.com">联系我们</a>
                </div>
                <div class="copyRight">
                    Copyright ?2017-2017 atguigu.com 版权所有
                </div>
            </div>

        </div>
    </div>
</div>
<script src="${APP_PATH}/jquery/jquery-2.1.1.min.js"></script>
<script src="${APP_PATH}/bootstrap/js/bootstrap.min.js"></script>
<script src="${APP_PATH}/script/docs.min.js"></script>
<script type="text/javascript" src="${APP_PATH}/jquery/layer/layer.js"></script>
<script src="${APP_PATH }/jquery/jquery-form/jquery-form.min.js"></script>
<script>
    $('#myTab a').click(function (e) {
        e.preventDefault()
        $(this).tab('show')
    });

    // 异步提交表单
    $("#nextBtn").click(function () {
        let options = {
            url: "${APP_PATH}/member/doUploadCert.do",
            beforeSubmit: function () {
                loadingIndex = layer.msg('数据正在保存中', {icon: 6});
                return true; //必须返回true,否则,请求终止.
            },
            success: function (result) {
                console.log(result);
                layer.close(loadingIndex);
                if (result.success) {
                    layer.msg("文件上传成功", {time: 1000, icon: 6});
                    window.location.href = "${APP_PATH}/member/apply.htm";
                } else {
                    layer.msg(result.message, {time: 1000, icon: 5, shift: 6});
                }
            }
        };
        $("#uploadCerForm").ajaxSubmit(options); //异步提交
        return;
    });

    // 预览图片
    $(":file").change(function (event) {
        var files = event.target.files;
        var file;

        if (files && files.length > 0) {
            file = files[0];

            var URL = window.URL || window.webkitURL;
            // 本地图片路径
            var imgURL = URL.createObjectURL(file);

            var imgObj = $(this).next().next(); //获取同辈紧邻的下一个元素
            //console.log(imgObj);
            imgObj.attr("src", imgURL);
            imgObj.show();
        }
    });
</script>
</body>
</html>