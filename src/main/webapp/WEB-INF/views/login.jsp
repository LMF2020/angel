<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Angel Home Page</title>
<link href="${pageContext.request.contextPath}/resources/css/login.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/framework/easyui/themes/bootstrap/easyui.css"/>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/framework/easyui/themes/icon.css"/>
<script src="${pageContext.request.contextPath}/resources/framework/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/framework/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript">
	var BP = '${pageContext.request.contextPath}';
</script>
<script type="text/javascript">
$(function(){
	
	function login(){

		var usrname = $('#txtUserName').val();
		var pwd = $('#txtPassWord').val();
		
		if(!usrname || !pwd){
			$.messager.alert('提示','<center>请将用户和密码填写完整!</center>');
			return;
		}
		
		$.ajax({
			  type: "POST",
			  url: BP +'/userController/login',
			  data: {usercode:usrname,password:pwd},
			  success: function(d){
				  if(d.success){
					  window.location.href='./userController/home';
				  }
			  },
			  error: function () {

					$('#txtPassWord').val('');
					$.messager.alert('登陆失败','<center>请检查用户和密码是否正确!</center>');
					$('#txtUserName').focus();
			  },
			  dataType: 'json'
			});
		
	}
	
	$('#btn').click(function() { login();});
	$('#txtUserName').on('keydown',function(e){if(e.keyCode==13){$('#txtPassWord').focus();}});
	$('#txtPassWord').on('keydown',function(e){if(e.keyCode==13){login()};});
	
});
</script>
</head>
<body>

    <div id="login">
	
	     <div id="top">
		      <div id="top_left"><img src="${pageContext.request.contextPath}/resources/images/login_03.png" /></div>
			  <div id="top_center"></div>
		 </div>
		 
		 <div id="center">
		      <div id="center_left"></div>
			  <div id="center_middle">
			       <div id="user">用户:
			         <input id="txtUserName" type="text" name="textfield" value="010000"/>
			       </div>
				   <div id="password">密码:
				     <input id="txtPassWord" type="password" name="textfield2" value="0000" />
				   </div>
				   <div id="btn"><a href="#">登陆</a><a href="#">重置</a></div>
			  
			  </div>
			  <div id="center_right"></div>		 
		 </div>
		 <div id="down">
		      <div id="down_left">
			      <div id="inf">
                       <span class="inf_text">Version</span>
					   <span class="copyright">Angel(Cango) Manage System 2014/7 v1.0</span>
			      </div>
			  </div>
			  <div id="down_center"></div>		 
		 </div>

	</div>
</body>
</html>
