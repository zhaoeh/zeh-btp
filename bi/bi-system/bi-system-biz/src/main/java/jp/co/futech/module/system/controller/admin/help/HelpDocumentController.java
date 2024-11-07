package jp.co.futech.module.system.controller.admin.help;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jp.co.futech.framework.common.enums.CommonStatusEnum;
import jp.co.futech.framework.common.pojo.CommonResult;
import jp.co.futech.framework.operatelog.core.annotations.OperateLog;
import jp.co.futech.module.system.controller.admin.auth.vo.AuthPermissionInfoRespVO;
import jp.co.futech.module.system.controller.admin.help.vo.HelpReqVO;
import jp.co.futech.module.system.convert.auth.AuthConvert;
import jp.co.futech.module.system.dal.dataobject.help.HelpDocumentDO;
import jp.co.futech.module.system.dal.dataobject.permission.MenuDO;
import jp.co.futech.module.system.service.help.HelpDocumentService;
import jp.co.futech.module.system.service.permission.MenuService;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static jp.co.futech.framework.common.pojo.CommonResult.success;

/**
 * @description: help帮助文档控制器
 * @author: ErHu.Zhao
 * @create: 2024-06-07
 **/
@Tag(name = "管理后台 - 帮助中心")
@RestController
@RequestMapping("/system/help")
@Validated
public class HelpDocumentController {

    @Autowired
    private MenuService menuService;

    @Autowired
    private HelpDocumentService helpDocumentService;

    @GetMapping("/help-menu-tree")
    @Operation(summary = "获取帮助中心menu菜单树")
    @OperateLog(enable = false)
    public CommonResult<AuthPermissionInfoRespVO> getHelpMenuTree() {
        List<MenuDO> menuList = menuService.getMenuList();
        AuthPermissionInfoRespVO vo = AuthConvert.INSTANCE.convertMenuTreeWithCondition(menuList,
                menu -> !CommonStatusEnum.ENABLE.getStatus().equals(menu.getStatus()) || BooleanUtils.isFalse(menu.getVisible()),
                menu -> "Insight".equals(menu.getName()),
                false);
        List<AuthPermissionInfoRespVO.MenuVO> menus = new ArrayList<>();
        AuthConvert.INSTANCE.ignoreFirstTreeNodes(menus, vo.getMenus(), 2);
        return success(vo.setMenus(menus));
    }

    @GetMapping("/help-menu-leaf")
    @Operation(summary = "获取帮助中心叶子menu")
    @OperateLog(enable = false)
    public CommonResult<AuthPermissionInfoRespVO> getHelpLeafMenus() {
        List<MenuDO> menuList = menuService.getMenuList();
        return success(AuthConvert.INSTANCE.convertMenuTreeWithCondition(menuList,
                menu -> !CommonStatusEnum.ENABLE.getStatus().equals(menu.getStatus()) || BooleanUtils.isFalse(menu.getVisible()),
                menu -> "Insight".equals(menu.getName()),
                true));
    }

    @PostMapping("/create")
    @Operation(summary = "创建帮助文档")
    @OperateLog(enable = false)
    @PreAuthorize("@ss.hasPermission('insight:helpDoc:create')")
    public CommonResult<Long> createHelpDocument(@Validated(HelpReqVO.CreateHelpDocument.class) @RequestBody HelpReqVO createReqVO) {
        Long menuId = helpDocumentService.createHelpDoc(createReqVO);
        return success(menuId);
    }

    @PostMapping("/edit")
    @Operation(summary = "编辑帮助文档")
    @OperateLog(enable = false)
    @PreAuthorize("@ss.hasPermission('insight:helpDoc:update')")
    public CommonResult<Long> editHelpDocument(@Validated({HelpReqVO.EditHelpDocument.class}) @RequestBody HelpReqVO createReqVO) {
        Long menuId = helpDocumentService.editHelpDoc(createReqVO);
        return success(menuId);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除帮助文档")
    @OperateLog(enable = false)
    @PreAuthorize("@ss.hasPermission('insight:helpDoc:delete')")
    public CommonResult<Long> deleteHelpDocument(@Validated(HelpReqVO.DeleteHelpDocument.class) @RequestBody HelpReqVO createReqVO) {
        Long menuId = helpDocumentService.deleteHelpDoc(createReqVO);
        return success(menuId);
    }

    @PostMapping("/list")
    @Operation(summary = "获取帮助文档列表", description = "用于【帮助中心】界面")
    @OperateLog(enable = false)
    public CommonResult<HelpDocumentDO> getMenuList(@Validated(HelpReqVO.QueryHelpDocument.class) @RequestBody HelpReqVO getReqVO) {
        List<HelpDocumentDO> list = helpDocumentService.getDocumentList(getReqVO);
        HelpDocumentDO result = Optional.ofNullable(list).map(e -> e.stream().findFirst().orElse(null)).orElse(null);
        return success(result);
    }
}
