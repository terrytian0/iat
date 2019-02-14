package com.terry.iat.dao.entity;

import com.terry.iat.dao.base.BaseEntity;
import lombok.Data;

import javax.persistence.*;


/**
 * @author terry
 */
@Data
@Table(name="testcase_keyword_api")
public class TestcaseKeywordApiEntity extends BaseEntity implements Comparable<TestcaseKeywordApiEntity>{
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
    public int compareTo(TestcaseKeywordApiEntity o) {
        return this.idx-o.getIdx();
    }
}
