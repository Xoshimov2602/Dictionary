package com.example.dictionary.screens

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.dictionary.R
import com.example.dictionary.databinding.ActivityMainBinding
import com.example.dictionary.screens.dictionary.DictionaryScreen
import com.example.dictionary.screens.selected.SelectedWordsScreen
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var navigation: BottomNavigationView
    private lateinit var binding: ActivityMainBinding
    private lateinit var dictionaryNavigation: DictionaryScreen
    private lateinit var selectedNavigation: SelectedWordsScreen

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navController = binding.fragmentContainer.getFragment<NavHostFragment>().navController
        navigation = binding.bottomNav

        dictionaryNavigation = DictionaryScreen()
        selectedNavigation = SelectedWordsScreen()

        navigation.setupWithNavController(navController)

    }
}