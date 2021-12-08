package me.jiayang.template.services

import com.intellij.openapi.project.Project
import me.jiayang.template.MyBundle

class MyProjectService(project: Project) {

    init {
        println(MyBundle.message("projectService", project.name))
    }
}
