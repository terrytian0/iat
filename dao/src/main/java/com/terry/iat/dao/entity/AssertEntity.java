package com.terry.iat.dao.entity;

import com.terry.iat.dao.base.BaseEntity;
import lombok.Data;

import javax.persistence.*;


/**
 * @Description TODO
 * @author terry
 * @Date 2019/2/23 10:54
 * @Version 1.0 
 **/
@Data
@Table(name="assert")
public class AssertEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "keyword_api_id")
    private Long keywordApiId;
    @Column(name = "locale")
    private String locale;
    @Column(name = "method")
    private String method;
    @Column(name = "rule")
    private String rule;
    @Column(name = "type")
    private String type;
    @Column(name = "value")
    private String value;
    @Column(name = "description")
    private String description;
    @Transient
    private String actual;
    @Transient
    private Boolean status;
}
