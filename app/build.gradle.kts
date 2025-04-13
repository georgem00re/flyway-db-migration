
plugins {
    id("org.flywaydb.flyway") version "10.0.0"
}

dependencies {
    implementation("org.apache.commons:commons-text")
}

application {
    mainClass.set("flyway.db.migration.app.AppKt")
}
