plugins {
    id("multiloader-common")
    alias(libs.plugins.moddev)
}

neoForge {
    neoFormVersion = libs.versions.neoForm.get()
    // Automatically enable AccessTransformers if the file exists
    val at = file("src/main/resources/META-INF/accesstransformer.cfg")
    if (at.exists()) {
        accessTransformers.from(at.absolutePath)
    }

    parchment {
        minecraftVersion = libs.versions.parchmentMC
        mappingsVersion = libs.versions.parchment
    }
}

dokka.dokkaPublications.html {
    moduleName.set("${rootProject.property("modName")} - Common")
}

val useCobbleSnap: Boolean
    get() = rootProject.property("useCobbleSnap").toString().toBoolean()

dependencies {
    compileOnly(libs.mixin)
    compileOnly(libs.mixinExtras.common)
    annotationProcessor(libs.mixinExtras.common)

    if (!useCobbleSnap) {
        implementation(libs.cobblemon.common)
    } else {

        implementation(libs.cobblemon.common.snap)
    }
}

configurations {
    create("commonJava") {
        isCanBeResolved = false
        isCanBeConsumed = true
    }
    create("commonKotlin") {
        isCanBeResolved = false
        isCanBeConsumed = true
    }
    create("commonResources") {
        isCanBeResolved = false
        isCanBeConsumed = true
    }
}

artifacts {
    add("commonJava", sourceSets.main.get().java.sourceDirectories.singleFile)
    add("commonKotlin", sourceSets.main.get().kotlin.sourceDirectories.filter { !it.name.endsWith("java") }.singleFile)
    add("commonResources", sourceSets.main.get().resources.sourceDirectories.singleFile)
}

val loaderAttribute = Attribute.of("io.github.mcgradleconventions.loader", String::class.java)
listOf("apiElements", "runtimeElements", "sourcesElements", "javadocElements").forEach { variant ->
    configurations.named(variant) {
        attributes {
            attribute(loaderAttribute, "common")
        }
    }
}

sourceSets.configureEach {
    listOf(compileClasspathConfigurationName, runtimeClasspathConfigurationName).forEach { variant->
        configurations.named(variant) {
            attributes {
                attribute(loaderAttribute, "common")
            }
        }
    }
}