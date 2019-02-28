package com.terry.iat.service.vo;

import com.terry.iat.service.common.enums.Role;
import com.terry.iat.service.common.base.BaseVO;
import lombok.Data;

import javax.validation.constraints.*;

@Data
public class UserVO extends BaseVO {
    public Long id;

    @Email
    @Size(min = 5,max = 64)
    private String name;
    @Pattern(regexp = "^(13[0-9]|14[579]|15[0-3,5-9]|16[6]|17[0135678]|18[0-9]|19[89])\\d{8}$")
    private String phone;
    @Size(min = 6,max = 20)
    private String password;

    private Role role;
}
