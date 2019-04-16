package com.henley.wxcallback.extension;

/**
 * The configuration properties.
 *
 * @author liyunlong
 * @date 2019/4/16 11:45
 */
public class WXCallbackExtension {

    /**
     * Whether the wxcallback plugin is enabled
     */
    boolean wxCallbackEnable
    /**
     * Whether to automatically add {@code WXEntryActivity} to {@code AndroidManifest.xml}.The default is {@code false}.
     */
    boolean wxEntryEnable
    /**
     * Whether to automatically add {@code WXPayEntryActivity} to {@code AndroidManifest.xml}.The default is {@code false}.
     */
    boolean wxPayEntryEnable
    /**
     * Ignore white lists that add to {@code AndroidManifest.xml}.
     */
    Iterable<String> wxCallbackWhiteList


    @Override
    public String toString() {
        return "WXCallbackExtension{" +
                "wxCallbackEnable=" + wxCallbackEnable +
                ", wxEntryEnable=" + wxEntryEnable +
                ", wxPayEntryEnable=" + wxPayEntryEnable +
                ", wxCallbackWhiteList=" + wxCallbackWhiteList +
                '}'
    }

}
