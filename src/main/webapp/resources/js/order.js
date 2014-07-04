/**
 * 订单管理
 */
var centerJS = (function () {
    var url;
    return {
        //添加订单
        newOrder: function () {
            $('#dlg').dialog('open').dialog('setTitle', '新增订单');
            $('#fm').form('clear');
            //$('#fm input[name="productCode"]').attr('readonly',false);
            url = '../orderController/addOrder.json';
        },
        //编辑订单
        editOrder: function () {
            var rows = $('#dg').datagrid('getSelections');
            if (rows.length > 1) {
                $.messager.show({
                    title: '提示',
                    msg: "更新时只能选中一条记录!"
                });
                return;
            }
            var row = rows[0];
            if (row) {
                $('#dlg').dialog('open').dialog('setTitle', '修改订单');
                $('#fm').form('load', row);
                //$('#fm input[name="productCode"]').attr('readonly',true).css('color','red');
                url = '../orderController/updateOrder.json';
            }
        },
        //删除订单
        destroyOrder: function () {
            var rows = $('#dg').datagrid('getSelections');
            if (rows.length > 0) {
                var codes = [];
                for (var i in rows) {
                    codes.push(rows[i].orderCode);
                }
                $.messager.confirm('提示', '您确定要删除当前选中的产品吗?', function (r) {
                    if (r) {
                        $.ajax({
                            url: '../orderController/destroyOrder.json',
                            dataType: "json",
                            type: "POST",
                            data: {
                                codes: codes
                            },
                            traditional: true, //浅序列化数组
                            success: function (result) {
                                if (result.message) {
                                    $.messager.show({
                                        title: '提示',
                                        msg: result.message
                                    });
                                } else {
                                    $.messager.show({
                                        title: '提示',
                                        msg: "删除成功!"
                                    });
                                    //刷新列表
                                    $('#dg').datagrid('reload');
                                }
                            }
                        });
                    }
                });
            }
        },
        saveOrder: function () {
            $('#fm').form('submit', {
                url: url,
                onSubmit: function () {
                    return $(this).form('validate');
                },
                success: function (re) {
                    var result = $.parseJSON(re);
                    if (result.message) {
                        $.messager.show({
                            title: '提示',
                            msg: result.message
                        });
                    } else {
                        $('#dlg').dialog('close');
                        $('#dg').datagrid('reload');
                        $.messager.show({
                            title: '提示',
                            msg: "保存成功!"
                        });
                    }
                }
            });
        },
        //表单查询
        formQuery: function () {
            var formData = $('#north_form').serializeObject();
            $('#dg').datagrid('load', formData);
        },
        //合计
        onChangeSum: function () {
            var num = $('#saleNumber').val();
            var price = $('#productPrice').val();
            $('#sumPrice').val(num * price);
        },
        //月销售额汇总
        getSumMon: function () {
            $.get("../orderController/getSumMon.json", function (result) {
                $.messager.alert("月销售额汇总", "汇总金额为：" + result, "info");
            });
        }
    }
})();

/**
 * 初始化
 */
$(function () {
    $('#dg').datagrid({
        url: "../orderController/pageOrderList.json",
        title: "订单列表",
        toolbar: "#toolbar",
        pagination: "true",
        rownumbers: "true",
        fitColumns: "true",
        fit: "true",
        sortName: 'saleTime',
        sortOrder: 'asc',
        multiSort: true,
        pageSize: 20,
        columns: [
            [
                {field: 'ck', checkbox: true},
                {field: 'orderCode', title: '订单编号', width: 100},
                {field: 'purchaserCode', title: '会员编号', width: 50, sortable: true, order: 'desc'},
                {field: 'purchaserName', title: '会员姓名', width: 50},
                {field: 'shopCode', title: '商店编号', width: 50},
                {field: 'shopName', title: '商店名称', width: 50},
                {field: 'productCode', title: '产品编号', width: 50},
                {field: 'productName', title: '产品名称', width: 50},
                {field: 'productPrice', title: '产品价格', width: 50},
                {field: 'pv', title: 'PV', width: 50},
                {field: 'bv', title: 'BV', width: 50},
                {field: 'saleNumber', title: '购买数量', width: 50},
                {field: 'sumPrice', title: '购买总额', width: 50},
                {field: 'saleTime', title: '购买时间', width: 100,
                    formatter: function (value, row, index) {
                        var unixTimestamp = new Date(value);
                        return unixTimestamp.toLocaleString();
                    }}
            ]
        ],
        onBeforeLoad: function (param) {
            //列表查询前将表单值作为参数传递到后台方法
            var formData = $('#north_form').serializeObject();
            $.extend(param, formData);
        }
    });

    //合计
    $("#saleNumber").keyup(function () {
        centerJS.onChangeSum();
    });

    //查询
    $('#querybtn').click(function (e) {
        e.preventDefault();
        centerJS.formQuery();
    });

    //月销售额汇总
    $('#sumbtn').click(function (e) {
        e.preventDefault();
        centerJS.getSumMon();
    });

});