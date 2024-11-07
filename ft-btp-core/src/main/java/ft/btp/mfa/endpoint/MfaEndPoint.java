package ft.btp.mfa.endpoint;

import com.google.zxing.WriterException;
import ft.btp.mfa.MfaProcess;
import ft.btp.mfa.aop.MfaCheckAround;
import ft.btp.mfa.component.GoogleAuthHelper;
import ft.btp.mfa.component.QrCodeUtils;
import ft.btp.mfa.exception.MfaException;
import ft.btp.mfa.exception.MfaExceptionThrower;
import ft.btp.mfa.show.MfaContext;
import ft.btp.mfa.show.MfaEnum;
import ft.btp.mfa.show.MfaInfo;
import ft.btp.mfa.show.MfaResult;
import jakarta.annotation.security.PermitAll;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Objects;

import static ft.btp.mfa.enums.MfaErrorEnum.*;

@RestController
@RequestMapping("/mfa/auth")
@Validated
@Slf4j
public class MfaEndPoint {

    public MfaEndPoint() {
        log.info("MfaEndPoint instant");
    }

    @Autowired
    private GoogleAuthHelper googleAuthHelper;

    /**
     * 返回绑定谷歌MFA的二维码
     *
     * @param request
     * @return
     */
    @RequestMapping("/mfa-qrcode")
    @PermitAll
    public Object getMfaQrcode(HttpServletRequest request) {
        MfaContext mfaContext = (MfaContext) request.getAttribute(MfaCheckAround.MFA_CONTEXT);
        String authUser = mfaContext.getAuthUser();
        String authFlag = mfaContext.getAuthFlag();
        String secret = googleAuthHelper.getSecret(authUser);

        MfaProcess process = mfaContext.getProcess();
        //生成二维码url
        String qrStr = "";
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
            String url = googleAuthHelper.getQRBarcodeURL(authUser, authFlag, secret);
            QrCodeUtils.writeToStream(url, bos);
            qrStr = Base64.encodeBase64String(bos.toByteArray());
        } catch (WriterException | IOException e) {
            MfaExceptionThrower.throwsMfaException(process, new MfaException(GENERATE_QR_CODE_FAILED, e));
        }
        if (StringUtils.isEmpty(qrStr)) {
            MfaExceptionThrower.throwsMfaException(process, new MfaException(QR_CODE_IS_BLANK));
        }

        if (Objects.nonNull(process)) {
            try {
                process.saveMfaSecret(request, mfaContext.getTargetArgs(), mfaContext.getTargetResult(), secret);
            } catch (Exception e) {
                log.error("getMfaQrcode error", e);
                MfaExceptionThrower.throwsMfaException(process, new MfaException(GENERATE_QR_CODE_FAILED, e));
            }
        }
        MfaInfo<String> mfaInfo = new MfaInfo<String>(MfaEnum.QR_CODE.getMfaStatus(), "data:image/png;base64," + qrStr);
        return wrapper(MfaResult.success(mfaInfo), process);
    }

    /**
     * 确认绑定MFA状态
     *
     * @param request
     * @return
     */
    @RequestMapping("/mfa-binding")
    @PermitAll
    public Object updateMfaBindingStatus(HttpServletRequest request) {
        MfaContext mfaContext = (MfaContext) request.getAttribute(MfaCheckAround.MFA_CONTEXT);
        MfaProcess process = mfaContext.getProcess();
        MfaExceptionThrower.notNull(process, mfaContext, MFA_NOT_NULL);
        if (Objects.nonNull(process)) {
            try {
                process.doActionWhenBindingDone(request, mfaContext.getTargetArgs(), mfaContext.getTargetResult());
            } catch (Exception e) {
                log.error("updateMfaBindingStatus error", e);
                MfaExceptionThrower.throwsMfaException(process, new MfaException(CONFIRM_BINDING_FAILED, e));
            }
        }
        MfaInfo<Boolean> mfaInfo = new MfaInfo<Boolean>(MfaEnum.CONFIRM_BINDING.getMfaStatus(), true);
        return wrapper(MfaResult.success(mfaInfo), process);
    }

    /**
     * 判断是否绑定了mfa
     *
     * @return 是否绑定了mfa true:已经绑定 false:未绑定
     */
    @RequestMapping("/is-binding-mfa")
    @PermitAll
    public Object isBindingMfa(HttpServletRequest request) {
        MfaContext mfaContext = (MfaContext) request.getAttribute(MfaCheckAround.MFA_CONTEXT);
        MfaProcess process = mfaContext.getProcess();
        MfaExceptionThrower.notNull(process, mfaContext, MFA_NOT_NULL);

        MfaInfo<Boolean> mfaInfo = new MfaInfo<Boolean>(MfaEnum.ALREADY_BINDING.getMfaStatus(), true);
        return wrapper(MfaResult.success(mfaInfo), process);
    }

    /**
     * 包装响应
     *
     * @param result  http request
     * @param process process处理器
     * @return 包装后结果
     */
    private Object wrapper(MfaResult result, MfaProcess process) {
        if (Objects.nonNull(process)) {
            return process.buildWrapper().wrapMfaResult(result);
        }
        return result;
    }

}
