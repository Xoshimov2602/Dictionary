package com.example.dictionary.source.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Update
import com.example.dictionary.source.entity.DictionaryEntity

@Dao
interface DictionaryDao {

    @Query("select * from dictionary")
    fun allWords(): List<DictionaryEntity>

    @Update
    fun updateData(data: DictionaryEntity)


    @Query("SELECT * FROM dictionary WHERE dictionary.english LIKE '%' || :text ")
    fun allWordsByQuery(text: String): List<DictionaryEntity>

    @Query("select * from dictionary where dictionary.is_favourite = 1")
    fun getAllSelected(): List<DictionaryEntity>

    @Query("select * from dictionary where dictionary.is_favourite = 1 like '%' || :text")
    fun getAllSelectedByQuery(text: String): List<DictionaryEntity>

    @Query("UPDATE  dictionary SET is_favourite = :fav WHERE id = :id")
    fun updateFav(id: Int, fav: Int)

    @Query("SELECT * FROM dictionary WHERE id = :id")
    fun getWordById(id: Int): LiveData<DictionaryEntity>
}