package com.example.dictionary.screens.dictionary

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.speech.RecognizerIntent
import android.speech.tts.TextToSpeech
import android.view.View
import android.widget.ImageView
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dictionary.R
import com.example.dictionary.adapter.DictionaryAdapter
import com.example.dictionary.databinding.ScreenDictionaryBinding
import com.example.dictionary.source.entity.DictionaryEntity
import java.util.Locale

class DictionaryScreen : Fragment(R.layout.screen_dictionary), DictionaryContract.View,
    TextToSpeech.OnInitListener {
    private lateinit var binding: ScreenDictionaryBinding
    private val adapter by lazy { DictionaryAdapter() }
    private val layoutManager by lazy { LinearLayoutManager(requireContext()) }
    private var query: String? = null
    private val presenter = DictionaryPresenter(this)
    private val handler by lazy { Handler(Looper.getMainLooper()) }
    private lateinit var textOut: TextToSpeech
    private val REQUEST = 1;


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = ScreenDictionaryBinding.bind(view)
        textOut = TextToSpeech(requireContext(), this)

        binding.recycler.adapter = adapter
        binding.recycler.layoutManager = layoutManager

        adapter.setChangeFavouriteListener { _, data ->
            presenter.updateData(data)
            if (query == null)
                presenter.allWords()
            else
                presenter.allWordsByQuery(query!!)
        }
        presenter.allWords()

        adapter.setListenWordListener { data ->
            textOut(data.english.toString())
//            textOut("are you happy to win this celebration")
        }

        binding.btnMic.setOnClickListener {
            startSpeechToText()
        }

        binding.btnSelected.setOnClickListener {
            presenter.clickSelectedWords()
        }

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                this@DictionaryScreen.query = query
                handler.removeCallbacksAndMessages(null)
                if (query == null) presenter.allWords()
                else presenter.allWordsByQuery(query)
                return true
            }

            override fun onQueryTextChange(query: String?): Boolean {
                this@DictionaryScreen.query = query
                if (query == null) {
                    presenter.allWords()
                    layoutManager.scrollToPosition(0)
                } else {
                    handler.removeCallbacksAndMessages(null)
                    handler.postDelayed({
                        presenter.allWordsByQuery(query)
                    }, 500)
                }
                return true
            }
        })
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

    override fun moveSelectedWords() {
        findNavController().navigate(DictionaryScreenDirections.actionDictionaryScreenToSelectedWordsScreen())
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