package com.henley.wxcallback;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import com.henley.wxcallback.annotation.WXCallback;
import com.henley.wxcallback.utils.Consts;
import com.henley.wxcallback.utils.Logger;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.annotation.Nonnull;
import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;

@AutoService(Processor.class)
public class WXCallbackProcessor extends AbstractProcessor {

    private Filer mFiler;
    private Logger logger;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        mFiler = processingEnv.getFiler();
        logger = new Logger(processingEnv.getMessager());

        logger.info("WXCallbackProcessor init.");

    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        Set<? extends Element> elements = roundEnvironment.getElementsAnnotatedWith(WXCallback.class);
        if (elements != null && !elements.isEmpty()) {
            try {
                parseAnnoations(elements);
            } catch (IOException e) {
                logger.error(e);
            }
            return true;
        }
        return false;
    }

    private void parseAnnoations(@Nonnull Set<? extends Element> set) throws IOException {
        logger.info("Found WXCallback, start...");
        for (Element element : set) {
            if (element == null) {
                continue;
            }
            WXCallback annotation = element.getAnnotation(WXCallback.class);
            if (annotation == null) {
                continue;
            }
            if (annotation.wxEntry()) {
                generateFile(element, annotation.packageName(), Consts.WXENTRY_NAME);
            }
            if (annotation.wxPayEntry()) {
                generateFile(element, annotation.packageName(), Consts.WXPAYENTRY_NAME);
            }
        }
    }

    private void generateFile(@Nonnull Element element, @Nonnull String packageName, @Nonnull String className) throws IOException {
        MethodSpec methodSpec = MethodSpec.methodBuilder(Consts.METHOD_NAME)
                .addModifiers(Modifier.PROTECTED)
                .addAnnotation(Override.class)
                .returns(void.class)
                .addParameter(ClassName.bestGuess(Consts.BUNDLE_PARAMETER_TYPE), Consts.BUNDLE_PARAMETER_NAME)
                .addStatement(Consts.STATEMENT_ONCREATE_SUPER)
                .build();
        TypeSpec typeSpec = TypeSpec.classBuilder(className)
                .addJavadoc(Consts.WARNING_TIPS)
                .superclass(TypeName.get(element.asType()))
                .addModifiers(Modifier.PUBLIC)
                .addMethod(methodSpec)
                .build();
        JavaFile.builder(packageName + Consts.WXAPI_PACKAGE, typeSpec)
                .indent(Consts.INDENT)
                .build()
                .writeTo(mFiler);
        logger.info("Generated WXCallback, name is " + packageName + "." + className);
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> supportAnnotations = new LinkedHashSet<>();
        supportAnnotations.add(WXCallback.class.getCanonicalName());
        return supportAnnotations;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

}
