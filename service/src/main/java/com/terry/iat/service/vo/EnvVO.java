package com.terry.iat.service.vo;

import lombok.Data;

@Data
public class EnvVO {
    private Long id;
    private Long serviceId;
    private String env;
    private String host;
    private Integer port;
}
