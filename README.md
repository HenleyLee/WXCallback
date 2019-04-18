# WXCallback —— WXCallbackActivity 自动生成并注册到 AndroidManifest.xml 文件中

## Usages ##
### 1. 配置 build.gradle ###
在位于项目的根目录 build.gradle 文件中添加 Gradle 插件的依赖，如下：
```gradle
buildscript {
    repositories {
        google()
        jcenter()
    }
    dependencies {
        classpath 'com.henley.wxcallback:wxcallback-build:3.3.2'
    }
}
```

并在当前项目需要生成对应 Activity 的 Module 的 build.gradle 文件中添加注解和注解解析器的依赖，如下：
```gradle
dependencies {
    implementation 'com.henley.wxcallback:wxcallback-annotation:1.0.0'
    annotationProcessor 'com.henley.wxcallback:wxcallback-compiler:1.0.0'
}
```

### 2. 配置插件 ###
在当前项目需要生成微信回调 Activity 的 Module 的 build.gradle 文件中应用并配置插件，如下：
```gradle
apply plugin: 'com.henley.wxcallback'

wxCallback {
    // 是否启用微信回调插件
    wxCallbackEnable = true
    // 是否启用将WXEntryActivity添加到AndroidManifest.xml文件中
    wxEntryEnable = true
    // 是否启用将WXPayEntryActivity添加到AndroidManifest.xml文件中
    wxPayEntryEnable = true
    // 忽略添加到AndroidManifest.xml文件中的包名白名单
    wxCallbackWhiteList = [
    ]
}
```

### 3. 添加注解 ###
在需要生成微信回调 Activity 的 Activity 基类上添加注解，如下：
```java
@WXCallback(packageName = BuildConfig.APPLICATION_ID, wxEntry = true, wxPayEntry = true)
public class WXCallbackActivity extends AppCompatActivity {

}
```

