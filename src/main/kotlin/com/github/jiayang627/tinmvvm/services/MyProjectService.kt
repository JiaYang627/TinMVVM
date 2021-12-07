package com.github.jiayang627.tinmvvm.services

import com.intellij.openapi.project.Project
import com.github.jiayang627.tinmvvm.MyBundle

class MyProjectService(project: Project) {

    init {
        println(MyBundle.message("projectService", project.name))
    }
}
