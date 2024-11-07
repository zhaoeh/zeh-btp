package jp.co.futech.module.system.controller.admin.file.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotNull;

@Schema(description = "管理后台 - 上传文件 Request VO")
@Data
public class FileUploadReqVO {

    @Schema(description = "文件附件", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "{100000028}{000000001}")
    private MultipartFile file;

    @Schema(description = "文件附件", example = "biyuanma.png")
    private String path;

}
