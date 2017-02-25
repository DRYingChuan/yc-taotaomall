package com.yc.taotao.service.impl;

import com.yc.common.pojo.PictureResult;
import com.yc.common.utils.FastDFSClient;
import com.yc.taotao.service.PictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by yc on 17-2-23.
 */
@Service
public class PictureServiceImpl implements PictureService {
    @Autowired
    private FastDFSClient fastDFSClient;

    @Value("${IMAGE_SERVER_BASE_PATH}")
    private String IMAGE_SERVER_BASE_PATH;
    @Override
    public PictureResult uploadPic(MultipartFile multipartFile) {
        PictureResult pictureResult=new PictureResult();
        if (multipartFile.isEmpty()){
            pictureResult.setError(1);
            pictureResult.setMessage("图片为空");
        }
        //上传到图片服务器
        try {
            //取图片扩展名
            String originalFilename = multipartFile.getOriginalFilename();
            //取扩展名不要“.”
            String extName = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
           // FastDFSClient client = new FastDFSClient("classpath:properties/client.conf");
            String url = fastDFSClient.uploadFile(multipartFile.getBytes(), extName);
            //把url响应给客户端
            url=IMAGE_SERVER_BASE_PATH+url;
            System.out.println(url);
            pictureResult.setError(0);
            pictureResult.setUrl(url);
        } catch (Exception e) {
            e.printStackTrace();
            pictureResult.setError(1);
            pictureResult.setMessage("图片上传失败");
        }
        return pictureResult;
    }
}
