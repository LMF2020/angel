
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
                app.triggerRadioSelected(rankCode);
            });
            //点击导出按钮
            $('#export').click(function(e){
                e.preventDefault();
                var radios = $('#north_form').find('input[name="starRadio"]:checked');
                if(radios.length>0){
                    app.triggerExportClick(radios[0].value);
                }
            });
        },
        //单击查询
        triggerRadioSelected: function(rankCode){
            //单选
            $('#dg').datagrid('load',{
                rankCode:   rankCode
            });
        },
        //点击导出
        triggerExportClick: function(rankCode){
            //导出
            var url = "../helperController/exportRankUserList?rankCode="+rankCode;
            window.open (url,"_blank");
        },
        showGrid: function(){
            //定义显示列表
            $('#dg').datagrid({
                url: "../helperController/pageUserList.json",
                queryParams: {
                    rankCode: '102003'
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
                        {field: 'purchaserCode', title: '会员编号', width: 50,sortable:true},
                        {field: 'purchaserName', title: '会员姓名', width: 50,sortable:true},
                        {field: 'shopCode', title: '专卖店', width: 50,sortable:true},
                        {field: 'rankName',title:'星级',width:30,sortable:true},
                        {field: 'createTime', title: '加入时间', width: 50, sortable: true, order: 'desc',
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