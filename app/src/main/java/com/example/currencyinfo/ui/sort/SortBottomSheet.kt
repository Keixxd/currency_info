package com.example.currencyinfo.ui.sort

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.currencyinfo.databinding.FragmentSortBottomSheetBinding
import com.example.currencyinfo.ui.RatesListAdapter
import com.example.currencyinfo.ui.sort.ListSort
import com.example.currencyinfo.ui.sort.SortTypes
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class SortBottomSheet(private val adapter: RatesListAdapter) : BottomSheetDialogFragment(){

    private lateinit var binding: FragmentSortBottomSheetBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSortBottomSheetBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupButtons()
    }

    private fun setupButtons() {
        with(binding){
            sort1.setOnClickListener {
                ListSort.key = SortTypes.AZ_NAME
                adapter.sortDataByKey(ListSort.key)
                closeSheet()
            }
            sort2.setOnClickListener {
                ListSort.key = SortTypes.ZA_NAME
                adapter.sortDataByKey(ListSort.key)
                closeSheet()
            }
            sort3.setOnClickListener {
                ListSort.key = SortTypes.ASCENDING_VALUE
                adapter.sortDataByKey(ListSort.key)
                closeSheet()
            }
            sort4.setOnClickListener {
                ListSort.key = SortTypes.DESCENDING_VALUE
                adapter.sortDataByKey(ListSort.key)
                closeSheet()
            }
        }
    }

    fun closeSheet() = dismiss()
}