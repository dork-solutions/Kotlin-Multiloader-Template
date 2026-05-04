plugins {
    id("multiloader-loader")
    alias(libs.plugins.loom)
}

val modId: String by project
val useCobbleSnap: Boolean
    get() = rootProject.property("useCobbleSnap").toString().toBoolean()

dokka.dokkaPublications.html {
    moduleName.set("${rootProject.property("modName")} - Fabric")
}

loom {
    val aw = project(":common").file("src/main/resources/${modId}.accesswidener")
    if (aw.exists()) {
        accessWidenerPath.set(aw)
    }
    mixin {
        defaultRefmapName.set("${modId}.refmap.json")
    }
    runs {
        named("client") {
            configName = "Fabric Client"
            client()
            ideConfigGenerated(true)
            runDir("runs/client")
        }
        named("server") {
            configName = "Fabric Server"
            server()
            ideConfigGenerated(true)
            runDir("runs/server")
        }
    }
}

dependencies {
    minecraft(libs.minecraft)
    mappings(loom.officialMojangMappings())
    modImplementation(libs.fabricLoader)
    modImplementation(libs.fabricApi)
    modImplementation(libs.flk)

    if (!useCobbleSnap) {
        modImplementation(libs.cobblemon.fabric)
    } else {
        modImplementation(libs.cobblemon.fabric.snap)
        runtimeOnly(libs.bundles.graal)
    }
}

val loaderAttribute = Attribute.of("io.github.mcgradleconventions.loader", String::class.java)
listOf("apiElements", "runtimeElements", "sourcesElements", "javadocElements").forEach { variant ->
    configurations.named(variant) {
        attributes {
            attribute(loaderAttribute, "fabric")
        }
    }
}

sourceSets.configureEach {
    listOf(compileClasspathConfigurationName, runtimeClasspathConfigurationName).forEach { variant->
        configurations.named(variant) {
            attributes {
                attribute(loaderAttribute, "fabric")
            }
        }
    }
}