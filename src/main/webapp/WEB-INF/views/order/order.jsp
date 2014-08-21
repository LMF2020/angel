<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <%@include file="../../views/base.jsp"%>
    <link rel="stylesheet" type="text/css"  href="${pageContext.request.contextPath}/resources/css/order.css">
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/order.js"></script>
</head>
<body class="easyui-layout">
    <div data-options="region:'north'" style="height:150px;overflow: hidden;">
        <div class='container'>
            <form id="north_form" class="form-horizontal" style="padding-top: 10px;">
                <div class="form-group">
                    <label for="purchaserCodeX" class="col-sm-1 control-label">会员编号</label>
                    <div class="col-md-2">
                        <input class="form-control" type="text" id="purchaserCodeX"  name="purchaserCode"/>
                    </div>
                    <label for="productCodeX" class="col-sm-1 control-label">产品编号</label>
                    <div class="col-md-2">
                        <input class="form-control" type="text" id="productCodeX"  name="productCode"/>
                    </div>
                    <label for="startTime" class="col-sm-1 control-label">开始时间</label>
                    <div class="col-md-2">
                        <input class="form-control" type="date" id="startTime"  name="startTime" />
                    </div>
                    <label for="endTime" class="col-sm-1 control-label">结束时间</label>
                    <div class="col-md-2">
                        <input class="form-control" type="date" id="endTime"  name="endTime" />
                    </div>
                </div>
                <div class="form-group">
                    <label for="purchaserNameX" class="col-sm-1 control-label">会员名称</label>
                    <div class="col-md-2">
                        <input class="form-control" type="text" id="purchaserNameX"  name="purchaserName"/>
                    </div>
                    <label for="productNameX" class="col-sm-1 control-label">产品名称</label>
                    <div class="col-md-2">
                        <input class="form-control" type="text" id="productNameX"  name="productName"/>
                    </div>
                    <label for="shopCodeX" class="col-sm-1 control-label">商店编号</label>
                    <div class="col-md-2">
                        <input class="form-control" type="text" id="shopCodeX"  name="shopCode" />
                    </div>
                    <label for="shopNameX" class="col-sm-1 control-label">商店名称</label>
                    <div class="col-md-2">
                        <input class="form-control" type="text" id="shopNameX"  name="shopName"/>
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-sm-offset-4 col-sm-4">
                        <button id="querybtn" class="btn btn-primary btn-small" style="margin-right: 20px;">查询</button>
                        <button type="reset" class="btn btn-warning btn-small" style="margin-right: 20px;">清空</button>
                        <button id="sumbtn"   class="btn btn-info btn-small">月销售额汇总</button>
                    </div>
                </div>
            </form>
        </div>
    </div>
    <div data-options="region:'center'">
        <table id="dg"></table>
        <div id="toolbar">
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" onclick="centerJS.newOrder()" plain="true">新增订单</a>
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" onclick="centerJS.editOrder()" plain="true">修改订单</a>
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove"  onclick="centerJS.destroyOrder()" plain="true">删除订单</a>
        </div>

        <!--新增修改-->
        <div id="dlg" class="easyui-dialog" style="width:800px;height:350px;padding:10px 20px"
             closed="true" buttons="#dlg-buttons" modal="true">
            <form id="fm" method="post" novalidate class="form-horizontal" role="form">
                <!--会员-->
                <div class="form-group">
                    <label for="purchaserCode" class="col-sm-2 control-label">会员编号</label>
                    <div class="col-md-4">
                        <input class="form-control easyui-combogrid"  style="width: 230px;" id="purchaserCode"  name="purchaserCode" data-options="

                        pageSize: 20,
                        pagination:'true',
                        rownumbers:'true',
                        fitColumns:'true' ,
                        fit:'true' ,
                        sortName: 'purchaserCode',
                        sortOrder:'asc',
                        url:'../userController/pageUserList.json',
                        columns:[[
                            {field:'purchaserCode',title:'会员编号',width:50},
                            {field:'purchaserName',title:'会员姓名',width:50},
                            {field:'sponsorCode',title:'上级会员编号',width:50},
                            {field:'shopCode',title:'商店编码',width:50,hidden:true},
                            {field:'shopName',title:'商店名称',width:50}
                        ]],

                        delay: 500,
                        mode: 'remote',
                        panelWidth:450,
                        idField:'purchaserCode',
                        textField:'purchaserCode',

                        onSelect:function(rowIndex, rowData){
                                $('#purchaserName').val(rowData['purchaserName']);
                                $('#shopCode').val(rowData['shopCode']);
                                $('#shopName').val(rowData['shopName']);
                        }

                        "/>
                    </div>
                    <label for="purchaserName" class="col-sm-2 control-label">会员名称</label>
                    <div class="col-md-4">
                        <input class="form-control" type="text" id="purchaserName"  name="purchaserName" readonly="readonly"/>
                    </div>
                </div>
                <!--商店(来源自会员信息)-->
                <div class="form-group">
                    <label for="shopCode" class="col-sm-2 control-label">商店编号</label>
                    <div class="col-md-4">
                        <input class="form-control" type="text" id="shopCode"  name="shopCode" readonly="readonly"/>
                    </div>
                    <label for="shopName" class="col-sm-2 control-label">商店名称</label>
                    <div class="col-md-4">
                        <input class="form-control" type="text" id="shopName"  name="shopName" readonly="readonly"/>
                    </div>
                </div>
                <!--产品-->
                <div class="form-group">
                    <label for="productCode" class="col-sm-2 control-label">产品编号</label>
                    <div class="col-md-4">
                        <input class="form-control easyui-combogrid" id="productCode" style="width: 230px;" name="productCode"
                               data-options="

                                url:'../productController/pageProductList.json',
                                pagination:'true',
                                rownumbers:'true',
                                fitColumns:'true' ,
                                fit:'true' ,
                                pageSize: 20,
                                columns:[[{field:'ck',checkbox:true},
                                    {field:'productCode',title:'产品编号',width:50},
                                    {field:'productName',title:'产品名称',width:50},
                                    {field:'productPrice',title:'产品价格',width:50},
                                    {field:'productPv',title:'PV',width:50},
                                    {field:'productBv',title:'BV',width:50}
                                ]],
                                delay: 500,
                                mode: 'remote',
                                panelWidth:450,
                                idField:'productCode',
                                textField:'productCode',

                                onSelect:function(rowIndex, rowData){
                                    $('#productName').val(rowData['productName']);
                                    $('#productPrice').val(rowData['productPrice']);
                                    $('#pv').val(rowData['productPv']);
                                    $('#bv').val(rowData['productBv']);

                                    $('#saleNumber').val('1');
                                    $('#sumPrice').val(rowData['productPrice']);
                                }
                            "
                        />
                    </div>
                    <label for="productName" class="col-sm-2 control-label">产品名称</label>
                    <div class="col-md-4">
                        <input class="form-control" type="text" id="productName"  name="productName" readonly="readonly"/>
                    </div>
                </div>
                <!--价格/BV/PV-->
                <div class="form-group">
                    <label for="productPrice" class="col-sm-2 control-label">产品单价</label>
                    <div class="col-md-2">
                        <input class="form-control" type="text" id="productPrice"  name="productPrice" <%--readonly="readonly"--%>/>
                    </div>
                    <!--以下的两个total是展现给用户看的，不需要传送到后台
                    <label for="pv_totalDiv" class="col-sm-2 control-label">PV</label>
                    <div class="col-md-2">
                        <input class="form-control" type="text" id="pv_totalDiv"  name="pv_total" readonly="readonly"/>
                    </div>
                    <label for="bv_totalDiv" class="col-sm-2 control-label">BV</label>
                    <div class="col-md-2">
                        <input class="form-control" type="text" id="bv_totalDiv"  name="bv_total" readonly="readonly"/>
                    </div>-->
                    <!--以下两个隐藏域才是真正的单位PV/BV，需要发送到后台
                    <input type="hidden" id="pv" name="pv">
                    <input type="hidden" id="bv" name="bv">
                    -->
                    <label for="pv" class="col-sm-2 control-label">PV</label>
                    <div class="col-md-2">
                        <input class="form-control" type="text" id="pv" name="pv">
                    </div>
                    <label for="bv" class="col-sm-2 control-label">BV</label>
                    <div class="col-md-2">
                        <input class="form-control" type="text" id="bv" name="bv">
                    </div>
                </div>
                <!--销量/book/统计-->
                <div class="form-group">
                    <label for="saleNumber" class="col-sm-2 control-label">购买数量</label>
                    <div class="col-md-2">
                        <input class="form-control" type="text" id="saleNumber" value="1" name="saleNumber"/>
                    </div>
                    <label for="book" class="col-sm-2 control-label">Book</label>
                    <div class="col-md-2">
                        <input class="form-control" type="text" id="book"  name="book"/>
                    </div>
                    <label for="sumPrice" class="col-sm-2 control-label">合计</label>
                    <div class="col-md-2">
                        <input class="form-control" type="text" id="sumPrice"  name="sumPrice" readonly="readonly"/>
                    </div>
                </div>
                <!--订单编号-->
                <input type="hidden" name="orderCode">
            </form>
        </div>
        <div id="dlg-buttons">
            <a href="javascript:void(0)" class="easyui-linkbutton"  onclick="centerJS.saveOrder()" iconCls="icon-ok">保存</a>
            <a href="javascript:void(0)" class="easyui-linkbutton"  onclick="javascript:$('#dlg').dialog('close')" iconCls="icon-cancel">关闭</a>
        </div>
    </div>
</body>
</html>