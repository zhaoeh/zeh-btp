package jp.co.futech.module.system.controller.admin.i18n;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jp.co.futech.framework.common.util.servlet.ServletUtils;
import jp.co.futech.framework.tenant.core.annotation.IgnoreTenantRequest;
import jp.co.futech.framework.tenant.core.aop.IgnoreTenantTable;
import jp.co.futech.module.system.service.i18n.I18nService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * @description:
 * @author: ErHu.Zhao
 * @create: 2024-08-05
 **/
@Tag(name = "管理后台 - 国际化文件操作")
@RestController
@RequestMapping("/system/i18n")
@Validated
public class I18nController {

    @Autowired
    private I18nService i18nService;

    @Operation(summary = "下载i18n资源文件")
    @IgnoreTenantRequest
    @IgnoreTenantTable
    @GetMapping("/download")
    public void downloadI18n(
            HttpServletResponse response) throws IOException {
        ObjectNode node = i18nService.obtainMessages();
        ObjectMapper objectMapper = new ObjectMapper();
        byte[] bytes = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsBytes(node);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        outputStream.write(bytes);
        ServletUtils.writeAttachment(response, "updated_translations.json", outputStream.toByteArray());
    }

}
