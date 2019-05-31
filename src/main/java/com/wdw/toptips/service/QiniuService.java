package com.wdw.toptips.service;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import com.wdw.toptips.util.ToutiaoUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

/**
 * @Author: Wudw
 * @Date: 2019/5/24 17:43
 * @Version 1.0
 */
@Service
public class QiniuService {
    private static final Logger logger = LoggerFactory.getLogger(QiniuService.class);

    /**
     * @param file
     * @return 图片在七牛上的url
     * @throws IOException
     */
    public String saveImage(MultipartFile file) throws IOException {


        if (file == null) {
            return null;
        }
        //String name = file.getName();   //不是这个
        String originalName = file.getOriginalFilename();
        //获取文件名
        String fileExt = ToutiaoUtil.getFileExt(originalName);
        if (!ToutiaoUtil.isAllowedFile(fileExt)) {
            return null;
        }
        //构造一个带指定Zone对象的配置类
        Configuration cfg = new Configuration(Zone.zone0());
        //...其他参数参考类注释

        UploadManager uploadManager = new UploadManager(cfg);
        //...生成上传凭证，然后准备上传
        String accessKey = "9xrSiaDOoPjO2zRYMkVgCjJdNys07_UrSHv23Vxq";
        String secretKey = "sflkB5KSs-dfnlxNoNRE1NmK___lfRzlhq4J82rX";
        String bucket = "ttb1";

        //默认不指定key的情况下，以文件内容的hash值作为文件名
        //String key = null;
        //随机产生一个文件名
        String key = UUID.randomUUID().toString().replaceAll("-", "") + "." + fileExt;
        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);

        try {
            Response response = uploadManager.put(file.getInputStream(), key, upToken, null, null);
            //解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
//            System.out.println(response.bodyString());
//            System.out.println(putRet.key);
//            System.out.println(putRet.hash);
            return ToutiaoUtil.QINIU_DOMAIN_PREFIX + JSONObject.parseObject(response.bodyString()).get("key").toString();
        } catch (QiniuException ex) {
            Response r = ex.response;
            logger.error("上传图片至七牛云失败" + ex.getMessage());
            return ToutiaoUtil.getJSONString(1, "上传至七牛云失败");
        }
    }


}
