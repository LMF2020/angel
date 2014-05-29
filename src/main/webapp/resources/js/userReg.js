var centerJS = (function(){
    var url;
	return {
        //编辑会员
        editUser : function(){
            var rows = $('#dg').datagrid('getSelections');
            if (rows.length>1){
                $.messager.show({    // show error message
                    title: '提示',
                    msg: "更新时只能选中一条记录!"
                });
                return;
            }
            var row = rows[0];
            if (row){
                $('#dlg').dialog('open').dialog('setTitle','编辑会员');
                $('#fm').form('load',row);
                $('#fm input[name="purchaserCode"]').attr('readonly',true).css('color','red');//会员编码只读
                $('#fm input[name="sponsorCode"]').attr('readonly',true).css('color','red');;//上级会员编码只读
                url = '../userController/updateUser.json';
            }
        },
        //添加会员
        newUser : function(){
            $('#dlg').dialog('open').dialog('setTitle','添加会员');
            $('#fm').form('clear');
            $('#fm input[name="purchaserCode"]').attr('readonly',false);
            $('#fm input[name="sponsorCode"]').attr('readonly',false);
            url = '../userController/addUser.json';
        },
        //删除会员
        destroyUser:function (){
            var rows = $('#dg').datagrid('getSelections');
            if (rows.length>0){
                var codes = [];
                for(var i in rows){
                    codes.push(rows[i].purchaserCode);
                }
                $.messager.confirm('提示','您确定要删除当前选中的会员吗?',function(r){
                    if (r){
                        $.ajax({
                            url: '../userController/destroyUser.json',
                            dataType:"json",
                            type:"POST",
                            data: {
                                codes:codes
                            },
                            traditional: true, //浅序列化数组
                            success: function(result){
                                if(result.message){
                                    $.messager.show({    // show error message
                                        title: '提示',
                                        msg: result.message
                                    });
                                }else{
                                    $.messager.show({    // show error message
                                        title: '提示',
                                        msg: "删除成功!"
                                    });
                                    $('#dg').datagrid('reload');    // reload the grid data
                                }
                            }
                        });

                    }
                });
            }
        },
        //保存会员(添加/编辑)
        saveUser:function (){
            $('#fm').form('submit',{
                url: url,
                onSubmit: function(){
                    return $(this).form('validate');
                },
                success: function(re){
                    var result = $.parseJSON(re);
                    if (result.message){
                        $.messager.show({
                            title: '提示',
                            msg: result.message
                        });
                    } else {
                        $('#dlg').dialog('close');        // close the dialog
                        $('#dg').datagrid('reload');    // reload the grid data
                        $.messager.show({
                            title: '提示',
                            msg: "保存成功!"
                        });
                    }
                }
            });
        },
        //表单查询
        formQuery:function(){
            var formData = $('#north_form').serializeObject();
            //console.dir(formData);
            $('#dg').datagrid('load',formData);
        }

	}
})();

$(function(){
    //定义显示列表
	$('#dg').datagrid({
		 url:"../userController/pageUserList.json",
		 title:"登记列表",
	     toolbar:"#toolbar" ,
	     pagination:"true",
	     rownumbers:"true",
	     fitColumns:"true" ,
	     fit:"true" ,
         sortName: 'purchaserCode',
         sortOrder:'asc',
         multiSort:true,
         pageSize: 20,
	     //singleSelect:"true",
	     columns:[[{field:'ck',checkbox:true},
                    {field:'purchaserCode',title:'会员编号',width:50},
	                {field:'purchaserName',title:'会员姓名',width:50},
	                {field:'sponsorCode',title:'上级会员编号',width:50},
	                {field:'sponsorName',title:'上级会员姓名',width:50},
	                {field:'shopCode',title:'商店编码',width:50,hidden:true},
                    {field:'shopName',title:'商店名称',width:50},
                    {field:'createTime',title:'加入时间',width:50, sortable:true,order:'desc',
                        formatter:function(value,row,index){
                            var unixTimestamp = new Date(value);
                            return unixTimestamp.toLocaleString();
                    }}]],
	     onLoadSuccess:function(data){
             //列表加载成功的回调函数
	    },
        onBeforeLoad:function(param){
            //列表查询前将表单值作为参数传递到后台方法
            var formData = $('#north_form').serializeObject();
            $.extend(param,formData);
        }
	});

    //定义表单条件查询
    $('#north_form input[type=button]').click(function(){
        centerJS.formQuery();
    });

});