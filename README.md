# service项目依赖工具包

## 使用场景
service项目依赖使用。集成了dal层访问模式，pegion的注解bean，dp公用组件等。如果dp公用组件升级，可以直接升级本工具包，然后其他依赖于本包的项目再做升级

## DB连接需要的文件
- 如果修改项目，以下三个文件可以直接在csc-framework-service中获取，为了使用dao类自动生成工具，文件的存放路径要和csc-framework-service中的一致
- 如果新增项目，code上创建项目的时候可以直接从git@code.dianpingoa.com:ba-csc/csc-framework.git clone，然后按照csc-framework中的说明改造

## 1.1 appcontext-db.xml
文件目录：resources/config/spring/local/
该文件用于配置数据源， value为数据库的dal配置，如下：

````xml

     需要用dal配置一个datasource,如：
     <!-- 数据源-->
     <bean id="dataSource" class="com.dianping.zebra.group.jdbc.GroupDataSource" init-method="init">
        <property name="jdbcRef" value="csconline"/>
     </bean>

````

##  1.2 appcontext-dao.xml
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

### 1.3 sqlmap.config
文件目录：resources/config/sqlmap/
配置sqlmap 具体文件,如下:

```xml

     <sqlMap resource="config/sqlmap/sqlmap-people.xml"/>

```

## 自动生成Dao/bean/sqlmap/DaoTest 类
本项目包含了生成如题这些类的模板文件。
假设TestEntity为数据中的实体类，如果需要生成，可以使用如下代码：

```java

      DaoCodeGenerate.generateByJavaBean(TestEntity.class);

```
为了和实体类名称对应。数据库表名不能包含下划线。

