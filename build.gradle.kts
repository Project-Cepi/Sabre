import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    // Apply the Kotlin JVM plugin to add support for Kotlin.
    id("org.jetbrains.kotlin.jvm") version "1.6.20"
    kotlin("plugin.serialization") version "1.6.20"
    id("com.github.johnrengelman.shadow") version "7.1.2"

    // Apply the application plugin to add support for building a jar
    java
}

repositories {
    // maven central
    mavenCentral()

    maven(url = "https://repo.spongepowered.org/maven")
    maven(url = "https://jitpack.io")
}

dependencies {
    // Align versions of all Kotlin components
    implementation(platform("org.jetbrains.kotlin:kotlin-bom"))

    // Use the Kotlin JDK 8 standard library.
    implementation(kotlin("stdlib"))

    // Use the Kotlin reflect library.
    implementation(kotlin("reflect"))

    // Add support for kotlinx courotines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.1")

    // Add tinylog
    implementation("org.tinylog:tinylog-api-kotlin:2.4.1")
    implementation("org.tinylog:tinylog-impl:2.4.1")

    // Add intergration
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-jdk8:1.6.1")

    // import kotlinx serialization
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.2")

	// Add MiniMessage
    implementation("net.kyori:adventure-text-minimessage:4.10.1")

    // Add Ktor
    //implementation("io.ktor:ktor-client-core:1.6.8")
    //implementation("io.ktor:ktor-client-cio:1.6.8")

    // Use the kotlin test library
    ///testImplementation("io.kotest:kotest-assertions-core:5.2.2")
    //testImplementation("io.kotest:kotest-runner-junit5:5.2.2")

    // Compile Minestom into project
    implementation("com.github.Minestom", "Minestom", "afddb6f549")

    // JLine
    implementation("org.jline:jline:3.21.0")

    // Jansi
    implementation("org.jline:jline-terminal-jansi:3.21.0")
}

tasks {
    named<ShadowJar>("shadowJar") {
        manifest {
            attributes (
                    "Main-Class" to "world.cepi.sabre.SabreLoader",
                    "Multi-Release" to true
            )
        }

        transform(com.github.jengelman.gradle.plugins.shadow.transformers.Log4j2PluginsCacheFileTransformer::class.java)

        mergeServiceFiles()

        archiveBaseName.set("sabre")
    }

    build { dependsOn(shadowJar) }

    withType<AbstractArchiveTask> {
        isPreserveFileTimestamps = false
        isReproducibleFileOrder = true
    }

}

configure<SourceSetContainer> {
    named("main") {
        java.srcDir("src/main/kotlin")
    }
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

tasks.withType<KotlinCompile> { kotlinOptions.jvmTarget = "17" }
