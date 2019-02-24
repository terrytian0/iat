package com.terry.iat.dao.entity;

import com.terry.iat.dao.base.BaseEntity;
import lombok.Data;

import javax.persistence.*;


/**
 * @Description TODO
 * @author terry
 * @Date 2019/2/23 10:54
 * @Version 1.0 
 **/
@Data
@Table(name="env")
public class EnvEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "service_id")
    private Long serviceId;
    @Column(name = "env")
    private String env;
    @Column(name = "host")
    private String host;
    @Column(name = "port")
    private Integer port;
}
