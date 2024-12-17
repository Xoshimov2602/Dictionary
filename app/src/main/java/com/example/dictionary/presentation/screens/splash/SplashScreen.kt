package com.example.dictionary.presentation.screens.splash

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.dictionary.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

@SuppressLint("CustomSplashScreen")
class SplashScreen : Fragment(R.layout.screen_splash) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        object :CountDownTimer(3000, 1000) {
//            override fun onTick(p0: Long) {
//
//            }
//
//            override fun onFinish() {
//                findNavController().navigate(SplashScreenDirections.actionSplashScreenToDictionaryScreen2())
//            }
//        }.start()

        lifecycleScope.launch {
            delay(2500)
            findNavController().navigate(SplashScreenDirections.actionSplashScreenToDictionaryScreen2())
        }
    }
}
