package com.example.dictionary.presentation.screens.selected

import android.database.Cursor
import androidx.lifecycle.LiveData
import com.example.dictionary.source.entity.DictionaryEntity

interface SelectedViewModel {
    val wordCursorLiveData: LiveData<Cursor>
    val openDetailsLiveData: LiveData<DictionaryEntity>
    val textOut : LiveData<String>


    fun updateItem(data: DictionaryEntity)
    fun getAllSelectedWords()
    fun textOut (text : String)
    fun openDetails(data: DictionaryEntity)
}