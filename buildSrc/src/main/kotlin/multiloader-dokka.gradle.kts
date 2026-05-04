import org.gradle.kotlin.dsl.provideDelegate
import utilities.requireCatalogVersions

plugins {
    id("org.jetbrains.dokka")
    id("org.jetbrains.dokka-javadoc")
}

val modName: String by project
val name: String by project
val versions = project.requireCatalogVersions("java")
val javaVersion = versions.getValue("java").toIntOrNull()

dokka {
    dokkaPublications.html {
        outputDirectory.set(layout.buildDirectory.dir("documentation/html"))
    }
}
