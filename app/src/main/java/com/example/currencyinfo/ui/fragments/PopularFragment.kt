package com.example.currencyinfo.ui.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.currencyinfo.R
import com.example.currencyinfo.databinding.FragmentRatesBinding
import com.example.currencyinfo.domain.model.Rates
import com.example.currencyinfo.ui.RatesListAdapter
import com.example.currencyinfo.ui.components.SortBottomSheet
import com.example.currencyinfo.ui.sort.ListSort
import com.example.currencyinfo.ui.viewmodels.MainActivityViewmodel
import com.example.currencyinfo.utils.ViewModelWrapper

class PopularFragment: Fragment() {

    private val viewModel: MainActivityViewmodel by activityViewModels()
    private lateinit var binding: FragmentRatesBinding
    private val ratesAdapter = RatesListAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

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
        setupRecyclerView()
        updateList()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.toolbar_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.sort -> {
                val sortBottomSheet = SortBottomSheet(ratesAdapter)
                sortBottomSheet.show(requireActivity().supportFragmentManager, "Sort")
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun updateList() {
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.rates.collect { wrapper ->
                when(wrapper){
                    is ViewModelWrapper.Success<*> -> {
                        ratesAdapter.setData((wrapper.result as Rates).rates)
                    }
                    is ViewModelWrapper.Error -> {

                    }
                    is ViewModelWrapper.Loading -> {

                    }
                }
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