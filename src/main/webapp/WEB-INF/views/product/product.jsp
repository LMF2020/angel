<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <%@include file="../../views/base.jsp"%>
    <link rel="stylesheet" type="text/css"  href="${pageContext.request.contextPath}/resources/css/product.css">
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/product.js"></script>
</head>
<body  class="easyui-layout">
        <div data-options="region:'north'" style="height:80px;overflow: hidden;">
            <form id="north_form" class="form-horizontal">
               <div class="form-group">
                   <label for="productCodeDiv" class="col-sm-1 control-label">会员编号:</label>
                   <div class="col-sm-2">
                       <input type="text" class="form-control" name="productCode" id="productCodeDiv" placeholder="输入会员编号">
                   </div>
                   <label for="productNameDiv" class="col-sm-1 control-label" style="margin-left: -20px;">会员名称:</label>
                   <div class="col-sm-2">
                       <input type="text" class="form-control" name="productName" id="productNameDiv" placeholder="输入会员名称">
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
                <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" onclick="centerJS.newProduct()" plain="true">新增产品</a>
                <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" onclick="centerJS.editProduct()" plain="true">修改产品</a>
                <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove"  onclick="centerJS.destroyProduct()" plain="true">移除产品</a>
            </div>

            <!---新增修改-->
            <div id="dlg" class="easyui-dialog" style="width:400px;height:320px;padding:10px 20px"
                 closed="true" buttons="#dlg-buttons" modal="true">
                <div class="ftitle">产品货架</div>
                <form id="fm" method="post" novalidate>
                    <div class="fitem">
                        <label>产品编号:</label>
                        <input name="productCode" class="easyui-validatebox" required="true">
                    </div>
                    <div class="fitem">
                        <label>产品名称:</label>
                        <input name="productName" class="easyui-validatebox" required="true">
                    </div>
                    <div class="fitem">
                        <label>产品价格:</label>
                        <input name="productPrice" class="easyui-validatebox" required="true" validType="moneyFormat">
                    </div>
                    <div class="fitem">
                        <label>PV:</label>
                        <input name="productPv" class="easyui-validatebox" required="true" validType="moneyFormat">
                    </div>
                    <div class="fitem">
                        <label>BV:</label>
                        <input name="productBv" class="easyui-validatebox" required="true" validType="moneyFormat">
                    </div>
                    <div class="fitem">
                        <label>在售状态:</label>
                        <select class="easyui-combobox" name="status" style="width:131px;" class="easyui-validatebox" required="true" >
                            <option value="1" selected>在售</option>
                            <option value="0">下架</option>
                        </select>
                    </div>
                </form>
            </div>
            <div id="dlg-buttons">
                <a href="javascript:void(0)" class="easyui-linkbutton"  onclick="centerJS.saveProduct()" iconCls="icon-ok">保存</a>
                <a href="javascript:void(0)" class="easyui-linkbutton"  onclick="javascript:$('#dlg').dialog('close')" iconCls="icon-cancel">关闭</a>
            </div>
            <!--新增修改 !end-->
        </div>
</body>
</html>