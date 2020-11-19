plugins {
    // Apply the Kotlin JVM plugin to add support for Kotlin.
    id("org.jetbrains.kotlin.jvm") version "1.4.10"
    id("com.github.johnrengelman.shadow") version "6.1.0"

    // Apply the application plugin to add support for building a jar
    java
}

repositories {
    // Use jcenter for resolving dependencies.
    jcenter()

    // Use mavenCentral
    maven(url = "https://repo1.maven.org/maven2/")
    maven(url = "http://repo.spongepowered.org/maven")
    maven(url = "https://libraries.minecraft.net")
    maven(url = "https://jitpack.io")
    maven(url = "https://jcenter.bintray.com/")
}

dependencies {
    // Align versions of all Kotlin components
    implementation(platform("org.jetbrains.kotlin:kotlin-bom"))

    // Use the Kotlin JDK 8 standard library.
    implementation(kotlin("stdlib"))

    // Use the Kotlin reflect library.
    implementation(kotlin("reflect"))

    // Use the Kotlin test library.
    testImplementation("org.junit.jupiter:junit-jupiter:5.6.0")

    // Compile Minestom into project
    implementation("com.github.Minestom:Minestom:0187864")

    // Use the Netty library
    implementation("io.netty:netty-transport-native-epoll:4.1.52.Final")

    // OkHttp
    implementation("com.squareup.okhttp3", "okhttp", "4.9.0")

    // org.json
    implementation("org.json", "json", "20200518")

    // Implement Klaxon
    implementation("com.beust:klaxon:5.0.1")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.jar {
    manifest {
        attributes (
            "Main-Class" to "world.cepi.sabre.BootstrapKt",
            "Multi-Release" to true
        )
    }
}

configurations.all {
    resolutionStrategy.cacheChangingModulesFor(0, "seconds")
}