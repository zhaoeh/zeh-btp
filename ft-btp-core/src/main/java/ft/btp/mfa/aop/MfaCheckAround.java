package ft.btp.mfa.aop;

import ft.btp.mfa.MfaProcess;
import ft.btp.mfa.annotation.MfaAuth;
import ft.btp.mfa.component.GoogleAuthHelper;
import ft.btp.mfa.exception.MfaException;
import ft.btp.mfa.exception.MfaExceptionThrower;
import ft.btp.mfa.show.MfaContext;
import io.micrometer.common.util.StringUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.BooleanUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Objects;

import static ft.btp.mfa.enums.MfaErrorEnum.*;

/**
 * @description:
 * @author: ErHu.Zhao
 * @create: 2024-05-17
 **/
@Aspect
@Component
@Slf4j
public class MfaCheckAround {

    public static final String MFA_CONTEXT = "mfaContext";

    private static final String GET_QRCODE = "/mfa/auth/mfa-qrcode";

    private static final String BINDING_DONE = "/mfa/auth/mfa-binding";

    private static final String ALREADY_BINDING = "/mfa/auth/is-binding-mfa";

    private MfaProcess mfaProcess;

    private final GoogleAuthHelper googleAuthHelper;


    public MfaCheckAround(ObjectProvider<MfaProcess> objectProvider, GoogleAuthHelper googleAuthHelper) {
        log.info("MfaCheckAround instant");
        MfaProcess process = objectProvider.getIfAvailable();
        if (Objects.nonNull(process)) {
            this.mfaProcess = process;
        }
        MfaExceptionThrower.notNull(process, googleAuthHelper, AUTH_HELPER_NOT_NULL);
        this.googleAuthHelper = googleAuthHelper;
    }

    @Pointcut("@annotation(mfaAuth)")
    public void mfa(MfaAuth mfaAuth) {
    }

    @Around("mfa(mfaAuth)")
    public Object doMfa(ProceedingJoinPoint proceedingJoinPoint, MfaAuth mfaAuth) throws Throwable {
        Object[] targetArgs = proceedingJoinPoint.getArgs();
        Object targetResult = proceedingJoinPoint.proceed();
        if (Objects.isNull(mfaProcess)) {
            return targetResult;
        }
        try {
            return handleMfa(targetArgs, targetResult);
        } catch (Exception e) {
            throw e;
        } finally {
            mfaProcess.destroy(targetArgs, targetResult);
        }
    }

    /**
     * 处理mfa认证
     *
     * @param targetArgs
     * @param targetResult
     * @return
     * @throws Throwable
     */
    private Object handleMfa(Object[] targetArgs, Object targetResult) throws Throwable {
        mfaProcess.init(targetArgs, targetResult);
        // mfa认证未开启，直接放行
        if (!mfaProcess.enableMfa(targetArgs, targetResult)) {
            return targetResult;
        }
        HttpServletRequest request = null;
        HttpServletResponse response = null;
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (Objects.nonNull(requestAttributes)) {
            request = requestAttributes.getRequest();
            response = requestAttributes.getResponse();
        }
        if (Objects.isNull(request) || Objects.isNull(response)) {
            MfaExceptionThrower.throwsMfaException(mfaProcess, new MfaException(NOT_SUPPORT_NON_WEB));
        }
        MfaContext context = new MfaContext();
        context.setProcess(mfaProcess);
        context.setTargetArgs(targetArgs);
        context.setTargetResult(targetResult);
        // 确认绑定
        if (mfaProcess.bindingMfaDone(request, targetArgs, targetResult)) {
            String mfaSecretKey = mfaProcess.supplyMfaSecret(request, targetArgs, targetResult);
            MfaExceptionThrower.isTrue(mfaProcess, StringUtils.isNotBlank(mfaSecretKey), MFA_SECRET_KEY_NOT_BLANK);
            request.setAttribute(MFA_CONTEXT, context);
            request.getRequestDispatcher(BINDING_DONE).forward(request, response);
            return null;
        }
        // 未绑定或者请求重新绑定时
        if (!mfaProcess.bindingMfa(request, targetArgs, targetResult) || mfaProcess.reBindingMfa(request, targetArgs, targetResult)) {
            String authFlag = mfaProcess.authFlag(request, targetArgs, targetResult);
            String authUser = mfaProcess.authUser(request, targetArgs, targetResult);
            MfaExceptionThrower.isTrue(mfaProcess, StringUtils.isNotBlank(authFlag), AUTH_FLAG_NOT_BLANK);
            MfaExceptionThrower.isTrue(mfaProcess, StringUtils.isNotBlank(authUser), AUTH_USER_NOT_BLANK);
            context.setAuthUser(authUser);
            context.setAuthFlag(authFlag);
            request.setAttribute(MFA_CONTEXT, context);
            request.getRequestDispatcher(GET_QRCODE).forward(request, response);
            return null;
        }

        if (mfaProcess.bindingMfa(request, targetArgs, targetResult)) {
            // 绑定了mfa，没有校验码，则直接返回mfa绑定状态
            String mfaCode = mfaProcess.supplyMfaCode(request, targetArgs, targetResult);
            if (StringUtils.isBlank(mfaCode)) {
                request.setAttribute(MFA_CONTEXT, context);
                request.getRequestDispatcher(ALREADY_BINDING).forward(request, response);
            }
            // 绑定了mfa，且校验码已经上送，则需要校验请求 mfa_code
            String mfaSecretKey = mfaProcess.supplyMfaSecret(request, targetArgs, targetResult);
            MfaExceptionThrower.isTrue(mfaProcess, StringUtils.isNotBlank(mfaSecretKey), MFA_SECRET_KEY_NOT_BLANK_VALIDATE);
            // 宽限时间5*30秒
            googleAuthHelper.setWindowSize(mfaProcess.offset());
            boolean result = checkMfaCode(mfaCode, mfaSecretKey, request, targetArgs, targetResult);
            if (BooleanUtils.isTrue(result)) {
                try {
                    mfaProcess.doActionWhenCheckSucceed(request, targetArgs, targetResult);
                } finally {
                    mfaProcess.destroy(targetArgs, targetResult);
                }
                return targetResult;
            } else {
                MfaExceptionThrower.throwsMfaException(mfaProcess, new MfaException(UNAUTHORIZED));
            }
        }
        mfaProcess.destroy(targetArgs, targetResult);
        MfaExceptionThrower.throwsMfaException(mfaProcess, new MfaException(UNAUTHORIZED));
        return null;
    }

    private boolean checkMfaCode(String mfaCode, String mfaSecretKey, HttpServletRequest request, Object[] targetArgs, Object targetResult) {
        long t = System.currentTimeMillis();
        boolean result = false;
        try {
            result = googleAuthHelper.check_code(mfaSecretKey, Long.valueOf(mfaCode), t);
        } catch (Exception e) {
            MfaExceptionThrower.throwsMfaException(mfaProcess, new MfaException(CHECK_FAILED, e));
        } finally {
            if (BooleanUtils.isFalse(result)) {
                mfaProcess.doActionWhenCheckFailed(request, targetArgs, targetResult);
            }
        }
        return result;
    }


}
