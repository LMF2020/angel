/**
 * 客户端图片管理工具
 * @autor Jiang
 * 参考：http://www.baidufe.com/component
 */
var ImgManager = (function(){
	
	// 图片默认导出为 png 格式
	var type = 'png';

	/**
	 * 获取mimeType
	 * @param  {String} type the old mime-type
	 * @return the new mime-type
	 */
	var _fixType = function(type) {
	    type = type.toLowerCase().replace(/jpg/i, 'jpeg');
	    var r = type.match(/png|jpeg|bmp|gif/)[0];
	    return 'image/' + r;
	};
	
	/**
	 * 在本地进行文件保存
	 * @param  {String} data     要保存到本地的图片数据
	 * @param  {String} filename 文件名
	 */
	var saveFile = function(data, filename){
	    var save_link = document.createElementNS('http://www.w3.org/1999/xhtml', 'a');
	    save_link.href = data;
	    save_link.download = filename;
	   
	    var event = document.createEvent('MouseEvents');
	    event.initMouseEvent('click', true, false, window, 0, 0, 0, 0, 0, false, false, false, false, 0, null);
	    save_link.dispatchEvent(event);
	};
	
	/**
	 * canvas导出为图片并用JS下载
	 * @param {string} selector Canvas选择器
	 * @param {string} imgType  文件类型:png/jpg/gif/bpm
	 * @param {string} prefix   下载后文件的前缀如.baidu_20140714.png
	 */
	var download = function(canvasDOM,imgType,prefix){
		if(imgType){
			type = imgType;
		}
		// 获得图片数据流
//		var canvas = document.getElementById(IdSelector);
		var imgData = canvasDOM.toDataURL(type);
		// 加工image data,替换mime type
		imgData = imgData.replace(_fixType(type),'image/octet-stream');
		// 定义下载后的文件名
		var filename = prefix +'-' + myformatter(new Date()) + '.' + type;
		// 下载
		saveFile(imgData,filename);
	}
	
	return {
		dowload:download
	}
})();
