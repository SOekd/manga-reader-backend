plugins {
    id("manga-reader.common-conventions")
}

dependencies {
    implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-client")
    implementation(project(":manga-reader-common"))
}