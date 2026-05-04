package utilities

import org.gradle.api.GradleException
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import java.util.*

fun Project.findLibs(): Optional<VersionCatalog> {
    val ext = rootProject.extensions.findByType(VersionCatalogsExtension::class.java)
    return ext?.find("libs") ?: Optional.empty()
}

fun Project.requireCatalogVersion(pName: String): String {
    val libsProvider: Optional<VersionCatalog> = findLibs()
    val value = runCatching {
        libsProvider.get().findVersion(pName).get().toString()
    }.getOrNull()
    return value ?: throw GradleException(
        "Missing version entry '$pName' in version catalog 'libs'. " +
                "Add it to `gradle/libs.versions.toml` or provide the value via project properties.",
    )
}

fun Project.libVersion(pName: String): String? {
    val libsProvider: Optional<VersionCatalog> = findLibs()
    return runCatching {
        libsProvider.get().findVersion(pName).get().toString()
    }.getOrNull()
}

fun Project.requireCatalogVersions(vararg pNames: String): Map<String, String> =
    pNames.associateWith { requireCatalogVersion(it) }