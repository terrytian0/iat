package com.terry.iat.dao.entity;

import com.terry.iat.dao.base.BaseEntity;
import lombok.Data;

import javax.persistence.*;

/**
 * @Description TODO
 * @author terry
 * @Date 2019/2/23 10:54
 * @Version 1.0 
 **/
@Data
@Table(name = "keyword_api")
public class KeywordApiEntity extends BaseEntity implements Comparable<KeywordApiEntity> {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "keyword_id")
  private Long keywordId;

  @Column(name = "api_id")
  private Long apiId;

  @Column(name = "idx")
  private Integer idx;

  @Transient private ApiEntity detail;

  @Override
  public int compareTo(KeywordApiEntity o) {
    return this.idx = o.getIdx();
  }
}
