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
		<%
			TPurchaserInfo sessionInfo = (TPurchaserInfo) request.getSession().getAttribute("__SESSIONKEY__");
		%>
	    <div data-options="region:'north'" style="height:70px">
            <form id="north_form">
                <table>
                    <tr>
                        <td class="form_td">编号:</td>
                        <td><input name="purchaserCode" type="text"></td>
                        <td class="form_td">等级:</td>
                        <td>
                            <select class="easyui-combobox" name="rankCode" style="width:131px;">
                                <option value="102001">一星</option>
                                <option value="102002">二星</option>
                                <option value="102003">三星</option>
                                <option value="102004">四星</option>
                                <option value="102005">五星</option>
                                <option value="102006">六星</option>
                                <option value="102007">七星</option>
                                <option value="102008">八星</option>
                                <option value="102009">九星</option>
                            </select>
                        </td>
                        <td class="form_td">商店:</td>
                        <td><input name="shopCode" type="text"></td>
                        <td class="form_td"><input type="button" value="查询"></td>
                    </tr>
                </table>
            </form>
	    </div>
	    <div data-options="region:'center'">
		    <table id="dg"></table>
		    <div id="toolbar">
		        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" onclick="centerJS.newUser()" plain="true">注册会员</a>
		        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" onclick="centerJS.editUser()" plain="true">编辑会员</a>
                <!--删除功能暂时屏蔽-->
		        <%--<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove"  onclick="centerJS.destroyUser()" plain="true">删除会员</a>--%>
            </div>
		    
		    <div id="dlg" class="easyui-dialog" style="width:400px;height:320px;padding:10px 20px"
		            closed="true" buttons="#dlg-buttons" modal="true">
		        <div class="ftitle">会员资料</div>
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
		                <label>所属店铺:</label>
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