import org.jetbrains.dokka.gradle.tasks.DokkaBaseTask
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("java")
    id("java-library")
    id("multiloader-common")
    id("org.jetbrains.kotlin.jvm")
}

val modId: String by project

val commonJava = configurations.create("commonJava") {
    isCanBeResolved = true
}

val commonKotlin = configurations.create("commonKotlin") {
    isCanBeResolved = true
}

val commonResources = configurations.create("commonResources") {
    isCanBeResolved = true
}

dependencies {
    compileOnly(project(":common")) {
        capabilities {
            requireCapability("$group:$modId")
        }
    }

    commonJava(project(":common", configuration = "commonJava"))
    commonKotlin(project(":common", configuration = "commonKotlin"))
    commonResources(project(":common", configuration = "commonResources"))
}

dokka {
    dokkaSourceSets {
        configureEach {
            sourceRoots.from(commonJava.singleFile, commonKotlin.singleFile)
        }
    }
}

tasks {
    named<JavaCompile>("compileJava") {
        dependsOn(commonJava)
        source(commonJava)
    }

    named<KotlinCompile>("compileKotlin") {
        dependsOn(commonJava)
        dependsOn(commonKotlin)
        source(commonJava)
        source(commonKotlin)
    }

    processResources {
        dependsOn(commonResources)
        from(commonResources)
    }

    named<Jar>("sourcesJar") {
        dependsOn(commonJava)
        from(commonJava)
        dependsOn(commonKotlin)
        from(commonKotlin)
        dependsOn(commonResources)
        from(commonResources)
    }

    named("dokkaGenerate") {
        dependsOn(commonJava)
        dependsOn(commonKotlin)
    }
}