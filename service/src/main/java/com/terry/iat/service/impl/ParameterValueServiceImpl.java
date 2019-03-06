package com.terry.iat.service.impl;

import com.terry.iat.service.common.bean.ResultCode;
import com.terry.iat.service.common.exception.BusinessException;
import com.terry.iat.dao.entity.ParameterValueEntity;
import com.terry.iat.dao.mapper.ParameterValueMapper;
import com.terry.iat.service.ParameterKeyService;
import com.terry.iat.service.ParameterValueService;
import com.terry.iat.service.common.base.BaseServiceImpl;
import com.terry.iat.service.vo.TestcaseAddParameterVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.*;

@Service
public class ParameterValueServiceImpl extends BaseServiceImpl implements ParameterValueService {

    @Autowired
    private ParameterKeyService parameterKeyService;

    @Autowired
    private ParameterValueMapper parameterValueMapper;

    @Override
    public List<Map<String, String>> getByTestcaseId(Long testcaseId) {
        Map<Long, String> parameterKey = parameterKeyService.getByTestcaseId(testcaseId);
        if(parameterKey.isEmpty()){
            return Collections.EMPTY_LIST;
        }
        Example example = new Example(ParameterValueEntity.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andIn("keyId", parameterKey.keySet());
        List<ParameterValueEntity> parameterValueEntityList = parameterValueMapper.selectByExample(example);
        Map<Integer, Map<String, String>> parameters = new HashMap<>();
        for (ParameterValueEntity parameterValueEntity : parameterValueEntityList) {
            Map<String, String> row = parameters.get(parameterValueEntity.getRowNum());
            if (row == null) {
                row = new HashMap<>();
                row.put("rowNum", String.valueOf(parameterValueEntity.getRowNum()));
            }
            row.put(parameterKey.get(parameterValueEntity.getKeyId()), parameterValueEntity.getValue());
            parameters.put(parameterValueEntity.getRowNum(),row);
        }
        List<Map<String, String>> data = new ArrayList<>();
        for (Map<String, String> value : parameters.values()) {
            data.add(value);
        }
        return data;
    }


    @Override
    public int deleteByKeyIds(List<Long> ids) {
        if (ids.isEmpty()) {
            return 0;
        }
        Example example = new Example(ParameterValueEntity.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andIn("keyId", ids);
        return parameterValueMapper.deleteByExample(example);
    }

    @Override
    public int createOrUpdate(List<TestcaseAddParameterVO> testcaseAddParameterVOS) {
        int rows = 0;
        for (TestcaseAddParameterVO testcaseAddParameterVO : testcaseAddParameterVOS) {
            if (testcaseAddParameterVO.getRowNum() != null) {
                int drows = delete(testcaseAddParameterVO.getTestcaseId(), testcaseAddParameterVO.getRowNum());
                if(drows==0){
                    throw new BusinessException(ResultCode.INVALID_PARAMS.setMessage("删除参数行失败！"));
                }
            }
            rows = rows + createOrupdate(testcaseAddParameterVO.getTestcaseId(), testcaseAddParameterVO.getParameters(), testcaseAddParameterVO.getRowNum());
        }
        return rows;
    }


    private synchronized int createOrupdate(Long testcaseId, Map<String, String> parameters, Integer rowNum) {
        if (parameters.isEmpty()) {
            return 0;
        }
        if (rowNum == null) {
            rowNum = getNextRowNum(testcaseId);
        }
        List<ParameterValueEntity> parameterValueEntityList = new ArrayList<>();
        Map<Long, String> keys = parameterKeyService.getByTestcaseId(testcaseId);
        Map<String, Long> skeys = new HashMap<>();
        keys.forEach((k, v) -> {
            skeys.put(v, k);
        });
        for (String s : parameters.keySet()) {
            ParameterValueEntity parameterValueEntity = new ParameterValueEntity();
            parameterValueEntity.setKeyId(skeys.get(s));
            parameterValueEntity.setValue(parameters.get(s));
            parameterValueEntity.setRowNum(rowNum);
            parameterValueEntity.setTestcaseId(testcaseId);
            parameterValueEntityList.add(parameterValueEntity);
        }
        return parameterValueMapper.insertList(parameterValueEntityList);
    }

    private int getNextRowNum(Long testcaseId) {
        Integer rowNum = parameterValueMapper.getMaxRowNum(testcaseId);
        if (rowNum == null) {
            return 1;
        } else {
            return rowNum + 1;
        }
    }

    @Override
    public int delete(Long testcaseId, Integer rowNum) {
        Example example = new Example(ParameterValueEntity.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("testcaseId", testcaseId);
        criteria.andEqualTo("rowNum", rowNum);
        return parameterValueMapper.deleteByExample(example);
    }

    @Override
    public Integer getCountByTestcaseId(Long testcaseId) {
        return parameterValueMapper.getCountByTestcaseId(testcaseId);
    }
}
