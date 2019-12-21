package com.example.kotlin_server

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "phone_table")
class Phone(
    @ColumnInfo(name="phone") val phone: String,
    @ColumnInfo(name="accessory") val accessory: String
) {
    @PrimaryKey(autoGenerate = true) var id: Long = 0

    fun set(id: Long) {
        this.id = id
    }
}

