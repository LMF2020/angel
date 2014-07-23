<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <%@include file="../../views/base.jsp"%>
    <link rel="stylesheet" type="text/css"  href="${pageContext.request.contextPath}/resources/css/order.css">
    <style>
        #network-center .l-btn-left {
            border: 1px solid;
        }
    </style>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/framework/date.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/net.js"></script>
</head>
<body class="easyui-layout">
<div data-options="region:'north'" style="height:120px;overflow: hidden;">
    <div class='container'>
        <form id="north_form" class="form-horizontal" role="form" style="padding-top: 20px;">
            <div class="form-group">
                <label for="purchaserCodeDiv" class="col-sm-2 control-label">查询会员业绩:</label>
                <div class="col-sm-4">
                    <input type="text" class="form-control" name="purchaserCode" id="purchaserCodeDiv" placeholder="请输入待查询的会员编号,如：010000">
                </div>
                <div class="col-sm-6">
                    <button id="b_query" class="btn btn-danger btn-small">查询</button>
                    <button id="b_export_network" class="btn btn-info btn-small">导出当前会员网络</button>
                    <button id="b_export_all_network" class="btn btn-info btn-small">导出所有会员网络</button>
                </div>
            </div>
            <div class="form-group">
                <label for="shopCodeDiv" class="col-sm-2 control-label">查询店铺奖金:</label>
                <div class="col-sm-4">
                    <input type="text" class="form-control" id="shopCodeDiv" name="shopCode"  placeholder="请输入待导出的店铺编号,如：CG982000">
                </div>
                <div class="col-sm-6">
                    <button id="b_export_bonus" class="btn btn-info btn-small">导出当前店铺奖金</button>
                    <button id="b_export_all_bouns" class="btn btn-info btn-small">导出所有店铺奖金</button>
                </div>
            </div>
        </form>
    </div>
</div>
<div data-options="region:'center'" id="network-center">
    <table id="dg"></table>
</div>
<!--系统计算对话框提示：选择结算的日期区间-->
<div id="dlgCount" class="easyui-dialog" title="系统提示"
     style="width:400px;height:200px;padding:10px;"
     closed="true" buttons="#dlgCount-buttons" modal="true" data-options="top:'100'">

    <h5 class="text-primary">请选择结算日期区间</h5>
    <div style="margin:20px 0;"></div>
    <div class="text-center">结算-<strong>开始</strong>-日期: <input id="startDate" class="easyui-datetimebox" required style="width:200px"></div>
    <div style="margin:7px 0;"></div>
    <div class="text-center">结算-<strong>结束</strong>-日期: <input id="lastDate"  class="easyui-datetimebox" required style="width:200px"></div>
</div>
<div id="dlgCount-buttons">
    <a href="javascript:void(0)" class="easyui-linkbutton" onclick="netJS.startCount()">提交</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" onclick="javascript:$('#dlgCount').dialog('close')">取消</a>
</div>
</body>
</html>