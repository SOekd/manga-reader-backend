plugins {
    id("manga-reader.common-conventions")
    id("org.springframework.boot")
    id("io.spring.dependency-management")
}

val libs = extensions.getByType(org.gradle.accessors.dm.LibrariesForLibs::class)

extra["springCloudVersion"] = libs.versions.spring.cloud.get()

dependencies {
    implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-server")
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
    }
}