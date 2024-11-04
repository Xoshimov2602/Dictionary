package com.example.dictionary.screens.dictionary

import com.example.dictionary.source.entity.DictionaryEntity

interface DictionaryContract {

    interface Model {
        fun updateData(data: DictionaryEntity)
        fun takeAllWords(): List<DictionaryEntity>
        fun takeWordsByQuery(query: String): List<DictionaryEntity>
    }

    interface View {
        fun submitList(list: List<DictionaryEntity>)
        fun textOut(text: String)
        fun startSpeechToText ()
        fun moveSelectedWords()
    }

    interface Presenter {
        fun allWords()
        fun allWordsByQuery(query: String)
        fun updateData(data: DictionaryEntity)
        fun textOut(text: String)
        fun convertSpeechToText ()
        fun clickSelectedWords ()
    }
}