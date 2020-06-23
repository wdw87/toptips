package com.wdw.toptips.dao;

import com.wdw.toptips.model.LikeRecord;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Mapper
@Component
public interface UserLikeNewsDao {
    String TABLE_NAME = "user_like_news";

    @Select({"select count(*) from " ,TABLE_NAME, " where news_id = #{newsId}"})
    int selectRecordCountByNewsId(@Param("newsId") int newsId);

    @Select({"select * from " ,TABLE_NAME , " where user_id = #{id}"})
    List<LikeRecord> selectByUserId(@Param("id") int id);

    @Select({"select * from " , TABLE_NAME, " where news_id = #{id}"})
    List<LikeRecord> selectByNewsId(@Param("id") int id);

    @Select({"select * from " , TABLE_NAME, " where news_id = #{newsId} and user_id = #{userId}"})
    LikeRecord selectByNewsIdAndUserId(@Param("newsId") int newsId, @Param("userId") int userId);

    @Insert({"insert into ",TABLE_NAME," values(#{newsId},#{userId},#{status},#{createdDate})"})
    int addRecord(LikeRecord record);

    @Insert({"update ",TABLE_NAME," set status = #{status}, created_date = #{createdDate} where news_id = #{newsId} and user_id = #{userId} "})
    int updateById(@Param("newsId") int newsId, @Param("userId") int userId, @Param("status") int status, @Param("createdDate")Date createdDate);

}
