package com.terry.iat.dao.entity;

import com.terry.iat.dao.base.BaseEntity;
import lombok.Data;

import javax.persistence.*;
import java.util.List;


/**
 * @Description TODO
 * @author terry
 * @Date 2019/2/23 10:53
 * @Version 1.0 
 **/
@Data
@Table(name="task_testcase_keyword")
public class TaskTestcaseKeywordEntity extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    @Column(name = "task_id")
    private Long taskId;
    @Column(name = "testcase_id")
    private Long testcaseId;
    @Column(name = "testcase_keyword_id")
    private Long testcaseKeywordId;
    @Column(name = "keyword_id")
    private Long keywordId;
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "idx")
    private Integer idx;

    @Transient
    private String status;
    @Transient
    private List<TaskTestcaseKeywordApiEntity> apis;
}
