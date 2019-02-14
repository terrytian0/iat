package com.terry.iat.dao.entity;

import com.terry.iat.dao.base.BaseEntity;
import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;


/**
 * @author terry
 */
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
