package com.terry.iat.service.vo;

import com.terry.iat.dao.entity.EnvEntity;
import lombok.Data;

import java.util.List;

/**
 * @author terry
 * @version 1.0
 * @class name TestplanEnvVO
 * @description TODO
 * @date 2019/2/25 10:56
 **/
@Data
public class TestplanEnvVO {
    private Long serviceId;
    private String serviceName;
    private List<EnvEntity> envs;
    private EnvEntity env;
}
