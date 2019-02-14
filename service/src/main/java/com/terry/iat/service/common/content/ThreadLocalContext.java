package com.terry.iat.service.common.content;

import com.terry.iat.dao.entity.UserEntity;
import lombok.Data;

/**
 * 本地线程上下文
 * @author houyin.tian
 */
@Data
public class ThreadLocalContext {
        private UserEntity userEntity;
}
