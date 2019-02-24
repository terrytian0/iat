package com.terry.iat.dao.mapper;


import com.terry.iat.dao.base.BaseMapper;
import com.terry.iat.dao.common.DataSource;
import com.terry.iat.dao.entity.TestplanTestcaseEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

/**
 * @Description TODO
 * @author terry
 * @Date 2019/2/18 10:40
 * @Version 1.0 
 **/
@Repository
@DataSource(value="iat")
public interface TestplanTestcaseMapper extends BaseMapper<TestplanTestcaseEntity> {
    @Select("SELECT MAX(idx) FROM testplan_testcase WHERE testplan_id=#{testplanId}")
    Integer getMaxIdx(Long testplanId);

    @Update("UPDATE testplan_testcase SET idx=idx+1 WHERE testplan_id=#{testplanId} AND idx<#{idx}")
    Integer updateFrist(@Param("testplanId") Long testplanId, @Param("idx") Integer idx);
    @Update("UPDATE testplan_testcase SET idx=idx-1 WHERE testplan_id=#{testplanId} AND idx>#{idx}")
    Integer updateLast(@Param("testplanId") Long testplanId,@Param("idx")Integer idx);
}
