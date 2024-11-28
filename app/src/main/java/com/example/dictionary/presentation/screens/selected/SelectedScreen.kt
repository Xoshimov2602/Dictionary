package com.example.dictionary.presentation.screens.selected

import android.database.Cursor
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.dictionary.R
import com.example.dictionary.presentation.adapter.DictionaryAdapter
import com.example.dictionary.databinding.ScreenSelectedWordsBinding
import com.example.dictionary.source.entity.DictionaryEntity
import java.util.Locale

class SelectedScreen : Fragment(R.layout.screen_selected_words) , TextToSpeech.OnInitListener {
    private val binding : ScreenSelectedWordsBinding by viewBinding(ScreenSelectedWordsBinding::bind)
    private val adapter : DictionaryAdapter by lazy { DictionaryAdapter() }
    private val viewModel : SelectedViewModel by viewModels<SelectedViewModelImpl> ()
    private lateinit var textOut : TextToSpeech

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.openDetailsLiveData.observe(this, openDetailsObserver)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recycler.adapter = adapter
        binding.recycler.layoutManager = LinearLayoutManager(requireContext())
        textOut = TextToSpeech(requireContext(), null)

        viewModel.wordCursorLiveData.observe(viewLifecycleOwner, wordCursorObserver)
        viewModel.getAllSelectedWords()
        viewModel.textOut.observe(viewLifecycleOwner) {
            textOut.speak(it, TextToSpeech.QUEUE_FLUSH, null, "")
        }

        adapter.setOnClickListener { viewModel.openDetails(it) }
        adapter.setOnFavClickListener {
            viewModel.updateItem(it)
        }
        adapter.setOnFavClickListener {
            viewModel.updateItem(it)
        }
    }

    private val openDetailsObserver = Observer<DictionaryEntity> {
        findNavController().navigate(SelectedScreenDirections.actionSelectedWordsScreen2ToDetailsScreen(it))
    }

    private val wordCursorObserver = Observer<Cursor> {
        adapter.setCursor(it)
    }

    override fun onInit(p0: Int) {
        if (p0  == TextToSpeech.SUCCESS){
            val result = textOut.setLanguage(Locale.UK)
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED){
                Toast.makeText(requireContext(), "error", Toast.LENGTH_SHORT).show()
            } else Toast.makeText(requireContext(), "error", Toast.LENGTH_SHORT).show()
        }
    }

}