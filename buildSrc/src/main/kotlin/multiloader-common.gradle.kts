import utilities.requireCatalogVersions

plugins {
    id("java")
    id("java-library")
    id("org.jetbrains.kotlin.jvm")
    id("org.jetbrains.dokka")
    id("multiloader-dokka")
}

val modId: String by project
val modName: String by project
val modAuthors: String by project
val modLicense: String by project
val modDescription: String by project
val credits: String by project

val versions = project.requireCatalogVersions(
    "java",
    "minecraft",
    "minecraftRange",
    "fabricLoader",
    "flk",
    "kotlin",
    "fabricApi",
    "neoforge",
    "neoforgeRange",
    "kff",
    "kffRange",
)

val javaVersion = versions.getValue("java").toIntOrNull()
val modVersion = project.version.toString()
val mcVersion = versions.getValue("minecraft")
val mcRange = versions.getValue("minecraftRange")
val kotlinVersion = versions.getValue("kotlin")
val fabricLoaderVersion = versions.getValue("fabricLoader")
val fabricApiVersion = versions.getValue("fabricApi")
val fabricKotlinVersion = versions.getValue("flk")
val neoforgeVersion = versions.getValue("neoforge")
val neoforgeRange = versions.getValue("neoforgeRange")
val neoforgeKotlinVersion = versions.getValue("kff")
val neoforgeKotlinRange = versions.getValue("kffRange")

base {
    archivesName = "${modId}-${project.name}-${mcVersion}"
}

java {
    withSourcesJar()
    withJavadocJar()
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(javaVersion!!))
    }
}

kotlin {
    jvmToolchain {
        languageVersion.set(JavaLanguageVersion.of(javaVersion!!))
    }
}

repositories {
    mavenCentral()
    maven("https://maven.blamejared.com")
    maven("https://thedarkcolour.github.io/KotlinForForge/")
    maven("https://artefacts.cobblemon.com/releases/")
}

tasks {
    processResources {
        val expandProps = mapOf(
            "version"                 to modVersion,
            "group"                   to project.group,
            "java_version"            to javaVersion,
            "mod_name"                to modName,
            "mod_authors"             to modAuthors,
            "mod_id"                  to modId,
            "license"                 to modLicense,
            "description"             to modDescription,
            "credits"                 to credits,
            "minecraft_version"       to mcVersion,
            "minecraft_version_range" to mcRange,
            "fabric_version"          to fabricApiVersion,
            "fabric_loader_version"   to fabricLoaderVersion,
            "fabric_kotlin_version"   to fabricKotlinVersion,
            "kotlin_version"          to kotlinVersion,
            "neoforge_version"        to neoforgeVersion,
            "neoforge_range"          to neoforgeRange,
            "neoforge_kotlin_version" to neoforgeKotlinVersion,
            "neoforge_kotlin_range"   to neoforgeKotlinRange,
        )

        filesMatching(listOf("pack.mcmeta", "fabric.mod.json", "META-INF/neoforge.mods.toml", "*.mixins.json")) {
            expand(expandProps)
        }

        inputs.properties(expandProps)
    }

    kotlinSourcesJar {
        from(rootProject.file("LICENSE")) {
            rename { "${it}_${modId}" }
        }
    }

    jar {
        from(rootProject.file("LICENSE")) {
            rename { "${it}_${modId}" }
        }

        manifest {
            attributes(mapOf(
                "Specification-Title"    to modName,
                "Specification-Vendor"   to modAuthors,
                "Specification-Version"  to modVersion,
                "Implementation-Title"   to modName,
                "Implementation-Vendor"  to modAuthors,
                "Implementation-Version" to modVersion,
                "Built-On-Minecraft"     to mcVersion
            ))
        }
    }
}