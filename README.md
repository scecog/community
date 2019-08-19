#问答社区
## 部署
### 依赖
-git
-JDK
-Maven
-Mysql
## 步骤
- yum update
- yum install git
- mkdir APP
- git clone https://github.com/scecog/community.git
- yum install maven
- mvn clean compile package
- java -jar -Dspring.profiles.active=production target/communi
  ty-0.0.1-SNAPSHOT.jar
#资料
[GithubOAuth](https://developer.github.com/apps/)
#步骤
1. 编写导航栏，直接使用bootstrap组件来应用  
[bootstrap](https://v3.bootcss.com/components/)
2. 使用其中的导航栏组件，更改其中代码直接使用，作为登录
，使用github的接口来进行认证授权，使用github的认证接口，文档：
注册接口使用认证  
[githubOAuth](https://developer.github.com/apps/building-oauth-apps/creating-an-oauth-app/)  
注册之后的使用方法文档:  
[使用文档](https://developer.github.com/apps/building-oauth-apps/authorizing-oauth-apps/)  
在此过程中需要使用okhttp，文档如下:  
[okhttp](https://square.github.io/okhttp/)  
使用这些技术完成github的授权登录，但是出现每次服务器重启之后，session会重新创建，登录状态不能保持，这就需要将登录信息存储到
数据库中。
3. 使用mysql数据库，建表语句如下  
```$xslt
create TABLE user(
 id INT(30) PRIMARY KEY auto_increment,
 account_id   VARCHAR(100),
 name        VARCHAR(30),
 token        CHAR(36),
 gmt_create   BIGINT,
 gmt_modified BIGINT
 )
```
4. 针对于登录的用户，每次登录都会进行插入数据库操作，产生token的随机数
，将token设置为cookie，然后再存入session，取出用户信息，这样做有很明显的弊端
每次都向数据库进行插入操作，后序进行优化
5. 登录完成后，添加了发布问题的界面html，publish.html
6. 需要将发布的问题都存储到数据库中，建表语句如下:  
```css
CREATE TABLE question(
id INT PRIMARY KEY auto_increment,
title VARCHAR(50),
descriptions TINYTEXT,
gmt_create BIGINT,
gmt_modified BIGINT,
crator int,
comment_count int DEFAULT 0,
view_count int DEFAULT 0,
like_count int DEFAULT 0,
tag VARCHAR(256)
);

7. 发布了问题之后存储到了数据库，然后需要将存储的数据在主页展示出来，在主页添加拿出存储的数据的方法，对主页进行修改
使用lombok来简化开发，并且使用双表查询查出Question的信息及头像信息（User）


8. 完成了问题列表的分页实现，并且将导航栏进行封装
9. 实现了查看自己问题的界面，自己可以看到自己发表的问题，并且修复了以前登录的时候，每次登录都会向数据库插入一条数据的问题，现在的模式是如果存在用户，就进行更新，否则进行插入操作
10. 实现了拦截器，对于任何请求都需要进行cookie的校验，实现了退出登录的功能，使cookie变为空。

11. 实现了阅读数，并且准备增加评论功能  
12. 评论功能数据库设计：  
 

   create TABLE comment(
   id BIGINT PRIMARY KEY auto_increment,
   parent_id BIGINT NOT NULL COMMENT '父类id',
   type INT COMMENT '确认是几级评论',
   comment_id INT COMMENT '评论人的id',
   comment_description text COMMENT '评论内容',
   gmt_create BIGINT NOT NULL COMMENT '评论创建时间',
   gmt_modified BIGINT NOT NULL COMMENT '评论修改时间',
   like_count BIGINT DEFAULT 0 COMMENT '点赞数'
   )
13. 实现评论功能采用前后端分离的思想，使评论在页面上进行局部刷新即可，实现评论的接口，  
对于回复的是问题或者是评论，进行不同的刷新页面。
14. 实现了评论时的异常处理

15. 实现了评论问题列表的功能，对于其中使用的jdk8 的特性需要掌握，优化了回复列表的页面
16. 实现了二级评论的功能，并且实现了标签的规范化，来以此标签查询到相关的问题展示出来
17. 实现了最新回复的功能及通知功能，并且实现了使用editer.md实现了markdown语法的使用

  




