package com.terry.iat.service.vo;

import com.terry.iat.service.common.base.BaseVO;
import lombok.Data;

import javax.persistence.Column;

/**
 * @author terry
 * @version 1.0
 * @class name TestplanVO
 * @description TODO
 * @date 2019/2/18 10:35
 **/
@Data
public class TestplanVO extends BaseVO {
    public Long id;
    /**
     * 服务ID
     */
    private Long serviceId;
    /**
     * 测试计划名称
     */
    private String name;
    /**
     * 测试策略
     */
    private String strategy;
}
