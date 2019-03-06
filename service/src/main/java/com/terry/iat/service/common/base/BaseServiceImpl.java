package com.terry.iat.service.common.base;

import com.terry.iat.service.common.bean.ResultCode;
import com.terry.iat.service.common.exception.BusinessException;
import com.terry.iat.dao.entity.UserEntity;
import com.terry.iat.service.common.content.WebContext;
import com.terry.iat.service.common.utils.DateUtils;
import com.terry.iat.service.vo.ParameterVO;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.xml.crypto.Data;
import java.lang.reflect.InvocationTargetException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.*;

/**
 * @author terry
 */
@Service
public abstract class BaseServiceImpl {

    protected UserEntity getCurrentUser() {
        UserEntity userEntity =  WebContext.getCurrentUser();
        if(userEntity==null){
            userEntity = new UserEntity();
            userEntity.setName("admin");
        }
        return userEntity;
    }

    protected Timestamp getTimestamp() {
        return new Timestamp(System.currentTimeMillis());
    }

    protected <B> Example beanToEqualExample(B b, Class e) {
        return beanToExample(b, e, BeanToExampleType.EQUAL);
    }

    protected <B> Example beanToLikeExample(B b, Class e) {
        return beanToExample(b, e, BeanToExampleType.LIKE);
    }

    private <B> Example beanToExample(B b, Class e, BeanToExampleType type) {
        Map<String, String> params = null;
        try {
            params = BeanUtils.describe(b);
        } catch (IllegalAccessException e1) {
            e1.printStackTrace();
            throw new BusinessException(ResultCode.SYSTEM_EXCEPTION);
        } catch (InvocationTargetException e1) {
            e1.printStackTrace();
            throw new BusinessException(ResultCode.SYSTEM_EXCEPTION);
        } catch (NoSuchMethodException e1) {
            e1.printStackTrace();
            throw new BusinessException(ResultCode.SYSTEM_EXCEPTION);
        }
        Example example = new Example(e, false, false);
        Example.Criteria criteria = example.createCriteria();
        params.forEach((k, v) -> {
            if (!k.equals("class") && v != null) {
                if (type == BeanToExampleType.EQUAL) {
                    criteria.andEqualTo(k, v);
                } else if (type == BeanToExampleType.LIKE) {
                    criteria.andLike(k, "%"+v+"%");
                }
            }
        });
        return example;
    }

    protected  String listToString(List<Long> ids) {
        if(ids==null||ids.isEmpty()){
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder();
        ids.forEach(t -> {
            stringBuilder.append(t);
            stringBuilder.append(",");
        });
        stringBuilder.replace(stringBuilder.length()-1,stringBuilder.length(),"");
        return stringBuilder.toString();
    }


    enum BeanToExampleType {
        LIKE, EQUAL;
    }

    protected Map<String, String> convertParameters(List<ParameterVO> parameters) {
        Map<String, String> params = new HashMap<>();
        if (parameters != null) {
            parameters.forEach(parameterVO -> {
                params.put(parameterVO.getName(), parameterVO.getValue());
            });
        }
        return params;
    }


    protected  String join(Collection var0, String var1) {
        StringBuffer var2 = new StringBuffer();
        int i = 0;
        for (Object o : var0) {
            var2.append(String.valueOf(o));
            if (i != 0) {
                var2.append(var1);
            }
            i++;
        }
        return var2.toString();
    }


    public static void main(String[] args) throws ParseException {
        System.out.println(DateUtils.getFirstDayOfWeek(new Date()));
    }
}
