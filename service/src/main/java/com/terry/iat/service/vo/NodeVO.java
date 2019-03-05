package com.terry.iat.service.vo;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * @Description TODO
 * @author terry
 * @Date 2019/3/3 19:29
 * @Version 1.0 
 **/
@Data
public class NodeVO {
    private Integer level;
    private Long id;
    private Long parentid;
    private String text;
    private String type;
    private String status;
    private List<String> tags;
    private List<NodeVO> nodes;
    private Long taskId;
    private Long testplanId;
    private Long testcaseId;
    private Long parameterId;
    private Long testcaseKeywordId;
    private Long keywordId;
    private Long keywordApiId;
    private Long apiId;
}
