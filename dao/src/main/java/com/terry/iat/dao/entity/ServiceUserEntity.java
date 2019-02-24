package com.terry.iat.dao.entity;

import com.terry.iat.dao.base.BaseEntity;
import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;


/**
 * @Description TODO
 * @author terry
 * @Date 2019/2/23 10:54
 * @Version 1.0 
 **/
@Data
@Table(name="service_user")
public class ServiceUserEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "service_id")
    private Long serviceId;
    @Column(name = "user_id")
    private Long userId;
    @Transient
    private UserEntity detail;
}
