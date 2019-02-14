package com.terry.iat.service;


import com.terry.iat.dao.entity.HeaderEntity;
import com.terry.iat.service.vo.HeaderVO;

import java.util.List;

/**
 * @author terry
 */
public interface HeaderService {
    /**
     * 通过Api查找所有Header
     *
     * @param apiId
     * @return
     */
    List<HeaderEntity> getByApiId(Long apiId);

    /**
     * 批量创建header
     *
     * @param headerVOList
     * @return
     */
    List<HeaderEntity> create(List<HeaderVO> headerVOList);

    int push(Long apiId, List<HeaderVO> headerVOList);

    /**
     * 批量修改header
     *
     * @param headerVOList
     * @return
     */
    List<HeaderEntity> update(List<HeaderVO> headerVOList);

    /**
     * 修改header
     *
     * @param headerVO
     * @return
     */
    HeaderEntity update(HeaderVO headerVO);

    /**
     * 批量删除header
     *
     * @param ids
     * @return
     */
    int delete(List<Long> ids);
}
