package com.terry.iat.service.vo;

import com.terry.iat.service.common.base.BaseVO;
import lombok.Data;

import java.io.Serializable;
import java.util.List;


/**
 * @author terry
 */
@Data
public class ApiVO extends BaseVO implements Serializable {
    private Long id;
    private Long serviceId;
    private String uniqueKey;
    private String name;
    private String description;
    private String path;
    private String method;
    private String contentType;
    private List<HeaderVO> headers;
    private List<FormDataVO> formDatas;
    private List<ResultCodeVO> resultCodes;
    private BodyVO body;
    private String host;
    private List<ParameterVO> parameters;
    private Long envId;
}
