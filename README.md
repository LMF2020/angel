### 系统功能 http://rj-angel.github.io/angel/

20140822更新日志:

    1.在录错的情况下，支持会员修改上线
    2.在录错的情况下，支持批量删除会员
    
20140720更新日志:

    1.添加存储过程angel.copyTableHis()备份历史表
        1.1 重置计数器
            ALTER TABLE t_achieve_his  AUTO_INCREMENT =1;
    2.界面增加选择时间范围对业绩奖金进行核算
    3.谷歌浏览器如发现图片不能下载，需要设置 - 重置浏览器

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
