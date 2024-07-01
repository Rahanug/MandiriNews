package com.example.mandirinews

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.mandirinews.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding
    private val navHostController by lazy{
        (supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment).navController
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        binding?.bottomNav?.setupWithNavController(navHostController)

        navHostController.addOnDestinationChangedListener{_, destination, _ ->
            when (destination.id){
                R.id.homeFragment -> binding?.bottomNav?.visibility = View.VISIBLE
                R.id.favoriteFragment -> binding?.bottomNav?.visibility = View.VISIBLE
                else -> binding?.bottomNav?.visibility = View.GONE
            }
        }
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}