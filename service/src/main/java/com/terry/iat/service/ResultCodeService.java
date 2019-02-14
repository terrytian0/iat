package com.terry.iat.service;


import com.terry.iat.dao.entity.ResultCodeEntity;
import com.terry.iat.service.vo.ResultCodeVO;

import java.util.List;

/**
 * @author terry
 */
public interface ResultCodeService {
    /**
     * 通过Api查找所有result code
     *
     * @param apiId
     * @return
     */
    List<ResultCodeEntity> getByApiId(Long apiId);

    /**
     * 批量创建result code
     *
     * @param resultCodeVOList
     * @return
     */
    List<ResultCodeEntity> create(List<ResultCodeVO> resultCodeVOList);

    int push(Long apiId, List<ResultCodeVO> resultCodeVOList);

    /**
     * 批量修改resultcode
     *
     * @param resultCodeVOList
     * @return
     */
    List<ResultCodeEntity> update(List<ResultCodeVO> resultCodeVOList);

    /**
     * 修改result code
     *
     * @param resultCodeVO
     * @return
     */
    ResultCodeEntity update(ResultCodeVO resultCodeVO);

    /**
     * 批量删除result code
     *
     * @param ids
     * @return
     */
    int delete(List<Long> ids);
}
