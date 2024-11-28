package com.example.dictionary.presentation.screens.details

import android.animation.ObjectAnimator
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.speech.tts.TextToSpeech
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.dictionary.R
import com.example.dictionary.databinding.PopupLayoutBinding
import androidx.lifecycle.Observer
import com.example.dictionary.databinding.ScreenDetailsBinding
import java.util.Locale

class DetailsScreen : Fragment(R.layout.screen_details), TextToSpeech.OnInitListener {
    private val data: DetailsScreenArgs by navArgs()
    private val binding: ScreenDetailsBinding by viewBinding(ScreenDetailsBinding::bind)
    private val viewModel: DetailsViewModelContract by viewModels<DetailsViewModel>();
    private lateinit var textOut: TextToSpeech
    private var isEnglish = true

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        var detector = data.data.isFavourite
        textOut = TextToSpeech(requireContext(), null)

        viewModel.textOut.observe(viewLifecycleOwner) {
            textOut.speak(it, TextToSpeech.QUEUE_FLUSH, null, "")
        }

        binding.apply {
            btnFavourite.setImageResource(if (data.data.isFavourite == 0) R.drawable.ic_unselected else R.drawable.ic_selected)

            textEnglish.text = data.data.english
            textUzbek.text = data.data.uzbek
            transcription.text = data.data.transcript
            countable.text = data.data.countable
            type.text = data.data.type
            copyEnglish.setOnClickListener{
                showPopUp(copyEnglish.rootView, textEnglish.text.toString())
                viewModel.clickCopy(textEnglish.text.toString())
            }

            viewModel.copyLiveData.observe(viewLifecycleOwner, copyObserver)

            languageChanger.setOnClickListener {
                if (!isEnglish) {
                    flagEng.setImageResource(R.drawable.img)
                    flagUzb.setImageResource(R.drawable.img_1)
                    txtEnglish.text = "ENG"
                    txtUzb.text = "UZB"
                    textWord.text = data.data.english

                    textEnglish.text = data.data.english
                    textUzbek.text = data.data.uzbek
                    transcription.text = data.data.transcript
                    countable.text = data.data.countable
                    type.text = data.data.type

                    typeUzb.text = ""
                    countableUzb.text = ""
                    transcriptionUzb.text = ""
                } else {
                    flagEng.setImageResource(R.drawable.img_1)
                    flagUzb.setImageResource(R.drawable.img)
                    txtUzb.text = "ENG"
                    txtEnglish.text = "UZB"
                    textWord.text = data.data.uzbek

                    typeUzb.text = data.data.type
                    countableUzb.text = data.data.countable
                    transcriptionUzb.text = data.data.transcript

                    type.text = ""
                    countable.text = ""
                    transcription.text = ""
                    textEnglish.text = data.data.uzbek
                    textUzbek.text = data.data.english

                }

                val rotateAnimator =
                    ObjectAnimator.ofFloat(
                        binding.changer,
                        "rotation",
                        binding.changer.rotation,
                        binding.changer.rotation + 180f
                    )
                rotateAnimator.duration = 200
                rotateAnimator.start()
                isEnglish = !isEnglish
            }

            copyEnglish.setOnClickListener {
                val clipboard = context?.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                val clip = ClipData.newPlainText("Copied", textEnglish.text)
                clipboard.setPrimaryClip(clip)
            }

            btnSpeaker.setOnClickListener {
                if (isEnglish)
                    viewModel.textToSpeech(textEnglish.text.toString())
                else viewModel.textToSpeech(textUzbek.text.toString())
            }

            btnBack.setOnClickListener {
                findNavController().navigateUp()
            }

            btnFavourite.setOnClickListener {
                viewModel.updateItem(data.data)
                data.data.isFavourite
                btnFavourite.setImageResource(if (detector!! % 2 == 0) R.drawable.ic_unselected else R.drawable.ic_selected)
                detector = detector!! + 1
            }
        }
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            if (isEnglish) {
                val result = textOut.setLanguage(Locale.UK)
                if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                    Toast.makeText(requireContext(), "FAIL", Toast.LENGTH_SHORT).show()
                }
            }
        } else Toast.makeText(requireContext(), "FAIL", Toast.LENGTH_SHORT).show()
    }
    private val copyObserver = Observer <String> {
        viewModel.clickCopy(it)
    }

    fun showPopUp(anchorView: View, message: String) {
        val popupView = LayoutInflater.from(anchorView.context).inflate(R.layout.popup_layout, null)
        val pBinding = PopupLayoutBinding.inflate(layoutInflater, null, false)
        pBinding.popupText.text = message
        val popupWindow = PopupWindow(
            popupView,
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT,
            true
        )

        popupWindow.setBackgroundDrawable(null)
        popupWindow.elevation = 10f

        Handler(Looper.getMainLooper()).postDelayed({
            if (popupWindow.isShowing) {
                popupWindow.dismiss()
            }
        }, 2000)
    }
}