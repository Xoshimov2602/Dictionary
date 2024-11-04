package com.example.dictionary.screens.selected

import com.example.dictionary.source.AppDatabase
import com.example.dictionary.source.entity.DictionaryEntity

class SelectedWordsModel : SelectedWordsContract.Model {
    private val dictionaryDao by lazy { AppDatabase.instance.dictionaryDao() }
    override fun getAllSelected(): List<DictionaryEntity> {
        return dictionaryDao.getAllSelected()
    }

    override fun updateData(data: DictionaryEntity) {
        dictionaryDao.updateData(data)
    }

    override fun takeWordsByQuery(query: String): List<DictionaryEntity> {
       return dictionaryDao.getAllSelectedByQuery(query)
    }
}