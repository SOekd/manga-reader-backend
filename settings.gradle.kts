rootProject.name = "manga-reader"

pluginManagement {
    includeBuild("build-logic")

    repositories {
        mavenCentral()
        gradlePluginPortal()
    }
}

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

sequenceOf(
        "common",
        "api-gateway",
        "discovery-server",
        "security",
).forEach {
    include("manga-reader-$it")
    project(":manga-reader-$it").projectDir = file(it)
}

sequenceOf(
        "discord",
).forEach {
    include("manga-reader-$it")
    project(":manga-reader-$it").projectDir = file("./storage/$it")
}

sequenceOf(
    "manga-service",
    "notification-service",
    "compress-service",
    "user-service"
).forEach {
    include("manga-reader-$it")
    project(":manga-reader-$it").projectDir = file("./services/$it")
}