package com.terry.iat.service.impl;

import com.terry.iat.service.core.JsonCompare;
import com.terry.iat.service.common.base.BaseServiceImpl;
import com.terry.iat.service.common.exception.BusinessException;
import com.terry.iat.dao.entity.ResultCodeEntity;
import com.terry.iat.dao.mapper.ResultCodeMapper;
import com.terry.iat.service.ResultCodeService;
import com.terry.iat.service.vo.ResultCodeVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.terry.iat.service.common.bean.ResultCode.*;

/**
 * @author terry
 */
@Slf4j
@Service
public class ResultCodeServiceImpl extends BaseServiceImpl implements ResultCodeService {

    @Autowired
    private ResultCodeMapper resultCodeMapper;

    @Override
    public List<ResultCodeEntity> getByApiId(Long apiId) {
        Example example = new Example(ResultCodeEntity.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("apiId", apiId);
        return resultCodeMapper.selectByExample(example);
    }

    @Override
    public List<ResultCodeEntity> create(List<ResultCodeVO> resultCodeVOList) {
        List<ResultCodeEntity> resultCodeEntityList = new ArrayList<>();
        if (resultCodeVOList == null || resultCodeVOList.isEmpty()) {
            return resultCodeEntityList;
        }
        resultCodeVOList.forEach(
                hv -> {
                    ResultCodeEntity resultCodeEntity = new ResultCodeEntity();
                    resultCodeEntity.setApiId(hv.getApiId());
                    resultCodeEntity.setCode(hv.getCode());
                    resultCodeEntity.setDescription(hv.getDescription());
                    resultCodeEntityList.add(resultCodeEntity);
                });
        resultCodeMapper.insertList(resultCodeEntityList);
        return resultCodeEntityList;
    }

    @Override
    public int push(Long apiId, List<ResultCodeVO> resultCodeVOList) {
        if (resultCodeVOList == null || resultCodeVOList.isEmpty()) {
            return deleteByApiId(apiId);
        }
        List<ResultCodeEntity> resultCodeEntityList = getByApiId(apiId);
        Map<String, ResultCodeEntity> resultCodeEntityMap = new HashMap<>();
        for (ResultCodeEntity resultCodeEntity : resultCodeEntityList) {
            resultCodeEntityMap.put(resultCodeEntity.getCode(), resultCodeEntity);
        }
        List<ResultCodeVO> insertResultCodeEntity = new ArrayList<>();
        List<ResultCodeVO> updateResultCodeEntity = new ArrayList<>();
        resultCodeVOList.forEach(
                hv -> {
                    ResultCodeEntity hce = resultCodeEntityMap.get(hv.getCode());
                    if (hce == null) {
                        hv.setApiId(apiId);
                        insertResultCodeEntity.add(hv);
                    } else if (!JsonCompare.compare(hce.getDescription(),hv.getDescription())) {
                        hv.setApiId(apiId);
                        updateResultCodeEntity.add(hv);
                        resultCodeEntityMap.remove(hv.getCode());
                    } else {
                        resultCodeEntityMap.remove(hv.getCode());
                    }
                });
        List<Long> ids = new ArrayList<>();
        resultCodeEntityMap.forEach(
                (k, v) -> {
                    ids.add(v.getId());
                });
        if (!insertResultCodeEntity.isEmpty()) {
            create(insertResultCodeEntity);
        }
        if (!updateResultCodeEntity.isEmpty()) {
            update(updateResultCodeEntity);
        }
        if (!ids.isEmpty()) {
            delete(ids);
        }
        return updateResultCodeEntity.size() + updateResultCodeEntity.size() + ids.size();
    }

    @Override
    public List<ResultCodeEntity> update(List<ResultCodeVO> resultCodeVOList) {
        List<Long> ids = new ArrayList<>();
        Map<Long, ResultCodeVO> resultCodeVOMap = new HashMap<>();
        resultCodeVOList.forEach(
                h -> {
                    ids.add(h.getId());
                    resultCodeVOMap.put(h.getId(), h);
                });
        List<ResultCodeEntity> resultCodeEntityList = getByIds(ids);
        if (resultCodeVOList.size() != resultCodeEntityList.size()) {
            throw new BusinessException(INVALID_PARAMS.setMessage("部分ResultCode不存在"));
        }
        resultCodeEntityList.forEach(
                h -> {
                    ResultCodeVO resultCodeVO = resultCodeVOMap.get(h.getId());
                    h.setCode(resultCodeVO.getCode());
                    h.setDescription(resultCodeVO.getDescription());
                    resultCodeMapper.updateByPrimaryKey(h);
                });
        return resultCodeEntityList;
    }

    @Override
    public ResultCodeEntity update(ResultCodeVO resultCodeVO) {
        ResultCodeEntity resultCodeEntity = resultCodeMapper.selectByPrimaryKey(resultCodeVO.getId());
        if (resultCodeEntity == null) {
            throw new BusinessException(INVALID_PARAMS.setMessage("header不存在"));
        }
        resultCodeEntity.setCode(resultCodeVO.getCode());
        resultCodeEntity.setDescription(resultCodeVO.getDescription());
        resultCodeMapper.updateByPrimaryKey(resultCodeEntity);
        return resultCodeEntity;
    }

    @Override
    public int delete(List<Long> ids) {
        return resultCodeMapper.deleteByIds(listToString(ids));
    }

    private List<ResultCodeEntity> getByIds(List<Long> ids) {
        return resultCodeMapper.selectByIds(listToString(ids));
    }

    private int deleteByApiId(Long apiId) {
        Example example = new Example(ResultCodeEntity.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("apiId", apiId);
        return resultCodeMapper.deleteByExample(example);
    }
}
