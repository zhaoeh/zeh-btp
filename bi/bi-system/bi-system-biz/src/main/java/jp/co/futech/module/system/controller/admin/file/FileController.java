package jp.co.futech.module.system.controller.admin.file;

import cn.hutool.core.io.IoUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.PermitAll;
import jp.co.futech.framework.common.pojo.FilePluginResult;
import jp.co.futech.framework.common.util.io.FileUtils;
import jp.co.futech.framework.operatelog.core.annotations.OperateLog;
import jp.co.futech.module.infra.api.file.FileApi;
import jp.co.futech.module.system.controller.admin.file.vo.FileUploadReqVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @description: 文件controller
 * @author: ErHu.Zhao
 * @create: 2024-07-01
 **/
@Tag(name = "管理后台 - 文件上传（特定前端插件）")
@RestController
@RequestMapping("/system/file")
@Validated
public class FileController {

    @Autowired
    private FileApi fileApi;

    @PostMapping("/upload")
    @Operation(summary = "上传文件")
    @OperateLog(enable = false) // 上传文件，没有记录操作日志的必要
    @PermitAll
    public FilePluginResult uploadFile(FileUploadReqVO uploadReqVO) throws Exception {
        MultipartFile file = uploadReqVO.getFile();
        byte[] content = IoUtil.readBytes(file.getInputStream());
        String fileName = file.getOriginalFilename();
        String path = uploadReqVO.getPath();
        path = StringUtils.isBlank(path) ? FileUtils.generatePathByUUID(content, fileName) : path;
        String fileLink = fileApi.createFile(fileName, path, content);
        return FilePluginResult.success(fileLink);
    }
}
