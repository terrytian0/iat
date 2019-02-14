package com.terry.iat.service.vo;

import com.terry.iat.service.common.base.BaseVO;
import lombok.Data;

import java.util.List;

@Data
public class ServiceAddUserVO extends BaseVO {
    private Long serviceId;
    private List<Long> userIds;
}
