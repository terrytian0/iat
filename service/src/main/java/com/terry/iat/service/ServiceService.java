package com.terry.iat.service;


import com.github.pagehelper.PageInfo;
import com.terry.iat.dao.entity.ServiceEntity;
import com.terry.iat.service.vo.ServiceVO;

/**
 * @author terry
 */
public interface ServiceService {
    /**
     * @param serviceVO
     * @return
     */
    ServiceEntity create(ServiceVO serviceVO);

    /**
     * @param serviceVO
     * @return
     */
    ServiceEntity update(ServiceVO serviceVO);

    /**
     * @param id
     * @return
     */
    int delete(Long id);

    /**
     * 获取service详情
     *
     * @param id
     * @return
     */
    ServiceEntity getById(Long id);

    /**
     * @param uniqueKey
     * @return
     */
    ServiceEntity getByUniqueKey(String uniqueKey);

    /**
     * @param pn
     * @param ps
     * @param searchText
     * @return
     */
    PageInfo search(Integer pn, Integer ps, String searchText);
}
