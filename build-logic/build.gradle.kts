plugins {
    `kotlin-dsl`
}

repositories {
    gradlePluginPortal()
}

dependencies {
    implementation(libs.shadow)
    implementation(libs.spring.dependency)
    implementation(libs.spring.boot)

    compileOnly(files(libs::class.java.protectionDomain.codeSource.location))
}