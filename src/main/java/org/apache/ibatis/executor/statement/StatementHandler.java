/**
 *    Copyright 2009-2016 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package org.apache.ibatis.executor.statement;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.apache.ibatis.cursor.Cursor;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.session.ResultHandler;

/**
 * @author Clinton Begin
(1. 对于JDBC的PreparedStatement类型的对象，创建的过程中，
我们使用的是SQL语句字符串会包含 若干个? 占位符，我们其后再对占位符进行设值。

StatementHandler通过parameterize(statement)方法对Statement进行设值；       

(2.StatementHandler通过List<E> query(Statement statement,
ResultHandler resultHandler)方法来完成执行Statement，和将Statement对象返回的resultSet封装成List；

 */
public interface StatementHandler {

  //根据标准的数据库链接生成Statement
  Statement prepare(Connection connection, Integer transactionTimeout)
      throws SQLException;

  //对statement设置参数接口
  void parameterize(Statement statement)
      throws SQLException;

  void batch(Statement statement)
      throws SQLException;

  int update(Statement statement)
      throws SQLException;

  //执行具体的SQL查询
  <E> List<E> query(Statement statement, ResultHandler resultHandler)
      throws SQLException;

  <E> Cursor<E> queryCursor(Statement statement)
      throws SQLException;

  BoundSql getBoundSql();

  ParameterHandler getParameterHandler();

}
