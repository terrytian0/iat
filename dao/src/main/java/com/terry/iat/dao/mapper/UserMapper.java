package com.terry.iat.dao.mapper;


import com.terry.iat.dao.common.DataSource;
import com.terry.iat.dao.base.BaseMapper;
import com.terry.iat.dao.entity.UserEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
@DataSource(value="iat")
public interface UserMapper extends BaseMapper<UserEntity> {
    @Select("SELECT * FROM user WHERE name=#{name}")
    UserEntity getByName(String name);
    @Select("SELECT * FROM user WHERE name=#{name} AND password=#{password}")
    UserEntity getByNameAndPassword(@Param("name")String name,@Param("password") String password);
}
