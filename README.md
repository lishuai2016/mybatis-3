# mybatis源码学习

> 总结

>> 1、初始化的整体流程

从大的方面来看mybatis的初始化首先都是先根据配置文件（xml或者bean）生成Configuration对象，然后基于Configuration对象创建SqlSessionFactory对象，然后基于SqlSessionFactory对象获取sqlsession对象[内部包含jdbc的数据库链接]，然后基于sqlsession完成对数据库的增删改查。

- 1、直接使用sessionsql方式：和spring整合使用的一般流程DataSource--->SqlSessionFactory--->SqlSessionTemplate[继承SqlSession]
- 2、使用mapper接口的方式：和spring整合使用的一般流程DataSource--->SqlSessionFactory--->MapperScannerConfigurer[指定mapper接口所在的包路径basePackage]

备注：在生成SqlSessionFactory的时候会把有mapping.xml的文件信息读取并解析保存。通过mapper接口的方式最后还是调用sqlsession来实现，所以sqlsession是重点。

>> 2、什么时候创建数据库的connection链接

只有在执行具体的SQL语句的时候，才会从sqlsession中间接通过DataSource来获取数据库的链接connection

org.apache.ibatis.transaction.jdbc.JdbcTransaction#openConnection
```java
protected void openConnection() throws SQLException {
    if (log.isDebugEnabled()) {
      log.debug("Opening JDBC Connection");
    }
    connection = dataSource.getConnection();
    if (level != null) {
      connection.setTransactionIsolation(level.getLevel());
    }
    setDesiredAutoCommit(autoCommit);
  }
```

>> 3、一级缓存和二级缓存

- 3.1、一级缓存，默认开启

- 3.2、二级缓存，要想使某条Select查询支持二级缓存，你需要保证：

1.MyBatis支持二级缓存的总开关：全局配置变量参数   cacheEnabled=true

2.该select语句所在的Mapper，配置了<cache> 或<cached-ref>节点，并且有效

3.该select语句的参数 useCache=true

MyBatis并不是简单地对整个Application就只有一个Cache缓存对象，它将缓存划分的更细，即是Mapper级别的，即每一个Mapper都可以拥有一个Cache对象，具体如下：

a.为每一个Mapper分配一个Cache缓存对象（使用<cache>节点配置）；

b.多个Mapper共用一个Cache缓存对象（使用<cache-ref>节点配置）；


- 3.3、一级缓存和二级缓存的使用顺序

请注意，如果你的MyBatis使用了二级缓存，并且你的Mapper和select语句也配置使用了二级缓存，那么在执行select查询的时候，MyBatis会先从二级缓存中取输入，其次才是一级缓存，即MyBatis查询数据的顺序是：二级缓存    ———> 一级缓存——> 数据库


- 3.4、二级缓存实现的选择

    MyBatis对二级缓存的设计非常灵活，它自己内部实现了一系列的Cache缓存实现类，并提供了各种缓存刷新策略如LRU，FIFO等等；另外，MyBatis还允许用户自定义Cache接口实现，用户是需要实现org.apache.ibatis.cache.Cache接口，然后将Cache实现类配置在<cache  type="">节点的type属性上即可；除此之外，MyBatis还支持跟第三方内存缓存库如Memecached的集成，总之，使用MyBatis的二级缓存有三个选择:

1.MyBatis自身提供的缓存实现；

2.用户自定义的Cache接口实现；

3.跟第三方内存缓存库的集成；


MyBatis将数据缓存设计成两级结构，分为一级缓存、二级缓存：

一级缓存是Session会话级别的缓存，位于表示一次数据库会话的SqlSession对象之中，又被称之为本地缓存。一级缓存是MyBatis内部实现的一个特性，用户不能配置，默认情况下自动支持的缓存，用户没有定制它的权利（不过这也不是绝对的，可以通过开发插件对它进行修改）；

一级缓存的工作机制：一级缓存是Session会话级别的，一般而言，一个SqlSession对象会使用一个Executor对象来完成会话操作，Executor对象会维护一个Cache缓存，以提高查询性能

二级缓存是Application应用级别的缓存，它的是生命周期很长，跟Application的声明周期一样，也就是说它的作用范围是整个Application应用。

  如上所言，一个SqlSession对象会使用一个Executor对象来完成会话操作，MyBatis的二级缓存机制的关键就是对这个Executor对象做文章。如果用户配置了"cacheEnabled=true"，那么MyBatis在为SqlSession对象创建Executor对象时，会对Executor对象加上一个装饰者：CachingExecutor，这时SqlSession使用CachingExecutor对象来完成操作请求。CachingExecutor对于查询请求，会先判断该查询请求在Application级别的二级缓存中是否有缓存结果，如果有查询结果，则直接返回缓存结果；如果缓存中没有，再交给真正的Executor对象来完成查询操作，之后CachingExecutor会将真正Executor返回的查询结果放置到缓存中，然后在返回给用户。

MyBatis的二级缓存设计得比较灵活，你可以使用MyBatis自己定义的二级缓存实现；你也可以通过实现org.apache.ibatis.cache.Cache接口自定义缓存；也可以使用第三方内存缓存库，如Memcached等












# 参考
- [github别人的源码解读](https://github.com/tuguangquan/mybatis)
- [github相关项目](https://github.com/search?q=mybatis)
- [mybatis-3官方文档](http://www.mybatis.org/mybatis-3/zh/configuration.html)
- [深入理解mybatis原理博客](https://blog.csdn.net/luanlouis/article/details/37744073)
- [原理分析之二：框架整体设计](https://chenjc-it.iteye.com/blog/1460990)








