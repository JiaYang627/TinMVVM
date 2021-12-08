package me.jiayang.tinmvvm.common.repository

fun mvvmRepositoryKt(
    mRootPackageName:String,
    mActivityPackageName:String,
    mPageName:String
) = """
package ${mRootPackageName}.${mActivityPackageName}

import ${mRootPackageName}.network.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject    
    
    
class ${mPageName}Repository @Inject constructor(private val apiService: ApiService){

} 
"""
