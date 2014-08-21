/**
 * 订单管理
 */
var netJS = (function () {
    return {
        //表单查询
        formQuery: function () {
            var formData = {
                purchaserCode:$("#purchaserCodeDiv").val()
            }
            CommonAjax.get("../userController/ifExist.json",formData,'POST', function (result) {
                if(result.success){
                    $('#dg').datagrid('load', formData);
                }else{
                    $.messager.alert("操作提示", "您输入的客户编号不存在！","info");
                }
            });
            return true;
        },
        //计算提交
        startCount:function(){
            //关闭对话框

            //调用计算服务接口
            var url =  '../busiController/beginCalculate.json';
            var startDate = $('#startDate').datetimebox('getValue');
            var endDate = $('#lastDate').datetimebox('getValue');
            var params = {};
            params["startDate"] = startDate;
            params["endDate"] = endDate;
            if(compareTime(startDate,endDate)){
                $('#dlgCount').dialog('close');
            }else{
                return false;
            }
            CommonAjax.get(url,params,'POST',function(result){
                if (!result.message) {
                    $.messager.alert('系统提示:', 'OK....计算成功。', 'info');
                    $('#dg').datagrid('reload');
                } else {
                    $.messager.alert('系统提示:', result.message, 'error');
                }
            });
            return true;
        }
    }
})();
/**
 * 初始化
 */
$(function () {
    var basePath = '${pageContext.request.contextPath}';
    $('#dg').datagrid({
        url: "../busiController/pageNetList.json",
        title: "会员网络结构列表",
        toolbar: [
            { //计算网络业绩
                iconCls: 'icon-edit',
                text: '<h5 id="toolTitleDiv" class="text-danger" style="background-color:#EDF7ED;">【核算当月业绩】<h5>',
                handler: function () {
                    //初始化时间框控件
                    var startDate = getFirstDayOfMonth();
                    $('#startDate').datetimebox('setValue',startDate);
                    var lastDate = getLastDayOfMonth();
                    $('#lastDate').datetimebox('setValue',lastDate);
                    //打开对话框
                    $('#dlgCount').dialog('open');

                }
            },{
                iconCls:'icon-large-shapes',
                text:'<h5 class="text-primary" style="background-color:#EDF7ED;">【备份当月业绩】<h5>',
                handler :function(){
                   $.messager.confirm("确认", "注意：本月上一次备份的计算结果将会被替换", function (r) {
                        if (r) {
                            var url =  '../busiController/backUp.json';
                            CommonAjax.get(url,{},'POST',function(result){

                                if (!result.message) {
                                    $.messager.alert('系统提示:', 'OK....备份成功。', 'info');
                                    $('#dg').datagrid('reload');
                                } else {
                                    $.messager.alert('系统提示:', result.message, 'error');
                                }
                            });
                            return true;
                        }
                    });
                }
            }
        ],
        pagination: "true",
     //   rownumbers: "true",
        fitColumns: "true",
        singleSelect:"true",
        fit: "true",
    //    multiSort: true,
        pageSize: 20,
        columns: [
            [
                {field: 'TIER', title: 'Tier', width: 50,sortable:true,align:'center'},
                {field: 'PURCHASER_ID_NAME', title: 'Distributor ID/Name', width: 137, sortable: true, sortable:true},
                {field: 'SPONSOR_ID_NAME', title: 'Sponsor ID/Name', width: 137,sortable:true},
                {field: 'SHOP_CODE', title: 'Shop ID', width: 100,sortable:true},
                {field: 'RANK_NAME', title: 'Rank', width: 70,sortable:true},
                {field: 'ATNPV', title: 'Accmulative TNPV', width: 110,sortable:true},
                {field: 'APPV', title: 'Accmulative PPV', width: 110,sortable:true},
                {field: 'TNPV', title: 'TNPV', width: 100,sortable:true},
                {field: 'GPV', title: 'GPV', width: 100,sortable:true},
                {field: 'PPV', title: 'Personal PV/BV', width: 110,sortable:true},
                {field: 'DB', title: 'DB', width: 50,sortable:true},
                {field: 'IB', title: 'IB', width: 50,sortable:true},
                {field: 'LB', title: 'LB', width: 50,sortable:true}
            ]
        ]

});

    //查询当前会员网络
    $('#b_query').click(function (e) {
        e.preventDefault();
        netJS.formQuery();
    });
    //导出当前会员网络
    $('#b_export_network').click(function(e){
        e.preventDefault();
        var purchaserCode = $("#purchaserCodeDiv").val();

        var formData = {
            purchaserCode:purchaserCode
        }
        //判断输入是否为空
        if(purchaserCode){
            //验证客户编号是否存在
            CommonAjax.get("../userController/ifExist.json",formData,'POST', function (result) {
                if(result.success){
                    var url = "../exportController/exportExcelNetwork?purchaserCode="+purchaserCode;
                    window.open (url,"_blank") ;
                }else{
                    $.messager.alert("操作提示", "查询的客户编号不存在！","info");
                }
            });
        }else{
            alert("请先输入客户编号！");
        }
    });
    //导出所有会员网络
    $('#b_export_all_network').click(function(e){
        e.preventDefault();
        var url = "../exportController/exportExcelNetwork";
        window.open (url,"_blank") ;
    });
    //导出当前店铺奖金
    $('#b_export_bonus').click(function(e){
        e.preventDefault();
        var shopCode = $("#shopCodeDiv").val();
        if(shopCode){
            var url = "../exportController/exportExcelShopBonus?shopCode="+shopCode;
            window.open (url,"_blank") ;
        }else{
            alert("请先输入要导出的店铺编号")
        }
    });
    //导出所有店铺奖金
    $('#b_export_all_bouns').click(function(e){
        e.preventDefault();
        var url = "../exportController/exportExcelShopBonus";
        window.open (url,"_blank") ;
    });

});