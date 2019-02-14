package com.terry.iat.service.impl;

import com.terry.iat.service.common.base.BaseServiceImpl;
import com.terry.iat.service.common.bean.ResultCode;
import com.terry.iat.service.common.exception.BusinessException;
import com.terry.iat.dao.entity.FormDataEntity;
import com.terry.iat.dao.mapper.FormDataMapper;
import com.terry.iat.service.FormDataService;
import com.terry.iat.service.vo.FormDataVO;
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
public class FormDataServiceImpl extends BaseServiceImpl implements FormDataService {

    @Autowired
    private FormDataMapper formDataMapper;

    @Override
    public List<FormDataEntity> getByApiId(Long apiId) {
        Example example = new Example(FormDataEntity.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("apiId", apiId);
        List<FormDataEntity> formDataEntityList = formDataMapper.selectByExample(example);
        if(formDataEntityList==null){
            return Collections.EMPTY_LIST;
        }
        return formDataEntityList;
    }

    @Override
    public List<FormDataEntity> create(List<FormDataVO> formDataVOList) {
        List<FormDataEntity> formDataEntityList = new ArrayList<>();
        if (formDataVOList == null || formDataVOList.isEmpty()) {
            return formDataEntityList;
        }
        formDataVOList.forEach(
                h -> {
                    FormDataEntity headerEntity = new FormDataEntity();
                    headerEntity.setApiId(h.getApiId());
                    headerEntity.setName(h.getName());
                    headerEntity.setDefaultValue(h.getDefaultValue());
                    headerEntity.setType(h.getType());
                    formDataEntityList.add(headerEntity);
                });
        formDataMapper.insertList(formDataEntityList);
        return formDataEntityList;
    }

    @Override
    public int push(Long apiId, List<FormDataVO> formDataVOList) {
        if (formDataVOList == null || formDataVOList.isEmpty()) {
            return 0;
        }
        List<FormDataEntity> formDataEntityList = getByApiId(apiId);
        Map<String, FormDataEntity> formDataEntityHashMap = new HashMap<>();
        for (FormDataEntity formDataEntity : formDataEntityList) {
            formDataEntityHashMap.put(formDataEntity.getName(), formDataEntity);
        }
        List<FormDataVO> insertFormDataEntity = new ArrayList<>();
        List<FormDataVO> updateFormDataEntity = new ArrayList<>();
        formDataVOList.forEach(
                fv -> {
                    FormDataEntity fde = formDataEntityHashMap.get(fv.getName());
                    if (fde == null) {
                        insertFormDataEntity.add(fv);
                    } else if (!fde.getType().equals(fv.getType())) {
                        fv.setDefaultValue(fde.getDefaultValue());
                        updateFormDataEntity.add(fv);
                        formDataEntityHashMap.remove(fv.getName());
                    } else {
                        formDataEntityHashMap.remove(fv.getName());
                    }
                });
        List<Long> ids = new ArrayList<>();
        formDataEntityHashMap.forEach(
                (k, v) -> {
                    ids.add(v.getId());
                });
        if (!insertFormDataEntity.isEmpty()) {
            create(insertFormDataEntity);
        }
        if (!updateFormDataEntity.isEmpty()) {
            update(updateFormDataEntity);
        }
        if (!ids.isEmpty()) {
            delete(ids);
        }
        return insertFormDataEntity.size() + updateFormDataEntity.size() + ids.size();
    }

    @Override
    public List<FormDataEntity> update(List<FormDataVO> formDataVOList) {
        List<Long> ids = new ArrayList<>();
        Map<Long, FormDataVO> formDataVOHashMap = new HashMap<>();
        formDataVOList.forEach(
                h -> {
                    ids.add(h.getId());
                    formDataVOHashMap.put(h.getId(), h);
                });
        List<FormDataEntity> formDataEntityList = getByIds(ids);
        if (formDataVOList.size() != formDataEntityList.size()) {
            throw new BusinessException(ResultCode.INVALID_PARAMS.setMessage("部分FormData不存在"));
        }
        formDataEntityList.forEach(
                h -> {
                    FormDataVO formDataVO = formDataVOHashMap.get(h.getId());
                    h.setDefaultValue(formDataVO.getDefaultValue());
                    h.setName(formDataVO.getName());
                    h.setType(formDataVO.getType());
                    formDataMapper.updateByPrimaryKey(h);
                });
        return formDataEntityList;
    }

    @Override
    public FormDataEntity update(FormDataVO formDataVO) {
        FormDataEntity formDataEntity = formDataMapper.selectByPrimaryKey(formDataVO.getId());
        if (formDataEntity == null) {
            throw new BusinessException(ResultCode.INVALID_PARAMS.setMessage("FormData不存在"));
        }
        formDataEntity.setDefaultValue(formDataVO.getDefaultValue());
        formDataEntity.setName(formDataVO.getName());
        formDataEntity.setType(formDataVO.getType());
        formDataMapper.updateByPrimaryKey(formDataEntity);
        return formDataEntity;
    }

    @Override
    public int delete(List<Long> ids) {
        if(!ids.isEmpty()) {
            return formDataMapper.deleteByIds(listToString(ids));
        }
        return 0;
    }

    private List<FormDataEntity> getByIds(List<Long> ids) {
        if(ids.isEmpty()){
            return Collections.EMPTY_LIST;
        }
        return formDataMapper.selectByIds(listToString(ids));
    }
}
