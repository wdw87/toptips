package com.wdw.toptips.model;

import java.util.Date;
import java.util.Objects;

/**
 * 用户点赞了哪条新闻
 */
public class LikeRecord {

    private int newsId;
    private int userId;
    private int status;
    private Date createdDate;

    public LikeRecord(){}

    public LikeRecord(int newsId, int userId, int status, Date createdDate) {
        this.newsId = newsId;
        this.userId = userId;
        this.status = status;
        this.createdDate = createdDate;
    }

    public int getNewsId() {
        return newsId;
    }

    public void setNewsId(int newsId) {
        this.newsId = newsId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LikeRecord that = (LikeRecord) o;
        return newsId == that.newsId &&
                userId == that.userId &&
                status == that.status &&
                Objects.equals(createdDate, that.createdDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(newsId, userId, status, createdDate);
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
}
