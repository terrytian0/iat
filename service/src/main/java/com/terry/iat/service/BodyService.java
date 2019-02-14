package com.terry.iat.service;


import com.terry.iat.dao.entity.BodyEntity;
import com.terry.iat.service.vo.BodyVO;

/**
 * @author terry
 */
public interface BodyService {
    /**
     * 通过Api查找所有Body
     *
     * @param apiId
     * @return
     */
    BodyEntity getByApiId(Long apiId);

    /**
     * 批量创建Body
     *
     * @param bodyVO
     * @return
     */
    BodyEntity create(BodyVO bodyVO);

    /**
     * 创建或修改
     *
     * @param bodyVO
     * @return
     */
    int push(Long apiId, BodyVO bodyVO);

    /**
     * 批量修改Body
     *
     * @param bodyVO
     * @return
     */
    BodyEntity update(BodyVO bodyVO);

    /**
     * 删除Body
     *
     * @param id
     * @return
     */
    int delete(Long id);
}
