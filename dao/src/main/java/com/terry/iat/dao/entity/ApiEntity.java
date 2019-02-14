package com.terry.iat.dao.entity;

import com.terry.iat.dao.base.BaseEntity;
import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;
import java.util.Set;


/**
 * @author terry
 */
@Data
@Table(name="api")
public class ApiEntity extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    @Column(name = "service_id")
    private Long serviceId;
    @Column(name = "name")
    private String name;
    @Column(name = "path")
    private String path;
    @Column(name = "method")
    private String method;
    @Column(name="version")
    private Integer version;
    @Column(name="description")
    private String description;
    @Column(name="deleted")
    private Integer deleted=0;
    @Column(name = "create_user")
    private String createUser;
    @Column(name = "create_time")
    private Timestamp createTime;
    @Column(name = "update_user")
    private String updateUser;
    @Column(name = "update_time")
    private Timestamp updateTime;

    @Transient
    private List<HeaderEntity> header;
    @Transient
    private List<FormDataEntity> formData;
    @Transient
    private List<ResultCodeEntity> resultCode;
    @Transient
    private BodyEntity body;

    @Transient
    private Set<String> parameters;

}
