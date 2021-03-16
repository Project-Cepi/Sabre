import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
plugins {
    // Apply the Kotlin JVM plugin to add support for Kotlin.
    id("org.jetbrains.kotlin.jvm") version "1.4.31"
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

    // Add support for kotlinx courotines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.4.3")

    // import kotlinx serialization
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.1.0")

    // Use the JUpiter test library.
    testImplementation("org.junit.jupiter:junit-jupiter:5.7.1")

    // Compile Minestom into project
    implementation("com.github.kezz", "Minestom", "ec9b2023e5")

    // org.json
    implementation("org.json", "json", "20200518")

    // implement KStom
    implementation("com.github.Project-Cepi","KStom", "0f25b85e63")

    // Log4j Jul
    implementation("org.apache.logging.log4j:log4j-jul:2.14.1")
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

tasks.withType<KotlinCompile> { kotlinOptions.jvmTarget = "11" }
