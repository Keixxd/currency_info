package com.example.currencyinfo.ui.fragments

import android.os.Bundle
import android.view.*
import android.view.inputmethod.EditorInfo
import androidx.annotation.CallSuper
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.currencyinfo.R
import com.example.currencyinfo.ui.RatesListAdapter
import com.example.currencyinfo.ui.sort.SortBottomSheet
import com.example.currencyinfo.ui.viewmodels.MainActivityViewmodel

open class ViewBindingFragment  : Fragment() {

    lateinit var ratesAdapter: RatesListAdapter
    val viewModel: MainActivityViewmodel by activityViewModels()

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    @CallSuper
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ratesAdapter = RatesListAdapter(viewModel)
    }

    open fun fetchRates() {
        viewModel.getRates()
    }

    private fun onSearch(item: MenuItem) {
        val searchView = item.actionView as SearchView
        searchView.setIconifiedByDefault(false)
        searchView.setBackgroundResource(R.drawable.search_bg)
        searchView.queryHint = "Search.."
        searchView.imeOptions = EditorInfo.IME_ACTION_DONE

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.getDatabaseRates()
                ratesAdapter.filter.filter(newText)
                return false
            }

        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.favorite_sort, R.id.popular_sort -> {
                val sortBottomSheet = SortBottomSheet(ratesAdapter)
                sortBottomSheet.show(requireActivity().supportFragmentManager, "Sort")
                true
            }
            R.id.favorite_search, R.id.popular_search -> {
                onSearch(item)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}