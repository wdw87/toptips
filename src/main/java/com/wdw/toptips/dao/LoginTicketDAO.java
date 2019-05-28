package com.wdw.toptips.dao;

import com.wdw.toptips.model.LoginTicket;
import org.apache.ibatis.annotations.*;

/**
 * 添加了@Mapper注解的接口在编译时回生成相应的类
 */
@Mapper
public interface LoginTicketDAO {
    String TABLE_NAME = "login_ticket";
    String INSERT_FIELDS = " user_id, ticket, expired, status ";
    String SELECT_FIELDS = " id, user_id, ticket, expired, status";

    //注意：mybatis自动匹配下划线命名和驼峰命名，#{ }中为Java变量名
    @Insert({"insert into ", TABLE_NAME, "(", INSERT_FIELDS,
            ") values (#{userId},#{ticket},#{expired},#{status})"})
    int addTicket(LoginTicket ticket);

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where ticket=#{ticket}"})
    LoginTicket selectByTicket(String ticket);

    @Update({"update ", TABLE_NAME, " set status=#{status} where ticket=#{ticket}"})
        //@Update({"update user set password= #{password} where id=#{id}"})
    void updateStatus(@Param("ticket") String ticket, @Param("status") int status);
}
