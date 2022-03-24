package com.example.currencyinfo.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import com.example.currencyinfo.R
import com.example.currencyinfo.databinding.ActivityMainBinding
import com.example.currencyinfo.ui.components.SortBottomSheet
import com.example.currencyinfo.ui.fragments.FavoriteFragment
import com.example.currencyinfo.ui.fragments.PopularFragment
import com.example.currencyinfo.ui.sort.SortTypes
import com.example.currencyinfo.ui.viewmodels.MainActivityViewmodel
import com.example.currencyinfo.ui.viewmodels.MainActivityViewmodel_Factory
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainActivityViewmodel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupBottomNavigation()
        fetchRates()
    }

    private fun fetchRates() {
        viewModel.getRates()
    }

    /*override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.sort -> {
                val sortBottomSheet = SortBottomSheet()
                sortBottomSheet.show(supportFragmentManager, "Sort")
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }*/

    private fun setupBottomNavigation() {
        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            when(item.itemId){
                R.id.popular -> {
                    supportFragmentManager.beginTransaction().replace(R.id.fragment_container, PopularFragment()).commit()
                    true
                }
                R.id.favorites -> {
                    supportFragmentManager.beginTransaction().replace(R.id.fragment_container, FavoriteFragment()).commit()
                    true
                }
                else -> false
            }
        }
    }
}