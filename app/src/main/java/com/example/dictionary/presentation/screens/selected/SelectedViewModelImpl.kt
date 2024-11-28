package com.example.dictionary.presentation.screens.selected

import android.database.Cursor
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dictionary.source.entity.DictionaryEntity
import com.example.dictionary.source.repository.AppRepository
import com.example.dictionary.source.repository.AppRepositoryImpl

class SelectedViewModelImpl : SelectedViewModel, ViewModel() {
    private val repository by lazy { AppRepositoryImpl.getRepository() }

    override val wordCursorLiveData =  MutableLiveData<Cursor> ()
    override val openDetailsLiveData = MutableLiveData<DictionaryEntity> ()
    override val textOut = MutableLiveData<String>()

    override fun updateItem(data: DictionaryEntity) {
        repository.update(data.copy(isFavourite = data.isFavourite?.xor(1)))
        wordCursorLiveData.value = repository.getSelectedCursor()
    }

    override fun getAllSelectedWords()  {
        wordCursorLiveData.value = repository.getSelectedCursor()
    }

    override fun textOut(text: String) {
        textOut.value = text
    }

    override fun openDetails(data : DictionaryEntity) {
        openDetailsLiveData.value = data
    }
}