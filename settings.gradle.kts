pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
    }
}

rootProject.name = "assertk-project"

//includeBuild("build-src")
include(
    ":assertk",
    ":assertk-coroutines",
    ":migration-assertk",
    ":migration-assertk-coroutines"
)

project(":migration-assertk").projectDir = file("migration/assertk")
project(":migration-assertk-coroutines").projectDir = file("migration/assertk-coroutines")
