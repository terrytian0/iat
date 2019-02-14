package com.terry.iat.service.impl;

import com.terry.iat.service.common.base.BaseServiceImpl;
import com.terry.iat.service.common.bean.ResultCode;
import com.terry.iat.service.common.exception.BusinessException;
import com.terry.iat.dao.entity.HeaderEntity;
import com.terry.iat.dao.mapper.HeaderMapper;
import com.terry.iat.service.HeaderService;
import com.terry.iat.service.vo.HeaderVO;
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
public class HeaderServiceImpl extends BaseServiceImpl implements HeaderService {

    @Autowired
    private HeaderMapper headerMapper;

    @Override
    public List<HeaderEntity> getByApiId(Long apiId) {
        Example example = new Example(HeaderEntity.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("apiId", apiId);
        List<HeaderEntity> headerEntityList = headerMapper.selectByExample(example);
        if (headerEntityList == null) {
            return Collections.EMPTY_LIST;
        }
        return headerEntityList;
    }

    @Override
    public List<HeaderEntity> create(List<HeaderVO> headerVOList) {
        List<HeaderEntity> headerEntityList = new ArrayList<>();
        if (headerVOList == null || headerVOList.isEmpty()) {
            return headerEntityList;
        }
        headerVOList.forEach(
                hv -> {
                    HeaderEntity headerEntity = new HeaderEntity();
                    headerEntity.setApiId(hv.getApiId());
                    headerEntity.setName(hv.getName());
                    headerEntity.setDefaultValue(hv.getDefaultValue());
                    headerEntity.setType(hv.getType());
                    headerEntityList.add(headerEntity);
                });
        headerMapper.insertList(headerEntityList);
        return headerEntityList;
    }

    @Override
    public int push(Long apiId, List<HeaderVO> headerVOList) {
        if (headerVOList == null || headerVOList.isEmpty()) {
            return 0;
        }
        List<HeaderEntity> headerEntityList = getByApiId(apiId);
        Map<String, HeaderEntity> headerEntityMap = new HashMap<>();
        for (HeaderEntity headerEntity : headerEntityList) {
            headerEntityMap.put(headerEntity.getName(), headerEntity);
        }
        List<HeaderVO> insertHeaderEntity = new ArrayList<>();
        List<HeaderVO> updateHeaderEntity = new ArrayList<>();
        headerVOList.forEach(
                hv -> {
                    HeaderEntity he = headerEntityMap.get(hv.getName());
                    if (he == null) {
                        insertHeaderEntity.add(hv);
                    } else if (!he.getType().equals(hv.getType())) {
                        hv.setDefaultValue(he.getDefaultValue());
                        updateHeaderEntity.add(hv);
                        headerEntityMap.remove(hv.getName());
                    } else {
                        headerEntityMap.remove(hv.getName());
                    }
                });

        if (!insertHeaderEntity.isEmpty()) {
            create(insertHeaderEntity);
        }
        if (!updateHeaderEntity.isEmpty()) {
            update(updateHeaderEntity);
        }
        return insertHeaderEntity.size() + updateHeaderEntity.size();
    }

    @Override
    public List<HeaderEntity> update(List<HeaderVO> headerVOList) {
        List<Long> ids = new ArrayList<>();
        Map<Long, HeaderVO> headerVOMap = new HashMap<>();
        headerVOList.forEach(
                h -> {
                    ids.add(h.getId());
                    headerVOMap.put(h.getId(), h);
                });
        List<HeaderEntity> headerEntityList = getByIds(ids);
        if (headerVOList.size() != headerEntityList.size()) {
            throw new BusinessException(ResultCode.INVALID_PARAMS.setMessage("部分Header不存在"));
        }
        headerEntityList.forEach(
                h -> {
                    HeaderVO headerVO = headerVOMap.get(h.getId());
                    h.setDefaultValue(headerVO.getDefaultValue());
                    h.setName(headerVO.getName());
                    h.setType(headerVO.getType());
                    headerMapper.updateByPrimaryKey(h);
                });
        return headerEntityList;
    }

    @Override
    public HeaderEntity update(HeaderVO headerVO) {
        HeaderEntity headerEntity = headerMapper.selectByPrimaryKey(headerVO.getId());
        if (headerEntity == null) {
            List<HeaderEntity> headerEntityList = create(Arrays.asList(headerVO));
            if(!headerEntityList.isEmpty()){
                return headerEntityList.get(0);
            }else{
                throw new BusinessException(ResultCode.INVALID_PARAMS.setMessage("修改Header失败！"));
            }
        }
        headerEntity.setType(headerVO.getType());
        headerEntity.setDefaultValue(headerVO.getDefaultValue());
        headerEntity.setName(headerVO.getName());
        headerMapper.updateByPrimaryKey(headerEntity);
        return headerEntity;
    }

    @Override
    public int delete(List<Long> ids) {
        if (!ids.isEmpty()) {
            return headerMapper.deleteByIds(listToString(ids));
        }
        return 0;
    }

    private List<HeaderEntity> getByIds(List<Long> ids) {
        if(ids.isEmpty()){
            return Collections.EMPTY_LIST;
        }
        return headerMapper.selectByIds(listToString(ids));
    }

    private int deleteByApiId(Long apiId) {
        Example example = new Example(HeaderEntity.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("apiId", apiId);
        return headerMapper.deleteByExample(example);
    }
}
