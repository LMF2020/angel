var centerJS = (function () {
    var url;
    return {
        //编辑会员
        editUser: function () {
            var rows = $('#dg').datagrid('getSelections');
            var row = rows[0];
            if (row) {
                $('#dlg').dialog('open').dialog('setTitle', 'Edit');
                $('#fm').form('load', row);
                $('#fm input[name="purchaserCode"]').attr('readonly', true).css('color', 'red');//会员编码只读
                $('#fm input[name="sponsorCode"]').attr('readonly', true).css('color', 'red');
                ;//上级会员编码只读
                url = '../userController/updateUser.json';
            }else{
                $.messager.alert('系统提示','请先选择要修改的会员','warning');
            }
        },
        //添加会员
        newUser: function () {
            $('#dlg').dialog('open').dialog('setTitle', 'Registor');
            $('#fm').form('clear');
            $('#fm input[name="purchaserCode"]').attr('readonly', false);
            $('#fm input[name="sponsorCode"]').attr('readonly', false);
            url = '../userController/addUser.json';
        },
/*        //删除会员
        destroyUser: function () {
            var rows = $('#dg').datagrid('getSelections');
            if (rows.length > 0) {
                var codes = [];
                for (var i in rows) {
                    codes.push(rows[i].purchaserCode);
                }
                $.messager.confirm('提示', '您确定要删除当前选中的会员吗?', function (r) {
                    if (r) {
                        var url = '../userController/destroyUser.json';
                        CommonAjax.get(url,{codes: codes},'POST',function(result){
                            if (result.message) {
                                $.messager.show({
                                    title: '提示',
                                    msg: result.message
                                });
                            } else {
                                $.messager.alert('系统提示','信息删除成功!','info');
                                $('#dg').datagrid('reload');
                            }
                        });
                    }
                });
            }else{
                $.messager.alert('系统提示','请先选择要删除的会员.','warning');
            }
        },*/
        //保存会员(添加/编辑)
        saveUser: function () {
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
                        $('#dlg').dialog('close');        // close the dialog
                        $('#dg').datagrid('reload');    // reload the grid data
                        $.messager.alert('系统提示','信息保存成功!','info');
                    }
                }
            });
        },

        //表单查询
        formQuery: function () {
            var formData = $('#north_form').serializeObject();
            $('#dg').datagrid('load', formData);
        },

        //查看会员网络结构图
        showGraph : function(){
            var rows = $('#dg').datagrid('getSelections');
            var row = rows[0];
            if (row) {
                //弹出窗口
                parent.window.$("#userNetworkModal").modal({backdrop:'static'});
                parent.window.$("#userNetworkModalLabel").html("正在查看会员:"+row['purchaserCode']+" 的网络结构图");
                //生成网络图
                parent.window.homeJS.generateGraph(row['purchaserCode']);
            }else{
                $.messager.alert('系统提示','请先选择要查看的会员.','warning');
            }
        }

    }
})();

$(function () {
    //定义显示列表
    $('#dg').datagrid({
        url: "../userController/pageUserList.json",
        title: "会员列表",
        toolbar: "#toolbar",
        pagination: "true",
        rownumbers: "true",
        fitColumns: "true",
        fit: "true",
        sortName: 'purchaserCode',
        sortOrder: 'asc',
        multiSort: true,
        pageSize: 20,
        singleSelect:"true",
        columns: [
            [
                {field: 'ck', checkbox: true},
                {field: 'purchaserCode', title: '会员编号', width: 50},
                {field: 'purchaserName', title: '会员姓名', width: 50},
                {field: 'sponsorCode', title: '上级会员编号', width: 50},
                {field: 'sponsorName', title: '上级会员姓名', width: 50},
                {field: 'shopCode', title: '店铺编号', width: 50},
                {field: 'createTime', title: '加入时间', width: 50, sortable: true, order: 'desc',
                    formatter: function (value, row, index) {
                        var unixTimestamp = new Date(value);
                        return unixTimestamp.toLocaleString();
                    }}
            ]
        ],
        onLoadSuccess: function (data) {
            //列表加载成功的回调函数
        },
        onBeforeLoad: function (param) {
            //列表查询前将表单值作为参数传递到后台方法
            var formData = $('#north_form').serializeObject();
            $.extend(param, formData);
        }
    });

    //双击列表某一行弹出网络拓补结构图
 /*   $('#dg').datagrid({
        onDblClickRow:function(rowIndex, rowData){
           alert(rowData['purchaserCode']);
        }
    });
*/
    //定义表单条件查询
    $('#beginQuery').click(function () {
        centerJS.formQuery();
    });

});