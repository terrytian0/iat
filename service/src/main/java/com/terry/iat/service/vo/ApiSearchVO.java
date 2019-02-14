package com.terry.iat.service.vo;

import com.terry.iat.service.common.base.BaseVO;
import lombok.Data;

import java.io.Serializable;


/**
 * @author terry
 */
@Data
public class ApiSearchVO extends BaseVO implements Serializable {
    private Integer pn;
    private Integer ps;
    private Long id;
    private String name;
    private String path;
    private String method;
}
