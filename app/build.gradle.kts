
plugins {
    id("org.jetbrains.kotlin.jvm") version "2.1.10"
    application
}

group = "com.example.app"
version = "0.1.0"

dependencies {
    implementation("org.flywaydb:flyway-core:9.21.0")
    implementation("com.zaxxer:HikariCP:6.2.1")
    implementation("com.typesafe:config:1.4.2")
    implementation("org.postgresql:postgresql:42.7.1")
}

tasks.withType<JavaExec>().all {
    environment = env.allVariables()
}

repositories {
    mavenCentral()
}

application {
    mainClass.set("com.example.app.AppKt")
}
