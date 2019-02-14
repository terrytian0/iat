package com.terry.iat.service.common.bean;

/**
 * @author terry
 */
public enum ResultCode {
    // 0-100为业务中的异常码


    // 100-200是统一的异常码
    /**
     * 成功
     */
    SUCCESS("D0000100", "成功"),
    /**
     * 参数异常
     */
    INVALID_PARAMS("D0000101", "参数异常"),
    /**
     * 非法的URL
     */
    URL_INVALID("D0000102", "非法的URL"),
    /**
     * 拒绝访问
     */
    REFUSE_ACCESS("D0000103", "拒绝访问"),
    /**
     * 您已下线,请重新登录
     */
    NEED_LOGIN("D0000104", "您已下线,请重新登录"),
    /**
     * 无效Session
     */
    INVALID_SESSION("D0000105", "无效Session"),
    /**
     * 权限异常
     */
    PERMISSION_DENIED("D0000106", "没有权限操作，请联系系统管理员或产品管理员"),

    /**
     * 外部服务异常
     */
    EXTERNAL_SERVICE_EXCEPTION("D0000107", "外部服务异常"),

    /**
     * 系统异常
     */
    SYSTEM_EXCEPTION("D000200", "系统错误，请联系系统管理员！");
    /**
     * 结果码
     */
    private String code;

    /**
     * 描述
     */
    private String message;

    ResultCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public ResultCode setMessage(String message){
        this.message=message;
        return this;
    }
    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
