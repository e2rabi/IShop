package com.errabi.common.annotation;

import com.errabi.common.config.OpenApiConfig;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import(OpenApiConfig.class)
public @interface EnableOpenApi {
}
