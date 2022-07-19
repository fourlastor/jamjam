package io.github.fourlastor.plugin

import com.squareup.javapoet.FieldSpec
import com.squareup.javapoet.JavaFile
import com.squareup.javapoet.TypeSpec
import org.gradle.api.DefaultTask
import org.gradle.api.file.FileCollection
import org.gradle.api.tasks.InputFiles
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.TaskAction
import java.io.File
import javax.lang.model.element.Modifier

open class AssetsTask : DefaultTask() {

    init {
        group = "build"
        description = "Create static class referencing assets file name."
    }

    @InputFiles
    val assetsDirectory = project.createProperty<FileCollection>()

    @OutputDirectory
    val assetsClassDirectory = project.createProperty<File>()

    @TaskAction
    fun generate() {
        val assets = directoryTypeSpec("Assets")
                .addAssets()
                .build()
        // TODO this should be configurable
        JavaFile.builder("io.github.fourlastor.jamjam", assets)
                .build().writeTo(assetsClassDirectory.get())
    }

    private fun TypeSpec.Builder.addAssets() = apply {
        assetsDirectory.get().files.forEach {
            val base = if (it.isDirectory) it else it.parentFile
            appendDirectory(it, base)
        }
    }

    private fun TypeSpec.Builder.appendDirectory(current: File, base: File, prefix: String = "") {
        if (current.isDirectory) {
            val normalizedName = current.nameWithoutExtension.normalized
            current.listFiles().orEmpty().forEach {
                appendDirectory(it, base, prefix + normalizedName + "_")
            }
        } else {
            val fieldName = prefix + current.name.normalized
            val fieldSpec = FieldSpec.builder(String::class.java, fieldName)
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL, Modifier.STATIC)
                .initializer("\$S", current.relativeTo(base).path)
                .build()
            addField(fieldSpec)
        }
    }

    private fun directoryTypeSpec(className: String): TypeSpec.Builder = TypeSpec.classBuilder(className)
            .addModifiers(Modifier.PUBLIC, Modifier.FINAL)

    private val String.normalized
        get() = replace(".", "_")
            .replace("-", "_")
            .let { NON_ALPHANUMERIC.replace(it, "_") }

    companion object {
        private val NON_ALPHANUMERIC = Regex("[^A-Za-z0-9]")
    }
}
