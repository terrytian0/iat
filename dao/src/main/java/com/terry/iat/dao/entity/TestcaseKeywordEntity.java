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
@Table(name="testcase_keyword")
public class TestcaseKeywordEntity extends BaseEntity implements Comparable<TestcaseKeywordEntity>{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "keyword_id")
    private Long keywordId;
    @Column(name = "testcase_id")
    private Long testcaseId;
    @Column(name = "idx")
    private Integer idx;
    @Transient
    private KeywordEntity detail;

    @Override
    public int compareTo(TestcaseKeywordEntity o) {
        return this.idx-o.getIdx();
    }
}
