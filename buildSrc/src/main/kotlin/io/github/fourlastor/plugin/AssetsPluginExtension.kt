package io.github.fourlastor.plugin

import org.gradle.api.Project
import org.gradle.api.file.FileCollection
import org.gradle.api.provider.Property
import java.io.File

open class AssetsPluginExtension(project: Project) {
    /**
     * Which directory should be scanned so all files will be referenced in the Assets object.
     */
    val assetsDirectory: Property<FileCollection> = project.createProperty<FileCollection>()

}
