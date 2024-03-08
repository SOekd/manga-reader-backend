plugins {
    id("manga-reader.client-conventions")
}

val libs = extensions.getByType(org.gradle.accessors.dm.LibrariesForLibs::class)

dependencies {
    implementation(libs.twelvemonkeys.jpeg)
    implementation(libs.twelvemonkeys.bmp)
    implementation(libs.twelvemonkeys.hdr)
    implementation(libs.twelvemonkeys.icns)
    implementation(libs.twelvemonkeys.iff)
    implementation(libs.twelvemonkeys.pcx)
    implementation(libs.twelvemonkeys.pict)
    implementation(libs.twelvemonkeys.pnm)
    implementation(libs.twelvemonkeys.psd)
    implementation(libs.twelvemonkeys.sgi)
    implementation(libs.twelvemonkeys.tga)
    implementation(libs.twelvemonkeys.thumbsdb)
    implementation(libs.twelvemonkeys.tiff)
    implementation(libs.twelvemonkeys.webp)
    implementation(libs.twelvemonkeys.xwd)
    implementation(libs.sejda.webp)
}