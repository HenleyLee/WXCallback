package com.henley.wxcallback.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Indicates that an activity declaration is intended to automatically generate {@code WXEntryActivity} and {@code WXPayEntryActivity}.
 *
 * @author liyunlong
 * @date 2019/4/3 16:48
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.CLASS)
public @interface WXCallback {

    /**
     * Specifies the package name of the generated class.Usually the {@code applicationId} of the application.
     */
    String packageName();

    /**
     * Whether to automatically generate {@code WXEntryActivity}.The default is {@code false}.
     */
    boolean wxEntry() default false;

    /**
     * Whether to automatically generate {@code WXPayEntryActivity}.The default is {@code false}.
     */
    boolean wxPayEntry() default false;

}
