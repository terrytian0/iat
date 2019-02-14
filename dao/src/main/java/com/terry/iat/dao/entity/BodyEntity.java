package com.terry.iat.dao.entity;

import com.terry.iat.dao.base.BaseEntity;
import lombok.Data;

import javax.persistence.*;


/**
 * @author terry
 */
@Data
@Table(name="body")
public class BodyEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "api_id")
    private Long apiId;
    @Column(name = "body")
    private String content;
    @Column(name = "`default`")
    private String defaultValue;
    @Column(name = "type")
    private String type;
}
