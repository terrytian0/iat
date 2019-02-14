package com.terry.iat.service.impl;

import com.terry.iat.service.common.base.BaseServiceImpl;
import com.terry.iat.dao.entity.ApiEntity;
import com.terry.iat.dao.entity.KeywordApiEntity;
import com.terry.iat.dao.mapper.KeywordApiMapper;
import com.terry.iat.service.ApiService;
import com.terry.iat.service.KeywordApiService;
import com.terry.iat.service.common.enums.Index;
import com.terry.iat.service.vo.AddApiVO;
import com.terry.iat.service.vo.KeywordIndexVO;
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
public class KeywordApiServiceImpl extends BaseServiceImpl implements KeywordApiService {

    @Autowired
    private KeywordApiMapper keywordApiMapper;

    @Autowired
    private ApiService apiService;

    @Override
    public synchronized List<KeywordApiEntity> create(AddApiVO addApiVO) {
        List<KeywordApiEntity> keywordApiEntityList = new ArrayList<>();
        Integer maxIdx = keywordApiMapper.getMaxIdx(addApiVO.getKeywordId());
        if (maxIdx == null) {
            maxIdx = 0;
        }
        for (Long apiId : addApiVO.getIds()) {
            maxIdx = maxIdx + 1;
            KeywordApiEntity keywordApiEntity = new KeywordApiEntity();
            keywordApiEntity.setKeywordId(addApiVO.getKeywordId());
            keywordApiEntity.setApiId(apiId);
            keywordApiEntity.setIdx(maxIdx);
            keywordApiEntityList.add(keywordApiEntity);
        }
        keywordApiMapper.insertList(keywordApiEntityList);
        return keywordApiEntityList;
    }


    @Override
    public int delete(List<Long> ids) {
        List<KeywordApiEntity> keywordApiEntityList = keywordApiMapper.selectByIds(listToString(ids));
        for (KeywordApiEntity keywordApiEntity : keywordApiEntityList) {
            keywordApiMapper.updateLast(keywordApiEntity.getKeywordId(),keywordApiEntity.getIdx());
        }
        return keywordApiMapper.deleteByIds(listToString(ids));
    }

    @Override
    public int delete(Long keywordId) {
        Example example = new Example(KeywordApiEntity.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("keywordId",keywordId);
        return keywordApiMapper.deleteByExample(example);
    }

    @Override
    public List<KeywordApiEntity> getByKeywordId(Long keywordsId) {
        Example example = new Example(KeywordApiEntity.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("keywordId", keywordsId);
        example.orderBy("idx").asc();
        List<KeywordApiEntity> keywordApiEntityList = keywordApiMapper.selectByExample(example);
        List<Long> ids = new ArrayList<>();
        if (!keywordApiEntityList.isEmpty()) {
            keywordApiEntityList.forEach(t -> ids.add(t.getApiId()));
        } else {
            return Collections.emptyList();
        }
        List<ApiEntity> apis = apiService.getByIds(ids);
        Map<Long, ApiEntity> apiEntityMap = new HashMap<>();
        apis.forEach(a -> {
            apiEntityMap.put(a.getId(), a);
        });
        keywordApiEntityList.forEach(k -> {
            k.setDetail(apiEntityMap.get(k.getApiId()));
        });
        return keywordApiEntityList;
    }

    @Override
    public void updateIdx(KeywordIndexVO keywordIndexVO) {
        List<KeywordApiEntity> keywordApiEntityList = getByKeywordId(keywordIndexVO.getKeywordId());
        KeywordApiEntity keywordApiEntity = keywordApiMapper.selectByPrimaryKey(keywordIndexVO.getKeywordApiId());
        if(keywordIndexVO.getIndex()== Index.UP){
            keywordApiUp(keywordApiEntityList,keywordApiEntity);
        }else if(keywordIndexVO.getIndex()== Index.DOWN){
            keywordApiDown(keywordApiEntityList,keywordApiEntity);
        }else if(keywordIndexVO.getIndex()== Index.FIRST){
            keywordApiFirst(keywordApiEntity);
        }else if(keywordIndexVO.getIndex()== Index.LAST){
            keywordApiLast(keywordApiEntityList.size(),keywordApiEntity);
        }
    }

    private void keywordApiLast(Integer count, KeywordApiEntity keywordApiEntity) {
        if(keywordApiEntity.getIdx()==count){
            return;
        }
        keywordApiMapper.updateLast(keywordApiEntity.getKeywordId(),keywordApiEntity.getIdx());
        keywordApiEntity.setIdx(count);
        keywordApiMapper.updateByPrimaryKey(keywordApiEntity);
    }

    private void keywordApiFirst(KeywordApiEntity keywordApiEntity) {
        if(keywordApiEntity.getIdx()==1){
            return;
        }
        keywordApiMapper.updateFrist(keywordApiEntity.getKeywordId(),keywordApiEntity.getIdx());
        keywordApiEntity.setIdx(1);
        keywordApiMapper.updateByPrimaryKey(keywordApiEntity);
    }

    private void keywordApiDown(List<KeywordApiEntity> keywordApiEntityList, KeywordApiEntity keywordApiEntity) {
        if(keywordApiEntity.getIdx()==keywordApiEntityList.size()){
            return;
        }
        int idx = keywordApiEntity.getIdx()+1;
        keywordApiEntity.setIdx(idx);
        keywordApiMapper.updateByPrimaryKey(keywordApiEntity);
        for(KeywordApiEntity ka:keywordApiEntityList){
            if(ka.getIdx()==idx){
                ka.setIdx(idx-1);
                keywordApiMapper.updateByPrimaryKey(ka);
                break;
            }
        }
    }

    private void keywordApiUp(List<KeywordApiEntity> keywordApiEntityList, KeywordApiEntity keywordApiEntity) {
        if(keywordApiEntity.getIdx()==1){
            return;
        }
        int idx = keywordApiEntity.getIdx()-1;
        keywordApiEntity.setIdx(idx);
        keywordApiMapper.updateByPrimaryKey(keywordApiEntity);
        for(KeywordApiEntity ka:keywordApiEntityList){
            if(ka.getIdx()==idx){
                ka.setIdx(idx+1);
                keywordApiMapper.updateByPrimaryKey(ka);
                break;
            }
        }
    }


}
