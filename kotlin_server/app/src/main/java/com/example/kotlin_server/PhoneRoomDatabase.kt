package com.example.kotlin_server

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.CoroutineScope

@Database(entities = arrayOf(Phone::class), version = 1, exportSchema = false)
public abstract  class PhoneRoomDatabase: RoomDatabase() {

    abstract fun phoneDao(): PhoneDao

    companion object{
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: PhoneRoomDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): PhoneRoomDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PhoneRoomDatabase::class.java,
                    "phone_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }

    }
}