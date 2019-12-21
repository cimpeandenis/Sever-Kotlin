package com.example.kotlin_server

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface PhoneDao {
    @Query("SELECT * from phone_table ORDER BY id ASC")
    fun getPhones(): LiveData<List<Phone>>

    @Query("SELECT * from phone_table ORDER BY id ASC")
    suspend fun getAllStatic(): List<Phone>

    @Insert
    suspend fun insert(phone: Phone)

    @Delete
    suspend fun delete(phone: Phone)

    @Update
    suspend fun update(phone: Phone)

    @Query("SELECT COUNT(*) from phone_table")
    suspend fun getCount(): Int

    @Query("DELETE FROM phone_table")
    suspend fun deleteAll()

    @Query("SELECT * FROM phone_table WHERE id < 0")
    suspend fun getDataToSync(): List<Phone>

}