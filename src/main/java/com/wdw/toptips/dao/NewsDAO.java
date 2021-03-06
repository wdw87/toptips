package com.wdw.toptips.dao;

import com.wdw.toptips.model.News;
import com.wdw.toptips.model.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface NewsDAO {
    String TABLE_NAME = "news";
    String INSERT_FIELDS = " title, link, image, like_count, comment_count, created_date, user_id ";
    String SELECT_FIELDS = " id, " + INSERT_FIELDS;

    @Insert({"insert into ", TABLE_NAME, "(", INSERT_FIELDS,
            ") values (#{title},#{link},#{image},#{likeCount},#{commentCount},#{createdDate},#{userId})"})
    int addNews(News news);

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where id=#{id}"})
    News selectById(int id);

    List<News> selectByUserIdAndOffset(@Param("userId") int userId, @Param("offset") int offset,
                                       @Param("limit") int limit);

    @Update({"update ", TABLE_NAME, " set comment_count=#{commentCount} where id=#{id}"})
    void updateNewsCommentCount(@Param("commentCount") int commentCount,@Param("id") int id);

    @Update({"update ", TABLE_NAME, " set like_count=#{likeCount} where id=#{id}"})
    void updateNewsLikeCount(@Param("likeCount") int likeCount,@Param("id") int id);

    @Select({"SELECT like_count from ",TABLE_NAME," where id = #{id}"})
    int selectLikeCount(@Param("id") int id);
}
