package com.example.currencyinfo.ui.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.currencyinfo.R
import com.example.currencyinfo.databinding.FragmentRatesBinding
import com.example.currencyinfo.ui.RatesListAdapter
import com.example.currencyinfo.ui.sort.ListSort
import com.example.currencyinfo.ui.viewmodels.MainActivityViewmodel

class FavoriteFragment : ViewBindingFragment() {

    private lateinit var binding: FragmentRatesBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRatesBinding.inflate(inflater)
        return binding.root
    }

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
}