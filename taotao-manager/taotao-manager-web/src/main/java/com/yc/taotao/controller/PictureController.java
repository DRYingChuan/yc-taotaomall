package com.yc.taotao.controller;

import com.yc.common.pojo.PictureResult;
import com.yc.common.utils.JsonUtils;
import com.yc.taotao.service.PictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by yc on 17-2-23.
 */
@Controller
public class PictureController {
    @Autowired
    private PictureService pictureService;
    @RequestMapping("/pic/upload")
    @ResponseBody
    public String uploadFile(MultipartFile uploadFile){
       PictureResult pictureResult= pictureService.uploadPic(uploadFile);
       String results= JsonUtils.objectToJson(pictureResult);
       return results;
    }
}
