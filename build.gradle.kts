import com.google.protobuf.gradle.id
import com.google.protobuf.gradle.proto
import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    kotlin("jvm") version "2.0.0"
    kotlin("kapt") version "2.0.0"
    kotlin("plugin.spring") version "2.0.0"
    kotlin("plugin.serialization") version "2.0.0"
    id("org.springframework.boot") version "3.3.2"
    id("io.spring.dependency-management") version "1.1.6"
    id("com.google.protobuf") version "0.9.4"
    idea    // proto sourceSet을 intelliJ가 인식하기 위함
}

val grpc = "1.63.0"
val grpcKotlin = "1.4.1"
val protoc = "4.27.2"

kotlin {
    jvmToolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
}

// Root에는 jar를 생성할 대상이 없음.
tasks {
    named<BootJar>("bootJar") {
        enabled = false
    }

    named<Jar>("jar") {
        enabled = false
    }
}

allprojects {
    group = "com.songko"
    version = "0.0.1-SNAPSHOT"

    repositories {
        mavenCentral()
    }

    tasks {
        withType<Test> {
            useJUnitPlatform()
        }

        withType<ProcessResources> {
            duplicatesStrategy = DuplicatesStrategy.EXCLUDE
        }
    }
}

subprojects {
    apply {
        plugin("kotlin")
        plugin("kotlin-kapt")
        plugin("kotlin-spring")
        plugin("org.springframework.boot")
        plugin("io.spring.dependency-management")
        plugin("com.google.protobuf")
    }

    dependencies {
        // Spring
        implementation("org.springframework.boot:spring-boot-starter")

        // Kotlin
        implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
        implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.7.1")
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.1")

        // gRPC
        implementation("io.grpc:grpc-api:${grpc}")
        implementation("io.grpc:grpc-stub:${grpc}")
        implementation("io.grpc:grpc-protobuf:${grpc}")
        implementation("io.grpc:grpc-kotlin-stub:${grpcKotlin}")

        // protobuf
        implementation("com.google.protobuf:protobuf-kotlin:${protoc}")

        // gRPC Spring Boot Starter
        implementation("net.devh:grpc-spring-boot-starter:3.1.0.RELEASE")

        testImplementation("org.springframework.boot:spring-boot-starter-test")
        testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
        testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    }

    protobuf {
        protoc {
            artifact = "com.google.protobuf:protoc:${protoc}"
        }

        plugins {
            id("grpc") {
                artifact = "io.grpc:protoc-gen-grpc-java:${grpc}"
            }
            id("grpckt") {
                artifact = "io.grpc:protoc-gen-grpc-kotlin:${grpcKotlin}:jdk8@jar"
            }
        }

        generateProtoTasks {
            ofSourceSet("main").forEach {
                it.plugins {
                    id("grpc")
                    id("grpckt")
                }
                it.builtins {
                    id("kotlin")
                }
            }
        }
    }

    sourceSets {
        main {
            proto {
                srcDir("src/main/proto")
            }
        }
    }

    tasks {
        named<Jar>("jar") {
            enabled = false
        }
    }
}