
$(function () {
    var app = {
        init : function(){
            this.showGrid();
            this.bindEvent();
            this.showDateInfo();
        },
        showDateInfo: function(){
          var dateAreaText = $('#dateAreadiv').html();
          var nowd = new Date();
          var yf = nowd.getFullYear();
          var mon = nowd.getMonth()+1;
          var newdateText = dateAreaText.replace(/{year}/g,yf).replace(/{month}/g,mon);
          $('#dateAreadiv').html(newdateText);
        },
        bindEvent: function(){
            //显示星级按钮选中
            $('#north_form').find('input[name="starRadio"]').click(function(e){
                var rankCode = $(this).val();
                app.triggerSelected(rankCode);
            });
            //点击导出按钮
            $('#export').click(function(e){
                e.preventDefault();
                var rankCode = $('#north_form').find('input[name="starRadio"]:checked').val();
                app.triggerExportClick(rankCode);
            });
            //选中新增选项
            $('#check').click(function(e){
                var rankCode = $('#north_form').find('input[name="starRadio"]:checked').val();
                app.triggerSelected(rankCode);
            });
        },
        //单击查询:需要判断是否有需要只过滤新增的会员
        triggerSelected: function(rankCode){
            var isCheck = '0'; //否
            if($('#check').is(':checked')){
                isCheck = '1'; //是
            }
            //单选
            $('#dg').datagrid('load',{
                rankCode:   rankCode,
                isCheck : isCheck
            });
        },
        //点击导出
        triggerExportClick: function(rankCode){
            var isCheck = '0'; //否
            if($('#check').is(':checked')){
                isCheck = '1'; //是
            }
            var url = "../helperController/exportRankUserList?rankCode="+rankCode+"&isCheck="+isCheck;
            window.open (url,"_blank");
        },
        showGrid: function(){
            //定义显示列表
            $('#dg').datagrid({
                url: "../helperController/pageUserList.json",
                queryParams: {
                    rankCode: '102003' //初始化选择3星级会员
                },
                title: "查询列表",
                pagination: "true",
                rownumbers: "true",
                fitColumns: "true",
                fit: "true",
                sortName: 'floors',
                sortOrder: 'asc',
                pageSize: 20,
                singleSelect:"true",
                columns: [
                    [
                        {field: 'purchaser_code', title: '会员编号', width: 50,sortable:true},
                        {field: 'purchaser_name', title: '会员姓名', width: 50,sortable:true},
                        {field: 'shop_code', title: '专卖店', width: 50,sortable:true},
                        {field: 'rank_name',title:'星级',width:30,sortable:true},
                        {field: 'PVBV',title:'PV/BV',width:30,sortable:true},
                        {field: 'APPV',title:'APPV',width:30,sortable:true},
                        {field: 'ATNPV',title:'ATNPV',width:30,sortable:true},
                        {field: 'create_time', title: '加入时间', width: 50, sortable: true, order: 'desc',
                            formatter: function (value, row, index) {
                                return new Date(value).toLocaleString();
                            }}
                    ]
                ],
                view: noRecordGridview,
                emptyMsg: '没有记录',
                onLoadSuccess: function (data) {

                },
                onBeforeLoad: function (param) {

                }
            });
        }
    }
    app.init();
});