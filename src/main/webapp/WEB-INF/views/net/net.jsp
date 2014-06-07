<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <%@include file="../../views/base.jsp"%>
    <link rel="stylesheet" type="text/css"  href="${pageContext.request.contextPath}/resources/css/order.css">
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/net.js"></script>
</head>
<body class="easyui-layout">
<div data-options="region:'north'" style="height:80px;overflow: hidden;">
    <div class='container'>
        <form id="north_form" class="form-inline" role="form" style="padding-top: 20px;">
            <div class="form-group">
                <label class="sr-only" for="purchaserCode">会员编号</label>
                <input type="text" required="required" pattern="\d{6}" class="form-control" id="purchaserCode" name="purchaserCode" placeholder="请输入会员查询编号">
            </div>
            <button id="querybtn" class="btn btn-primary btn-small" style="margin-right: 20px;">查询</button>
            <button id="calcbtn" class="btn btn-success btn-small">开始计算当月网络</button>
        </form>
    </div>
</div>
<div data-options="region:'center'">
    <table id="dg"></table>
    <div id="toolbar">
        测试测试
    </div>
</div>
</body>
</html>