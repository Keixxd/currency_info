package com.example.currencyinfo.ui.activities

import android.content.Context
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.example.currencyinfo.R
import com.example.currencyinfo.databinding.ActivityMainBinding
import com.example.currencyinfo.domain.model.apientity.Rates
import com.example.currencyinfo.domain.model.roomentity.DbRates
import com.example.currencyinfo.ui.fragments.FavoriteFragment
import com.example.currencyinfo.ui.fragments.PopularFragment
import com.example.currencyinfo.ui.sort.ListSort
import com.example.currencyinfo.ui.sort.SortTypes
import com.example.currencyinfo.ui.viewmodels.MainActivityViewmodel
import com.example.currencyinfo.utils.ViewModelWrapper
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import java.io.File

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainActivityViewmodel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ListSort.key = SortTypes.AZ_NAME
        setupBottomNavigation()
        fetchRates()
        manageFetchedData()
    }

    /**
     * Manages the fetched data:
     *
     * If response's timestamp differs local, database will be updated;
     *
     * If not, simply using local database without changes;
     *
     * If internet connection is presented nor local database, throws an error message;
     *
     * If database is presented, but internet connection isn't, uses local base.
     */

    private fun manageFetchedData() {
        lifecycleScope.launchWhenCreated {
            viewModel.rates.collect {
                when (it) {
                    is ViewModelWrapper.Success<*> -> {
                        val result = it.result as Rates
                        if (checkTimestamps(result)) {
                            viewModel.getDatabaseRates()
                        } else {
                            updateDatabase(result)
                        }
                    }
                    is ViewModelWrapper.Error -> {
                        if (!isDatabaseExist()) {
                            viewModel.getDatabaseRates()
                        } else {
                            showFetchError(it.error)
                        }
                    }
                }
            }
        }
    }

    private fun showFetchError(message: String?) {
        Snackbar.make(binding.root, "$message", Snackbar.LENGTH_INDEFINITE)
            .setAction("Retry") {
                fetchRates()
            }
            .show()
    }

    private fun isDatabaseExist() =  File(ContextCompat.getDataDir(this), "databases").listFiles().isNullOrEmpty()

    /**
     * Updates database, if exists, or simply populates it, if not.
     */

    private fun updateDatabase(rates: Rates) {
        if(!isDatabaseExist()){
            viewModel.getDatabaseRates().invokeOnCompletion {
                val dbRates = viewModel.db.value
                val ratesList = mutableListOf<DbRates>()
                rates.rates.map { item -> Pair(item.key, item.value) }.forEachIndexed { index, pair ->
                    ratesList.add(
                        DbRates(
                            dbRates[index].id,
                            pair.first,
                            pair.second,
                            dbRates[index].favorite
                        )
                    )
                }
                viewModel.updateDbRates(ratesList).invokeOnCompletion {
                    viewModel.getDatabaseRates()
                }
            }
        }else{
            val ratesList = mutableListOf<DbRates>()
            rates.rates.forEach { rate ->
                ratesList.add(DbRates(0, rate.key, rate.value, false))
            }
            viewModel.updateDbRates(ratesList).invokeOnCompletion {
                viewModel.getDatabaseRates()
            }
        }
        updateTimestamp(rates.timestamp)
    }

    /**
     * Updates local timestamp, which is stored in SharedPreferences
     */

    private fun updateTimestamp(newValue: Long) {
        val pref = getSharedPreferences("timestamp_pref", Context.MODE_PRIVATE)
        pref.edit().putLong("timestamp_value", newValue).commit()
    }

    /**
     * Checks the difference between local and fetched timestamp to decide how to manage database in the future
     */

    private fun checkTimestamps(rates: Rates) : Boolean {
        val pref = getSharedPreferences("timestamp_pref", Context.MODE_PRIVATE)
        val localTimestamp = pref.getLong("timestamp_value", 0)
        return rates.timestamp == localTimestamp
    }

    private fun fetchRates() {
        viewModel.getRates()
    }

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