package com.terry.iat.dao.entity;

import com.terry.iat.dao.base.BaseEntity;
import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;


/**
 * @Description TODO
 * @author terry
 * @Date 2019/2/23 20:59
 * @Param 
 * @return 
 **/
@Data
@Table(name="client")
public class ClientEntity extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    @Column(name = "client")
    private String client;
    @Column(name = "`key`")
    private String key;
    @Column(name = "`status`")
    private String status;
    @Column(name = "registration_time")
    private Timestamp registrationTime;
    @Column(name = "last_time")
    private Timestamp lastTime;

}
