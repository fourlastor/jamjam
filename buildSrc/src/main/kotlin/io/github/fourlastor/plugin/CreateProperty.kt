package io.github.fourlastor.plugin

import org.gradle.api.Project
import org.gradle.api.provider.Property

inline fun <reified T> Project.createProperty(): Property<T> {
    return this.objects.property(T::class.java)
}
