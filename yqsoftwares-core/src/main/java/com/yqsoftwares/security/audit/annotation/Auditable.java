package com.yqsoftwares.security.audit.annotation;

import java.lang.annotation.*;

/**
 * Created by Administrator on 2015-12-14.
 */
@Target({ElementType.METHOD})
@Retention(value = RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface Auditable {
    int code();
}
