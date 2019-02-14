package com.terry.iat.service.vo;

import lombok.Data;

@Data
public class HeaderVO {
    private Long id;
    private Long apiId;
    private String name;
    private String defaultValue;
    /**
     * String,Int
     */
    private String type;
}
