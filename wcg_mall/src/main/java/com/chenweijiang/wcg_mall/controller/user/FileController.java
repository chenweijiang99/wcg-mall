package com.chenweijiang.wcg_mall.controller.user;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Dict;
import com.chenweijiang.wcg_mall.constant.MessageConstant;
import com.chenweijiang.wcg_mall.result.Result;
import com.chenweijiang.wcg_mall.utils.AliOssUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author 韋
 * 2025/4/18
 */
@RestController
@RequestMapping("/user/file")
@Tag(name = "用户文件相关接口")
@Slf4j
@RequiredArgsConstructor
public class FileController {
    private final AliOssUtil aliOssUtil;
    /**
     * wang-editor编辑器文件上传接口
     */
    @PostMapping("/wang/upload")
    @Operation(summary = "wang-editor编辑器文件上传接口")
    public Map<String, Object> wangEditorUpload(MultipartFile file) {
        // 判断文件是否为空
        if(file.isEmpty() || file == null){
            throw new RuntimeException("文件不能为空");
        }
        log.info("文件上传：{}",file);

        try {
            // 获取文件原始名称
            String originalFilename = file.getOriginalFilename();
            // 保证文件的名字是唯一的，防止覆盖
            String filename = UUID.randomUUID().toString()+originalFilename.substring(originalFilename.lastIndexOf("."));
            // 存储到本地
            // file.transferTo(new File("C:\\Users\\Administrator\\Desktop\\files\\"+filename));
            // 调用AliOssUtil工具类上传文件到阿里云OSS，并获取文件的URL
            String url = aliOssUtil.upload(filename, file.getInputStream());
            Map<String, Object> resMap = new HashMap<>();
            // wangEditor上传图片成功后， 需要返回的参数
            resMap.put("errno", 0);
            resMap.put("data", CollUtil.newArrayList(Dict.create().set("url", url)));

            return resMap;
        } catch (IOException e) {
            log.error("文件上传失败", e);
        }
        return null;
    }
}
