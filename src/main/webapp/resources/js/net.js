/**
 * 订单管理
 */
var netJS = (function(){
    return {
        //表单查询
        formQuery:function(){
            var formData = $('#north_form').serializeObject();
            $('#dg').datagrid('load',formData);
        }
    }
})();

/**
 * 初始化
 */
$(function(){
    $('#dg').datagrid({
        url:"../busiController/pageNetList.json",
        title:"网络表格",
        toolbar:"#toolbar" ,
        pagination:"true",
        rownumbers:"true",
        fitColumns:"true" ,
        fit:"true" ,
        multiSort:true,
        pageSize: 20,
        columns:[[
            {field:'TIER',title:'Tier',width:50},
            {field:'PURCHASER_ID_NAME',title:'Distributor ID/Name',width:137,sortable:true,order:'desc'},
            {field:'SPONSOR_ID_NAME',title:'Sponsor ID/Name',width:137},
            {field:'RANK_NAME',title:'Rank',width:137},
            {field:'ATNPV',title:'Accmulative TNPV',width:140},
            {field:'APPV',title:'Accmulative PPV',width:140},
            {field:'TNPV',title:'TNPV',width:50},
            {field:'GPV',title:'GPV',width:50},
            {field:'PPV',title:'Personal PV/BV',width:137},
            {field:'DB',title:'DB',width:50},
            {field:'IB',title:'IB',width:50},
            {field:'LB',title:'LB',width:50}
        ]],
        onBeforeLoad:function(param){

        }
    });

    //查询
    $('#querybtn').click(function(e){
        e.preventDefault();
        netJS.formQuery();
    });

    //计算
    $('#calcbtn').click(function(e){
        e.preventDefault();
        $.messager.confirm("确认", "您确定开始核算本月会员的奖金吗？", function (r) {
            if (r) {
                $.ajax({
                    url: '../busiController/beginCalculate.json',
                    dataType:"json",
                    type:"GET",
                    success: function(result){
                        if(!result.message){
                            $.messager.alert('提示:','你好,计算完毕!','info');
                        }else{
                            $.messager.alert('提示:', result.message,'error');
                            $('#dg').datagrid('reload');    // reload the grid data
                        }
                    }
                });
                return true;
            }
        });
    });


});