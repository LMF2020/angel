<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@	page import="com.angel.my.model.TPurchaserInfo"%>
<!DOCTYPE HTML>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>Angel | Marketing</title>
		<%@include file="../views/base.jsp"%>
        <script src="${pageContext.request.contextPath}/resources/framework/go.js"></script>
		<script src="${pageContext.request.contextPath}/resources/js/home.js"></script>
        <script src="${pageContext.request.contextPath}/resources/framework/ImgManager.js"></script>
	</head>
	<body class="easyui-layout">
		<%
			TPurchaserInfo sessionInfo = (TPurchaserInfo) request.getSession().getAttribute("__SESSIONKEY__");
		%>
		
        <div data-options="region:'north'" style="height:60px;overflow-y: hidden;">
            <nav class="navbar navbar-inverse" role="navigation">
                <div class="navbar-header">
                    <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#bs-example-navbar-collapse-9">
                        <span class="sr-only">导航按钮切换</span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </button>
                    <a class="navbar-brand" href="javascript:void(0);">angel</a>
                </div>

                <!-- Collect the nav links, forms, and other content for toggling -->
                <div id="header" class="collapse navbar-collapse" id="bs-example-navbar-collapse-9">
                    <ul class="nav navbar-nav">
                        <li class="active"><a href="../userController/userReg">会员管理</a></li>
                        <%--<li><a href="javascript:void(0);">店铺管理</a></li>--%>
                        <li><a href="../productController/product">产品货架</a></li>
                        <li><a href="../orderController/order">会员订单管理</a></li>
                        <li><a href="../busiController/net">会员业绩结算</a></li>
                        <li><a href="../helperController/helper">结算月星级查询</a></li>
                        <li><a href="../helperController/history">历史结算月查询</a></li>
                    </ul>
                </div><!-- /.navbar-collapse -->
            </nav>
        </div>

        <div region="center">
            <div id="tabsDiv" class="easyui-tabs" data-options="fit:true,border:false,plain:true" style="height: 100%"/>
        </div>
        <!-- 弹出层 : 主窗口 -->
        <!--==============================================================-->
        <!-- 弹出层 : 会员新增修改对话框   HTML-START-->
        <div class="modal" id="userNetworkModal" aria-describedby="会员网络结构视窗" tabindex="-1" role="dialog"
                aria-labelledby="userNetworkModalLabel" aria-hidden="true" style="overflow-y:hidden;">
            <div class="modal-dialog" style="width: 100%;height: 100%;">
                <div class="modal-content" style="height: 100%;">
                    <div class="modal-header" style="padding: 8px 15px 8px;
                    background: url('../resources/images/angel.png') no-repeat 35% #f5f5f5;
                    background-size: contain; color: #000;">
                        <button type="button" class="btn btn-primary btn-sm pull-left" id="exportImgDiv">
                            <span class="glyphicon glyphicon-export"></span> 导出
                        </button>

                        <button type="button" class="close dlgClose" style="opacity: 1;"><span class="glyphicon glyphicon-remove"></span></button>
                        <h4 class="modal-title" style="text-align: center;">
                            <i id="userNetworkModalLabel" style="font-style: normal;">当前会员网络</i>
                        </h4>

                    </div>
                    <div class="modal-body" style="padding: 2px;height:100%;">
                        <div id="myDiagramDiv" style="background-color: white; border: solid 1px black; width: 100%; height: 100%;">

                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!-- 弹出层 : 会员新增修改对话框 HTML-END-->
	</body>
</html>