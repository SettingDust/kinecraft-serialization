plugins {
    `maven-publish`
    alias(catalog.plugins.kotlin.jvm)
    alias(catalog.plugins.kotlin.plugin.serialization)
    alias(catalog.plugins.fabric.loom)
}

val mod_id: String by rootProject

loom { mixin { defaultRefmapName = "$mod_id.refmap.json" } }

dependencies {
    minecraft(catalog.minecraft.fabric)
    mappings(loom.officialMojangMappings())
}

tasks {
    remapJar {
        inputFile = project(":versions:1.20").tasks.jar.flatMap { it.archiveFile }
    }
}