plugins {
    id("java-library")
    kotlin("jvm") version "1.6.21"
    id("java-gradle-plugin")
}

gradlePlugin {
    plugins {
        create("gdxAssets") {
            id = "gdx-assets"
            implementationClass = "io.github.fourlastor.plugin.AssetsPlugin"
        }
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(gradleApi())
    implementation(kotlin("stdlib"))
    implementation("com.squareup:javapoet:1.13.0")
}
