package jp.co.futech.module.system.controller.admin.user;

import cn.hutool.core.collection.CollUtil;
import jakarta.annotation.security.PermitAll;
import jp.co.futech.framework.common.enums.CommonStatusEnum;
import jp.co.futech.framework.common.pojo.CommonResult;
import jp.co.futech.framework.common.pojo.PageParam;
import jp.co.futech.framework.common.pojo.PageResult;
import jp.co.futech.framework.excel.core.util.ExcelUtils;
import jp.co.futech.framework.operatelog.core.annotations.OperateLog;
import jp.co.futech.module.system.controller.admin.user.vo.user.*;
import jp.co.futech.module.system.convert.user.UserConvert;
import jp.co.futech.module.system.dal.dataobject.dept.DeptDO;
import jp.co.futech.module.system.dal.dataobject.permission.RoleDO;
import jp.co.futech.module.system.dal.dataobject.user.AdminUserDO;
import jp.co.futech.module.system.enums.common.SexEnum;
import jp.co.futech.module.system.service.dept.DeptService;
import jp.co.futech.module.system.service.permission.PermissionService;
import jp.co.futech.module.system.service.permission.RoleService;
import jp.co.futech.module.system.service.user.AdminUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static jp.co.futech.framework.common.pojo.CommonResult.success;
import static jp.co.futech.framework.common.util.collection.CollectionUtils.convertList;
import static jp.co.futech.framework.operatelog.core.enums.OperateTypeEnum.EXPORT;

@Tag(name = "管理后台 - 用户")
@RestController
@RequestMapping("/system/user")
@Validated
public class UserController {

    @Resource
    private AdminUserService userService;
    @Resource
    private DeptService deptService;
    @Resource
    private PermissionService permissionService;
    @Resource
    private RoleService roleService;

    @PostMapping("/create")
    @Operation(summary = "新增用户")
    @PermitAll
//    @PreAuthorize("@ss.hasPermission('system:user:create')")
    public CommonResult<Long> createUser(@Valid @RequestBody UserSaveReqVO reqVO) {
        Long id = userService.createUser(reqVO);
        return success(id);
    }

    @PutMapping("update")
    @Operation(summary = "修改用户")
    @PreAuthorize("@ss.hasPermission('system:user:update')")
    public CommonResult<Boolean> updateUser(@Valid @RequestBody UserSaveReqVO reqVO) {
        userService.updateUser(reqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除用户")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('system:user:delete')")
    public CommonResult<Boolean> deleteUser(@RequestParam("id") Long id) {
        userService.deleteUser(id);
        return success(true);
    }

    @PutMapping("/update-password")
    @Operation(summary = "重置用户密码")
    @PreAuthorize("@ss.hasPermission('system:user:update-password')")
    public CommonResult<Boolean> updateUserPassword(@Valid @RequestBody UserUpdatePasswordReqVO reqVO) {
        userService.updateUserPassword(reqVO.getId(), reqVO.getPassword());
        return success(true);
    }

    @PutMapping("/update-status")
    @Operation(summary = "修改用户状态")
    @PreAuthorize("@ss.hasPermission('system:user:update')")
    public CommonResult<Boolean> updateUserStatus(@Valid @RequestBody UserUpdateStatusReqVO reqVO) {
        userService.updateUserStatus(reqVO.getId(), reqVO.getStatus());
        return success(true);
    }

    @PutMapping("/mfa-update-status")
    @Operation(summary = "修改用户MFA状态")
    @PreAuthorize("@ss.hasPermission('system:user:mfa-update')")
    public CommonResult<Boolean> updateUserMfaStatus(@Valid @RequestBody UserUpdateMfaStatusReqVO reqVO) {
        userService.updateUserMfaStatus(reqVO.getId(), reqVO.getIsMfa());
        return success(true);
    }


    @GetMapping("/page")
    @Operation(summary = "获得用户分页列表")
    @PreAuthorize("@ss.hasPermission('system:user:list')")
    public CommonResult<PageResult<UserRespVO>> getUserPage(@Valid UserPageReqVO pageReqVO) {
        // 获得用户分页列表
        PageResult<AdminUserDO> pageResult = userService.getUserPage(pageReqVO);
        if (CollUtil.isEmpty(pageResult.getList())) {
            return success(new PageResult<>(pageResult.getTotal()));
        }
        Map<Long, String> rolesMap =new HashMap<>();
        pageResult.getList().forEach(key -> {
            Set<Long> roleIds = permissionService.getUserRoleIdListByUserId(key.getId());
            List<RoleDO> roles = roleService.getRoleList(roleIds);
            roles.removeIf(role -> !CommonStatusEnum.ENABLE.getStatus().equals(role.getStatus())); // 移除禁用的角色
            rolesMap.put(key.getId(), roles.stream().map(RoleDO::getName).collect(Collectors.joining(",")));
        });
        // 拼接数据
        return success(new PageResult<>(UserConvert.INSTANCE.convertRoleList(pageResult.getList(),rolesMap),pageResult.getTotal()));
    }

    @GetMapping({"/list-all-simple", "/simple-list"})
    @Operation(summary = "获取用户精简信息列表", description = "只包含被开启的用户，主要用于前端的下拉选项")
    public CommonResult<List<UserSimpleRespVO>> getSimpleUserList() {
        List<AdminUserDO> list = userService.getUserListByStatus(CommonStatusEnum.ENABLE.getStatus());
        // 拼接数据
        Map<Long, DeptDO> deptMap = deptService.getDeptMap(
                convertList(list, AdminUserDO::getDeptId));
        return success(UserConvert.INSTANCE.convertSimpleList(list, deptMap));
    }

    @GetMapping("/get")
    @Operation(summary = "获得用户详情")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('system:user:query')")
    public CommonResult<UserRespVO> getUser(@RequestParam("id") Long id) {
        AdminUserDO user = userService.getUser(id);
        // 拼接数据
        DeptDO dept = deptService.getDept(user.getDeptId());
        return success(UserConvert.INSTANCE.convert(user, dept));
    }

    @GetMapping("/export")
    @Operation(summary = "导出用户")
    @PreAuthorize("@ss.hasPermission('system:user:export')")
    @OperateLog(type = EXPORT)
    public void exportUserList(@Validated UserPageReqVO exportReqVO,
                               HttpServletResponse response) throws IOException {
        exportReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<AdminUserDO> list = userService.getUserPage(exportReqVO).getList();
        // 输出 Excel
        Map<Long, DeptDO> deptMap = deptService.getDeptMap(
                convertList(list, AdminUserDO::getDeptId));
        ExcelUtils.write(response, "用户数据.xls", "数据", UserRespVO.class,
                UserConvert.INSTANCE.convertList(list, deptMap));
    }

    @GetMapping("/get-import-template")
    @Operation(summary = "获得导入用户模板")
    public void importTemplate(HttpServletResponse response) throws IOException {
        // 手动创建导出 demo
        List<UserImportExcelVO> list = Arrays.asList(
                UserImportExcelVO.builder().username("yunai").deptId(1L).email("yunai@iocoder.cn").mobile("15601691300")
                        .nickname("芋道").status(CommonStatusEnum.ENABLE.getStatus()).sex(SexEnum.MALE.getSex()).build(),
                UserImportExcelVO.builder().username("yuanma").deptId(2L).email("yuanma@iocoder.cn").mobile("15601701300")
                        .nickname("源码").status(CommonStatusEnum.DISABLE.getStatus()).sex(SexEnum.FEMALE.getSex()).build()
        );
        // 输出
        ExcelUtils.write(response, "用户导入模板.xls", "用户列表", UserImportExcelVO.class, list);
    }

    @PostMapping("/import")
    @Operation(summary = "导入用户")
//    @Parameters({
//            @Parameter(name = "file", description = "Excel 文件", required = true),
//            @Parameter(name = "updateSupport", description = "是否支持更新，默认为 false", example = "true")
//    })
//    @PreAuthorize("@ss.hasPermission('system:user:import')")
    @PermitAll
    public CommonResult<UserImportRespVO> importExcel(@RequestParam("file") MultipartFile file,
                                                      @RequestParam(value = "updateSupport", required = false, defaultValue = "false") Boolean updateSupport) throws Exception {
        List<UserImportExcelVO> list = ExcelUtils.read(file, UserImportExcelVO.class);
        return success(userService.importUserList(list, updateSupport));
    }
}
