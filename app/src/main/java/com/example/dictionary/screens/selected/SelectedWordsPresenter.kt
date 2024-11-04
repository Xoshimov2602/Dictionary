package com.example.dictionary.screens.selected

import com.example.dictionary.source.entity.DictionaryEntity

class SelectedWordsPresenter(private val view: SelectedWordsContract.View) :
    SelectedWordsContract.Presenter {
    private val model = SelectedWordsModel()
    override fun allSelected() {
        view.submitList(model.getAllSelected())
    }

    override fun updateData(data: DictionaryEntity) {
        model.updateData(data)
    }

    override fun textOut(text: String) {
        view.textOut(text)
    }

    override fun allWordsByQuery(query: String) {
        view.submitList(model.getAllSelected())
    }

    override fun convertSpeechToText() {
        view.startSpeechToText()
    }
}