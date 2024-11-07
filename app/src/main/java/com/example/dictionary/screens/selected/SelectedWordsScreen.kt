package com.example.dictionary.screens.selected

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.speech.RecognizerIntent
import android.speech.tts.TextToSpeech
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dictionary.R
import com.example.dictionary.adapter.DictionaryAdapterList
import com.example.dictionary.databinding.ScreenSelectedWordsBinding
import com.example.dictionary.source.entity.DictionaryEntity
import java.util.Locale

class SelectedWordsScreen : Fragment(R.layout.screen_selected_words), SelectedWordsContract.View,
    TextToSpeech.OnInitListener {
    private lateinit var binding: ScreenSelectedWordsBinding
    private val adapter by lazy { DictionaryAdapterList() }
    private val presenter = SelectedWordsPresenter(this)
    private val query: String? = null
    private val handler by lazy { Handler(Looper.getMainLooper()) }
    private val layoutManager by lazy { LinearLayoutManager(requireContext()) }
    private lateinit var textOut: TextToSpeech
    private val REQUEST = 1;

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = ScreenSelectedWordsBinding.bind(view)

        binding.recycler.adapter = adapter
        binding.recycler.layoutManager = layoutManager

        adapter.setChangeFavouriteListener { _, data ->
            presenter.updateData(data)
            if (query == null)
                presenter.allSelected()
            else
                presenter.allWordsByQuery(query)
        }
    }

    override fun submitList(list: List<DictionaryEntity>) {
        adapter.submitList(list)
    }

    override fun textOut(text: String) {
        textOut.speak(text, TextToSpeech.QUEUE_FLUSH, null, "")
    }

    override fun startSpeechToText() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH). apply {
            putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
            )
            putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
            putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak something...")
        }

        try {
            startActivityForResult(intent, REQUEST)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = textOut.setLanguage(Locale.UK)
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Toast.makeText(requireContext(), "FAIL", Toast.LENGTH_SHORT).show()
            }
        } else Toast.makeText(requireContext(), "FAIL", Toast.LENGTH_SHORT).show()
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST && resultCode == AppCompatActivity.RESULT_OK) {
            val result = data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
            if (!result.isNullOrEmpty()) {
                binding.searchView.setQuery(result[0], false)
            }
        }
    }
}