package com.henley.wxcallback.task

import com.android.SdkConstants
import com.henley.wxcallback.WXCallbackPlugin
import com.henley.wxcallback.utils.Consts
import com.henley.wxcallback.utils.FileOperation
import com.henley.wxcallback.utils.Logger
import com.henley.wxcallback.utils.StreamUtil
import groovy.xml.Namespace
import org.apache.commons.io.Charsets
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

/**
 * Add wxcallback activity to {@code AndroidManifest.xml}
 *
 * @author liyunlong
 * @date 2019/4/3 18:57
 */
class WXCallbackManifestTask extends DefaultTask {

    private static final String ANDROID_MANIFEST_XML = SdkConstants.ANDROID_MANIFEST_XML

    def configuration
    String manifestPath
    String applicationId
    String variantDirName

    WXCallbackManifestTask() {
        group = 'wxcallback'
        configuration = project.wxCallback
    }

    @TaskAction
    def updateManifest() {
        writeManifestMeta(manifestPath)
        File manifestFile = new File(manifestPath)
        File manifestXml = new File(WXCallbackPlugin.WXCALLBACK_INTERMEDIATES + variantDirName, ANDROID_MANIFEST_XML)
        if (configuration.wxCallbackEnable && manifestFile.exists()) {
            FileOperation.copyFileUsingStream(manifestFile, project.file(manifestXml))
            Logger.info("wxcallback gen ${ANDROID_MANIFEST_XML} in ${project.file(manifestXml)}!")
        }
    }

    void writeManifestMeta(String path) {
        def ns = new Namespace(Consts.NAME_SPACE_URI, Consts.NAME_SPACE_PREFIX)
        def isr = null
        def pw = null
        try {
            isr = new InputStreamReader(new FileInputStream(path), Charsets.UTF_8)
            Node xml = new XmlParser().parse(isr)
            Node application = xml.application[0]
            if (application) {
                String wxEntryName = applicationId + Consts.WXAPI_PACKAGE + "." + Consts.WXENTRY_NAME
                String wxPayEntryName = applicationId + Consts.WXAPI_PACKAGE + "." + Consts.WXPAYENTRY_NAME

                // Remove any old wxcallback activity nodes
                application[Consts.ACTIVITY_NODE].findAll {
                    def name = it.attributes()[ns.name]
                    name == wxEntryName || name == wxPayEntryName
                }.each {
                    if (it.parent().remove(it)) {
                        Logger.info("wxcallback remove activity ${it.attributes()[ns.name]} from ${ANDROID_MANIFEST_XML}")
                    }
                }

                // Add the new wxcallback activity nodes
                if (!configuration.wxEntryEnable && !configuration.wxPayEntryEnable) {
                    Logger.error("neither wxEntryEnable nor wxPayEntryEnable is enabled!")
                } else {
                    Iterable<String> wxCallbackWhiteList = configuration.wxCallbackWhiteList
                    if (wxCallbackWhiteList != null && wxCallbackWhiteList.contains(applicationId)) {
                        Logger.error("skip add to AndroidManifest.xml！${applicationId} is the applicationId that on the wxCallbackWhiteList.")
                    } else {
                        // Add the new wxcallback activity nodes
                        if (configuration.wxEntryEnable) {
                            addActivity(application, ns, wxEntryName)
                        } else {
                            Logger.error("wxEntryEnable is not enabled!skip add ${wxEntryName} to AndroidManifest.xml！")
                        }
                        if (configuration.wxPayEntryEnable) {
                            addActivity(application, ns, wxPayEntryName)
                        } else {
                            Logger.error("wxPayEntryEnable is not enabled!skip add ${wxPayEntryName} to AndroidManifest.xml！")
                        }
                    }
                }

                // Write the manifest file
                pw = new PrintWriter(path, Charsets.UTF_8.name())
                def printer = new XmlNodePrinter(pw)
                printer.preserveWhitespace = true
                printer.print(xml)
            }
        } finally {
            StreamUtil.closeQuietly(pw, isr)
        }
    }

    private static void addActivity(Node application, Namespace ns, String className) {
        application.appendNode(Consts.ACTIVITY_NODE, [
                (ns.name)         : className,
                (ns.configChanges): Consts.CONFIG_CHANGES,
                (ns.exported)     : Consts.EXPORTED,
                (ns.theme)        : Consts.THEME
        ])
        Logger.info("wxcallback add activity ${className} to ${ANDROID_MANIFEST_XML}")
    }

}
