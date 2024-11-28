package com.example.dictionary.source.repository

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.dictionary.source.dao.DictionaryDao
import com.example.dictionary.source.entity.DictionaryEntity

@Database (entities = [DictionaryEntity::class], version = 1 )
abstract class AppDatabase : RoomDatabase() {
    abstract fun dictionaryDao() : DictionaryDao

    companion object {
        lateinit var instance : AppDatabase
            private set

        fun init (context: Context) {
            if (!(Companion::instance.isInitialized)) {
                instance = Room.databaseBuilder(context, AppDatabase::class.java, "Dictionary.db")
                    .createFromAsset("dictionary.db")
                    .allowMainThreadQueries()
                    .build()
            }
        }
    }
}