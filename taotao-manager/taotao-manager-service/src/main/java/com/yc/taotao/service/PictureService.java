package com.yc.taotao.service;

import com.yc.common.pojo.PictureResult;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by yc on 17-2-22.
 */
public interface PictureService {
    PictureResult uploadPic(MultipartFile multipartFile);
}
