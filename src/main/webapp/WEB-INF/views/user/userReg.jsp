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
                        <td class="form_td">Distributor's ID:</td>
                        <td><input name="purchaserCode" type="text"></td>
                        <td class="form_td">Rank:</td>
                        <td>
                            <select class="easyui-combobox" name="rankCode" style="width:131px;">
                                <option value="">no limit</option>
                                <option value="102001">1 star</option>
                                <option value="102002">2 star</option>
                                <option value="102003">3 star</option>
                                <option value="102004">4 star</option>
                                <option value="102005">5 star</option>
                                <option value="102006">6 star</option>
                                <option value="102007">7 star</option>
                                <option value="102008">8 star</option>
                                <option value="102009">9 star</option>
                            </select>
                        </td>
                        <td class="form_td">Shop 's ID:</td>
                        <td><input name="shopCode" type="text"></td>
                        <td class="form_td"><input type="button" value="Query"></td>
                    </tr>
                </table>
            </form>
	    </div>
	    <div data-options="region:'center'">
		    <table id="dg"></table>
		    <div id="toolbar">
		        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" onclick="centerJS.newUser()" plain="true">Register</a>
		        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" onclick="centerJS.editUser()" plain="true">Edit</a>
                <!--删除功能暂时屏蔽-->
		        <%--<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove"  onclick="centerJS.destroyUser()" plain="true">删除会员</a>--%>
            </div>
		    
		    <div id="dlg" class="easyui-dialog" style="width:400px;height:320px;padding:10px 20px"
		            closed="true" buttons="#dlg-buttons" modal="true">
		        <div class="ftitle">User Info</div>
		        <form id="fm" method="post" novalidate>
		            <div class="fitem">
		                <label>Distributor's ID:</label>
		                <input name="purchaserCode" class="easyui-validatebox" required="true" validType="puchaserCodeFormat">
		            </div>
		            <div class="fitem">
		                <label>Sponsor's ID:</label>
		                <input name="sponsorCode" class="easyui-validatebox" required="true" validType="puchaserCodeFormat">
		            </div>
		            <div class="fitem">
		                <label>Distributor's Name:</label>
		                <input name="purchaserName">
		            </div>
		            <div class="fitem">
		                <label>Password:</label>
		                <input name="purchaserPass" type="password" class="easyui-validatebox">
		            </div>
		            <div class="fitem">
		                <label>Shop's Id:</label>
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
		        <a href="javascript:void(0)" class="easyui-linkbutton"  onclick="centerJS.saveUser()" iconCls="icon-ok">Save</a>
		        <a href="javascript:void(0)" class="easyui-linkbutton"  onclick="javascript:$('#dlg').dialog('close')" iconCls="icon-cancel">Close</a>
		    </div> 
	   
	    </div>
	  
	</body>
</html>