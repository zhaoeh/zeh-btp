package jp.co.futech.module.insight.sqltemplate;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.PermitAll;
import jp.co.futech.framework.common.pojo.CommonResult;
import jp.co.futech.module.insight.sqltemplate.core.EventSqlTemplateCore;
import jp.co.futech.module.insight.sqltemplate.template.EventSqlTemplate;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description: jooq框架测试
 * @author: ErHu.Zhao
 * @create: 2024-07-24
 **/
@Tag(name = "JOOQ - jooq test")
@RestController
@RequestMapping("/jooq/demo")
public class JooqController {

    @Autowired
    private EventSqlTemplateCore parser;

    @Autowired
    private DSLContext dsl;

    @PostMapping("/test_jooq")
    @Operation(summary = "测试jooq")
    @PermitAll
    public CommonResult<String> getInsight1(@RequestBody EventSqlTemplate jooqPojo) {
        parser.parse(jooqPojo);
        return CommonResult.success("");
    }
}
