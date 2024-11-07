package jp.co.futech.module.insight.aspect;

import com.alibaba.fastjson2.JSONObject;
import jakarta.servlet.http.HttpServletRequest;
import jp.co.futech.framework.common.pojo.CommonResult;
import jp.co.futech.framework.security.core.LoginUser;
import jp.co.futech.framework.security.core.util.SecurityFrameworkUtils;
import jp.co.futech.module.insight.annotation.OperationLogAnnotation;
import jp.co.futech.module.insight.entity.ms.UserOperLogEntity;
import jp.co.futech.module.insight.mapper.ms.UserOperLogMapper;
import jp.co.futech.module.system.api.user.AdminUserApi;
import jp.co.futech.module.system.api.user.dto.AdminUserRespDTO;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.lang.reflect.Method;
import java.time.LocalDateTime;

@Aspect
@Component
public class OperationLogAspect {

    @Autowired
    private AdminUserApi adminUserApi;

    @Autowired
    private UserOperLogMapper userOperLogMapper;

    private static final ThreadLocal<String> CONTENT = new ThreadLocal<>();

    @Pointcut("@annotation(jp.co.futech.module.insight.annotation.OperationLogAnnotation)")
    public void operLogPointCut() {

    }

    @Around(value = "operLogPointCut()")
    public Object saveOperLog(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        try {
            Object result = proceedingJoinPoint.proceed();
            UserOperLogEntity userOperLogEntity = new UserOperLogEntity();
            String resStr = JSONObject.toJSONString(result);
            System.out.println("resStr=" + resStr);
            CommonResult commonResult = (CommonResult) result;
            userOperLogEntity.setResponseCode(commonResult.getCode().toString());
            userOperLogEntity.setResponseMessage(commonResult.getMsg());

            MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
            Method method = signature.getMethod();
            OperationLogAnnotation annotation = method.getAnnotation(OperationLogAnnotation.class);
            if (annotation != null) {
                userOperLogEntity.setOperType(annotation.operType());
                userOperLogEntity.setOperDesc(annotation.operDesc());
                userOperLogEntity.setModuleCode(annotation.operModule());
            }
            LoginUser loginUser = SecurityFrameworkUtils.getLoginUser();
            if (loginUser == null) {
                throw new RuntimeException("user_id not exits");
            }
            String objId = CONTENT.get();
            if (objId == null) {
                throw new RuntimeException("op_objet_id not in current thead");
            }
            // 获得用户基本信息
            AdminUserRespDTO user = adminUserApi.getUser(loginUser.getId()).getCheckedData();
            userOperLogEntity.setCreatorUserid(user.getId());
            userOperLogEntity.setCreatorUsername(user.getUsername());
            RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
            if (requestAttributes != null) {
                HttpServletRequest request = (HttpServletRequest) requestAttributes.resolveReference(RequestAttributes.REFERENCE_REQUEST);
                if(request!=null){
                    userOperLogEntity.setUri(request.getRequestURI());
                    userOperLogEntity.setRemoteAddr(request.getRemoteAddr());
                } else {
                    userOperLogEntity.setUri("");
                    userOperLogEntity.setRemoteAddr("");
                }
            } else {
                throw new RuntimeException("Request object is null");
            }
            userOperLogEntity.setObjectId(objId);
            userOperLogEntity.setCreateTime(LocalDateTime.now());
            Object[] args = proceedingJoinPoint.getArgs();
            userOperLogEntity.setParams(JSONObject.toJSONString(args));
            userOperLogMapper.insert(userOperLogEntity);
            return result;
        } finally {
            clearThreadLocal();
        }
    }

    public static void setContent(String content) {
        CONTENT.set(content);
    }

    private static void clearThreadLocal() {
        CONTENT.remove();
    }

}
