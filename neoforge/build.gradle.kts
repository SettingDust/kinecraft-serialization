import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `maven-publish`
    alias(catalog.plugins.kotlin.jvm)
    alias(catalog.plugins.kotlin.plugin.serialization)
    alias(catalog.plugins.neoforge.gradle)
    alias(catalog.plugins.neoforge.gradle.mixin)
}

mixin {
    config("${rootProject.name}.mixins.json")
}

dependencies {
    implementation(catalog.neoforge)
    implementation(project(":common"))
}

tasks {
    withType<ProcessResources> { from(project(":common").sourceSets.main.get().resources) }
    withType<KotlinCompile> { source(project(":common").sourceSets.main.get().allSource) }
    withType<JavaCompile> { source(project(":common").sourceSets.main.get().allSource) }

    sourcesJar { from(project(":common").sourceSets.main.get().allSource) }

    jar {
        manifest {
            "FMLModType" to "GAMELIBRARY"
            "Automatic-Module-Name" to project.path
        }
    }
}

rootProject.publishing {
    publications {
        named<MavenPublication>("maven") {
            artifact(tasks.jar) {
                classifier = "neoforge"
            }
        }
    }
}
