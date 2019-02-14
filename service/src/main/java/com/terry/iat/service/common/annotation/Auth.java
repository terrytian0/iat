/**
 *
 */
package com.terry.iat.service.common.annotation;

import com.terry.iat.service.common.enums.Role;

import java.lang.annotation.*;


@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Auth {
    Role role() default Role.NORMAL;
}
