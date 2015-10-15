## csc-common-service

### 1 DB连接需要的文件
#### 1 appcontext-db.xml
````xml

     需要用dal配置一个datasource,如：
     <!-- 数据源-->
     <bean id="dataSource" class="com.dianping.zebra.group.jdbc.GroupDataSource" init-method="init">
        <property name="jdbcRef" value="csconline"/>
     </bean>

````
#### 2 appcontext-dao.xml(配置自定义的dao bean)

#### 3 sqlmap.config 以及具体的sqlmap文件


### 2 自动生成Dao/sqlmap/Dao 测试类

