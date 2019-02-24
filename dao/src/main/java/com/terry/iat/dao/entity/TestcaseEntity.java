package com.terry.iat.dao.entity;

import com.terry.iat.dao.base.BaseEntity;
import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;


/**
 * @Description TODO
 * @author terry
 * @Date 2019/2/23 10:53
 * @Version 1.0 
 **/
@Data
@Table(name="testcase")
public class TestcaseEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "service_id")
    private Long serviceId;
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "create_user")
    private String createUser;
    @Column(name = "create_time")
    private Timestamp createTime;
    @Column(name = "update_user")
    private String updateUser;
    @Column(name = "update_time")
    private Timestamp updateTime;
    @Transient
    private List<TestcaseKeywordApiEntity> keywords;
}
