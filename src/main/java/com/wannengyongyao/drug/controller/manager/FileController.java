package com.wannengyongyao.drug.controller.manager;

import com.wannengyongyao.drug.common.ResultCode;
import com.wannengyongyao.drug.common.ResultObject;
import com.wannengyongyao.drug.util.CryptoUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@RestController
@RequestMapping("/file")
public class FileController {
    private final Logger logger = LoggerFactory.getLogger(FileController.class);

    @Value("${spring.resources.static-locations}")
    private String resourceLocation;

    @Value("${azbrain.static.icon.url}")
    private String staticUrl;


    /**
     * 上传图片
     *
     * @param file
     * @return
     */
    @RequestMapping(value="/upload", method= {RequestMethod.POST,RequestMethod.GET})
    public ResultObject upload(@RequestParam(value = "file") MultipartFile file){
        if (file.isEmpty()) {
            logger.warn("upload file is empty");
            return ResultObject.fail(ResultCode.UPLOAD_FILE_FAILED);
        }

        String fileName = file.getOriginalFilename();
        String suffixName = fileName.substring(fileName.lastIndexOf('.'));

        // 过滤掉file:前缀
        String filePath = resourceLocation.substring(5);
        String destFileName = CryptoUtil.sha256(fileName, System.currentTimeMillis()+"", Math.random()+"");
        File dest = new File(filePath + destFileName + suffixName);
        logger.info("-------{}", dest.getName());
        try {
            file.transferTo(dest);
            String iconUrl = staticUrl + destFileName + suffixName;
            return ResultObject.ok("file", iconUrl);
        } catch (Exception e) {
            logger.error("file", e);
            return ResultObject.fail(ResultCode.UPLOAD_FILE_FAILED);
        }
    }
}