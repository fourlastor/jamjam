package io.github.fourlastor.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPlugin
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.api.tasks.SourceSet

class AssetsPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        val exts = project.extensions.create("gdxAssets", AssetsPluginExtension::class.java)

        val generatedSourcesRoot = "generated/sources/assets"
        val assetClassDirectory = project.buildDir.resolve("$generatedSourcesRoot")

        val task = project.tasks.register("assets", AssetsTask::class.java) { task ->
            task.assetsDirectory.set(exts.assetsDirectory)
            task.assetsPackage.set(exts.assetsPackage)
            task.assetsClassDirectory.set(assetClassDirectory)
        }.get()

        project.tasks.named("build").get().dependsOn(task)

        project.plugins.withType(JavaPlugin::class.java) {
            val extension = project.extensions.findByType(JavaPluginExtension::class.java)!! // ?: return@withType
            val main = extension.sourceSets.getByName(SourceSet.MAIN_SOURCE_SET_NAME)
            val srcDir = assetClassDirectory.relativeTo(project.buildDir.parentFile).path
            main.java.srcDir(srcDir)
        }
    }
}
