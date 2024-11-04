package com.example.dictionary.screens.dictionary

import com.example.dictionary.source.entity.DictionaryEntity

class DictionaryPresenter(private val view : DictionaryContract.View) : DictionaryContract.Presenter {
    private val model = DictionaryModel()
    override fun updateData(data: DictionaryEntity) {
        model.updateData(data)
    }

    override fun allWords(){
        view.submitList(model.takeAllWords())
    }

    override fun allWordsByQuery(query: String) {
        view.submitList(model.takeWordsByQuery(query))
    }

    override fun textOut(text: String) {
        view.textOut(text)
    }

    override fun convertSpeechToText() {
        view.startSpeechToText()
    }

    override fun clickSelectedWords() {
        view.moveSelectedWords()
    }
}