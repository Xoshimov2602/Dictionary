package com.example.dictionary.app

import android.app.Application
import com.example.dictionary.source.AppDatabase

class App : Application  (){
    override fun onCreate() {
        super.onCreate()
        AppDatabase.init(this)
    }
}