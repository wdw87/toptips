package com.wdw.toptips.dao;

import com.wdw.toptips.model.Comment;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Author: Wudw
 * @Date: 2019/5/28 20:51
 * @Version 1.0
 */
@Mapper
public interface CommentDao {
    String TABLE_NAME = "comment";
    String INSERT_FIELDS = " content, user_id, entity_id, entity_type, created_date, status ";
    String SELECT_FIELDS = " id," + INSERT_FIELDS;

    @Insert({"insert into ", TABLE_NAME, "(", INSERT_FIELDS,
            ") values (#{content},#{userId},#{entityId},#{entityType},#{createdDate},#{status})"})
    int addComment(Comment comment);

    @Select({"select " + SELECT_FIELDS," from ",TABLE_NAME,
            " where entity_id = #{entityId} and entity_type = #{entityType} order by id desc"})
    List<Comment> selectByEntity(@Param("entityId") int entityId,@Param("entityType") int entityType);

    @Select({"select count(id) from ",TABLE_NAME,
            " where entity_id = #{entityId} and entity_type = #{entityType}"})
    int getCommentCount(@Param("entityId") int entityId,@Param("entityType") int entityType);


}
