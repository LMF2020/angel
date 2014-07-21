/*转日期-字符串: 2014-05-03*/
function myformatter(date) {
	var y = date.getFullYear();
	var m = date.getMonth() + 1;
	var d = date.getDate();
	return y + '-' + (m < 10 ? ('0' + m) : m) + '-' + (d < 10 ? ('0' + d) : d);
}
/*转字符串-日期: 2014-05-03*/
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

function showLoading(){
    var loading = '<div class="ares_loading">' +
                       '<div class="loading black refresh">' +
                            '<span class="top"></span>' +
                            '<span class="right"></span>' +
                            '<span class="bottom"></span>' +
                            '<span class="left"></span>' +
                       '</div>' +
                       '<div class="text">正在加载...</div>' +
                   '</div>';

//    var loading = '<div dhxbox="1" class="ares_loading" style="z-index: 20000; position: fixed; left: 50%; top: 50%;">' +
//                        '<img src="../resources/images/loading.gif">' +
//                   '</div>';

    $(document.body).append(loading);
}

function hideLoading(){
    $('.ares_loading').remove();
}
/* form序列化出一个对象 */
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
     */
    _ajax = function (url,data,type,success) {

        if(!type){ //类型为空的话，默认POST请求
            type='POST';
        }
        $.ajax({
            url: url,
            type:type,
            data:data,
            traditional: true, //浅序列化数组
            dataType:'json',
            timeout:60*1000, //请求超时默认60秒
            beforeSend: function () {
                showLoading();
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
    get = function(url, data ,type ,success){
        return _ajax(url,data,type,success);
    }

    return {
        get: get
    }
})(jQuery);