package com.example.dictionary.screens.selected

import com.example.dictionary.source.entity.DictionaryEntity

interface SelectedWordsContract {

    interface Model{
        fun getAllSelected() : List<DictionaryEntity>
        fun updateData(data: DictionaryEntity)
        fun takeWordsByQuery(query: String): List<DictionaryEntity>
    }

    interface View{
        fun submitList (list : List<DictionaryEntity>)
        fun textOut(text: String)
        fun startSpeechToText ()
    }

    interface Presenter{
        fun allSelected ()
        fun updateData(data: DictionaryEntity)
        fun textOut(text: String)
        fun allWordsByQuery(query: String)
        fun convertSpeechToText ()
    }

}