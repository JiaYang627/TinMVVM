package me.jiayang.tinmvvm.activity

import com.android.tools.idea.wizard.template.WizardUiContext
import com.android.tools.idea.wizard.template.template
import com.android.tools.idea.wizard.template.*
import com.android.tools.idea.wizard.template.impl.activities.common.MIN_API
import com.intellij.ui.layout.checkBoxFollowedBySpinner
import java.lang.StringBuilder

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

//    val mIsUseHilt = booleanParameter {
//        name = "Page Use Hilt"
//        default = false
//        help = "创建的组件页面是否使用Hilt注入"
//    }

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
//        CheckBoxWidget(mIsUseHilt)
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
