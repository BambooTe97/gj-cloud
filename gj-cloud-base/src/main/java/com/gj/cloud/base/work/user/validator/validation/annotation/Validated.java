package com.gj.cloud.base.work.user.validator.validation.annotation;

import com.gj.cloud.base.work.user.validator.validation.Validator;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(RUNTIME)
@Target(METHOD)
public @interface Validated {
    Class<? extends Validator> value();
}
