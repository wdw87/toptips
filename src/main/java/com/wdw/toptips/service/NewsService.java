package com.wdw.toptips.service;

import com.wdw.toptips.dao.NewsDAO;
import com.wdw.toptips.model.News;
import com.wdw.toptips.util.ToutiaoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

@Service
public class NewsService {
    @Autowired
    private NewsDAO newsDao;

    public List<News> getLatestNews(int userId, int offset, int limit) {
        return newsDao.selectByUserIdAndOffset(userId, offset, limit);
    }

    public int addNews(News news){
        newsDao.addNews(news);
        return news.getId();
    }

<<<<<<< HEAD
    public void updateCommentCount(int commentCount,int id){
        newsDao.updateNewsCommentCount(commentCount,id);
    }

    public News getNewsById(int newsId){
        return newsDao.selectById(newsId);
    }

=======
>>>>>>> 3cc66204b4719be79d987ead743b7e288db734aa
    /**
     * 将文件保存到本地
     *
     * @param file
     * @return 文件链接
     */
    public String saveImage(MultipartFile file) throws IOException {
        if (file == null) {
            return null;
        }
        //String name = file.getName();   //不是这个
        String originalName = file.getOriginalFilename();
        int dotPos = originalName.lastIndexOf(".");
        if (dotPos < 0) {
            return null;
        }
        String fileExt = originalName.substring(dotPos + 1).toLowerCase();
        if (!ToutiaoUtil.isAllowedFile(fileExt)) {
            return null;
        }
        //随机产生一个文件名
        String fileName = UUID.randomUUID().toString().replaceAll("-", "") + "." + fileExt;
        Files.copy(file.getInputStream(), new File(ToutiaoUtil.IMG_DIR + fileName).toPath(),
                StandardCopyOption.REPLACE_EXISTING);
        return ToutiaoUtil.TOPTIPS_DOMAIN + "image?name=" + fileName;
    }

}
