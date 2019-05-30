package com.wdw.toptips.dao;

import ch.qos.logback.classic.db.names.TableName;
import com.wdw.toptips.model.Message;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.lang.annotation.Target;
import java.util.List;

/**
 * @Author: Wudw
 * @Date: 2019/5/29 21:16
 * @Version 1.0
 */
@Mapper
public interface MessageDao {

    String TABLE_NAME = "message";
    String INSERT_FIELDS = " from_id, to_id, content,created_date, has_read,conversation_id ";
    String SELECT_FIELDS = " id, " + INSERT_FIELDS;

    @Insert({"insert into ", TABLE_NAME, "(", INSERT_FIELDS,
            ") values (#{fromId},#{toId},#{content},#{createdDate},#{hasRead},#{conversationId})"})
    int addMessage(Message message);

    @Select({"select ",SELECT_FIELDS ," from ",TABLE_NAME," where conversation_id = #{conversationId} order by id desc limit #{offset},#{limit}"})
    List<Message> getMessageDetail(@Param("conversationId") String conversationId,
                                   @Param("offset") int offset,
                                   @Param("limit") int limit);

    @Select({"select count(id) from ", TABLE_NAME," where has_read = 0 and to_id = #{userId} and conversation_id = #{conversationId}"})
    int getUnReadCount(@Param("userId") int userId,
                       @Param("conversationId") String conversationId);

    //加上limit 999999防止mysql5.7以上版本对Derived table的优化，导致不能先排序再分组
    //select *,count(id) as cnt from (select * from toutiao.message where from_id = 12 or to_id = 12 order by id desc limit 999999)tt group by conversation_id
    @Select({"select ",INSERT_FIELDS,",count(id) as id from (select ",SELECT_FIELDS," from ",TABLE_NAME," where from_id = #{userId} or to_id = #{userId} order by id desc limit 999999)tt group by conversation_id limit #{offset},#{limit}"})
    List<Message> getConversationList(@Param("userId") int userId,
                                      @Param("offset") int offset,
                                      @Param("limit") int limit);


}
