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

        <div region="center">
            <div id="tabsDiv" class="easyui-tabs" data-options="fit:true,border:false,plain:true" style="height: 100%"/>
        </div>
        <!-- 弹出层 : 主窗口 -->
        <!--==============================================================-->
        <!-- 弹出层 : 会员新增修改对话框   HTML-START-->
        <div class="modal" id="userNetworkModal" aria-describedby="会员网络结构视窗" tabindex="-1" role="dialog"
                aria-labelledby="userNetworkModalLabel" aria-hidden="true">
            <div class="modal-dialog" style="width: 95%;height: 600px;">
                <div class="modal-content">
                    <div class="modal-header" style="padding: 15px 15px 5px;background-color: rgb(153, 143, 105);color: #fff;">
                        <button type="button" class="close dlgClose" ><span class="glyphicon glyphicon-remove"></span></button>
                        <h4 class="modal-title" id="userNetworkModalLabel" style="text-align: center;">当前会员网络</h4>
                    </div>
                    <div class="modal-body" >
                        <div id="myDiagramDiv" style="background-color: white; border: solid 1px black; width: 100%; height: 500px;">

                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!-- 弹出层 : 会员新增修改对话框 HTML-END-->
	</body>
</html>