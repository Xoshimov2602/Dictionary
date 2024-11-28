package com.example.dictionary.presentation.screens.details

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dictionary.source.entity.DictionaryEntity
import com.example.dictionary.source.repository.AppRepository
import com.example.dictionary.source.repository.AppRepositoryImpl

class DetailsViewModel : DetailsViewModelContract, ViewModel() {
    private val repository : AppRepository = AppRepositoryImpl.getRepository()

    override val textOut = MutableLiveData<String>()

    override fun textToSpeech(word: String) {
        textOut.value = word
    }

    override fun updateItem(data: DictionaryEntity) {
        repository.update(data.copy(isFavourite = data.isFavourite?.xor(1)))
    }
}