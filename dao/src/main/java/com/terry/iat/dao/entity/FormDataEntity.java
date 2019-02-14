package com.terry.iat.dao.entity;

import com.terry.iat.dao.base.BaseEntity;
import lombok.Data;

import javax.persistence.*;


/**
 * @author terry
 */
@Data
@Table(name="form_data")
public class FormDataEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "api_id")
    private Long apiId;
    @Column(name = "name")
    private String name;
    @Column(name = "`default`")
    private String defaultValue;
    @Column(name = "type")
    private String type;
}
