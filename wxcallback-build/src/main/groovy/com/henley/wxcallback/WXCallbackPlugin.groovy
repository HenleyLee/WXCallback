package com.henley.wxcallback

import com.android.build.gradle.AppExtension
import com.android.build.gradle.api.BaseVariant
import com.android.build.gradle.api.BaseVariantOutput
import com.henley.wxcallback.extension.WXCallbackExtension
import com.henley.wxcallback.task.WXCallbackManifestTask
import com.henley.wxcallback.utils.Consts
import com.henley.wxcallback.utils.Logger
import org.gradle.api.GradleException
import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * Registers the plugin's tasks and add wxcallback activity to AndroidManifest.xml.
 *
 * @author liyunlong
 * @date 2019/4/3 18:52
 */
class WXCallbackPlugin implements Plugin<Project> {

    public static final String PLUGIN_EXTENSION_NAME = "wxCallback"
    public static final String WXCALLBACK_INTERMEDIATES = "build/intermediates/wxcallback_manifests/"

    @Override
    void apply(Project project) {
        if (!project.plugins.hasPlugin('com.android.application')) {
            throw new GradleException(Consts.PREFIX_OF_LOGGER + "'com.android.application' plugin required！")
        }

        project.extensions.create(PLUGIN_EXTENSION_NAME, WXCallbackExtension)

        AppExtension android = project.extensions.android

        project.afterEvaluate {
            WXCallbackExtension configuration = project.wxCallback
            if (configuration == null) {
                Logger.error("The wxCallback configuration is null.")
                return
            }
            if (!configuration.wxCallbackEnable) {
                Logger.error("The wxCallback plugin is disabled.")
                return
            }

            android.applicationVariants.all { BaseVariant variant ->

                BaseVariantOutput variantOutput = variant.outputs.first()
                String variantName = variant.name.capitalize()

                WXCallbackManifestTask manifestTask = project.tasks.create("wxcallbackProcess${variantName}Manifest", WXCallbackManifestTask)
                if (variantOutput.processManifestProvider.get().properties['manifestOutputFile'] != null) {
                    manifestTask.manifestPath = variantOutput.processManifestProvider.get().manifestOutputFile
                } else if (variantOutput.processResourcesProvider.get().properties['manifestFile'] != null) {
                    manifestTask.manifestPath = variantOutput.processResourcesProvider.get().manifestFile
                }
                manifestTask.applicationId = variant.applicationId
                manifestTask.variantDirName = variant.dirName
                manifestTask.mustRunAfter variantOutput.processManifestProvider.get()
                variantOutput.processResourcesProvider.get().dependsOn manifestTask

            }

        }
    }

}
