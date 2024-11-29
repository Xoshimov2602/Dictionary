package com.example.dictionary.source.repository

import android.database.Cursor
import android.util.Log
import androidx.lifecycle.LiveData
import com.example.dictionary.source.dao.DictionaryDao
import com.example.dictionary.source.entity.DictionaryEntity

class AppRepositoryImpl(private val dictionaryDao: DictionaryDao) : AppRepository {


    companion object {
        private lateinit var appRepository: AppRepository

        fun init(dictionaryDao: DictionaryDao) {
            if (!Companion::appRepository.isInitialized) {
                appRepository = AppRepositoryImpl(dictionaryDao)
            }
        }

        fun getRepository(): AppRepository = appRepository

    }

    override fun getCursor(): Cursor = dictionaryDao.getWordsCursor()

    override fun getSelectedCursor(): Cursor = dictionaryDao.getSelectedWords()

    override fun changeFavById(id: Int, fav: Int) {
        dictionaryDao.updateFav(id, fav)
    }

    override fun getWordById(id: Int): LiveData<DictionaryEntity> = dictionaryDao.getWordById(id)

    override fun update(dictionaryEntity: DictionaryEntity) : Int {
        dictionaryDao.insert(dictionaryEntity)
        return dictionaryEntity.id
    }

    override fun getWordsByQuery(query: String): Cursor {
        return dictionaryDao.allWordsByQuery(query)
    }
}