package com.example.app

import org.flywaydb.core.Flyway

fun main() {
    flywayInit()

    PostgresDataRepository().deleteAllRecords()
    PostgresDataRepository().addRecord()
    val records = PostgresDataRepository().getAllRecords()

    check(records.size == 1)
}

// Execute this function wherever you want Flyway to apply your database migrations.
fun flywayInit() {
    Flyway.configure()
        .dataSource(PooledDataSource.dataSource)
        .load()
        .migrate()
}
