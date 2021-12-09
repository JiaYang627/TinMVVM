# TinMVVM
## AndroidStudio 4.X+ 编写自定义模板
> 写在前面 此文仅为记录AndroidStudio fox 编写自定义模板，网上关于此类文章少之又少，踩了不少坑，遂写文记录希望能帮到有需要的人。

#### 原创文章 禁止未授权转载

* AndroidStudio 自定义模板主要区分在AS 4.0版本
  * AndroidStudio 4.0版本之前，编写自定义模板 使用`Freemarket`进行编写，并存放在 AS 的plugin - template 文件夹下即可。可参考 鸿洋 文章 [AndroidStudio自定义模板](https://mp.weixin.qq.com/s/doXNWf_TAB-ZlaHVkrUgzQ) 进行学习。
  * AndroidStudio 4.0版本之后，AS 没有了 plugin 文件夹，但提供了新的方式：使用`Kotlin` 编写 template，以`jar`包形式使用。


* 此文编写模板是依附于 自己项目 [QuickAndroid](https://github.com/JiaYang627/QuickAndroid) 进行编写,开发工具：AndroidStudio Fox 2020.3.1 Patch 3 Build

### 开始
---
#### Use this template

我们需要在官方的template模板上进行编写，官方模板地址为:[intellij-platform-plugin-template](https://github.com/JetBrains/intellij-platform-plugin-template),打开模板仓库后，点击`Use this template` 会提示你 `Create a new repository from intellij-platform-plugin-template` 类似`fork`，创建好仓库后，git clone 到本地，并使用AS 打开

#### 添加依赖

* 添加wizard-template.jar
  * 项目根目录下创建`lib`文件夹
  * 添加AndroidStudio目录下的`wizard-template.jar`,具体路径为:`/Applications/Android Studio.app/Contents/plugins/android/lib/`
  * 打开项目`build.gradle.kts`文件，添加代码如下：
```
dependencies {
    compileOnly(files("lib/wizard-template.jar"))
}
```
* 添加plugins
  * 打开项目`build.gradle.kts`文件，在`plugins`下添加`detekt` 和 `ktlint`插件
  * 添加`detekt`依赖代码如下：

```
    plugins {
    ...
    // detekt linter - read more: https://detekt.github.io/detekt/gradle.html
    id("io.gitlab.arturbosch.detekt") version "1.16.0"
    // ktlint linter - read more: https://github.com/JLLeitschuh/ktlint-gradle
    id("org.jlleitschuh.gradle.ktlint") version "10.0.0"
}


dependencies {
    detektPlugins("io.gitlab.arturbosch.detekt:detekt-formatting:1.18.1")
}
```
* 添加过依赖后`build.gradle.kts` 下`plugins` 和`dependencies`代码：

```
plugins {
    // Java support
    id("java")
    // Kotlin support
    id("org.jetbrains.kotlin.jvm") version "1.6.0"
    // Gradle Changelog Plugin
    id("org.jetbrains.changelog") version "1.3.1"
    // Gradle Qodana Plugin
    id("org.jetbrains.qodana") version "0.1.13"
    // Gradle IntelliJ Plugin
    id("org.jetbrains.intellij") version "1.3.0"
    // detekt linter - read more: https://detekt.github.io/detekt/gradle.html
    id("io.gitlab.arturbosch.detekt") version "1.16.0"
    // ktlint linter - read more: https://github.com/JLLeitschuh/ktlint-gradle
    id("org.jlleitschuh.gradle.ktlint") version "10.0.0"
}


dependencies {
    detektPlugins("io.gitlab.arturbosch.detekt:detekt-formatting:1.18.1")
    compileOnly(files("lib/wizard-template.jar"))
}
```

#### 修改gradle.properties

* 修改`gradle.properties`文件下相关配置 具体意义查看[Gradle properties](https://github.com/JetBrains/intellij-platform-plugin-template#gradle-properties)
  * pluginGroup
  * pluginName
  * pluginVersion : 编译后生成jar版本号就是这里控制的
  * pluginSinceBuild
  * pluginUntilBuild
  * pluginVerifierIdeVersions
  * platformType
  * platformVersion
  * platformPlugins
* 此处需注意的是 `pluginSinceBuild` `pluginUntilBuild` `pluginVerifierIdeVersions`
  * pluginSinceBuild:表示插件适配的最低版本
  * pluginUntilBuild:表示插件适配的最高版本
  * pluginVerifierIdeVersions: 4.X版本AS 使用模板会有此字段，Fox版本没有，文档也没有显示，个人还是添加上了
  * 关于最低 最高版本 就是AS 版本信息中build 信息，本人使用的是AndroidStudio Fox 2020.3.1 Patch 3 Build，build 信息为203.xxxx

![AndroidStudioVersion](http://m.qpic.cn/psc?/V14YlNrL2eQEkW/TmEUgtj9EK6.7V8ajmQrEHaEg.pjcWCXX0nr96pir5CoKyWqO*je8zs0FBWZKkLBFoA8M4zCRITvyWkrylH90Fi0sN*Zd5VFCuhAtJhwsOs!/b&bo=AAUgAwAAAAADFxQ!&rf=viewer_4)

* `gradle.properties`文件内容如下：

```
# IntelliJ Platform Artifacts Repositories
# -> https://plugins.jetbrains.com/docs/intellij/intellij-artifacts.html

pluginGroup = me.jiayang
pluginName = tin-mvvm
# SemVer format -> https://semver.org
pluginVersion = 1.0.72

# See https://plugins.jetbrains.com/docs/intellij/build-number-ranges.html
# for insight into build numbers and IntelliJ Platform versions.
pluginSinceBuild = 201
pluginUntilBuild = 213.*
pluginVerifierIdeVersions = 2020.2.4, 2020.3.4, 2021.1
# IntelliJ Platform Properties -> https://github.com/JetBrains/gradle-intellij-plugin#intellij-platform-properties
platformType = IC
platformVersion = 2020.2.4

# Plugin Dependencies -> https://plugins.jetbrains.com/docs/intellij/plugin-dependencies.html
# Example: platformPlugins = com.intellij.java, com.jetbrains.php:203.4449.22
platformPlugins = java, com.intellij.java, org.jetbrains.android, android, org.jetbrains.kotlin

# Java language level used to compile sources and to generate the files for - Java 11 is required since 2020.3
javaVersion = 11

# Gradle Releases -> https://github.com/gradle/gradle/releases
gradleVersion = 7.3

# Opt-out flag for bundling Kotlin standard library.
# See https://plugins.jetbrains.com/docs/intellij/kotlin.html#kotlin-standard-library for details.
# suppress inspection "UnusedProperty"
kotlin.stdlib.default.dependency = false

```

#### 修改包名以及创建Template生成类

* 修改项目包名
  * AS 打开项目后，可以看到src - kotlin - 包名 - listeners services ...
  * 修改包名，个人修改结果如下
  * 具体为src - kotlin - 包名 me.jiayang - 两个文件夹，一个存放 项目原本文件 template，一个存放自定义模板代码 tinmvvm

![Project-Package](http://m.qpic.cn/psc?/V14YlNrL2eQEkW/TmEUgtj9EK6.7V8ajmQrEC.gd5GSUb4UHX1jtSigXuJF.G06JTbj4EEds6nMvmbgca8GtR3k*qKGzX.hNq1iE7rwRxNdFKQGYfsRiVZ0d7g!/b&bo=BgPIAwAAAAADF*w!&rf=viewer_4&t=5)

* 修改MyProjectManagerListener


```
internal class MyProjectManagerListener : ProjectManagerListener {

    override fun projectOpened(project: Project) {
        projectInstance = project
        project.getService(MyProjectService::class.java)
    }

    override fun projectClosing(project: Project) {
        projectInstance = null
        super.projectClosing(project)
    }

    companion object {
        var projectInstance: Project? = null
    }
}
```

* 创建Template入口生成类 SamplePluginTemplateProviderImpl
  * tinmvvm文件夹下创建 `SamplePluginTemplateProviderImpl`类
  * `WizardTemplateProvider`的是 lib 下 `wizard-template.jar`的抽象类
  * `mvvmActivityTemplate`为具体生成Template对象

```
class SamplePluginTemplateProviderImpl: WizardTemplateProvider() {
    override fun getTemplates(): List<Template> = listOf(
        mvvmActivityTemplate
    )
}
```
* 修改plugin.xml
  * 创建好`SamplePluginTemplateProviderImpl`类后，打开`resources-META-INF-plugin.xml`文件
  * 修改id
  * 修改name：此处名字是AS plugin安装后显示的名字
  * 修改vendor:此处为AS plugin 安装后显示的作者名字
  * 添加三个 depends
  * 添加extensions 指定`defaultExtensionNs` 为 `com.android.tools.idea.wizard.template`，`wizardTemplateProvider` 为创建的`SamplePluginTemplateProviderImpl`类
  * 此处注意，一定要写全路径，包名一定一定一定要写上,4.X版本编写的时候 网上的文章都没有写，个人不清楚4.X版本是否真的不用写全路径
    

```
<idea-plugin>
    <id>me.jiayang.tinmvvm</id>
    <name>TinMVVM</name>
    <vendor>jiayang</vendor>

    <depends>org.jetbrains.android</depends>
    <depends>org.jetbrains.kotlin</depends>
    <depends>com.intellij.modules.java</depends>
    <depends>com.intellij.modules.platform</depends>

    <extensions defaultExtensionNs="com.intellij">
        <applicationService serviceImplementation="me.jiayang.template.services.MyApplicationService"/>
        <projectService serviceImplementation="me.jiayang.template.services.MyProjectService"/>
    </extensions>

    <applicationListeners>
        <listener class="me.jiayang.template.listeners.MyProjectManagerListener"
                  topic="com.intellij.openapi.project.ProjectManagerListener"/>
    </applicationListeners>
    <extensions defaultExtensionNs="com.android.tools.idea.wizard.template">
        <wizardTemplateProvider implementation="me.jiayang.tinmvvm.SamplePluginTemplateProviderImpl" />
    </extensions>
</idea-plugin>
```




* 创建Teplater具体对象`mvvmActivityTemplate`,本人原本想着写两个Template对象，后将Fragment Activity 都写在了一个下面，别纠结创建的文件夹名字已经文件名为activity
  * tinmvvm 文件夹下创建了`activity`文件夹，并在其下创建名为`mvvmActivityTemplate`的kt文件
  * 此处个人说明一下个人踩的坑以及对字段含义
    * revision ：低版本的时候用于说明版本，此次fox编译的时候 revision会报错，遂注释
    * name : 编译好Jar包使用时，显示的模板名字
    * description : 使用模板时，顶部简介
    * minApi : 低版本4.0以下编写的时候 使用的min_api minBuildApi，用于说明编译版本，此次fox编写时，minBuildApi会报错，遂也改minApi ,MIN_API为lib 下 `wizard-template.jar`中字段
    * category : 定义为other
    * formFactor : 定义为 mobile
    * screens : 低版本编写的时候没有，此处具体本人也不太懂
    * 接下来就是模板上要显示内容了
    * 具体分为 stringParameter booleanParameter enumParameter
    * 说一些和低版本不同的地方：低版本有id type字段，此次 发现没有了id type字段 ,type 个人觉得在你创建的时候已经指定了具体类型的Template
    * name：界面上的类似label的提示语,constraints：填写值的约束,suggest：建议值，比如填写ActivityName的时候，会给出一个布局文件的建议值,default:默认值,help:底部显示的提示语
    * 低版本 help 会在底部显示提示语，高版本会在鼠标放置字段上停滞一段时间后 显示。
    * 编写template 必须给default 值，不然安装jar后会连入口都没有，包括官方的模板都没有！！！这里坑了自己两天，后扒AS启动日志才发现报错
    * widgets:将编写的Template对象放入。
    * recipe :设置具体替换对象。此处在`activity`文件夹下创建`mvvmActivityRecipe`的kt文件，将需要的值通过参数传入
    * `mvvmActivityRecipe`下根据类型写了 创建act viewmodel repository 等，这些看代码即可，具体act viewmodel repository 看代码即可，这里简单说明一下 就是创建方法，然后 返回值为 string内容，内容写基础内容即可，放一个activity的内容吧，具体根据自己项目替换即可
  * 编写template 必须给default 值错误日志图、文件代码内容如下:
    ![AndroidStudioLog](http://m.qpic.cn/psc?/V14YlNrL2eQEkW/TmEUgtj9EK6.7V8ajmQrELZ0nze9UlwU0hlIlNznzA4R3pfthA9lPYlj9jkGtjQk5TXDv7dtLp0mXLwfta177V*CMa8UXzNzzLMTLSvgm1M!/b&bo=agY4BAAAAAADVyI!&rf=viewer_4&t=5)

```

val mvvmActivityTemplate
    get() = template {
//        revision = 1
        name = "Quick Template"
        description = "一键创建 QuickAndroid 单个页面所需要的全部组件"
        minApi = MIN_API
        minApi = MIN_API
        category = Category.Other
        formFactor = FormFactor.Mobile
        screens = listOf(
            WizardUiContext.ActivityGallery,
            WizardUiContext.MenuEntry,
            WizardUiContext.NewProject,
            WizardUiContext.NewModule
        )


        val mRootPackageName = stringParameter {
            name = "Root Package Name"
            constraints = listOf(Constraint.PACKAGE)
            default = "com.jiayang.quickandroid"
            visible = { !isNewModule }
            help = "此 PluginTemplate 是针对 `QuickAndroid` 项目编写,默认包名为项目的包名,可根据自己需要填写"
        }

        val mPageName = stringParameter {
            name = "Create Page Name"
            constraints = listOf(Constraint.UNIQUE, Constraint.NONEMPTY)
            default = "Main"
            help = "需要生成页面的名字,不需要再写 名字后缀:如Activity、Fragment,会自动生成,以及对应文件名后缀"
        }

        val mIsFragment = booleanParameter {
            name = "Generate Fragment"
            default = false
            help = "是否需要创建 Fragment ? 不勾选则不生成"
        }

        val mIsLazyFragment = booleanParameter {
            name = "Fragment is Lazy ？"
            default = false
            visible = { mIsFragment.value}
            help = "是否 使创建的Fragment 为Lazy Fragment"
        }

        val mIsActivity = booleanParameter {
            name = "Generate Activity"
            default = true
            visible = { !mIsFragment.value }
            help = "是否需要创建 Activity ? 不勾选则不生成"
        }


        val mActivityLayoutName = stringParameter {
            name = "Activity Layout Name"
            default = "activity_main"
            constraints = listOf(Constraint.LAYOUT, Constraint.NONEMPTY)
            suggest = { activityToLayout(mPageName.value.toLowerCase()) }
            visible = { mIsActivity.value }
        }

        val mIsGenerateActivityLayout = booleanParameter {
            name = "Generate Activity Layout"
            default = true
            visible = { mIsActivity.value }
            help = "默认勾选,如果使用已存在布局 则无需勾选,若不勾选,创建后记得修改Act或 Fragment 绑定的视图文件！！！"
        }

        val mActivityPackageName = stringParameter {
            name = "Activity Package Name"
            constraints = listOf(Constraint.NONEMPTY)
            default = "ui"
            visible = { mIsActivity.value }
            help = "Activity 将被输出到此包下,请认真核实此包名是否是你需要输出的目标包名 (基于 Root Package Name )"
        }

        val mFragmentLayoutName = stringParameter {
            name = "Fragment Layout Name"
            default = "fragment_main"
            constraints = listOf(Constraint.LAYOUT, Constraint.NONEMPTY)
            suggest = { fragmentToLayout(mPageName.value.toLowerCase()) }
            visible = { mIsFragment.value }
        }
        val mIsGenerateFragmentLayout = booleanParameter {
            name = "Generate Fragment Layout"
            default = true
            visible = { mIsFragment.value }
            help = "默认勾选,如果使用已存在布局 则无需勾选,若不勾选,创建后记得修改Act或 Fragment 绑定的视图文件！！！"
        }

        val mFragmentPackageName = stringParameter {
            name = "Fragment Package Name"
            constraints = listOf(Constraint.NONEMPTY)
            default = "ui"
            visible = { mIsFragment.value }
            help = "Fragment 将被输出到此包下,请认真核实此包名是否是你需要输出的目标包名 (基于 Root Package Name )"
        }


        widgets(
            PackageNameWidget(mRootPackageName),
            TextFieldWidget(mPageName),
            CheckBoxWidget(mIsFragment),
            CheckBoxWidget(mIsActivity),
            TextFieldWidget(mActivityLayoutName),
            CheckBoxWidget(mIsGenerateActivityLayout),
            TextFieldWidget(mActivityPackageName),
            TextFieldWidget(mFragmentLayoutName),
            CheckBoxWidget(mIsGenerateFragmentLayout),
            TextFieldWidget(mFragmentPackageName),
            CheckBoxWidget(mIsLazyFragment)
        )

        recipe = { data: TemplateData ->
            mvvmActivityRecipe(
                data as ModuleTemplateData,
                mRootPackageName.value,
                mPageName.value,
                mIsActivity.value,
                mActivityLayoutName.value,
                mIsGenerateActivityLayout.value,
                mActivityPackageName.value,
                false,
                mIsFragment.value,
                mIsLazyFragment.value,
                mFragmentLayoutName.value,
                mIsGenerateFragmentLayout.value,
                mFragmentPackageName.value
            )
        }

    }

fun createLayoutName(className: String): String {
    val array = className.toCharArray()
    val string = StringBuilder()
    array.forEach {
        if (it.isUpperCase()) {
            //第一个首字母大写的话 不加下划线
            if (string.isNotEmpty()) {
                string.append("_")
            }
            string.append(it.toLowerCase())
        } else {
            string.append(it)
        }
    }
    return string.toString()
}



mvvmActivityRecipe 代码:

fun RecipeExecutor.mvvmActivityRecipe(
    moduleTemplateData: ModuleTemplateData,
    mRootPackageName: String,
    mPageName: String,
    mIsActivity: Boolean,
    mActivityLayoutName: String,
    mIsGenerateActivityLayout: Boolean,
    mActivityPackageName: String,
    mIsUseHilt: Boolean,
    mIsFragment :Boolean,
    mIsLazyFragment : Boolean,
    mFragmentLayoutName : String,
    mIsGenerateFragmentLayout : Boolean,
    mFragmentPackageName : String
) {
    val (projectData, srcOut, resOut) = moduleTemplateData
    val ktOrJavaExt = projectData.language.extension



    if (mIsActivity) {

        generateManifest(
            moduleData = moduleTemplateData,
            activityClass = "${mPageName}Activity",
            packageName = ".$mActivityPackageName",
            isLauncher = false,
            hasNoActionBar = false,
            generateActivityTitle = true
        )

        val mvvmActivity = mvvmActivityKt(mRootPackageName, mActivityPackageName, mPageName)
        // 保存Activity
        save(
            mvvmActivity,
            srcOut.resolve("${mActivityPackageName}/${mPageName}Activity.${ktOrJavaExt}")
        )
        if (mIsGenerateActivityLayout) {
            // 保存xml
            save(mvvmXml(), resOut.resolve("layout/${mActivityLayoutName}.xml"))
        }
        // 保存viewmodel
        save(
            mvvmViewModelKt(mRootPackageName, mActivityPackageName, mPageName),
            srcOut.resolve("${mActivityPackageName}/${mPageName}ViewModel.${ktOrJavaExt}")
        )
        // 保存repository
        save(
            mvvmRepositoryKt(mRootPackageName, mActivityPackageName, mPageName),
            srcOut.resolve("${mActivityPackageName}/${mPageName}Repository.${ktOrJavaExt}")
        )
    } else if (mIsFragment){
        val mvvmFragment: String = if (mIsLazyFragment) {

            mvvmLazyFragmentKt(mRootPackageName, mFragmentPackageName, mPageName)
        } else {
            mvvmFragmentKt(mRootPackageName, mFragmentPackageName, mPageName)
        }

        // 保存Fragment
        save(
            mvvmFragment,
            srcOut.resolve("${mFragmentPackageName}/${mPageName}Fragment.${ktOrJavaExt}")
        )
        if (mIsGenerateFragmentLayout) {
            // 保存xml
            save(mvvmXml(), resOut.resolve("layout/${mFragmentLayoutName}.xml"))
        }
        // 保存viewmodel
        save(
            mvvmViewModelKt(mRootPackageName, mFragmentPackageName, mPageName),
            srcOut.resolve("${mFragmentPackageName}/${mPageName}ViewModel.${ktOrJavaExt}")
        )
        // 保存repository
        save(
            mvvmRepositoryKt(mRootPackageName, mFragmentPackageName, mPageName),
            srcOut.resolve("${mFragmentPackageName}/${mPageName}Repository.${ktOrJavaExt}")
        )
    }
}

mvvmActivityKt.kt 代码:


fun mvvmActivityKt(
    mRootPackageName:String?,
    mActivityPackageName:String,
    mPageName:String
)="""
package ${mRootPackageName}.${mActivityPackageName}

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import $mRootPackageName.databinding.Activity${mPageName}Binding
import $mRootPackageName.R
import $mRootPackageName.base.BaseActivity
import ${mRootPackageName}.${mActivityPackageName}.${mPageName}ViewModel


@AndroidEntryPoint
class ${mPageName}Activity : BaseActivity<Activity${mPageName}Binding>() {
    // use hilt
    val mViewModel : ${mPageName}ViewModel by viewModels()

    override fun initActivity(savedInstanceState: Bundle?) {
    }
}
"""
```

#### 编译
编写好后 点击 Gradle - Task - Build - jar，编译好后 会在 项目根目录-build-libs文件夹下生成jar包，名字为 项目名-版本号.jar

可能你的Gradle 页面是这样的:

![Gradle-No-Task](http://m.qpic.cn/psc?/V14YlNrL2eQEkW/TmEUgtj9EK6.7V8ajmQrEPShRQnwYV4WA2oABXa2wJbFTRI0mkesF5wQRvGuWvqH0D4Dbg*79qsKJH4U2i4q8NKv46WYhr*5ttMY2P10PRw!/b&bo=zgFRAgAAAAABF6w!&rf=viewer_4&t=5)

点击`Task list not built..`

去掉`Gradle` 下第一个 `Do not build Gradle task list ..`的勾选

![Gradle-Setting](http://m.qpic.cn/psc?/V14YlNrL2eQEkW/TmEUgtj9EK6.7V8ajmQrEJtIPB2GbSyunO.0JVBvJl1bBLp0txCQPU9BcuhW1SWTIqDijFCW75hxDgZCO1tJASiCFouK7NEm5V5ZL3JO2Tc!/b&bo=sAU4BAAAAAADJ4s!&rf=viewer_4&t=5)

拿着编译好的jar包，打开AS `Settings/Preferences` > `Plugins` > `⚙️` > `Install plugin from disk...` > `RESTART IDE`

点击`app` 或者`包名` 右键使用模板

![TemplateUse](http://m.qpic.cn/psc?/V14YlNrL2eQEkW/TmEUgtj9EK6.7V8ajmQrEN.eHrY7PKL3bMie9TC*jyaRObxfv3KgrDGQK3fogi*EHzRTZwhSLhCV7xgkqCAZw.nZhIqLtME3Cqs2F0TtoP0!/b&bo=OAQiBQAAAAADNwk!&rf=viewer_4&t=5)

![TemplateDeatils](http://m.qpic.cn/psc?/V14YlNrL2eQEkW/TmEUgtj9EK6.7V8ajmQrEDQA*swxVTzKroR6O124z9uhkyXJdgcWiJoKhX*O8vug5X20gBWW1RbR2swglnadFpTA2cH05nPSqkhbr11CeWg!/b&bo=rwU4BAAAAAADF6Q!&rf=viewer_4&t=5)

#### 最后
编写匆忙 恐有疏漏 如有不足尽情谅解



### 鸣谢
[鸿洋](https://blog.csdn.net/lmj623565791?spm=1001.2014.3001.5509) : [Android Studio自定义模板 写页面竟然可以如此轻松](https://blog.csdn.net/lmj623565791/article/details/51635533)

[敲代码的鱼哇](https://blog.csdn.net/qq_35195386) : [Android Studio自定义页面模板](https://blog.csdn.net/qq_35195386/article/details/121624604?spm=1001.2014.3001.5501)

[亿特博客](https://www.bigademo.com/) : [AndroidStudio4.1 自定义模板 ](https://www.bigademo.com/2021/01/20/AndroidStudio%204.1%E8%87%AA%E5%AE%9A%E4%B9%89%E6%A8%A1/index.html)



![Build](https://github.com/JiaYang627/TinMVVM/workflows/Build/badge.svg)
[![Version](https://img.shields.io/jetbrains/plugin/v/PLUGIN_ID.svg)](https://plugins.jetbrains.com/plugin/PLUGIN_ID)
[![Downloads](https://img.shields.io/jetbrains/plugin/d/PLUGIN_ID.svg)](https://plugins.jetbrains.com/plugin/PLUGIN_ID)

## Template ToDo list
- [x] Create a new [IntelliJ Platform Plugin Template][template] project.
- [ ] Get familiar with the [template documentation][template].
- [ ] Verify the [pluginGroup](/gradle.properties), [plugin ID](/src/main/resources/META-INF/plugin.xml) and [sources package](/src/main/kotlin).
- [ ] Review the [Legal Agreements](https://plugins.jetbrains.com/docs/marketplace/legal-agreements.html).
- [ ] [Publish a plugin manually](https://plugins.jetbrains.com/docs/intellij/publishing-plugin.html?from=IJPluginTemplate) for the first time.
- [ ] Set the Plugin ID in the above README badges.
- [ ] Set the [Deployment Token](https://plugins.jetbrains.com/docs/marketplace/plugin-upload.html).
- [ ] Click the <kbd>Watch</kbd> button on the top of the [IntelliJ Platform Plugin Template][template] to be notified about releases containing new features and fixes.

<!-- Plugin description -->
This Fancy IntelliJ Platform Plugin is going to be your implementation of the brilliant ideas that you have.

This specific section is a source for the [plugin.xml](/src/main/resources/META-INF/plugin.xml) file which will be extracted by the [Gradle](/build.gradle.kts) during the build process.

To keep everything working, do not remove `<!-- ... -->` sections. 
<!-- Plugin description end -->

## Installation

- Using IDE built-in plugin system:
  
  <kbd>Settings/Preferences</kbd> > <kbd>Plugins</kbd> > <kbd>Marketplace</kbd> > <kbd>Search for "TinMVVM"</kbd> >
  <kbd>Install Plugin</kbd>
  
- Manually:

  Download the [latest release](https://github.com/JiaYang627/TinMVVM/releases/latest) and install it manually using
  <kbd>Settings/Preferences</kbd> > <kbd>Plugins</kbd> > <kbd>⚙️</kbd> > <kbd>Install plugin from disk...</kbd>


---
Plugin based on the [IntelliJ Platform Plugin Template][template].

[template]: https://github.com/JetBrains/intellij-platform-plugin-template
