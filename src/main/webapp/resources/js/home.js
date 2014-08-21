var homeJS = (function () {

    return {

        init: function () {
            $('#cc').layout();
        },

        addTab: function (title, href) {
            if ($('#tabsDiv').tabs('exists', title)) {
                $('#tabsDiv').tabs('select', title);
            } else { //height:510px;
//                var content =
//                    '<iframe id="iframeDiv" frameborder="0"  frameborder="0"  src="'
//                    + url
//                    + '" style="width:100%;height:100%"></iframe>';
//                $('#tabsDiv').tabs('add', {
//                    title: title,
//                    content: content,
//                    closable: true
//                });

             //iframe窗口管理
                $('#tabsDiv').tabs('addIframeTab',{
                    //tab参数为一对象，其属性同于原生add方法参数
                    tab:{
                        title:title,
                        closable:true,
                        tools:[{
                            iconCls:'icon-mini-refresh',
                            handler:function(e){
                                var title = $(e.target).parent().parent().text();
                                $('#tabsDiv').tabs('updateIframeTab',{'which':title});
                            }
                        }]
                    },
                    //iframe参数用于设置iframe信息，包含：
                    //src[iframe地址],frameBorder[iframe边框,，默认值为0],delay[淡入淡出效果时间]
                    //height[iframe高度，默认值为100%],width[iframe宽度，默认值为100%]
                    iframe:{
                        src:href
                    }
                });
                $('#tabsDiv').tabs('addEventParam');
            }
        },

        //根据编号生成网络结构图
        generateGraph:function(purchaserCode){

            var $$ = go.GraphObject.make;

            if(typeof(myDiagram) == "undefined" || !myDiagram){

                myDiagram =
                    $$(go.Diagram, "myDiagramDiv",
                        { allowCopy: false });

                //定义节点
//                myDiagram.nodeTemplate =
//                    $$(go.Node, "Horizontal",
//                        { background: "#44CCFF" },
//                        $$(go.TextBlock, "Default Text",
//                            { margin: 12, stroke: "white", font: "bold 16px sans-serif" },
//                            new go.Binding("text", "name"))
//                    );

                myDiagram.nodeTemplate =
                    $$(go.Node, "Spot",
                        { locationSpot: go.Spot.Center },
                        new go.Binding("text", "name"),
                        $$(go.Shape, "Ellipse",
                            { fill: "lightgray",
                              stroke: "black",
                              desiredSize: new go.Size(30, 30)
                            },
                            new go.Binding("desiredSize", "size"),
                            new go.Binding("fill", "fill")),
                            $$(go.TextBlock,
                                {font: "bold 16px sans-serif" },
                                new go.Binding("text", "name"))
                    );

                // 定义连线
                myDiagram.linkTemplate =
                    $$(go.Link,
                        { routing: go.Link.Orthogonal,
                            corner: 5,
                            selectable: false
                        },
                        $$(go.Shape));
                // 定义布局
                myDiagram.layout =
                    $$(go.TreeLayout,
                        {
                            angle: 90,
                            nodeSpacing: 5
                        });
                myDiagram.model = new go.TreeModel([]);
            }
            //布局
            //myDiagram.layout.alignment = go.TreeLayout.AlignmentCenterChildren;
            myDiagram.layout.alignment = go.TreeLayout.AlignmentCenterSubtrees;
            //发送数据请求
            CommonAjax.get("../busiController/pageNetList/"+purchaserCode,{},"GET",function(json){
                //清除说明
                myDiagram.clear();
                // 创建model
                var treeLst = json.treeLst;
                var userMap = json.userMap;

                console.log(userMap);

                myDiagram.model.nodeDataArray = treeLst;
                var x = $('#userNetworkModal').width()*0.7;
                var y = 10;
                //创建说明
                myDiagram.add(
                    $$(go.Part, "Auto",
                        { position: new go.Point(x, y), selectable: true },
                        $$(go.Panel, "Table",
                            //默认样式
                            { padding: 0,
                                //defaultAlignment: go.Spot.Right,
                                defaultRowSeparatorStroke: "gray",
                                defaultColumnSeparatorStroke: "gray",
                                defaultSeparatorPadding: new go.Margin(10, 25, 5, 20) },

                            $$(go.TextBlock, "DISTRIBUTOR ID", { row: 0, column: 0, margin: 2 ,font: "bold 16px sans-serif"}),
                            $$(go.TextBlock, purchaserCode, { row: 0 ,column: 1, margin: 2 ,font: "bold 16px sans-serif"}),
                            $$(go.TextBlock, "PPV", { row: 1, column: 0, margin: 2 ,font: "bold 16px sans-serif"}),
                            $$(go.TextBlock, userMap['PPV'].toString(), { row: 1, column: 1, margin: 2 ,font: "bold 16px sans-serif"}),
                            $$(go.TextBlock, "APPV", { row: 2, column: 0, margin: 2 ,font: "bold 16px sans-serif"}),
                            $$(go.TextBlock, userMap['APPV'].toString(), { row: 2, column: 1, margin: 2 ,font: "bold 16px sans-serif"}),
                            $$(go.TextBlock, "ATNPV", { row: 3, column: 0, margin: 2 ,font: "bold 16px sans-serif"}),
                            $$(go.TextBlock, userMap['ATNPV'].toString(), { row: 3, column: 1, margin: 2 ,font: "bold 16px sans-serif"})
                        )
                    ));
            });

        }

    };

})();

$(function () {

    //点击导航菜单
    $('#header ul>li>a').on('click', function (e) {
        //激活焦点
        var $Li = $(this).parent();
        $Li.addClass('active').siblings().removeClass('active');
        //加载页面
        var title = $(this).text();
        var url = $(this).attr('href');
        homeJS.addTab(title, url);
        e.preventDefault();
    });

    //导出矢量图
    $('#exportImgDiv').click(function(){
        //获取当前会员的ID
        var purchaserCode = $("#userNetworkModalLabel").attr("hiddenPurchaserCode");
        //导出会员网络图PNG
        var canvasDOM = $("#myDiagramDiv").children("canvas")[0];
        ImgManager.dowload(canvasDOM,"png","User"+purchaserCode);
    });

    //激活首个面板(会员等级板块)
    $('#header ul>li:first>a').click();
    //关闭网络结构窗口
    $('#userNetworkModal .dlgClose').click(function(){
        //隐藏窗口
        $('#userNetworkModal').modal('hide');
    });

});