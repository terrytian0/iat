package com.terry.iat.dao.entity;

import com.terry.iat.dao.base.BaseEntity;
import lombok.Data;

import javax.persistence.*;


/**
 * @Description TODO
 * @author terry
 * @Date 2019/2/23 10:53
 * @Version 1.0 
 **/
@Data
@Table(name="task_testcase_keyword_api")
public class TaskTestcaseKeywordApiEntity extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    @Column(name = "task_id")
    private Long taskId;
    @Column(name = "testplan_id")
    private Long testplanId;
    @Column(name = "testcase_id")
    private Long testcaseId;
    @Column(name = "testcase_keyword_id")
    private Long testcaseKeywordId;
    @Column(name = "keyword_id")
    private Long keywordId;
    @Column(name = "keyword_api_id")
    private Long keywordApiId;
    @Column(name = "api_id")
    private Long apiId;
    @Column(name = "path")
    private String path;
    @Column(name = "method")
    private String method;
    @Column(name = "version")
    private Integer version;
    @Column(name = "description")
    private String description;
    @Column(name = "idx")
    private Integer idx;
    @Column(name = "headers")
    private String headers;
    @Column(name = "formdatas")
    private String formdatas;
    @Column(name = "body")
    private String body;
    @Column(name = "extractors")
    private String extractors;
    @Column(name = "asserts")
    private String asserts;
}
