import gradle.kotlin.dsl.accessors._ef6ec7848131bff92226e36f14b6a3b0.dokka
import gradle.kotlin.dsl.accessors._ef6ec7848131bff92226e36f14b6a3b0.main
import gradle.kotlin.dsl.accessors._ef6ec7848131bff92226e36f14b6a3b0.sourceSets
import org.gradle.kotlin.dsl.provideDelegate
import org.jetbrains.dokka.gradle.engine.parameters.VisibilityModifier
import utilities.requireCatalogVersions

plugins {
    id("org.jetbrains.dokka")
}

val modName: String by project
val versions = project.requireCatalogVersions("java")
val javaVersion = versions.getValue("java").toIntOrNull()

dokka {
    moduleName.set(modName)
    dokkaSourceSets.configureEach {
        documentedVisibilities(VisibilityModifier.Public)
        skipDeprecated.set(true)
        reportUndocumented.set(false)
        jdkVersion.set(javaVersion as Int?)
        sourceRoots.from(sourceSets.main.get().allSource)
    }
}