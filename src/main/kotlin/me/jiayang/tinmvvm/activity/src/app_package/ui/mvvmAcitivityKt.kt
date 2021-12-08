
package me.jiayang.tinmvvm.activity.src.app_package.ui

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
