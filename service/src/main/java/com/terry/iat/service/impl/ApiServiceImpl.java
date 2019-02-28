package com.terry.iat.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.terry.iat.service.common.utils.StringUtils;
import com.terry.iat.service.common.base.BaseServiceImpl;
import com.terry.iat.service.common.bean.ResultCode;
import com.terry.iat.service.common.exception.BusinessException;
import com.terry.iat.dao.entity.*;
import com.terry.iat.dao.mapper.ApiMapper;
import com.terry.iat.service.*;
import com.terry.iat.service.core.HttpDriver;
import com.terry.iat.service.core.HttpResult;
import com.terry.iat.service.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.*;

/**
 * @author terry
 */
@Slf4j
@Service
public class ApiServiceImpl extends BaseServiceImpl implements ApiService {

    @Autowired
    private ApiMapper apiMapper;

    @Autowired
    private HeaderService headerService;

    @Autowired
    private BodyService bodyService;

    @Autowired
    private FormDataService formDataService;

    @Autowired
    private ServiceService serviceService;

    @Autowired
    private ResultCodeService resultCodeService;

    @Autowired
    private EnvService envService;


    @Override
    public ApiEntity create(ApiVO apiVO) {
        ApiEntity apiEntity = new ApiEntity();
        apiEntity.setServiceId(apiVO.getServiceId());
        apiEntity.setName(apiVO.getName());
        apiEntity.setPath(apiVO.getPath());
        apiEntity.setMethod(apiVO.getMethod());
        apiEntity.setDescription(apiVO.getDescription());
        apiEntity.setCreateTime(getTimestamp());
        apiEntity.setCreateUser(getCurrentUser().getName());
        apiEntity.setVersion(1);
        apiMapper.insert(apiEntity);
        if (apiVO.getHeaders() != null) {
            for (HeaderVO headerVO : apiVO.getHeaders()) {
                headerVO.setApiId(apiEntity.getId());
            }
            headerService.create(apiVO.getHeaders());
        }
        if (apiVO.getFormDatas() != null) {
            for (FormDataVO formDataVO : apiVO.getFormDatas()) {
                formDataVO.setApiId(apiEntity.getId());
            }
            formDataService.create(apiVO.getFormDatas());
        }
        if (apiVO.getBody() != null) {
            apiVO.getBody().setApiId(apiEntity.getId());
            bodyService.create(apiVO.getBody());
        }
        return apiEntity;
    }

    @Override
    public void push(List<ApiVO> apiVOS) {
        if (apiVOS == null || apiVOS.isEmpty()) {
            return;
        }
        ServiceEntity serviceEntity = null;
        List<Long> insterIds = new ArrayList<>();
        for (ApiVO apiVO : apiVOS) {
            if (serviceEntity == null) {
                serviceEntity = serviceService.getByUniqueKey(apiVO.getUniqueKey());
                if (serviceEntity == null) {
                    throw new BusinessException(ResultCode.INVALID_PARAMS.setMessage("服务不存在！"));
                }
            }
            ApiEntity apiEntity = get(serviceEntity.getId(), apiVO.getPath(), apiVO.getMethod());
            apiVO.setServiceId(serviceEntity.getId());
            if (apiEntity == null) {
                apiEntity = create(apiVO);
            } else {

                int count = headerService.push(apiEntity.getId(), apiVO.getHeaders());
                count = count + formDataService.push(apiEntity.getId(), apiVO.getFormDatas());
                count = count + bodyService.push(apiEntity.getId(), apiVO.getBody());
                count = count + resultCodeService.push(apiEntity.getId(), apiVO.getResultCodes());
                if (count > 0) {
                    apiEntity.setVersion(apiEntity.getVersion() + 1);
                }
                apiEntity.setDeleted(0);
                apiEntity.setName(apiVO.getName());
                apiEntity.setDescription(apiVO.getDescription());
                apiMapper.updateByPrimaryKey(apiEntity);
            }
            insterIds.add(apiEntity.getId());
        }
        List<ApiEntity> apiEntityList = getByServiceId(serviceEntity.getId());
        if (apiEntityList != null && !apiEntityList.isEmpty()) {
            apiEntityList.forEach(apiEntity -> {
                if (!insterIds.contains(apiEntity.getId())) {
                    apiEntity.setDeleted(1);
                    apiMapper.updateByPrimaryKey(apiEntity);
                }
            });
        }
    }

    @Override
    public ApiEntity update(ApiVO apiVO) {
        ApiEntity apiEntity = apiMapper.selectByPrimaryKey(apiVO.getId());
        if (apiEntity == null) {
            throw new BusinessException(ResultCode.INVALID_PARAMS);
        }
        apiEntity.setMethod(apiVO.getMethod());
        apiEntity.setPath(apiVO.getPath());
        apiEntity.setName(apiVO.getName());
        apiEntity.setDescription(apiVO.getDescription());
        apiEntity.setUpdateTime(getTimestamp());
        apiEntity.setUpdateUser(getCurrentUser().getName());
        apiMapper.updateByPrimaryKey(apiEntity);
        headerService.update(apiVO.getHeaders());
        formDataService.update(apiVO.getFormDatas());
        bodyService.update(apiVO.getBody());
        return apiEntity;
    }

    @Override
    public int delete(Long id) {
        ApiEntity apiEntity = getById(id);
        if (apiEntity.getBody() != null) {
            bodyService.delete(apiEntity.getBody().getId());
        }
        if (apiEntity.getHeader() != null) {
            List<Long> ids = new ArrayList<>();
            for (HeaderEntity headerEntity : apiEntity.getHeader()) {
                ids.add(headerEntity.getId());
            }
            headerService.delete(ids);
        }
        if (apiEntity.getFormData() != null) {
            List<Long> ids = new ArrayList<>();
            for (FormDataEntity formDatum : apiEntity.getFormData()) {
                ids.add(formDatum.getId());
            }
            formDataService.delete(ids);
        }
        return apiMapper.deleteByPrimaryKey(id);
    }

    @Override
    public ApiEntity getById(Long id) {
        ApiEntity apiEntity = apiMapper.selectByPrimaryKey(id);
        if (apiEntity != null) {
            List<HeaderEntity> headerEntityList = headerService.getByApiId(id);
            List<FormDataEntity> formDataEntityList = formDataService.getByApiId(id);
            List<ResultCodeEntity> resultCodeEntityList = resultCodeService.getByApiId(id);
            BodyEntity bodyEntity = bodyService.getByApiId(id);
            apiEntity.setHeader(headerEntityList);
            apiEntity.setFormData(formDataEntityList);
            apiEntity.setResultCode(resultCodeEntityList);
            apiEntity.setBody(bodyEntity);
        } else {
            throw new BusinessException(ResultCode.INVALID_PARAMS.setMessage("Api不存在"));
        }
        return apiEntity;
    }

    @Override
    public PageInfo getByKeys(Integer pn, Integer ps, String searchText, Long serviceId) {
        String key = "%%";
        if (searchText != null) {
            key = new StringBuilder().append("%").append(searchText).append("%").toString();
        }
        PageHelper.startPage(pn, ps);
        Example example = new Example(ApiEntity.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("serviceId", serviceId);
        criteria.andEqualTo("deleted", 0);
        Example.Criteria criteria2 = example.createCriteria();
        criteria2.andLike("name", key);
        criteria2.orLike("path", key);
        example.and(criteria2);
        return new PageInfo(apiMapper.selectByExample(example));
    }

    @Override
    public List<ApiEntity> getByIds(List<Long> ids) {
        if(ids.isEmpty()){
            return Collections.EMPTY_LIST;
        }
        Example example = new Example(ApiEntity.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andIn("id", ids);
        List<ApiEntity> apiEntityList = apiMapper.selectByExample(example);
        if (apiEntityList.isEmpty()) {
            return Collections.emptyList();
        } else {
            apiEntityList.forEach(
                    a -> {
                        List<HeaderEntity> headerEntityList = headerService.getByApiId(a.getId());
                        List<FormDataEntity> formDataEntityList = formDataService.getByApiId(a.getId());
                        BodyEntity bodyEntity = bodyService.getByApiId(a.getId());
                        a.setHeader(headerEntityList);
                        a.setFormData(formDataEntityList);
                        a.setBody(bodyEntity);
                        a.setParameters(getParameters(a));
                    });
            return apiEntityList;
        }

    }

    @Override
    public ApiEntity get(Long serviceId, String path, String method) {
        Example example = new Example(ApiEntity.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("serviceId", serviceId);
        criteria.andEqualTo("path", path);
        criteria.andEqualTo("method", method);
        List<ApiEntity> apiEntityList = apiMapper.selectByExample(example);
        if (apiEntityList == null || apiEntityList.isEmpty()) {
            return null;
        }
        return apiEntityList.get(0);
    }

    @Override
    public List<ApiEntity> getByServiceId(Long serviceId) {
        Example example = new Example(ApiEntity.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("serviceId", serviceId);
        return apiMapper.selectByExample(example);
    }

    @Override
    public HttpResult debug(ApiVO apiVO) {
        if (apiVO.getEnvId() == null) {
            throw new BusinessException(ResultCode.INVALID_PARAMS.setMessage("请选择调试环境！"));
        }
        ApiEntity apiEntity = update(apiVO);
        EnvEntity envEntity = envService.getById(apiVO.getEnvId());
        return debug(apiEntity, envEntity, convertParameters(apiVO.getParameters()), Collections.EMPTY_LIST,Collections.EMPTY_LIST);
    }



    @Override
    public HttpResult debug(ApiEntity apiEntity, EnvEntity envEntity, Map<String, String> parameters, List<ExtractorEntity> extractorEntityList,List<AssertEntity> assertEntityList) {
        return HttpDriver.execute("http",envEntity.getHost(),envEntity.getPort(),apiEntity,parameters,extractorEntityList,assertEntityList);
    }


    @Override
    public List<ParameterVO> getParameters(Long apiId) {
        ApiEntity apiEntity = getById(apiId);
        if (apiEntity == null) {
            throw new BusinessException(ResultCode.INVALID_PARAMS.setMessage("api不存在！"));
        }
        Set<String> parameters = getParameters(apiEntity);
        List<ParameterVO> parameterVOList = new ArrayList<>();
        for (String parameter : parameters) {
            ParameterVO parameterVO = new ParameterVO();
            parameterVO.setName(parameter);
            parameterVOList.add(parameterVO);
        }

        return parameterVOList;
    }

    @Override
    public Set<String> getParameters(ApiEntity apiEntity) {
        Set<String> parameters = new HashSet<>();
        if (!apiEntity.getHeader().isEmpty()) {
            for (HeaderEntity headerEntity : apiEntity.getHeader()) {
                if (headerEntity.getDefaultValue() == null) {
                    continue;
                }
                if (headerEntity.getDefaultValue().startsWith("#{") && headerEntity.getDefaultValue().endsWith("}")) {
                    parameters.add(headerEntity.getDefaultValue().substring(2, headerEntity.getDefaultValue().length() - 1));
                }
            }
        }

        if (!apiEntity.getFormData().isEmpty()) {
            for (FormDataEntity formDatum : apiEntity.getFormData()) {
                if (formDatum.getDefaultValue() == null) {
                    continue;
                }
                if (formDatum.getDefaultValue().startsWith("#{") && formDatum.getDefaultValue().endsWith("}")) {
                    parameters.add(formDatum.getDefaultValue().substring(2, formDatum.getDefaultValue().length() - 1));
                }
            }
        }

        if (apiEntity.getBody() != null && apiEntity.getBody().getDefaultValue() != null) {
            Set bp = StringUtils.getParameters(apiEntity.getBody().getDefaultValue());
            parameters.addAll(bp);
        }
        return parameters;
    }
}
