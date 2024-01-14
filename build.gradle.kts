import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "3.0.4"
    id("io.spring.dependency-management") version "1.1.0"
    id("java")
    id("idea")
    id("eclipse")
    id("jacoco")
    id("org.sonarqube") version "4.4.1.3373"

    kotlin("jvm") version "1.7.22"
    kotlin("plugin.spring") version "1.7.22"
    kotlin("plugin.jpa") version "1.7.22"
}

group = "ar.edu.unq"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
    mavenCentral()
    mavenLocal()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    //implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    runtimeOnly("com.h2database:h2")
    //runtimeOnly("com.mysql:mysql-connector-j")
    //runtimeOnly("mysql:mysql-connector-java")
    implementation("mysql:mysql-connector-java:8.0.30")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    //testImplementation("org.springframework.security:spring-security-test")
    implementation("org.springframework.boot:spring-boot-starter-mail:3.0.5")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "17"
    }
}

sonar {
    properties {
        property("sonar.projectKey", "agile-tutor_agile-tutor-backend")
        property("sonar.organization", "agile-tutor-1")
        property("sonar.host.url", "https://sonarcloud.io")
    }
}

tasks.jacocoTestReport {
    dependsOn(tasks.test) // tests are required to run before generating the report
}

jacoco {
    toolVersion = "0.8.9"
    reportsDirectory.set(layout.buildDirectory.dir("customJacocoReportDir"))
}

tasks.withType<JacocoReport> {
    reports {
        xml.required.set(true)
        csv.required.set(true)
        html.required.set(true)
    }
}

tasks.withType<Test> {
    useJUnitPlatform() // Note: automatically generated when creating project
    finalizedBy(tasks.jacocoTestReport)
}