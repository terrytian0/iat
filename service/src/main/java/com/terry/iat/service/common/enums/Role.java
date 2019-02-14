package com.terry.iat.service.common.enums;

public enum Role {
    ADMIN, NORMAL;

    public static Role getRole(String name) {
        switch (name){
            case "ADMIN":
                return ADMIN;
            default:
                return NORMAL;
        }
    }
}
