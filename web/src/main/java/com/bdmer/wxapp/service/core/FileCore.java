package com.bdmer.wxapp.service.core;

import com.bdmer.wxapp.common.enums.ResponseEnum;
import com.bdmer.wxapp.common.exception.WxException;
import com.bdmer.wxapp.common.tool.B;
import com.bdmer.wxapp.common.tool.Util;
import com.bdmer.wxapp.dto.response.ResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

/**
 * 文件上传管理 - 核心类
 *
 * @since 2019-05-07
 * @author gongdl
 */
@Component
@Transactional
public class FileCore {
    private static final Logger LOG = LoggerFactory.getLogger(FileCore.class);

    /**
     * 存储图片
     *
     * @param uploadDir
     * @param imageName
     * @param img
     * @return
     * @throws Exception
     */
    public ResponseDTO<?> storageImage(String uploadDir, String imageName, MultipartFile img) throws Exception{

        String suffixName = img.getOriginalFilename().substring(img.getOriginalFilename().lastIndexOf("."));
        // 判断后缀是否为图片
        if(!suffixName.equals(".png") && !suffixName.equals(".jpg") &&  !suffixName.equals(".jpge") && !suffixName.equals(".gif")){
            throw new WxException(ResponseEnum.ERROR_BDMER_IMG_FORMAT);
        }

        String imgName = imageName + suffixName;
        File path = Util.getClassPath();
        File dest = new File(path.getAbsolutePath(),uploadDir+imgName);
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        img.transferTo(dest);
        LOG.info("【FileCore - storageImage】 - 储存图片：{}", uploadDir + imgName);

        return B.success(imgName);
    }

}
