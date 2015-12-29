# service项目依赖工具包

## 1 使用场景
service项目依赖使用。集成了dal层访问模式，pegion的注解bean，dp公用组件等。如果dp公用组件升级，可以直接升级本工具包，然后其他依赖于本包的项目再做升级

## 2 工具包情况

最新的工具包依赖版本

````xml

       <dependency>
           <groupId>com.dianping</groupId>
           <artifactId>csc-common-service</artifactId>
           <version>2.2.3</version>
       </dependency>

````


功能点包含：
1. 点评公用jar包依赖
2. dal层使用方式定义
3. dao层相关类模板定义
4. dao层自动生成工具
5. 测试相关基础服务
6. 日志统一输出
...

## 3 DB连接需要的文件
- 如果修改项目，以下三个文件可以直接在csc-framework-service中获取，为了使用dao类自动生成工具，文件的存放路径要和csc-framework-service中的一致
- 如果新增项目，code上创建项目的时候可以直接从git@code.dianpingoa.com:ba-csc/csc-framework.git clone，然后按照csc-framework中的说明改造

### 3.1 appcontext-db.xml
文件目录：resources/config/spring/local/
该文件用于配置数据源， value为数据库的dal配置，如下：

````xml

     需要用dal配置一个datasource,如：
     <!-- 数据源-->
     <bean id="dataSource" class="com.dianping.zebra.group.jdbc.GroupDataSource" init-method="init">
        <property name="jdbcRef" value="csconline"/>
     </bean>

````

###  3.2 appcontext-dao.xml
文件目录：resources/config/spring/local/
配置dao bean，如下：

```xml

      <bean id="peopleDao" parent="parentDao">
        <property name="proxyInterfaces" value="com.dianping.csc.demo.dao.PeopleDao"/>
        <property name="target">
          <bean parent="daoRealizeTarget">
            <constructor-arg value="People"/>
          </bean>
        </property>
      </bean>

```

### 3.3 sqlmap.config
文件目录：resources/config/sqlmap/
配置sqlmap 具体文件,如下:

```xml

     <sqlMap resource="config/sqlmap/sqlmap-people.xml"/>

```

## 4 自动生成Dao/bean/sqlmap/DaoTest 类

本项目包含了生成如题这些类的模板文件。
假设TestEntity为数据中的实体类，如果需要生成，可以使用如下代码：

```java

      DaoCodeGenerate.generateByJavaBean(TestEntity.class);

```
为了和实体类名称对应。数据库表名不能包含下划线。

## 5 服务器日志输出

目前作为dp的监控平台，cat已经提供了很多强大的功能。但是还有一些缺点，不能很好的满足客服这边的需要。
- cat监控的粒度时服务器级别，如果一个应用比如海豚进线很快很多个应用，这样进线出错的时候定位时哪个应用也比较费时
- cat监控的普通日志明细不能在页面上查询到，不能在测试时方便的看到整个任务的执行流水。

本工具包提供的服务器打点功能可以解决cat的上面两个问题

### 5.1 webpush 输出日志到页面

### 5.1.1 定义appender

```xml

    appender：
    <appender name="webPush" class="com.dianping.csc.common.service.util.WebPushAppender">
        <param name="namespace" value="csc-online-monitor"/>
        <param name="topic" value="/topic/dialog-monitor"/>
        <param name="app" value="csc-online-service"/>
        <param name="lionSwitch" value="csc-online-service.log.webpush.enabled"/>
        <layout class="org.apache.log4j.PatternLayout">
            <param name="ConversionPattern" value="%d: %-5p  %m%n"/>
        </layout>
    </appender>

    参数名：
    1.namespace 接入websocket-service 的namespace 名称，接入文档可以参考 http://wiki.sankuai.com/display/HAOBAO/web+push
    2.topic 输出的topic名称
    3.app 本应用的名称
    4.lion开关 ，是否打开输出工具，value为lion值，强烈建议在beta打开，线上关闭该功能，lion值为1 为打开状态
```

### 5.1.2 消费日志

   在需要统一监控日志的页面上输出topic的内容即可。具体使用也可以参考websocket-service 的ReadMe

```javascript

    var socketClient = new SocketClient();
    socketClient.connect(namespace, 'monitor', function () {
        socketClient.subscribe(topic, function (msg) {
            $("#monitor-show-area").append(msg.data + "<br/>");
        });
    }, function () {

    }, {
        "domain": "http://192.168.216.155/"
    });
```

   展示的效果如：

![image](http://code.dianpingoa.com/ba-csc/csc-common-service/raw/lightmerge/src/main/resources/img/webPushAppender.png)

http://code.dianpingoa.com/ba-csc/csc-common-service/tree/lightmerge
