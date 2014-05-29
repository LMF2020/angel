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