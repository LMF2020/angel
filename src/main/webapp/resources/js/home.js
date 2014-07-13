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
                myDiagram.nodeTemplate =
                    $$(go.Node, "Horizontal",
                        { background: "#44CCFF" },
                        $$(go.TextBlock, "Default Text",
                            { margin: 12, stroke: "white", font: "bold 16px sans-serif" },
                            new go.Binding("text", "name"))
                    );

                // 定义连线
                myDiagram.linkTemplate =
                    $$(go.Link,
                        { routing: go.Link.Orthogonal,
                            corner: 5,
                            selectable: false },
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

            //发送数据请求
            CommonAjax.get("../busiController/pageNetList/"+purchaserCode,{},"GET",function(nodeDataArray){
                // 创建model
                myDiagram.model.nodeDataArray = nodeDataArray;
            });

        }

    };

})();

$(function () {

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
    //激活会员登记面板
    $('#header ul>li:first>a').click();
    //关闭网络结构窗口
    $('#userNetworkModal .dlgClose').click(function(){
        //隐藏窗口
        $('#userNetworkModal').modal('hide');
        //清空内存
/*        var dom = document.getElementById("myDiagramDiv");
          clearDom(dom);*/
      //  myDiagram.model.clear();

    });

});