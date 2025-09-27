plugins {
    kotlin("jvm") version "1.9.25"
    kotlin("plugin.spring") version "1.9.25"
    id("org.springframework.boot") version "3.3.5" // latest stable
    id("io.spring.dependency-management") version "1.1.7"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"
description = "ProductsFullStackTest"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17) // or 21 if available
    }
}

repositories {
    mavenCentral()
}

dependencies {
    // --- Core Spring Boot ---
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jdbc")
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter")

    // --- Database ---
    implementation("org.postgresql:postgresql")
    implementation("org.flywaydb:flyway-core")

    // --- UI libraries ---
    implementation("org.webjars:webjars-locator-core")
    implementation("org.webjars.npm:htmx.org:1.9.10")

    // --- Kotlin support ---
    implementation("org.jetbrains.kotlin:kotlin-reflect")

    // --- Testing ---
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")


    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.17.2")
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
