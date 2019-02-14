package com.terry.iat.service;


import com.terry.iat.dao.entity.FormDataEntity;
import com.terry.iat.service.vo.FormDataVO;

import java.util.List;

/**
 * @author terry
 */
public interface FormDataService {
    /**
     * 通过Api查找所有FormData
     *
     * @param apiId
     * @return
     */
    List<FormDataEntity> getByApiId(Long apiId);

    /**
     * 批量创建FormData
     *
     * @param headerVOList
     * @return
     */
    List<FormDataEntity> create(List<FormDataVO> headerVOList);

    /**
     * 创建或修改
     *
     * @param apiId
     * @param headerVOList
     * @return
     */
    int push(Long apiId, List<FormDataVO> headerVOList);

    /**
     * 批量修改FormData
     *
     * @param headerVOList
     * @return
     */
    List<FormDataEntity> update(List<FormDataVO> headerVOList);

    /**
     * 修改formdata
     *
     * @param formDataVO
     * @return
     */
    FormDataEntity update(FormDataVO formDataVO);

    /**
     * 批量删除header
     *
     * @param ids
     * @return
     */
    int delete(List<Long> ids);
}
