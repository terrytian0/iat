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
@Table(name="testplan_testcase")
public class TestplanTestcaseEntity extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    @Column(name = "testplan_id")
    private Long testplanId;
    @Column(name = "testcase_id")
    private Long testcaseId;
    @Column(name = "idx")
    private Integer idx;

    @Transient
    private TestcaseEntity detail;
}
