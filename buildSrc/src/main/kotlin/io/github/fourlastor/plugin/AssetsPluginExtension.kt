package io.github.fourlastor.plugin

import org.gradle.api.file.FileCollection
import org.gradle.api.provider.Property

abstract class AssetsPluginExtension {
    /**
     * Which directory should be scanned so all files will be referenced in the Assets object.
     */
    abstract val assetsDirectory: Property<FileCollection>

    /** The package in which to generate the assets class. */
    abstract val assetsPackage: Property<String>

}
