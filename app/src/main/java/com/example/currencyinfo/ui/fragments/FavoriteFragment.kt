package com.example.currencyinfo.ui.fragments

import android.os.Bundle
import android.view.*
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.currencyinfo.R
import com.example.currencyinfo.databinding.FragmentRatesBinding
import com.example.currencyinfo.ui.sort.ListSort

class FavoriteFragment : ViewBindingFragment<FragmentRatesBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentRatesBinding
        get() = FragmentRatesBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getDatabaseRates()
        setupRecyclerView()
        binding.refreshLayout.isEnabled = false
        updateFavoriteList()
    }

    private fun updateFavoriteList() {
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.db.collect { list ->
                ratesAdapter.setData(list.filter { rate -> rate.favorite })
                ratesAdapter.sortDataByKey(ListSort.key)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.favorite_menu, menu)
    }

    private fun setupRecyclerView() {
        binding.ratesRv.apply {
            adapter = ratesAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    override fun onStop() {
        super.onStop()
        viewModel.getDatabaseRates()
    }

    override fun fetchRates() {
        viewModel.getRates()
    }
}