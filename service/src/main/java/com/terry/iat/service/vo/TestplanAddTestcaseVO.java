package com.terry.iat.service.vo;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @Description TODO
 * @author terry
 * @Date 2019/2/18 12:33
 * @Param 
 * @return 
 **/
@Data
public class TestplanAddTestcaseVO {
    @NotNull
    private List<Long> testcaseIds;
    private Long testplanId;
}
