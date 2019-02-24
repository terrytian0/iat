package com.terry.iat.dao.entity;

import com.terry.iat.dao.base.BaseEntity;
import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @Description TODO
 * @author terry
 * @Date 2019/2/23 10:54
 * @Version 1.0 
 **/
@Data
@Table(name="parameter_value")
public class ParameterValueEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long testcaseId;
    private Long keyId;
    private Integer rowNum;
    private String value;
}
