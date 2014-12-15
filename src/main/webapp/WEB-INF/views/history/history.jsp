<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <%@include file="../../views/base.jsp"%>
        <style type="text/css">
            #north_form {
                margin: 30px 75px;
            }
            #north_form .col-sm-2 {
                width: inherit;
            }
        </style>
        <script src="${pageContext.request.contextPath}/resources/framework/My97DatePicker/WdatePicker.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/history.js"></script>
	</head>
	<body  class="easyui-layout">
	    <div region="north" style="overflow: hidden;">
            <form id="north_form" class="form-horizontal">
                  <div class="form-group">
                      <label for="purchaserCode" class="col-sm-2 control-label">输入会员编号:</label>
                      <div class="col-sm-2">
                          <input type="text" class="form-control" name="purchaserCode" id="purchaserCode" placeholder="输入会员编号">
                      </div>
                      <label for="achieveDate" class="col-sm-2 control-label">选择年月:</label>
                      <div class="col-sm-2">
                          <input id="achieveDate" name="achieveDate"
                                 onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM'})" class="form-control Wdate" style="height:34px" />
                      </div>
                      <div class="col-sm-2">
                          <button type="button" id="beginQuery" class="btn btn-primary">查询</button>
                      </div>
                      <div class="col-sm-2">
                          <button type="reset" class="btn btn-warning btn-small" style="margin-right: 20px;">清除输入框</button>
                      </div>
                  </div>
            </form>
	    </div>
	    <div region="center">
		    <table id="dg"></table>
	    </div>
	</body>
</html>