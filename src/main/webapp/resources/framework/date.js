Date.prototype.format = function(fmt){
    var year    =   this.getFullYear();
    var month   =   this.getMonth()+1;
    var date    =   this.getDate();
    var hour    =   this.getHours();
    var minute  =   this.getMinutes();
    var second  =   this.getSeconds();
    function fix(n){
        return n<10?"0"+n:n;
    }
    fmt = fmt.replace("yyyy",year);
    fmt = fmt.replace("yy",year%100);
    fmt = fmt.replace("MM",fix(month));
    fmt = fmt.replace("dd",fix(this.getDate()));
    fmt = fmt.replace("hh",fix(this.getHours()));
    fmt = fmt.replace("mm",fix(this.getMinutes()));
    fmt = fmt.replace("ss",fix(this.getSeconds()));
    return fmt;
}

//获取第一天
function getFirstDayOfMonth(){
    var myDate = new Date();
    var year = myDate.getFullYear();
    var month = myDate.getMonth()+1;
    if (month<10){
        month = "0"+month;
    }
    var firstDay = year+"-"+month+"-"+"01"+" 00:00:00";
    return firstDay;
}

//获取最后第一天
function getLastDayOfMonth(){
    var dt = new Date();
    dt.setDate(1);
    dt.setMonth(dt.getMonth()+1);
    var cdt = new Date(dt.getTime()-1000*60*60*24);
    var lastDay = cdt.getFullYear()+"-"+(Number(cdt.getMonth())+1)+"-"+cdt.getDate()+" 23:59:59";
    return lastDay;
}

//时间比较(yyyy-mm-dd hh:mi:ss)
function compareTime(beginTime,endTime) {
    var a = (Date.parse(endTime) - Date.parse(beginTime)) / 3600 / 1000;
    if (a < 0) {
        alert("结束日期不能小于开始日期");
        return false;
    } else if (a > 0) {
        return true;
    } else if (a == 0) {
        alert("结束日期时间不等于开始日期相同!");
        return false;
    } else {
        alert("时间选择异常");
        return false;
    }
}