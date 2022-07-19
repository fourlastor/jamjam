package io.github.fourlastor.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPlugin
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.api.tasks.SourceSet

class AssetsPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        val exts = project.extensions.create("assets", AssetsPluginExtension::class.java, project)

        project.tasks.register("assets", AssetsTask::class.java) { task ->
            task.assetsDirectory.set(exts.assetsDirectory)
            task.assetsClass.set(exts.assetsClass)
        }

        project.plugins.withType(JavaPlugin::class.java) {
            val extension = project.extensions.findByType(JavaPluginExtension::class.java)!! // ?: return@withType
            val main = extension.sourceSets.getByName(SourceSet.MAIN_SOURCE_SET_NAME)
            main.java.srcDir("build/generated/sources/assets")
        }
    }
}
