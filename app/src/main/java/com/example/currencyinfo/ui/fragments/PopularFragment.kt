package com.example.currencyinfo.ui.fragments

import android.os.Bundle
import android.view.*
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.currencyinfo.R
import com.example.currencyinfo.databinding.FragmentRatesBinding
import com.example.currencyinfo.ui.sort.ListSort

class PopularFragment: ViewBindingFragment() {

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
        setupRefreshLayout()
        setupRecyclerView()
        updateList()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.popular_menu, menu)
        //searchView = menu.findItem(R.id.search).actionView as SearchView
    }

    private fun setupRefreshLayout() {
        binding.refreshLayout.setOnRefreshListener {
            fetchRates()
            binding.refreshLayout.isRefreshing = false
        }
        binding.refreshLayout.setColorSchemeResources(R.color.purple_700)
    }


    private fun updateList() {
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.db.collect {
                ratesAdapter.setData(it)
                ratesAdapter.sortDataByKey(ListSort.key)
            }
        }
    }

    private fun setupRecyclerView() {
        binding.ratesRv.apply{
            layoutManager = LinearLayoutManager(requireContext())
            adapter = ratesAdapter
        }
    }

}