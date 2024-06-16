plugins {
    alias(libs.plugins.neoforge.gradle)
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.plugin.serialization)
    `maven-publish`
}

base { archivesName.set("${rootProject.base.archivesName.get()}-${project.name}") }

repositories {
    maven("https://thedarkcolour.github.io/KotlinForForge/")
    maven("https://maven.neoforged.net/releases") { name = "NeoForge" }
}

subsystems {
    parchment {
//        minecraftVersion = libs.versions.minecraft.get()
        minecraftVersion = "1.20.6"
        mappingsVersion = libs.versions.parchmentmc.get()
    }
}

minecraft {
    runs {
        afterEvaluate {
            clear()
        }
    }
}

jarJar.enable()

dependencies {
    implementation(libs.neoforge)
    implementation(libs.kotlinx.serialization.core)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.kotlin.reflect)
    jarJar(project(":neoforge"))
}

tasks {
    jar {
        archiveClassifier.set("dev")
        from("LICENSE") { rename { "${it}_KinecraftSerialization" } }
        manifest.attributes(
            "FMLModType" to "GAMELIBRARY"
        )
        duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    }

    jarJar.configure {
        archiveClassifier.set("")
    }

    sourcesJar { from(rootProject.sourceSets.main.get().allSource) }
}

publishing {
    publications {
        create<MavenPublication>(rootProject.name) {
            groupId = "${rootProject.group}"
            artifactId = base.archivesName.get()
            version = "${rootProject.version}"
            from(components.getByName("java"))
        }
    }
}