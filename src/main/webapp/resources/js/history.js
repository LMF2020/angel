$(function () {
    //定义显示列表
    $('#dg').datagrid({
        url: "",
        title: "查询列表",
        pagination: "true",
        rownumbers: "true",
        fitColumns: "true",
        fit: "true",
        pageSize: 20,
        singleSelect:"true",
        columns: [
            [
                {field: 'achieve_date', title: 'ACHIEVE DATE',width: 60, sortable: true, order: 'desc',
                    formatter: function (value, row, index) {
                        var d = new Date(value);
                        return myformatter(d);
                }},
                {field: 'purchaser_code', title: 'DISTRIBUTOR ID', width: 50,sortable:true},
                {field: 'purchaser_name', title: 'DISTRIBUTOR NAME', width: 80,sortable:true},
                {field: 'sponsor_code',title:'SPONSOR ID',width:50,sortable:true},
                {field: 'shop_code', title: 'SHOP ID', width: 50,sortable:true},
                {field: 'rank_name', title: 'RANK', width: 20,sortable:true},
                {field: 'PPV',title:'PPV',width:30,sortable:true},
                {field: 'DBV',title:'DBV',width:30,sortable:true},
                {field: 'IBV',title:'IBV',width:30,sortable:true},
                {field: 'ATNPV',title:'ATNPV',width:30,sortable:true},
                {field: 'TNPV',title:'TNPV',width:30,sortable:true},
                {field: 'GPV',title:'GPV',width:30,sortable:true},
                {field: 'APPV',title:'GPV',width:30,sortable:true}
            ]
        ],
        view: noRecordGridview,
        emptyMsg: '没有记录',
        onLoadSuccess: function (data) {
            //列表加载成功的回调函数
        },
        onBeforeLoad: function (param) {
            //拼接前台参数
            var params = {};
            params.purchaserCode = $('#purchaserCode').val();
            //表单验证
            if(!params.purchaserCode){
                $.messager.alert('系统提示','请输入查询的会员编号');
                return false;
            }
            if(!/^[0-9]*$/.test(params.purchaserCode)){
                $.messager.alert('系统提示','会员编号输入错误');
                return false;
            }
            var exp = $("#achieveDate").val();
            if(exp && typeof(exp)!="undefined" && exp!=0){
                params.achieveDate = myformatter(new Date(Date.parse(exp)));
            }else{
                params.achieveDate = null;
            }
            $.extend(param, params);
        }
    });
    //定义表单条件查询
    $('#beginQuery').click(function () {
       $('#dg').datagrid('options').url = '../helperController/pageQueryUserGradeByMon.json';
       $('#dg').datagrid('load',{});
    });

});