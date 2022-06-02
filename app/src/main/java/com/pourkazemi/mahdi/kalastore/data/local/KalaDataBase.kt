package com.pourkazemi.mahdi.kalastore.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.pourkazemi.mahdi.kalastore.data.model.Kala

@Database(entities = [Kala::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class KalaDataBase:RoomDatabase() {
        abstract fun kalaDao():KalaDao
    }