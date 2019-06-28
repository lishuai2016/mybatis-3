package org.apache.ibatis.lstest;

/**
 * @program: mybatis
 * @author: lishuai
 * @create: 2019-06-20 19:54
 */
public interface UserMapper {
    User selectUserById(Integer id);
}
