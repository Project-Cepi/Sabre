import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    // Apply the Kotlin JVM plugin to add support for Kotlin.
    id("org.jetbrains.kotlin.jvm") version "1.7.22"
    kotlin("plugin.serialization") version "1.7.22"
    id("com.github.johnrengelman.shadow") version "7.1.2"
	id("io.gitlab.arturbosch.detekt") version "1.22.0"

    // Apply the application plugin to add support for building a jar
    java
}

detekt {
    toolVersion = "1.22.0"
    config = files("config/detekt/detekt.yml")
    buildUponDefaultConfig = true
}

repositories {
    // maven central
    mavenCentral()

    maven(url = "https://repo.spongepowered.org/maven")
    maven(url = "https://jitpack.io")
    maven(url = "https://repo.velocitypowered.com/snapshots")
    maven(url = "https://repo.minestom.com/repository/maven-public/")
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

    // Add intergration
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-jdk8:1.6.4")

    // import kotlinx serialization
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.4.1")

	// Add MiniMessage
    implementation("net.kyori:adventure-text-minimessage:4.12.0")

    // Add Ktor
    implementation("io.ktor:ktor-client-core:2.3.9")
    implementation("io.ktor:ktor-client-cio:2.2.1")

    // Use the kotlin test library
    testImplementation("io.kotest:kotest-assertions-core:5.5.4")
    testImplementation("io.kotest:kotest-runner-junit5:5.5.4")

    // Compile Minestom into project
    implementation("com.github.Minestom", "Minestom", "7867313290")

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

    test { useJUnitPlatform() }

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

sourceSets.create("demo") {
    java.srcDir("src/demo/java")
    java.srcDir("build/generated/source/apt/demo")
    resources.srcDir("src/demo/resources")
    compileClasspath += sourceSets.main.get().output + sourceSets.main.get().compileClasspath
    runtimeClasspath += sourceSets.main.get().output + sourceSets.main.get().runtimeClasspath
}
