package com.example.dictionary.presentation.screens

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.dictionary.R
import com.example.dictionary.databinding.ActivityMainBinding
import com.example.dictionary.presentation.screens.dictionary.DictionaryScreen
import com.example.dictionary.presentation.screens.selected.SelectedScreen
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var navigation: BottomNavigationView
    private lateinit var binding: ActivityMainBinding
    private lateinit var dictionaryNavigation: DictionaryScreen
    private lateinit var selectedNavigation: SelectedScreen

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val navController = binding.fragmentContainer.getFragment<NavHostFragment>().navController
        navigation = binding.bottomNav

        dictionaryNavigation = DictionaryScreen()
        selectedNavigation = SelectedScreen()

        navigation.setupWithNavController(navController)



        val bottomNavigationScreens = setOf(
            R.id.detailsScreen
        )

        navController.addOnDestinationChangedListener { _, destinaton, _  ->
            if (bottomNavigationScreens.contains(destinaton.id)) {
                binding.bottomNav.visibility = View.GONE
                binding.materialCardView.visibility = View.GONE
            } else {
                binding.bottomNav.visibility = View.VISIBLE
                binding.materialCardView.visibility = View.VISIBLE
            }
        }

    }
}