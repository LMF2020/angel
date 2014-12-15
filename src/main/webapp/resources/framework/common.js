/* 转日期-字符串: 2014-05-03 */
function myformatter(date) {
	var y = date.getFullYear();
	var m = date.getMonth() + 1;
	var d = date.getDate();
	return y + '-' + (m < 10 ? ('0' + m) : m) + '-' + (d < 10 ? ('0' + d) : d);
}
/* 转字符串-日期: 2014-05-03 */
function myparser(s) {
	if (!s) return new Date();
	var ss = (s.split('-'));
	var y = parseInt(ss[0], 10);
	var m = parseInt(ss[1], 10);
	var d = parseInt(ss[2], 10);
	if (!isNaN(y) && !isNaN(m) && !isNaN(d)) {
		return new Date(y, m - 1, d);
	} else {
		return new Date();
	}
}
/* ajax-Loader弹出效果*/
function showLoading(loadtext){
    var loadtext =  loadtext || '给力加载中`````';
    var loading = '<div id="overlay" class="overlay"></div><div id="page_loading"><img src="../resources/images/ajax-loader.gif" alt="page loading"><span>'+loadtext+'</span></div>';
    $(document.body).append(loading);
}
function hideLoading(){
    $('#overlay').remove();
    $('#page_loading').remove();
}
/* form序列化对象 */
jQuery.prototype.serializeObject = function(){
    var obj=new Object();
    $.each(this.serializeArray(),function(index,param){
        if(!(param.name in obj)){
            obj[param.name]=param.value;
        }
    });
    return obj;
};

/* easyui表单验证器*/
$.extend($.fn.validatebox.defaults.rules, {
    puchaserCodeFormat: {   //验证6位编号
        validator: function(value){
            var reg = /^\d{6}$/;
            return reg.test(value);
        },
        message: '请输入6位的会员编号.'
    },
    moneyFormat:{
        validator:function(value){
            var reg = /^\d+(\.\d{2})?$/;
            return reg.test(value);
        },
        message:'只能输入数字或小数保留两位有效数字'
    }
});

/**
 * 清楚dom元素下的所有元素
 * @param obj   dom元素   (document.getElementById)
 */
function clearDom(obj){
    $( "*", obj).add([obj]).each(function(){
        $.event.remove(this);
        $.removeData(this);
    });
    obj.innerHTML = "";
}

var CommonAjax = (function($){
    var get,getLocal,_ajax;

    /**
     * ajax内部函数
     * @param url   服务地址
     * @param data  请求参数
     * @param type  请求类型：GET，POST
     * @param success   成功回调函数
     * @param options {
     *     timeout:5
     *     loadText:'系统正在核算中...'
     * }
     */
    _ajax = function (url,data,type,success,options) {

        if(!type){ //类型为空的话，默认POST请求
            type='POST';
        }
        var timeout = (options && options.timeout) || 1;
        var loadText = options && options.loadText || '正在给力加载中```';
        console.log(timeout + '==' + loadText);
        $.ajax({
            url: url,
            type:type,
            data:data,
            traditional: true, //浅序列化数组
            dataType:'json',
            timeout:timeout*60*1000, //请求超时默认60秒
            beforeSend: function () {
                showLoading(loadText);
            }
        }).done(success)
            .fail(function () {
               alert('请求接口失败');
                return false;
            })
            .always(function( jqXHR, textStatus ) {
                if(textStatus=='timeout'){
                    alert( "请求超时"  );
                }
                hideLoading();
            });
    };

    /**
     * 远程服务
     * @returns {*}
     */
    get = function(url, data ,type ,success,options){
        return _ajax(url,data,type,success,options);
    }

    return {
        get: get
    }
})(jQuery);