package com.terry.iat.dao.entity;

import com.terry.iat.dao.base.BaseEntity;
import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;


/**
 * @Description TODO
 * @author terry
 * @Date 2019/2/20 12:57
 * @Version 1.0 
 **/
@Data
@Table(name="task_testcase_keyword_api_result")
public class TaskTestcaseKeywordApiResultEntity extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    @Column(name = "task_id")
    private Long taskId;
    @Column(name = "testplan_id")
    private Long testplanId;
    @Column(name = "testcase_id")
    private Long testcaseId;
    @Column(name = "parameter_id")
    private Long parameterId;
    @Column(name = "testcase_keyword_id")
    private Long testcaseKeywordId;
    @Column(name = "keyword_id")
    private Long keywordId;
    @Column(name = "keyword_api_id")
    private Long keywordApiId;
    @Column(name = "api_id")
    private Long apiId;
    @Column(name = "url")
    private String url;
    @Column(name = "method")
    private String method;
    @Column(name = "request_headers")
    private String requestHeaders;
    @Column(name = "request_formdatas")
    private String requestFormdatas;
    @Column(name = "request_body")
    private String requestBody;
    @Column(name = "response_headers")
    private String responseHeaders;
    @Column(name = "response_body")
    private String responseBody;
    @Column(name = "extractors")
    private String extractors;
    @Column(name = "asserts")
    private String asserts;
    @Column(name = "status")
    private String status;
    @Column(name = "message")
    private String message;
    @Column(name = "start_time")
    private Timestamp startTime;
    @Column(name = "end_time")
    private Timestamp endTime;

    @Transient
    private List<TaskTestcaseEntity> testcases;
}
