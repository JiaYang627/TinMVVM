
package me.jiayang.tinmvvm.activity.src.app_package.ui

fun mvvmLazyFragmentKt(
    mRootPackageName:String?,
    mActivityPackageName:String,
    mPageName:String
)="""
package ${mRootPackageName}.${mActivityPackageName}

import android.view.View
import android.os.Bundle
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import $mRootPackageName.base.BaseLazyFragment
import $mRootPackageName.databinding.Fragment${mPageName}Binding
import ${mRootPackageName}.${mActivityPackageName}.${mPageName}ViewModel


@AndroidEntryPoint
class ${mPageName}Fragment : BaseLazyFragment<Fragment${mPageName}Binding>() {
    // use hilt
    val mViewModel : ${mPageName}ViewModel by viewModels()

    override fun initLazyFragment() {
    }
}
"""
