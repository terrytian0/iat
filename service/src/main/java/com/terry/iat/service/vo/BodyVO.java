package com.terry.iat.service.vo;

import lombok.Data;

@Data
public class BodyVO {
    private Long id;
    private Long apiId;
    private String content;
    private String defaultValue;
    /**
     * String,Byte
     */
    private String type;
}
