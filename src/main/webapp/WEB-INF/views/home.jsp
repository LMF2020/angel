<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@	page import="com.angel.my.model.TPurchaserInfo"%>
<!DOCTYPE HTML>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>Angel | Marketing</title>
		<%@include file="../views/base.jsp"%>
		<script src="${pageContext.request.contextPath}/resources/js/home.js"></script>
	</head>
	<body class="easyui-layout">
		<%
			TPurchaserInfo sessionInfo = (TPurchaserInfo) request.getSession().getAttribute("__SESSIONKEY__");
		%>
		
        <div data-options="region:'north'" style="height:60px;overflow-y: hidden;">
            <nav class="navbar navbar-inverse" role="navigation">
                <!-- Brand and toggle get grouped for better mobile display -->
                <div class="navbar-header">
                    <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#bs-example-navbar-collapse-9">
                        <span class="sr-only">Toggle navigation</span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </button>
                    <a class="navbar-brand" href="javascript:void(0);">Angel</a>
                </div>

                <!-- Collect the nav links, forms, and other content for toggling -->
                <div id="header" class="collapse navbar-collapse" id="bs-example-navbar-collapse-9">
                    <ul class="nav navbar-nav">
                        <li class="active"><a href="../userController/userReg">会员登记</a></li>
                        <%--<li><a href="javascript:void(0);">店铺管理</a></li>--%>
                        <li><a href="../productController/product">产品货架</a></li>
                        <li><a href="../orderController/order">订单处理</a></li>
                        <li><a href="../busiController/net">网络计算</a></li>
                    </ul>
                </div><!-- /.navbar-collapse -->
            </nav>
        </div>

        <div data-options="region:'center',iconCls:'icon-ok'">
            <div id="tabsDiv" class="easyui-tabs" data-options="fit:true,border:false,plain:true"/>
        </div>
		
	</body>
</html>