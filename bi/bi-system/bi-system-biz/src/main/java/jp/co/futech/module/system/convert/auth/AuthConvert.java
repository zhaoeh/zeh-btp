package jp.co.futech.module.system.convert.auth;

import cn.hutool.core.collection.CollUtil;
import jp.co.futech.module.system.api.sms.dto.code.SmsCodeSendReqDTO;
import jp.co.futech.module.system.api.sms.dto.code.SmsCodeUseReqDTO;
import jp.co.futech.module.system.api.social.dto.SocialUserBindReqDTO;
import jp.co.futech.module.system.controller.admin.auth.vo.*;
import jp.co.futech.module.system.dal.dataobject.oauth2.OAuth2AccessTokenDO;
import jp.co.futech.module.system.dal.dataobject.permission.MenuDO;
import jp.co.futech.module.system.dal.dataobject.permission.RoleDO;
import jp.co.futech.module.system.dal.dataobject.user.AdminUserDO;
import jp.co.futech.module.system.enums.permission.MenuTypeEnum;
import org.apache.commons.lang3.StringUtils;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static jp.co.futech.framework.common.util.collection.CollectionUtils.convertSet;
import static jp.co.futech.framework.common.util.collection.CollectionUtils.filterList;
import static jp.co.futech.module.system.dal.dataobject.permission.MenuDO.ID_ROOT;

@Mapper
public interface AuthConvert {

    AuthConvert INSTANCE = Mappers.getMapper(AuthConvert.class);

    AuthLoginRespVO convert(OAuth2AccessTokenDO bean);

    /**
     * 忽略前几个树节点
     *
     * @param iteratorList
     * @return
     */
    default void ignoreFirstTreeNodes(List<AuthPermissionInfoRespVO.MenuVO> resultContainer, List<AuthPermissionInfoRespVO.MenuVO> iteratorList, int ignoreNodeIndex) {
        if (CollUtil.isEmpty(iteratorList)) {
            return;
        }
        if (ignoreNodeIndex > 0) {
            for (AuthPermissionInfoRespVO.MenuVO menu : iteratorList) {
                int useIndex = ignoreNodeIndex;
                List<AuthPermissionInfoRespVO.MenuVO> children = menu.getChildren();
                ignoreFirstTreeNodes(resultContainer, children, --useIndex);
            }
        } else {
            resultContainer.addAll(iteratorList);
        }
    }

    /**
     * 根据条件转换菜单树
     *
     * @param menuList       菜单集合
     * @param removeIf       删除条件
     * @param menuTreeFilter 菜单树过滤器
     * @param onlyLeaf       是否仅仅聚合叶子节点 true：是 false：否
     * @return 符合条件的菜单树
     */
    default AuthPermissionInfoRespVO convertMenuTreeWithCondition(List<MenuDO> menuList,
                                                                  Predicate<MenuDO> removeIf,
                                                                  Predicate<AuthPermissionInfoRespVO.MenuVO> menuTreeFilter,
                                                                  boolean onlyLeaf) {
        if (Objects.nonNull(menuList) && Objects.nonNull(removeIf)) {
            menuList.removeIf(removeIf);
        }
        List<AuthPermissionInfoRespVO.MenuVO> menus = buildMenuTree(menuList);
        if (Objects.nonNull(menuTreeFilter)) {
            menus = menus.stream().filter(menuTreeFilter).collect(Collectors.toList());
        }
        if (onlyLeaf) {
            menus = collectLeafMenus(menus);
        }
        return AuthPermissionInfoRespVO.builder()
                // 菜单树
                .menus(menus)
                .build();
    }

    /**
     * 聚合菜单叶子节点
     *
     * @param menus 菜单树集合
     * @return 叶子节点集合
     */
    default List<AuthPermissionInfoRespVO.MenuVO> collectLeafMenus(List<AuthPermissionInfoRespVO.MenuVO> menus) {
        if (CollUtil.isEmpty(menus)) {
            return Collections.emptyList();
        }
        List<AuthPermissionInfoRespVO.MenuVO> leafMenus = new ArrayList<>();
        menus.stream().forEach(currentMenu -> findLeafMenus(leafMenus, currentMenu));
        return leafMenus;
    }

    /**
     * 递归查找菜单树叶子节点
     *
     * @param leafMenus   当前叶子节点容器
     * @param currentMenu 当前菜单信息
     */
    default void findLeafMenus(List<AuthPermissionInfoRespVO.MenuVO> leafMenus, AuthPermissionInfoRespVO.MenuVO currentMenu) {
        List<AuthPermissionInfoRespVO.MenuVO> children = currentMenu.getChildren();
        if (CollUtil.isEmpty(children)) {
            leafMenus.add(currentMenu);
        } else {
            children.stream().forEach(childrenMenu -> findLeafMenus(leafMenus, childrenMenu));
        }
    }

    default AuthPermissionInfoRespVO convert(AdminUserDO user, List<RoleDO> roleList, List<MenuDO> menuList) {
        return AuthPermissionInfoRespVO.builder()
                .user(AuthPermissionInfoRespVO.UserVO.builder().id(user.getId()).nickname(user.getNickname()).avatar(user.getAvatar()).build())
                .roles(convertSet(roleList, RoleDO::getCode))
//                .dept(roleList.stream().map(RoleDO::getDataScopeDeptIds).filter(Objects::nonNull).distinct().collect(Collectors.toList()))
                // 权限标识信息
                .permissions(convertSet(menuList, MenuDO::getPermission))
                // 菜单树
                .menus(buildMenuTree(menuList))
                .build();
    }

    AuthPermissionInfoRespVO.MenuVO convertTreeNode(MenuDO menu);

    /**
     * 将菜单列表，构建成菜单树
     *
     * @param menuList 菜单列表
     * @return 菜单树
     */
    default List<AuthPermissionInfoRespVO.MenuVO> buildMenuTree(List<MenuDO> menuList) {
        if (CollUtil.isEmpty(menuList)) {
            return Collections.emptyList();
        }
        // 移除按钮
        menuList.removeIf(menu -> menu.getType().equals(MenuTypeEnum.BUTTON.getType()));
        // 排序，保证菜单的有序性
        menuList.sort(Comparator.comparing(MenuDO::getSort));

        // 构建菜单树
        // 使用 LinkedHashMap 的原因，是为了排序 。实际也可以用 Stream API ，就是太丑了。
        Map<Long, AuthPermissionInfoRespVO.MenuVO> treeNodeMap = new LinkedHashMap<>();
        menuList.forEach(menu -> treeNodeMap.put(menu.getId(), AuthConvert.INSTANCE.convertTreeNode(menu).
                setName(StringUtils.trim(menu.getName())).setI18nName(StringUtils.trim(menu.getName()))));
        // 处理父子关系
        treeNodeMap.values().stream().filter(node -> !node.getParentId().equals(ID_ROOT)).forEach(childNode -> {
            // 获得父节点
            AuthPermissionInfoRespVO.MenuVO parentNode = treeNodeMap.get(childNode.getParentId());
            if (parentNode == null) {
//                LoggerFactory.getLogger(getClass()).error("[buildRouterTree][resource({}) 找不到父资源({})]",
//                        childNode.getId(), childNode.getParentId());
                return;
            }
            // 将自己添加到父节点中
            if (parentNode.getChildren() == null) {
                parentNode.setChildren(new ArrayList<>());
            }
            parentNode.getChildren().add(childNode);
        });
        // 获得到所有的根节点
        return filterList(treeNodeMap.values(), node -> ID_ROOT.equals(node.getParentId()));
    }

    SocialUserBindReqDTO convert(Long userId, Integer userType, AuthSocialLoginReqVO reqVO);

    SmsCodeSendReqDTO convert(AuthSmsSendReqVO reqVO);

    SmsCodeUseReqDTO convert(AuthSmsLoginReqVO reqVO, Integer scene, String usedIp);

}
