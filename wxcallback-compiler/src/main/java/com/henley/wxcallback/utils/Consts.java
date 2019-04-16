package com.henley.wxcallback.utils;

/**
 * Some consts used in processors
 *
 * @author liyunlong
 * @date 2019/4/3 17:10
 */
public class Consts {

    static final String PROJECT = "WXCallback";
    static final String PREFIX_OF_LOGGER = PROJECT + "::Compiler >>> ";

    public static final String WARNING_TIPS = "DO NOT EDIT THIS FILE!!! IT WAS GENERATED BY WXCallback.\n";

    public static final String INDENT = "    ";
    public static final String WXAPI_PACKAGE = ".wxapi";
    public static final String WXENTRY_NAME = "WXEntryActivity";
    public static final String WXPAYENTRY_NAME = "WXPayEntryActivity";

    public static final String METHOD_NAME = "onCreate";
    public static final String BUNDLE_PARAMETER_TYPE = "android.os.Bundle";
    public static final String BUNDLE_PARAMETER_NAME = "savedInstanceState";
    public static final String STATEMENT_ONCREATE_SUPER = "super.onCreate(savedInstanceState)";
}