package jp.co.futech.module.system.enums;

import jp.co.futech.framework.common.exception.ErrorCode;

/**
 * System 错误码枚举类
 *
 * system 系统，使用 1-002-000-000 段
 */
public interface ErrorCodeConstants {

    // ========== AUTH 模块 1-002-000-000 ==========
    ErrorCode AUTH_LOGIN_BAD_CREDENTIALS = new ErrorCode(1_002_000_000, "登录失败，账号密码不正确", "{200000059}");
    ErrorCode AUTH_LOGIN_USER_DISABLED = new ErrorCode(1_002_000_001, "登录失败，账号被禁用", "{200000060}");
    ErrorCode AUTH_LOGIN_CAPTCHA_CODE_ERROR = new ErrorCode(1_002_000_004, "验证码不正确，原因：{}", "{200000168}");
    ErrorCode AUTH_THIRD_LOGIN_NOT_BIND = new ErrorCode(1_002_000_005, "未绑定账号，需要进行绑定", "{200000061}");
    ErrorCode AUTH_TOKEN_EXPIRED = new ErrorCode(1_002_000_006, "Token 已经过期", "{200000062}");
    ErrorCode AUTH_MOBILE_NOT_EXISTS = new ErrorCode(1_002_000_007, "手机号不存在", "{200000063}");
    ErrorCode AUTH_LOGIN_QR_CODE = new ErrorCode(1_002_000_008, "生成二维码失败", "{200000064}");
    ErrorCode AUTH_LOGIN_CHECK_CODE = new ErrorCode(1_002_000_009, "校验验证码失败", "{200000065}");

    // ========== 菜单模块 1-002-001-000 ==========
    ErrorCode MENU_NAME_DUPLICATE = new ErrorCode(1_002_001_000, "已经存在该名字的菜单", "{200000066}");
    ErrorCode MENU_PARENT_NOT_EXISTS = new ErrorCode(1_002_001_001, "父菜单不存在", "{200000067}");
    ErrorCode MENU_PARENT_ERROR = new ErrorCode(1_002_001_002, "不能设置自己为父菜单", "{200000068}");
    ErrorCode MENU_NOT_EXISTS = new ErrorCode(1_002_001_003, "菜单不存在", "{200000069}");
    ErrorCode MENU_EXISTS_CHILDREN = new ErrorCode(1_002_001_004, "存在子菜单，无法删除", "{200000070}");
    ErrorCode MENU_PARENT_NOT_DIR_OR_MENU = new ErrorCode(1_002_001_005, "父菜单的类型必须是目录或者菜单", "{200000071}");

    // ========== 角色模块 1-002-002-000 ==========
    ErrorCode ROLE_NOT_EXISTS = new ErrorCode(1_002_002_000, "角色不存在", "{200000072}");
    ErrorCode ROLE_NAME_DUPLICATE = new ErrorCode(1_002_002_001, "已经存在名为【{}】的角色", "{200000167}");
    ErrorCode ROLE_CODE_DUPLICATE = new ErrorCode(1_002_002_002, "已经存在编码为【{}】的角色", "{200000166}");
    ErrorCode ROLE_CAN_NOT_UPDATE_SYSTEM_TYPE_ROLE = new ErrorCode(1_002_002_003, "不能操作类型为系统内置的角色", "{200000073}");
    ErrorCode ROLE_IS_DISABLE = new ErrorCode(1_002_002_004, "名字为【{}】的角色已被禁用", "{200000165}");
    ErrorCode ROLE_ADMIN_CODE_ERROR = new ErrorCode(1_002_002_005, "编码【{}】不能使用", "{200000164}");

    // ========== 用户模块 1-002-003-000 ==========
    ErrorCode USER_USERNAME_EXISTS = new ErrorCode(1_002_003_000, "用户账号已经存在", "{200000074}");
    ErrorCode USER_MOBILE_EXISTS = new ErrorCode(1_002_003_001, "手机号已经存在", "{200000075}");
    ErrorCode USER_EMAIL_EXISTS = new ErrorCode(1_002_003_002, "邮箱已经存在", "{200000076}");
    ErrorCode USER_NOT_EXISTS = new ErrorCode(1_002_003_003, "用户不存在", "{200000077}");
    ErrorCode USER_IMPORT_LIST_IS_EMPTY = new ErrorCode(1_002_003_004, "导入用户数据不能为空！", "{200000078}");
    ErrorCode USER_PASSWORD_FAILED = new ErrorCode(1_002_003_005, "用户密码校验失败", "{200000079}");
    ErrorCode USER_IS_DISABLE = new ErrorCode(1_002_003_006, "名字为【{}】的用户已被禁用", "{200000163}");
    ErrorCode USER_COUNT_MAX = new ErrorCode(1_002_003_008, "创建用户失败，原因：超过租户最大租户配额({})！", "{200000162}");

    // ========== 部门模块 1-002-004-000 ==========
    ErrorCode DEPT_NAME_DUPLICATE = new ErrorCode(1_002_004_000, "已经存在该名字的部门", "{200000080}");
    ErrorCode DEPT_PARENT_NOT_EXITS = new ErrorCode(1_002_004_001,"父级部门不存在", "{200000081}");
    ErrorCode DEPT_NOT_FOUND = new ErrorCode(1_002_004_002, "当前部门不存在", "{200000082}");
    ErrorCode DEPT_EXITS_CHILDREN = new ErrorCode(1_002_004_003, "存在子部门，无法删除", "{200000083}");
    ErrorCode DEPT_PARENT_ERROR = new ErrorCode(1_002_004_004, "不能设置自己为父部门", "{200000084}");
    ErrorCode DEPT_EXISTS_USER = new ErrorCode(1_002_004_005, "部门中存在员工，无法删除", "{200000085}");
    ErrorCode DEPT_NOT_ENABLE = new ErrorCode(1_002_004_006, "部门({})不处于开启状态，不允许选择", "{200000161}");
    ErrorCode DEPT_PARENT_IS_CHILD = new ErrorCode(1_002_004_007, "不能设置自己的子部门为父部门", "{200000086}");

    // ========== 岗位模块 1-002-005-000 ==========
    ErrorCode POST_NOT_FOUND = new ErrorCode(1_002_005_000, "当前岗位不存在", "{200000087}");
    ErrorCode POST_NOT_ENABLE = new ErrorCode(1_002_005_001, "岗位({}) 不处于开启状态，不允许选择", "{200000160}");
    ErrorCode POST_NAME_DUPLICATE = new ErrorCode(1_002_005_002, "已经存在该名字的岗位", "{200000088}");
    ErrorCode POST_CODE_DUPLICATE = new ErrorCode(1_002_005_003, "已经存在该标识的岗位", "{200000089}");

    // ========== 字典类型 1-002-006-000 ==========
    ErrorCode DICT_TYPE_NOT_EXISTS = new ErrorCode(1_002_006_001, "当前字典类型不存在", "{200000090}");
    ErrorCode DICT_TYPE_NOT_ENABLE = new ErrorCode(1_002_006_002, "字典类型不处于开启状态，不允许选择", "{200000091}");
    ErrorCode DICT_TYPE_NAME_DUPLICATE = new ErrorCode(1_002_006_003, "已经存在该名字的字典类型", "{200000092}");
    ErrorCode DICT_TYPE_TYPE_DUPLICATE = new ErrorCode(1_002_006_004, "已经存在该类型的字典类型", "{200000093}");
    ErrorCode DICT_TYPE_HAS_CHILDREN = new ErrorCode(1_002_006_005, "无法删除，该字典类型还有字典数据", "{200000094}");

    // ========== 字典数据 1-002-007-000 ==========
    ErrorCode DICT_DATA_NOT_EXISTS = new ErrorCode(1_002_007_001, "当前字典数据不存在", "{200000095}");
    ErrorCode DICT_DATA_NOT_ENABLE = new ErrorCode(1_002_007_002, "字典数据({})不处于开启状态，不允许选择", "{200000159}");
    ErrorCode DICT_DATA_VALUE_DUPLICATE = new ErrorCode(1_002_007_003, "已经存在该值的字典数据", "{200000096}");

    // ========== 通知公告 1-002-008-000 ==========
    ErrorCode NOTICE_NOT_FOUND = new ErrorCode(1_002_008_001, "当前通知公告不存在", "{200000097}");

    // ========== 短信渠道 1-002-011-000 ==========
    ErrorCode SMS_CHANNEL_NOT_EXISTS = new ErrorCode(1_002_011_000, "短信渠道不存在", "{200000098}");
    ErrorCode SMS_CHANNEL_DISABLE = new ErrorCode(1_002_011_001, "短信渠道不处于开启状态，不允许选择", "{200000099}");
    ErrorCode SMS_CHANNEL_HAS_CHILDREN = new ErrorCode(1_002_011_002, "无法删除，该短信渠道还有短信模板", "{200000100}");

    // ========== 短信模板 1-002-012-000 ==========
    ErrorCode SMS_TEMPLATE_NOT_EXISTS = new ErrorCode(1_002_012_000, "短信模板不存在", "{200000101}");
    ErrorCode SMS_TEMPLATE_CODE_DUPLICATE = new ErrorCode(1_002_012_001, "已经存在编码为【{}】的短信模板", "{200000158}");
    ErrorCode SMS_TEMPLATE_API_ERROR = new ErrorCode(1_002_012_002, "短信 API 模板调用失败，原因是：{}", "{200000157}");
    ErrorCode SMS_TEMPLATE_API_AUDIT_CHECKING = new ErrorCode(1_002_012_003, "短信 API 模版无法使用，原因：审批中", "{200000102}");
    ErrorCode SMS_TEMPLATE_API_AUDIT_FAIL = new ErrorCode(1_002_012_004, "短信 API 模版无法使用，原因：审批不通过，{}", "{200000156}");
    ErrorCode SMS_TEMPLATE_API_NOT_FOUND = new ErrorCode(1_002_012_005, "短信 API 模版无法使用，原因：模版不存在", "{200000103}");

    // ========== 短信发送 1-002-013-000 ==========
    ErrorCode SMS_SEND_MOBILE_NOT_EXISTS = new ErrorCode(1_002_013_000, "手机号不存在", "{200000104}");
    ErrorCode SMS_SEND_MOBILE_TEMPLATE_PARAM_MISS = new ErrorCode(1_002_013_001, "模板参数({})缺失", "{200000155}");
    ErrorCode SMS_SEND_TEMPLATE_NOT_EXISTS = new ErrorCode(1_002_013_002, "短信模板不存在", "{200000105}");

    // ========== 短信验证码 1-002-014-000 ==========
    ErrorCode SMS_CODE_NOT_FOUND = new ErrorCode(1_002_014_000, "验证码不存在", "{200000106}");
    ErrorCode SMS_CODE_EXPIRED = new ErrorCode(1_002_014_001, "验证码已过期", "{200000107}");
    ErrorCode SMS_CODE_USED = new ErrorCode(1_002_014_002, "验证码已使用", "{200000108}");
    ErrorCode SMS_CODE_NOT_CORRECT = new ErrorCode(1_002_014_003, "验证码不正确", "{200000109}");
    ErrorCode SMS_CODE_EXCEED_SEND_MAXIMUM_QUANTITY_PER_DAY = new ErrorCode(1_002_014_004, "超过每日短信发送数量", "{200000110}");
    ErrorCode SMS_CODE_SEND_TOO_FAST = new ErrorCode(1_002_014_005, "短信发送过于频率", "{200000111}");
    ErrorCode SMS_CODE_IS_EXISTS = new ErrorCode(1_002_014_006, "手机号已被使用", "{手机号已被使用}");
    ErrorCode SMS_CODE_IS_UNUSED = new ErrorCode(1_002_014_007, "验证码未被使用", "{200000113}");

    // ========== 租户信息 1-002-015-000 ==========
    ErrorCode TENANT_NOT_EXISTS = new ErrorCode(1_002_015_000, "租户不存在", "{200000114}");
    ErrorCode TENANT_DISABLE = new ErrorCode(1_002_015_001, "名字为【{}】的租户已被禁用", "{200000153}");
    ErrorCode TENANT_EXPIRE = new ErrorCode(1_002_015_002, "名字为【{}】的租户已过期", "{200000154}");
    ErrorCode TENANT_CAN_NOT_UPDATE_SYSTEM = new ErrorCode(1_002_015_003, "系统租户不能进行修改、删除等操作！", "{200000115}");
    ErrorCode TENANT_NAME_DUPLICATE = new ErrorCode(1_002_015_004, "名字为【{}】的租户已存在", "{200000152}");
    ErrorCode TENANT_WEBSITE_DUPLICATE = new ErrorCode(1_002_015_005, "域名为【{}】的租户已存在", "{200000151}");

    // ========== 租户套餐 1-002-016-000 ==========
    ErrorCode TENANT_PACKAGE_NOT_EXISTS = new ErrorCode(1_002_016_000, "租户套餐不存在", "{200000116}");
    ErrorCode TENANT_PACKAGE_USED = new ErrorCode(1_002_016_001, "租户正在使用该套餐，请给租户重新设置套餐后再尝试删除", "{200000117}");
    ErrorCode TENANT_PACKAGE_DISABLE = new ErrorCode(1_002_016_002, "名字为【{}】的租户套餐已被禁用", "{200000150}");

    // ========== 错误码模块 1-002-017-000 ==========
    ErrorCode ERROR_CODE_NOT_EXISTS = new ErrorCode(1_002_017_000, "错误码不存在", "{200000118}");
    ErrorCode ERROR_CODE_DUPLICATE = new ErrorCode(1_002_017_001, "已经存在编码为【{}】的错误码", "{200000149}");

    // ========== 社交用户 1-002-018-000 ==========
    ErrorCode SOCIAL_USER_AUTH_FAILURE = new ErrorCode(1_002_018_000, "社交授权失败，原因是：{}", "{200000148}");
    ErrorCode SOCIAL_USER_NOT_FOUND = new ErrorCode(1_002_018_001, "社交授权失败，找不到对应的用户", "{200000119}");

    ErrorCode SOCIAL_CLIENT_WEIXIN_MINI_APP_PHONE_CODE_ERROR = new ErrorCode(1_002_018_200, "获得手机号失败", "{200000120}");
    ErrorCode SOCIAL_CLIENT_NOT_EXISTS = new ErrorCode(1_002_018_201, "社交客户端不存在", "{200000121}");
    ErrorCode SOCIAL_CLIENT_UNIQUE = new ErrorCode(1_002_018_201, "社交客户端已存在配置", "{200000122}");

    // ========== 系统敏感词 1-002-019-000 =========
    ErrorCode SENSITIVE_WORD_NOT_EXISTS = new ErrorCode(1_002_019_000, "系统敏感词在所有标签中都不存在", "{200000123}");
    ErrorCode SENSITIVE_WORD_EXISTS = new ErrorCode(1_002_019_001, "系统敏感词已在标签中存在", "{200000124}");

    // ========== OAuth2 客户端 1-002-020-000 =========
    ErrorCode OAUTH2_CLIENT_NOT_EXISTS = new ErrorCode(1_002_020_000, "OAuth2 客户端不存在", "{200000125}");
    ErrorCode OAUTH2_CLIENT_EXISTS = new ErrorCode(1_002_020_001, "OAuth2 客户端编号已存在", "{200000126}");
    ErrorCode OAUTH2_CLIENT_DISABLE = new ErrorCode(1_002_020_002, "OAuth2 客户端已禁用", "{200000127}");
    ErrorCode OAUTH2_CLIENT_AUTHORIZED_GRANT_TYPE_NOT_EXISTS = new ErrorCode(1_002_020_003, "不支持该授权类型", "{200000128}");
    ErrorCode OAUTH2_CLIENT_SCOPE_OVER = new ErrorCode(1_002_020_004, "授权范围过大", "{200000129}");
    ErrorCode OAUTH2_CLIENT_REDIRECT_URI_NOT_MATCH = new ErrorCode(1_002_020_005, "无效 redirect_uri: {}", "{200000142}");
    ErrorCode OAUTH2_CLIENT_CLIENT_SECRET_ERROR = new ErrorCode(1_002_020_006, "无效 client_secret: {}", "{200000143}");

    // ========== OAuth2 授权 1-002-021-000 =========
    ErrorCode OAUTH2_GRANT_CLIENT_ID_MISMATCH = new ErrorCode(1_002_021_000, "client_id 不匹配", "{200000130}");
    ErrorCode OAUTH2_GRANT_REDIRECT_URI_MISMATCH = new ErrorCode(1_002_021_001, "redirect_uri 不匹配", "{200000131}");
    ErrorCode OAUTH2_GRANT_STATE_MISMATCH = new ErrorCode(1_002_021_002, "state 不匹配", "{200000132}");
    ErrorCode OAUTH2_GRANT_CODE_NOT_EXISTS = new ErrorCode(1_002_021_003, "code 不存在", "{200000133}");

    // ========== OAuth2 授权 1-002-022-000 =========
    ErrorCode OAUTH2_CODE_NOT_EXISTS = new ErrorCode(1_002_022_000, "code 不存在", "{200000134}");
    ErrorCode OAUTH2_CODE_EXPIRE = new ErrorCode(1_002_022_001, "code 已过期", "{200000135}");

    // ========== 邮箱账号 1-002-023-000 ==========
    ErrorCode MAIL_ACCOUNT_NOT_EXISTS = new ErrorCode(1_002_023_000, "邮箱账号不存在", "{200000136}");
    ErrorCode MAIL_ACCOUNT_RELATE_TEMPLATE_EXISTS = new ErrorCode(1_002_023_001, "无法删除，该邮箱账号还有邮件模板", "{200000137}");

    // ========== 邮件模版 1-002-024-000 ==========
    ErrorCode MAIL_TEMPLATE_NOT_EXISTS = new ErrorCode(1_002_024_000, "邮件模版不存在", "{200000138}");
    ErrorCode MAIL_TEMPLATE_CODE_EXISTS = new ErrorCode(1_002_024_001, "邮件模版 code({}) 已存在", "{200000144}");

    // ========== 邮件发送 1-002-025-000 ==========
    ErrorCode MAIL_SEND_TEMPLATE_PARAM_MISS = new ErrorCode(1_002_025_000, "模板参数({})缺失", "{200000145}");
    ErrorCode MAIL_SEND_MAIL_NOT_EXISTS = new ErrorCode(1_002_025_001, "邮箱不存在", "{200000139}");

    // ========== 站内信模版 1-002-026-000 ==========
    ErrorCode NOTIFY_TEMPLATE_NOT_EXISTS = new ErrorCode(1_002_026_000, "站内信模版不存在", "{200000140}");
    ErrorCode NOTIFY_TEMPLATE_CODE_DUPLICATE = new ErrorCode(1_002_026_001, "已经存在编码为【{}】的站内信模板", "{200000146}");

    // ========== 站内信模版 1-002-027-000 ==========

    // ========== 站内信发送 1-002-028-000 ==========
    ErrorCode NOTIFY_SEND_TEMPLATE_PARAM_MISS = new ErrorCode(1_002_028_000, "模板参数({})缺失", "{200000147}");

}
