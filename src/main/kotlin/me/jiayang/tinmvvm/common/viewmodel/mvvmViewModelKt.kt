package me.jiayang.tinmvvm.common.viewmodel

fun mvvmViewModelKt(
    mRootPackageName:String,
    mActivityPackageName:String,
    mPageName:String
) = """
package ${mRootPackageName}.${mActivityPackageName}

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject    
    
@HiltViewModel
class ${mPageName}ViewModel @Inject constructor(private val repository : ${mPageName}Repository) : ViewModel() {
} 
"""
