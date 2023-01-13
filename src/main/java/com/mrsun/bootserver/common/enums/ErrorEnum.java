package com.mrsun.bootserver.common.enums;

/**
 * 错误枚举
 */
public enum ErrorEnum {
    USERNAME_EXISTS_ERROR(10001, "用户名已存在"),
    ROLE_NAME_EXISTS_ERROR(10002, "角色名已存在"),
    ROLE_CODE_EXISTS_ERROR(10003, "角色编码已存在"),
    ROLE_USED_ERROR(10003, "角色已被分配"),
    PERMISSION_NAME_EXISTS_ERROR(10004, "权限名已存在"),
    PERMISSION_CODE_EXISTS_ERROR(10005, "权限编码已存在"),
    PERMISSION_USED_ERROR(10006, "权限已被分配"),
    SUPER_ADMIN_DELETE_ERROR(10007, "超级管理员不能删除"),
    PASSWORD_GROUP_NAME_EXISTS(10008, "分组名称已存在"),
    PASSWORD_SALT_ERROR(10009, "秘钥不正确"),
    PASSWORD_NOT_NULL(10010, "密码不能为空"),
    DATA_NOT_EXISTS(10011, "信息不存在"),
    WEBSITE_EXISTS(10012, "该网址已存在"),
    SYSTEM_CONFIG_DO_NOT_DELETE(10013, "不能删除系统参数"),
    HAS_CHILDREN_CONFIG_ERROR(10014, "请先删除下级配置"),
    USERNAME_PASSWORD_ERROR(10015, "用户名或密码错误"),
    CREDENTIALS_ERROR(10016, "认证失败"),
    ;
    private final int code;
    private final String message;

    ErrorEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
