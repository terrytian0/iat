package com.terry.iat.dao.entity;

import com.terry.iat.dao.base.BaseEntity;
import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;


/**
 * @Description TODO
 * @author terry
 * @Date 2019/2/23 10:53
 * @Version 1.0 
 **/
@Data
@Table(name="task_testcase")
public class TaskTestcaseEntity extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    @Column(name = "task_id")
    private Long taskId;
    @Column(name = "testcase_id")
    private Long testcaseId;
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "idx")
    private Integer idx;
    @Column(name = "status")
    private String status;

    @Transient
    private List<TaskTestcaseKeywordEntity> keywords;
    @Transient
    private List<TaskTestcaseParameterEntity> parameters;
}
