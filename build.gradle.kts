import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    // Apply the Kotlin JVM plugin to add support for Kotlin.
    id("org.jetbrains.kotlin.jvm") version "1.4.21-2"
    kotlin("plugin.serialization") version "1.4.20"
    id("com.github.johnrengelman.shadow") version "6.1.0"

    // Apply the application plugin to add support for building a jar
    java
}

repositories {
    // maven central
    mavenCentral()

    // Use jcenter for resolving dependencies.
    jcenter()

    listOf(
            "repo1.maven.org/maven2",
            "repo.spongepowered.org/maven",
            "libraries.minecraft.net",
            "jitpack.io",
            "jcenter.bintray.com"
    ).forEach { maven(url = "https://$it") } // require https for all dependencies
}

dependencies {
    // Align versions of all Kotlin components
    implementation(platform("org.jetbrains.kotlin:kotlin-bom"))

    // Use the Kotlin JDK 8 standard library.
    implementation(kotlin("stdlib"))

    // Use the Kotlin reflect library.
    implementation(kotlin("reflect"))

    // Compile Minestom into project
    implementation("com.github.Minestom", "Minestom", "238ea649ab")

    // OkHttp
    implementation("com.squareup.okhttp3", "okhttp", "4.9.0")

    // org.json
    implementation("org.json", "json", "20200518")

    // import kotlinx serialization
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.0.1")

    // implement KStom
    implementation("com.github.Project-Cepi:KStom:main-SNAPSHOT")

    // Log4j Jul
    implementation("org.apache.logging.log4j:log4j-jul:2.14.0")
}

tasks {
    named<ShadowJar>("shadowJar") {
        archiveBaseName.set("sabre")
        manifest {
            attributes (
                    "Main-Class" to "world.cepi.sabre.BootstrapWrapper",
                    "Multi-Release" to true
            )
        }
        mergeServiceFiles()
    }

    test { useJUnitPlatform() }

    build { dependsOn(shadowJar) }

}

configure<SourceSetContainer> {
    named("main") {
        java.srcDir("src/main/kotlin")
    }
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

configurations.all {
    resolutionStrategy {
        cacheChangingModulesFor(0, TimeUnit.SECONDS)
    }
}
