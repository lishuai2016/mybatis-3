package org.apache.ibatis.lstest;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.jupiter.api.Test;

import java.io.InputStream;

/**
 * @program: mybatis
 * @author: lishuai
 * @create: 2019-06-20 20:49
 */
public class MyTest {

    /**
     * 测试读写xml配置，通过sqlsession来访问数据库
     * @throws Exception
     */
    @Test
    public void test_xml() throws Exception {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession session = sqlSessionFactory.openSession();
        try {
            User user = session.selectOne("org.apache.ibatis.lstest.UserMapper.selectUserById", 1);

            System.out.println(user);
        } finally {
            session.close();
        }
    }


    /**
     * 测试读写mapper配置
     * @throws Exception
     */
    @Test
    public void test_mapper() throws Exception {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession session = sqlSessionFactory.openSession();
        try {

            UserMapper mapper = session.getMapper(UserMapper.class);
            User user = mapper.selectUserById(1);
            System.out.println(user);
        } finally {
            session.close();
        }
    }



}
