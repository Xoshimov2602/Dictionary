package com.example.dictionary.presentation.screens.dictionary

import android.database.Cursor
import androidx.lifecycle.LiveData
import com.example.dictionary.source.entity.DictionaryEntity

interface DictionaryViewModel {
    val wordCursorLiveData: LiveData<Cursor>
    val openDetailsLiveData: LiveData<DictionaryEntity>
    val textOut: LiveData<String>
    val textIn: LiveData<Unit>

    fun updateItem(isChanged : Boolean, query: String,da: DictionaryEntity)
    fun getAllWords()
    fun openDetails(data: DictionaryEntity)
    fun textOut(word: String)
    fun convertSpeechToText()
    fun getWordsByQuery ( isChanged : Boolean ,query : String)

}