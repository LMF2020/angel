/**
 * 产品货架
 */

var centerJS = (function () {
    var url;
    return {
        //添加产品
        newProduct: function () {
            $('#dlg').dialog('open').dialog('setTitle', '新增产品');
            $('#fm').form('clear');
            $('#fm input[name="productCode"]').attr('readonly', false);
            url = '../productController/addProduct.json';
        },
        //编辑产品
        editProduct: function () {
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
                $('#dlg').dialog('open').dialog('setTitle', '修改产品');
                $('#fm').form('load', row);
                //产品编码只读
                $('#fm input[name="productCode"]').attr('readonly', true).css('color', 'red');
                url = '../productController/updateProduct.json';
            }
        },
        //移除产品
        destroyProduct: function () {
            var rows = $('#dg').datagrid('getSelections');
            if (rows.length > 0) {
                var codes = [];
                for (var i in rows) {
                    codes.push(rows[i].productCode);
                }
                $.messager.confirm('提示', '您确定要删除当前选中的产品吗?', function (r) {
                    if (r) {
                        $.ajax({
                            url: '../productController/destroyProduct.json',
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
        saveProduct: function () {
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
        formQuery: function () {
            var formData = $('#north_form').serializeObject();
            $('#dg').datagrid('load', formData);
        }
    }
})();

//初始化数据
$(function () {
    $('#dg').datagrid({
        url: "../productController/pageProductList.json",
        title: "产品货架",
        toolbar: "#toolbar",
        pagination: "true",
        rownumbers: "true",
        fitColumns: "true",
        fit: "true",
        multiSort: true,
        pageSize: 20,
        columns: [
            [
                {field: 'ck', checkbox: true},
                {field: 'productCode', title: '产品编号', width: 50},
                {field: 'productName', title: '产品名称', width: 50},
                {field: 'productPrice', title: '产品价格', width: 50},
                {field: 'productPv', title: 'PV', width: 50},
                {field: 'productBv', title: 'BV', width: 50},
                {field: 'status', title: '在售状态', width: 50,
                    formatter: function (value, row, index) {
                        if (value == 1) {
                            return '在售';
                        } else {
                            return '下架';
                        }
                    }
                },
                {field: 'createTime', title: '更新时间', sortable: true, order: 'desc', width: 50,
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

    //定义表单条件查询
    $('#north_form input[type=button]').click(function () {
        centerJS.formQuery();
    });
});