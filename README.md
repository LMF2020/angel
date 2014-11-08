## 系统主页
============
http://rj-angel.github.io/angel/

##更新日志
----------
####20141108
* 增加工具箱功能，按月统计并导出各星级会员
    
####20140822
* 在录错的情况下，支持会员修改上线
* 在录错的情况下，支持批量删除会员

####20140720
* 添加存储过程angel.copyTableHis()备份历史表,重置计数器`ALTER TABLE t_achieve_his  AUTO_INCREMENT =1;`
* 界面增加选择时间范围对业绩奖金进行核算
* 谷歌浏览器如发现图片不能下载，需要设置 - 重置浏览器

####20140716
* 会员管理模块中：会员网络图支持PNG格式导出
* 会员管理增加时间范围检索,与星级组合查询可以了解每个月的会员加入情况
* 新增页面载入动画效果,每个页面增加刷新功能
* 整理了领导奖的计算思路

##环境配置
----------
1.Intellj idea 12
2.热部署插件`jrebel` (class文件修改在idea中需要重新make/compile)
3.Gradle插件JetGradle&Debug Run With JRebel
4.启动服务访问 http://localhost:8081/login
5.(配置过程详细)[http://blog.csdn.net/hf_xiaoyou/article/details/26093267]

##数据库
--------
1.Mysql5.6/3307
2.Mysql中新建库名 -angel
3.自动建表(用户表`t_purchaser_info`账号/密码:000000/0000)

##前端
------
(EasyUI)[http://www.jeasyui.com/]
(Twitter Bootstrap)[http://getbootstrap.com/css/]

##后端
------
SpringMVC / Nutz

##Idea技巧&快捷键
-----------------
1.view java doc `CTRL +Q / CTRL +P`
2.auto import class `Alt + Enter`
3.try/catch/finally `CTRL+ ALT+ T`
