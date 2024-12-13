package com.example.dictionary.source.dao

import android.database.Cursor
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.dictionary.source.entity.DictionaryEntity

@Dao
interface DictionaryDao {

    @Query("select * from dictionary")
    fun allWords(): Cursor

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateData(data: DictionaryEntity)

    @Query("SELECT * FROM dictionary WHERE dictionary.english LIKE:text || '%'")
    fun allWordsByQuery(text: String): Cursor

    @Query("select * from dictionary where dictionary.uzbek like:words || '%'")
    fun getWordsByUzbQuery(words:String) : Cursor

    @Query("select * from dictionary where dictionary.is_favourite = 1")
    fun getAllSelected(): List<DictionaryEntity>

    @Query("select * from dictionary where dictionary.is_favourite = 1 like '%' || :text")
    fun getAllSelectedByQuery(text: String): List<DictionaryEntity>

    @Query("UPDATE dictionary SET is_favourite = :fav WHERE id = :id")
    fun updateFav(id: Int, fav: Int) : Int

    @Query("SELECT * FROM dictionary WHERE id = :id")
    fun getWordById(id: Int): LiveData<DictionaryEntity>

    @Query("SELECT * FROM dictionary")
    fun getWordsCursor(): Cursor

    @Query("select * from dictionary where is_favourite = 1")
    fun getSelectedWords () : Cursor

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(data : DictionaryEntity)

}