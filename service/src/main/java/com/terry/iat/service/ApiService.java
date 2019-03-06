package com.terry.iat.service;


import com.github.pagehelper.PageInfo;
import com.terry.iat.dao.entity.ApiEntity;
import com.terry.iat.dao.entity.AssertEntity;
import com.terry.iat.dao.entity.EnvEntity;
import com.terry.iat.dao.entity.ExtractorEntity;
import com.terry.iat.service.core.HttpResult;
import com.terry.iat.service.vo.ApiVO;
import com.terry.iat.service.vo.ParameterVO;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author terry
 */
public interface ApiService {
    /**
     * 创建API
     *
     * @param apiVO
     * @return
     */
    ApiEntity create(ApiVO apiVO);

    /**
     * @param apiVOS
     * @return
     */
    void push(List<ApiVO> apiVOS);

    /**
     * 创建API
     *
     * @param apiVO
     * @return
     */
    ApiEntity update(ApiVO apiVO);

    /**
     * 删除API
     *
     * @param id
     * @return
     */
    int delete(Long id);

    /**
     * 通过ID 获取API
     *
     * @param id
     * @return
     */
    ApiEntity getById(Long id);

    /**
     * 通过参数获取API
     *
     * @param pn
     * @param ps
     * @param searchText
     * @return
     */
    PageInfo getByKeys(Integer pn, Integer ps, String searchText, Long serviceId);

    /**
     * @param ids
     * @return
     */
    List<ApiEntity> getByIds(List<Long> ids);

    /**
     * @param serviceId
     * @param path
     * @param method
     * @return
     */
    ApiEntity get(Long serviceId, String path, String method);

    /**
     * @param serviceId
     * @return
     */
    List<ApiEntity> getByServiceId(Long serviceId);

    /**
     * api调试
     *
     * @param apiVO
     */
    HttpResult debug(ApiVO apiVO);

    /**
     *
     * @param apiEntity
     * @param envEntity
     * @param parameters
     * @param extractorEntityList
     * @return
     */
    HttpResult debug(ApiEntity apiEntity, EnvEntity envEntity, Map<String,String> parameters, List<ExtractorEntity> extractorEntityList, List<AssertEntity> assertEntityList);
    /**
     * 获取Api下的所有参数
     * @param apiId
     * @return
     */
    List<ParameterVO> getParameters(Long apiId);
    /**
     * @Description TODO
     * @author terry
     * @Date 2019/3/6 16:06
     * @Param [apiEntity]
     * @return java.util.Set<java.lang.String>
     **/
    Set<String> getParameters(ApiEntity apiEntity);

    /**
     * @Description TODO
     * @author terry          
     * @Date 2019/3/6 16:06
     * @Param []
     * @return java.lang.Long
     **/
    Integer getCount();
    /**
     * @Description TODO
     * @author terry
     * @Date 2019/3/6 16:39
     * @Param []
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.String>>
     **/
    List<Map<String,Object>> getWeekChart();
}
