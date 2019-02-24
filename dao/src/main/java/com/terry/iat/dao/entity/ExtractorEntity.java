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
@Table(name="extractor")
public class ExtractorEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "keyword_api_id")
    private Long keywordApiId;
    @Column(name = "type")
    private String type;
    @Column(name = "rule")
    private String rule;
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @Transient
    private String value;
}
