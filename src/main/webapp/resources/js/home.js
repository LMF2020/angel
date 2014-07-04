var homeJS = (function () {

    return {

        init: function () {
            $('#cc').layout();
        },

        addTab: function (title, url) {
            if ($('#tabsDiv').tabs('exists', title)) {
                $('#tabsDiv').tabs('select', title);
            } else { //height:510px;
                var content = '<iframe scrolling="auto" id="iframeDiv" frameborder="0"  src="'
                    + url
                    + '" style="width:100%;min-height:580px;"></iframe>';
                $('#tabsDiv').tabs('add', {
                    title: title,
                    content: content,
                    closable: true
                });
                // $('#tabsDiv').tabs("resize");
            }
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


});