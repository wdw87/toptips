# toptips
来自于牛客网中级项目,仿今日头条网站





### 点赞缓存表结构以及逻辑

#### MySQL表结构

~~~sql
DROP TABLE IF EXISTS `user_like_news`;
CREATE TABLE `user_like_news` (
    `news_id` int(11) unsigned NOT NULL,
    `user_id` int(11) unsigned NOT NULL,
    `status` int NOT NULL ,
    `created_date` datetime NOT NULL,
  PRIMARY KEY (`news_id`, `user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
~~~

将news_id和user_id设置为联合主键

### Redis表结构
> 两个set 两个键值对
* LIKE;NEWS_SET   新闻状态变动set，所有缓存期间，点赞状态改变过的新闻id都将被放入
* LIKE;NEWS_USER_SET;{news_id}  以news_id为key，所有给这条新闻点过赞或者取消过赞的用户id
* LIKE;LIKE_RECORD;{news_id};{user_id}  对应MySQL中的user_like_news表，存放点赞记录对象的json串
* LIKE;COUNTER;{news_id}  每条新闻的点赞计数器，可以为负值

### 定时刷回逻辑

~~~
while(LIKE;NEWS_SET不为空，弹出一个news_id){
	while(LIKE;NEWS_USER_SET;{news_id}不为空，弹出一个user_id){
		从LIKE;LIKE_RECORD;{news_id};{user_id}获取点赞记录；
		从数据库获取原始点赞记录；
		数据不一致则刷回数据库;
		删掉LIKE;LIKE_RECORD;{news_id};{user_id};
	}
	从LIKE;COUNTER;{news_id}获取点赞计数器;
	更新数据库中新闻表的点赞数量;
	删掉LIKE;COUNTER;{news_id};
}
~~~

