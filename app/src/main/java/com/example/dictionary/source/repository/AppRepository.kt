package com.example.dictionary.source.repository

import android.database.Cursor
import androidx.lifecycle.LiveData
import com.example.dictionary.source.entity.DictionaryEntity


interface AppRepository {
    fun getCursor(): Cursor
    fun getSelectedCursor () : Cursor
    fun changeFavById(id: Int, fav: Int)
    fun getWordById(id: Int): LiveData<DictionaryEntity>
    fun update(dictionaryEntity: DictionaryEntity) : Int
    fun getWordsByQuery ( iChanged : Boolean, query : String) : Cursor
}