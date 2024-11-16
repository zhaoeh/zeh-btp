package zeh.btp.enums;

public interface ResultCode {

    Boolean getSuccess(Object... actualParams);

    String getCode(Object... actualParams);

    String getMessage(Object... actualParams);
}
