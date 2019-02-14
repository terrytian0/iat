package com.terry.iat.dao.entity;

import com.terry.iat.dao.base.BaseEntity;
import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;
import java.util.Set;


/**
 * @author terry
 */
@Data
@Table(name="user")
public class UserEntity extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "phone")
    private String phone;
    @Column(name = "password")
    private String password;
    @Column(name="role")
    private String role;
    @Column(name="deleted")
    private Integer deleted = 0;
    @Column(name = "create_user")
    private String createUser;
    @Column(name = "create_time")
    private Timestamp createTime;
    @Column(name = "update_user")
    private String updateUser;
    @Column(name = "update_time")
    private Timestamp updateTime;

    @Transient
    private String token;

    @Transient
    private Long lastOperateTime;
}
