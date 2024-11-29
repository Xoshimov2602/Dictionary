package com.example.dictionary.presentation.screens.dictionary

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.database.Cursor
import android.speech.RecognizerIntent
import android.speech.tts.TextToSpeech
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dictionary.source.entity.DictionaryEntity
import com.example.dictionary.source.repository.AppRepository
import com.example.dictionary.source.repository.AppRepositoryImpl
import java.util.Locale

class DictionaryViewModelImpl : DictionaryViewModel, ViewModel() {
    private val repository: AppRepository = AppRepositoryImpl.getRepository()
    override val wordCursorLiveData = MutableLiveData<Cursor>()
    override val openDetailsLiveData: MutableLiveData<DictionaryEntity> = MutableLiveData()
    override val textOut = MutableLiveData<String>()
    override val textIn = MutableLiveData<Unit>()

    override fun updateItem(dictionaryEntity: DictionaryEntity)  {
       val updated =  repository.update(dictionaryEntity.copy(isFavourite = dictionaryEntity.isFavourite?.xor(1)))
        wordCursorLiveData.value = repository.getCursor()
        repository.getWordById(updated)
    }

    override fun getAllWords() {
        wordCursorLiveData.value = repository.getCursor()
    }

    override fun openDetails(data : DictionaryEntity) {
        openDetailsLiveData.value = data
    }

    override fun textOut(word: String) {
        textOut.value = word
    }

    override fun convertSpeechToText() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH). apply {
            putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
            )
            putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
            putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak something...")
        }
    }

    override fun getWordsByQuery(query: String) {
        wordCursorLiveData.value = repository.getWordsByQuery(query)
    }


}