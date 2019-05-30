package com.wdw.toptips.dao;

import com.wdw.toptips.model.Message;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

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




}
