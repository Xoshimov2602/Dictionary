package com.example.dictionary.app

import android.app.Application
import com.example.dictionary.source.repository.AppDatabase
import com.example.dictionary.source.repository.AppRepository
import com.example.dictionary.source.repository.AppRepositoryImpl

class App : Application  (){
    override fun onCreate() {
        super.onCreate()
        AppDatabase.init(this)
        AppRepositoryImpl.init(AppDatabase.instance.dictionaryDao())
    }
}