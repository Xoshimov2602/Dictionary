package com.example.dictionary.presentation.screens.details

import androidx.lifecycle.LiveData
import com.example.dictionary.source.entity.DictionaryEntity

interface DetailsViewModelContract {
    val textOut : LiveData<String>

    fun textToSpeech (word : String)
    fun updateItem (data : DictionaryEntity)
}