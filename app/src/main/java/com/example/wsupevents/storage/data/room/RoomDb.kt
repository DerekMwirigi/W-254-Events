package com.example.wsupevents.storage.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.wsupevents.models.auth.User
import com.example.wsupevents.utils.Converters

@Database(entities = arrayOf(User::class), version = 2, exportSchema = false)
@TypeConverters(Converters::class)
abstract class RoomDb : RoomDatabase() {
    companion object {
        private lateinit var INSTANCE: RoomDb
        fun getDatabase(context: Context): RoomDb? {
            synchronized(RoomDb::class.java) {
                INSTANCE = Room.databaseBuilder(context.applicationContext,
                    RoomDb::class.java, "events_database")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build()
            }
            return INSTANCE
        }
    }
    abstract fun getSesssionDao(): SessionDao
}
