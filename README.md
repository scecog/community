#问答社区
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

