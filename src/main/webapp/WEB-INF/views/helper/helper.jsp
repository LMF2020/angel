<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <%@include file="../../views/base.jsp"%>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/helper.js"></script>
</head>
<body  class="easyui-layout">
<div region="north" style="height:120px;overflow: hidden;">
    <form id="north_form" class="form-horizontal" style="margin-top: 20px;margin-left: 2%;">
            <div style="margin-bottom: 15px;">
                <label class="radio-inline">
                    <input type="radio" name="starRadio" value="102003" checked>
                    显示<span class="text-danger">三星级</span>新会员
                </label>
                <label class="radio-inline">
                    <input type="radio" name="starRadio" value="102004">
                    显示<span class="text-danger">四星级</span>会员
                </label>
                <label class="radio-inline">
                    <input type="radio" name="starRadio" value="102005">
                    显示<span class="text-danger">五星级</span>会员
                </label>
                <label class="radio-inline">
                    <input type="radio" name="starRadio" value="102006">
                    显示<span class="text-danger">六星级</span>会员
                </label>
                <label class="radio-inline">
                    <input type="radio" name="starRadio" value="102007">
                    显示<span class="text-danger">七星级</span>会员
                </label>
                <label class="radio-inline">
                    <input type="radio" name="starRadio" value="102008">
                    显示<span class="text-danger">八星级</span>会员
                </label>
                <label class="radio-inline">
                    <input type="radio" name="starRadio" value="102009">
                    显示<span class="text-danger">九星级</span>会员
                </label>
            </div>
            <button type="submit" id="export" class="btn btn-info btn-sm"  style="margin-right: 10px;">
                <span class="glyphicon glyphicon-export"></span> 导出文件
            </button>
            <strong class="alert alert-info" id="dateAreadiv">
                友情提示：当前核算月为<span class="text-danger">{year}</span>年<span class="text-danger">{month}</span>月
            </strong>
    </form>
</div>
<div region="center">
    <table id="dg"></table>
</div>

</body>
</html>