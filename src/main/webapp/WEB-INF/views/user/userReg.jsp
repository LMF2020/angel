<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@	page import="com.angel.my.model.TPurchaserInfo"%>
<!DOCTYPE HTML>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<%@include file="../../views/base.jsp"%>
        <link rel="stylesheet" type="text/css"  href="${pageContext.request.contextPath}/resources/css/userReg.css">
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/userReg.js"></script>
	</head>
	<body  class="easyui-layout">
	    <div data-options="region:'north'" style="height:80px;overflow: hidden;">
            <form id="north_form" class="form-horizontal">
                  <div class="form-group">
                      <label for="purchaserCodeDiv" class="col-sm-1 control-label">会员编号:</label>
                      <div class="col-sm-2">
                          <input type="text" class="form-control" name="purchaserCode" id="purchaserCodeDiv" placeholder="输入会员编号">
                      </div>
                      <label for="shopCodeDiv" class="col-sm-1 control-label" style="margin-left: -20px;">店铺编号:</label>
                      <div class="col-sm-2">
                          <input type="text" class="form-control" name="shopCode" id="shopCodeDiv" placeholder="输入店铺编号">
                      </div>
                      <label for="rankCodeDiv" class="col-sm-1 control-label" style="margin-left: -20px;">选择星级:</label>
                      <div class="col-sm-2">
                             <select class="form-control" name="rankCode" id="rankCodeDiv">
                                 <option value="">不限</option>
                                 <option value="102001">1</option>
                                 <option value="102002">2</option>
                                 <option value="102003">3</option>
                                 <option value="102004">4</option>
                                 <option value="102005">5</option>
                                 <option value="102006">6</option>
                                 <option value="102007">7</option>
                                 <option value="102008">8</option>
                                 <option value="102009">9</option>
                             </select>
                      </div>
                      <div class="col-sm-1">
                          <button type="button" id="beginQuery" class="btn btn-primary">开始查询</button>
                      </div>
                  </div>

            </form>
	    </div>
	    <div data-options="region:'center'">
		    <table id="dg"></table>
		    <div id="toolbar">
		        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" onclick="centerJS.newUser()" plain="true">注册新会员</a>
		        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" onclick="centerJS.editUser()" plain="true">修改会员信息</a>
		        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove"  onclick="centerJS.showGraph()" plain="true">查看会员网络结构</a>
            </div>
		    
		    <div id="dlg" class="easyui-dialog" style="width:400px;height:320px;padding:10px 20px"
		            closed="true" buttons="#dlg-buttons" modal="true">
		        <div class="ftitle">会员信息</div>
		        <form id="fm" method="post" novalidate>
		            <div class="fitem">
		                <label>会员编号:</label>
		                <input name="purchaserCode" class="easyui-validatebox" required="true" validType="puchaserCodeFormat">
		            </div>
		            <div class="fitem">
		                <label>上级会员编号:</label>
		                <input name="sponsorCode" class="easyui-validatebox" required="true" validType="puchaserCodeFormat">
		            </div>
		            <div class="fitem">
		                <label>会员姓名:</label>
		                <input name="purchaserName">
		            </div>
		            <div class="fitem">
		                <label>登陆密码:</label>
		                <input name="purchaserPass" type="password" class="easyui-validatebox">
		            </div>
		            <div class="fitem">
		                <label>店铺编号:</label>
		                <input  class="easyui-combobox"<%-- style="width:130px;"--%>
					           name="shopCode"
					           data-options="
				                    url:'../shopController/getShopList.json',
				                    method:'get',
				                    valueField:'shopCode',
				                    textField:'shopCode',
				                    panelHeight:'auto',
				                    required: true
					     ">
		            </div>
		        </form>
		    </div>

		    <div id="dlg-buttons">
		        <a href="javascript:void(0)" class="easyui-linkbutton"  onclick="centerJS.saveUser()" iconCls="icon-ok">保存</a>
		        <a href="javascript:void(0)" class="easyui-linkbutton"  onclick="javascript:$('#dlg').dialog('close')" iconCls="icon-cancel">关闭</a>
		    </div> 
	   
	    </div>
	  
	</body>
</html>