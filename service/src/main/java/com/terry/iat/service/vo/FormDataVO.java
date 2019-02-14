package com.terry.iat.service.vo;

import lombok.Data;

@Data
public class FormDataVO {
    private Long id;
    private Long apiId;
    private String name;
    private boolean required;
    private String defaultValue;
    /**
     * String,Int
     */
    private String type;
}
