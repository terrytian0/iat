package com.terry.iat.dao.entity;

import com.terry.iat.dao.base.BaseEntity;
import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;


/**
 * @Description TODO
 * @author terry
 * @Date 2019/2/23 10:53
 * @Version 1.0 
 **/
@Data
@Table(name="task_testcase_parameter")
public class TaskTestcaseParameterEntity extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    @Column(name = "task_id")
    private Long taskId;
    @Column(name = "testplan_id")
    private Long testplanId;
    @Column(name = "testcase_id")
    private Long testcaseId;
    @Column(name = "parameters")
    private String parameters;
    @Column(name = "status")
    private String status;
    @Column(name = "message")
    private String message;
    @Column(name = "start_time")
    private Timestamp startTime;
    @Column(name = "end_time")
    private Timestamp endTime;
}
