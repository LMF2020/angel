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
            $('#dg').datagrid('load', formData);
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
                text: '<h5 class="text-danger" style="background-color:#EDF7ED;text-shadow: 5px 5px 70px #EB4A2B;">计算上个月28号到本月当日的网络业绩<h5>',
                handler: function () {
                    $.messager.confirm("确认", "您确定开始核算本月会员的奖金吗？", function (r) {
                        if (r) {
                            var url =  '../busiController/beginCalculate.json';
                            CommonAjax.get(url,{},'POST',function(result){
                                if (!result.message) {
                                    $.messager.alert('提示:', '你好,计算完毕!', 'info');
                                } else {
                                    $.messager.alert('提示:', result.message, 'error');
                                    $('#dg').datagrid('reload');
                                }
                            });
                            return true;
                        }
                    });
                }
            }
        ],
        pagination: "true",
        rownumbers: "true",
        fitColumns: "true",
        fit: "true",
        multiSort: true,
        pageSize: 20,
        columns: [
            [
                {field: 'TIER', title: 'Tier', width: 50},
                {field: 'PURCHASER_ID_NAME', title: 'Distributor ID/Name', width: 137, sortable: true, order: 'desc'},
                {field: 'SPONSOR_ID_NAME', title: 'Sponsor ID/Name', width: 137},
                {field: 'SHOP_CODE', title: 'Shop ID', width: 100},
                {field: 'RANK_NAME', title: 'Rank', width: 70},
                {field: 'ATNPV', title: 'Accmulative TNPV', width: 110},
                {field: 'APPV', title: 'Accmulative PPV', width: 110},
                {field: 'TNPV', title: 'TNPV', width: 100},
                {field: 'GPV', title: 'GPV', width: 100},
                {field: 'PPV', title: 'Personal PV/BV', width: 110},
                {field: 'DB', title: 'DB', width: 50},
                {field: 'IB', title: 'IB', width: 50},
                {field: 'LB', title: 'LB', width: 50}
            ]
        ],
        onBeforeLoad: function (param) {

        }
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
        if(purchaserCode){
            var url = "../exportController/exportExcelNetwork?purchaserCode="+purchaserCode;
            window.open (url,"_blank") ;
        }else{
            alert("请先输入要导出的会员编号");
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