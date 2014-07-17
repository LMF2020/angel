20140716更新日志：

    1.会员管理模块中：会员网络图支持PNG格式导出
    2.会员管理增加时间范围检索，与星级组合查询可以了解每个月的会员加入情况
    3.新增页面载入动画效果，每个页面增加刷新功能
    4.整理了领导奖的计算思路

#
一,环境配置:

    1.将工程导入 Intellj idea 12进行构建
    2.配置JRebel插件, 以实现热部署 (class文件修改在idea中需要重新make/compile)
    3.在JetGradle插件中进行调试 Debug Run With JRebel
    4.访问http://localhost:8081/login
    5.配置过程见BlOG： http://blog.csdn.net/hf_xiaoyou/article/details/26093267

二,数据库脚本

	1.版本 Mysql-5.6,端口号 -3307
	2.Mysql中新建库名 -angel
	3.服务启动表自动创建 ,其中t_purchaser_info -用户表,用户编码:000000,密码:0000

四,UI组件

	EasyUI 3.6 	- http://www.jeasyui.com/
	Bootstrap 3 	- http://getbootstrap.com/css/
	JqueryUI 	- http://jqueryui.com/download/

五,后台框架

    SpringMVC / Nutz

    
六,碎碎念

    1.查看java问档 CTRL +Q / CTRL +Ｐ
    2.自动导入类 Alt +回车
    3.try...catch...finally CTRL+ ALT+ T
