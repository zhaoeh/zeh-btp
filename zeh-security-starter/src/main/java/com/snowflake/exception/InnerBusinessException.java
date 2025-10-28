package com.snowflake.exception;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.io.Serial;


@Getter
@Setter
public class InnerBusinessException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = -6947767363155388313L;

    /**
     * 错误码
     */
    protected Integer errorCode;


    /**
     * 错误信息
     */
    protected String errorMsg;

    /**
     * 动态提示日志描述
     */
    private String causeDesc;

    /**
     * 是否打印异常堆栈
     */
    private boolean printStack;


    public InnerBusinessException(String errorMsg) {
        super(errorMsg);
        this.errorMsg = errorMsg;
    }

    public String buildCauseDesc() {
        if (!printStack && StringUtils.isNotBlank(causeDesc)) {
            // 不打印堆栈并且动态提示语不为空的场景，再构建动态提示语
            StackTraceElement[] stackTraces = this.getStackTrace();
            if (stackTraces.length > 0) {
                StringBuilder builder = new StringBuilder();
                // 获取第一层堆栈，即栈顶元素，为最开始抛出异常的位置
                StackTraceElement element = stackTraces[0];
                String className = element.getClassName();
                String methodName = element.getMethodName();
                int lineNumber = element.getLineNumber();
                Integer errorCoded = this.getErrorCode();
                String errorMsg = this.getErrorMsg();
                builder.append("\n").append("className:").append(className).
                        append("\n").append("methodName:").append(methodName).
                        append("\n").append("lineNumber:").append(lineNumber).
                        append("\n").append("errorCode:").append(errorCoded).
                        append("\n").append("errorMsg:").append(errorMsg).
                        append("\n").append("causeDesc:").append(causeDesc);
                return builder.toString();
            }
        }
        return null;
    }

    public InnerBusinessException toPrintStack() {
        this.printStack = true;
        return this;
    }

    public InnerBusinessException desc(String causeDesc) {
        this.setCauseDesc(causeDesc);
        return this;
    }

}
