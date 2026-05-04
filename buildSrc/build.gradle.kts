plugins {
    `kotlin-dsl`
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:${libs.versions.kotlin.get()}")
    implementation("org.jetbrains.dokka:dokka-gradle-plugin:${libs.versions.dokka.get()}")
    implementation("org.jetbrains.dokka-javadoc:org.jetbrains.dokka-javadoc.gradle.plugin:${libs.versions.dokka.get()}")
}

repositories {
    mavenCentral()
}