plugins {
    id("java")
    id("java-library")
    id("multiloader-common")
    id("multiloader-dokka")
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
        val loaderAttribute = Attribute.of("io.github.mcgradleconventions.loader", String::class.java)
        attributes {
            attribute(loaderAttribute, "common")
        }
    }

    dokka(project(":common"))
    commonJava(project(":common", configuration = "commonJava"))
    commonKotlin(project(":common", configuration = "commonKotlin"))
    commonResources(project(":common", configuration = "commonResources"))
}

tasks {
    compileJava  {
        dependsOn(commonJava)
        source(commonJava)
    }

    compileKotlin {
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
}