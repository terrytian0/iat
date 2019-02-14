package com.terry.iat.service.common.enums;

public enum HttpMethod {
    POST, GET, PUT, DELETE;

    public static HttpMethod getHttpMethod(String method) {
        String m = method.toUpperCase();
        switch (m) {
            case "POST":
                return POST;
            case "GET":
                return GET;
            case "PUT":
                return PUT;
            case "DELETE":
                return DELETE;
            default:
                return GET;
        }
    }
}
