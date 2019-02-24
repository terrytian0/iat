package com.terry.iat.dao.entity;

import com.terry.iat.dao.base.BaseEntity;
import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;


/**
 * @Description TODO
 * @author terry
 * @Date 2019/2/23 10:52
 * @Version 1.0 
 **/
@Data
@Table(name="task")
public class TaskEntity extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    @Column(name = "service_id")
    private Long serviceId;
    @Column(name = "testplan_id")
    private Long testplanId;
    @Column(name = "testplan_name")
    private String testplanName;
    @Column(name = "tag")
    private String tag;
    @Column(name = "pass_rate")
    private Integer passRate;
    @Column(name = "coverage")
    private Integer coverage;
    @Column(name = "status")
    private String status;
    @Column(name = "message")
    private String message;
    @Column(name = "start_time")
    private Timestamp startTime;
    @Column(name = "end_time")
    private Timestamp endTime;
    @Column(name = "client")
    private String client;
    @Column(name = "create_user")
    private String createUser;
    @Column(name = "create_time")
    private Timestamp createTime;
    @Column(name = "update_user")
    private String updateUser;
    @Column(name = "update_time")
    private Timestamp updateTime;

    @Transient
    private List<TaskTestcaseEntity> testcases;
}
