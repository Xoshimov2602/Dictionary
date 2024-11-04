package com.example.dictionary.screens.dictionary

import com.example.dictionary.source.AppDatabase
import com.example.dictionary.source.entity.DictionaryEntity

class DictionaryModel : DictionaryContract.Model {
    private val dictionaryDao by lazy { AppDatabase.instance.dictionaryDao() }

    override fun updateData(data: DictionaryEntity) {
        dictionaryDao.updateData(data)
    }

    override fun takeAllWords(): List<DictionaryEntity> {
        return dictionaryDao.allWords()
    }

    override fun takeWordsByQuery(query: String): List<DictionaryEntity> {
        return dictionaryDao.allWordsByQuery(query)
    }
}