package com.example.app

import java.io.Closeable
import java.sql.Connection
import java.sql.ResultSet
import java.time.OffsetDateTime
import java.time.ZoneOffset
import java.util.Calendar
import java.util.TimeZone
import java.util.UUID

interface DataRepository : Closeable {
    fun addRecord()
    fun getAllRecords(): List<Record>
    fun deleteAllRecords()
}

data class Record (
    val id: UUID,
    val timestamp: OffsetDateTime
)

class PostgresDataRepository(
    private val connection: Connection = PooledDataSource.dataSource.connection,
): DataRepository {

    override fun addRecord() {
        connection.prepareStatement(
            """
                INSERT INTO example DEFAULT VALUES
            """.trimIndent()
        ).execute()
    }



    override fun getAllRecords(): List<Record> {
        val results = connection.prepareStatement(
            """
                SELECT id, timestamp
                FROM example
            """.trimIndent()
        ).executeQuery()

        return buildList {
            while (results.next()) {
                add(
                    Record(
                        id = results.getUUID("id"),
                        timestamp = results.getOffsetDateTime("timestamp")
                    )
                )
            }
        }
    }

    override fun deleteAllRecords() {
        connection.prepareStatement(
            """
                TRUNCATE example
            """.trimIndent()
        ).execute()
    }

    override fun close() {
        connection.close()
    }
}

fun ResultSet.getOffsetDateTime(columnLabel: String): OffsetDateTime {
    val calendar = Calendar.getInstance().apply {
        timeZone = TimeZone.getTimeZone("UTC")
    }
    val result = this.getTimestamp(columnLabel, calendar)
    return OffsetDateTime.ofInstant(result.toInstant(), ZoneOffset.UTC)
}

fun ResultSet.getUUID(columnLabel: String): UUID {
    val result = this.getString(columnLabel)
    return UUID.fromString(result)
}
