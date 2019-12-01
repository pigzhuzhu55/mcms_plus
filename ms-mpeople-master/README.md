#用户插件
包含登陆、注册、取回密码、修改密码、基本资料编辑、后台用户管理等常规功能
> [java doc](http://api.ms.mingsoft.net/people)

> [JavaScript API Doc](http://ms.mingsoft.net/html/86/6507/6511/index.html)

#maven依赖方式使用
```
<!--war包含用户模块编译好的字节码文件与前端试图ftl页面-->
<dependency>
  <groupId>net.mingsoft</groupId>
  <artifactId>ms-mpeople</artifactId>
  <version>1.0.0-SNAPSHOT</version>
  <type>war</type>
</dependency>

<!--用户模块的源代码-->
<dependency>
  <groupId>net.mingsoft</groupId>
  <artifactId>ms-mpeople</artifactId>
  <version>1.0.0-SNAPSHOT</version>
  <classifier>sources</classifier>
  <scope>provided</scope>
</dependency>

<!--jar包依赖方便业务开发调用-->
<dependency>
  <groupId>net.mingsoft</groupId>
  <artifactId>ms-mpeople</artifactId>
  <version>1.0.0-SNAPSHOT</version>
  <classifier>classes</classifier>
  <scope>provided</scope>
</dependency> 
```
#源码方式使用
直接将在MStore下载的源代码解压覆盖到项目中，编译并运行

