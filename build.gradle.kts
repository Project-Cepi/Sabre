import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.jetbrains.kotlin.jvm") version "1.7.20"
    kotlin("plugin.serialization") version "1.7.20"
    id("com.github.johnrengelman.shadow") version "7.1.2"

    java
}

repositories {
    mavenCentral()
    maven("https://jitpack.io")
}

dependencies {
    // Align versions of all Kotlin components
    implementation(platform("org.jetbrains.kotlin:kotlin-bom"))

    // Use the Kotlin JDK 8 standard library.
    implementation(kotlin("stdlib"))

    // Use the Kotlin reflect library.
    implementation(kotlin("reflect"))

    // Add support for kotlinx courotines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")

    // Add tinylog
    implementation("org.tinylog:tinylog-api-kotlin:2.5.0")
    implementation("org.tinylog:tinylog-impl:2.5.0")

    // Storage dependencies
    implementation("org.redisson:redisson:3.17.7")
//    implementation("io.lettuce:lettuce-core:6.2.0.RELEASE")
    implementation("org.litote.kmongo:kmongo-coroutine-serialization:4.7.1")
    implementation("mysql:mysql-connector-java:8.0.30")
    implementation("com.zaxxer:HikariCP:5.0.1")

    // Add intergration
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-jdk8:1.6.4")

    // import kotlinx serialization
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.4.0")

    // Compile Minestom into project
    implementation("com.github.Minestom", "Minestom", "17ef1c2f57")

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
        relocate("com.zaxxer.hikari", "dev.emortal.datadependency.libs.hikari")
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
