package com.example.dictionary.presentation.screens.dictionary

import android.annotation.SuppressLint
import android.content.Intent
import android.database.Cursor
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.speech.RecognizerIntent
import android.speech.tts.TextToSpeech
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.dictionary.R
import com.example.dictionary.databinding.PopupLayoutBinding
import com.example.dictionary.databinding.ScreenDictionaryBinding
import com.example.dictionary.presentation.adapter.DictionaryAdapter
import com.example.dictionary.source.entity.DictionaryEntity
import com.example.dictionary.source.repository.AppRepository
import com.example.dictionary.source.repository.AppRepositoryImpl
import java.util.Locale

class DictionaryScreen : Fragment(R.layout.screen_dictionary), TextToSpeech.OnInitListener {
    private val binding: ScreenDictionaryBinding by viewBinding(ScreenDictionaryBinding::bind)
    private val adapter: DictionaryAdapter by lazy { DictionaryAdapter() }
    private val vm: DictionaryViewModel by viewModels<DictionaryViewModelImpl>()
    private lateinit var textOut: TextToSpeech
    private val request = 1
    private var query: String? = null
    //my last variant was to take instance of repository here, because I had less time
    private val repository: AppRepository = AppRepositoryImpl.getRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vm.openDetailsLiveData.observe(this, openDetailObserver)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.recycler.adapter = adapter

        if (binding.recycler.layoutManager == null) {
            binding.recycler.layoutManager = LinearLayoutManager(requireContext())
        }

        binding.searchView.queryHint = "Search"

        textOut = TextToSpeech(requireContext(), this)

        vm.textOut.observe(viewLifecycleOwner) { word ->
            textOut.speak(word, TextToSpeech.QUEUE_FLUSH, null, "")
        }
        vm.wordCursorLiveData.observe(viewLifecycleOwner, wordCursorObserver)
        vm.getAllWords()

        adapter.setOnClickListener { vm.openDetails(it) }
        adapter.setOnFavClickListener {
            vm.updateItem(it)
        }


        binding.btnMic.setOnClickListener {
            startSpeechToText()
        }

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query.isNullOrEmpty()) {
                    this@DictionaryScreen.query = ""
                    vm.getAllWords()
                } else {
                    this@DictionaryScreen.query = query
                    adapter.updateQuery(query)
                    vm.getWordsByQuery(query)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText.isNullOrEmpty()) {
                    this@DictionaryScreen.query = ""
                    adapter.updateQuery("")
                    vm.getAllWords()
                } else {
                    this@DictionaryScreen.query = newText
                    adapter.updateQuery(newText)
                    vm.getWordsByQuery(newText)
                }
                return true
            }
        })



        binding.searchView.setOnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                val currentQuery = binding.searchView.query.toString()
                if (currentQuery.isEmpty()) {
                    vm.getAllWords()
                } else {
                    vm.getWordsByQuery(currentQuery)
                }
            }
            false
        }

    }

    private val wordCursorObserver = Observer<Cursor> { newCursor ->
        adapter.setCursor(newCursor)
    }

    private val openDetailObserver = Observer<DictionaryEntity> {
        findNavController().navigate(
            DictionaryScreenDirections.actionDictionaryScreen2ToDetailsScreen(
                it
            )
        )
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = textOut.setLanguage(Locale.UK)
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Toast.makeText(requireContext(), "FAIL", Toast.LENGTH_SHORT).show()
            }
        } else Toast.makeText(requireContext(), "FAIL", Toast.LENGTH_SHORT).show()

    }

    private fun startSpeechToText() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
            )
            putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
            putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak something...")
        }
        try {
            startActivityForResult(intent, request)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == request && resultCode == AppCompatActivity.RESULT_OK) {
            val result = data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
            if (!result.isNullOrEmpty()) {
                binding.searchView.setQuery(result[0], false)
            }
        }
    }
}