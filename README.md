私 - 直销核算系统V2.0 for Admin
Update Log
---
#####20141220
* 增加历史月业绩查询功能，可以按月车讯会员历史业绩
* 在结算业绩查询中,不仅可以查看历史加入的所有会员，还可以只看当月新增的会员

#####20141108
* 增加工具箱功能，按月统计并导出各星级会员

#####20140822
* 在录错的情况下，支持会员修改上线
* 在录错的情况下，支持批量删除会员

#####20140720
* 添加存储过程angel.copyTableHis()备份历史表，重置计数器

```
ALTER TABLE t_achieve_his  AUTO_INCREMENT =1;
```
* 界面增加选择时间范围对业绩奖金进行核算
* 谷歌浏览器如发现图片不能下载，需要[设置 - 重置浏览器]

#####20140716
* 会员管理模块中：会员网络图支持PNG格式导出
* 会员管理增加时间范围检索，与星级组合查询可以了解每个月的会员加入情况
* 新增页面载入动画效果，每个页面增加刷新功能
* 整理了领导奖的计算思路

Configuration
-------------
* IntelljIdea V12
* 热部署插件`jrebel` (class文件修改在idea中需要重新make/compile)
* Gradle插件JetGradle&Debug Run With JRebel
* [配置过程详细](http://jiangzx.github.io/2014/10/03/idea-for-gradle-service/)

Database
--------
* Mysql5.6/3307
* Mysql中新建库名 -angel
* 自动建表(用户表`t_purchaser_info`账号/密码:010000/0000)

Frontside
---------
* [EasyUI](http://www.jeasyui.com/)
* [Twitter Bootstrap](http://v3.bootcss.com/css/)

Backend
-------
* SpringMVC & Nutz

Skill
-----
* view java doc `CTRL +Q / CTRL +P`
* auto import class `Alt + Enter`
* try/catch/finally `CTRL+ ALT+ T`

Run
------------
1. gradle clean war
2. 启动服务后，访问 http://localhost:8081/login
