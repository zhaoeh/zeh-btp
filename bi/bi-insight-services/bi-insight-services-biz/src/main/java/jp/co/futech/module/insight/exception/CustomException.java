package jp.co.futech.module.insight.exception;

/**
 * Custom Exception
 *
 * @author tao.ma@futech.co.jp
 * @date 2024/4/4
 */
public class CustomException extends RuntimeException{

    protected Integer code;
    protected String message;

    public CustomException(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public CustomException() {

    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
