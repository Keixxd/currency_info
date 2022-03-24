package com.example.currencyinfo.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.currencyinfo.databinding.RateItemBinding
import com.example.currencyinfo.ui.sort.SortTypes
import kotlin.math.nextUp

class RatesListAdapter : RecyclerView.Adapter<RatesListAdapter.ItemViewHolder>() {

    private var adapterList = emptyMap<String, Double>()

    inner class ItemViewHolder(private val binding: RateItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            with(binding){
                rateName.text = adapterList.keys.toList()[adapterPosition]
                rateValue.text = adapterList.values.toList()[adapterPosition].toString()
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = RateItemBinding.inflate(layoutInflater, parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount() = adapterList.size

    fun setData(map: Map<String, Double>){
        adapterList = map
        notifyDataSetChanged()
    }

    fun sortDataByKey(key: SortTypes){
        when(key){
            SortTypes.AZ_NAME -> sortAZ()
            SortTypes.ZA_NAME -> sortZA()
            SortTypes.ASCENDING_VALUE -> sortAscending()
            SortTypes.DESCENDING_VALUE -> sortDescending()
        }
    }

    private fun sortZA() {
        adapterList = adapterList.toSortedMap(reverseOrder())
        notifyDataSetChanged()
    }

    private fun sortAZ() {
        adapterList = adapterList.toSortedMap()
        notifyDataSetChanged()
    }

    private fun sortAscending(){
        adapterList = adapterList.toList().sortedBy { ( _ ,value) -> value }.toMap()
        notifyDataSetChanged()
    }

    private fun sortDescending(){
        adapterList = adapterList.toList().sortedByDescending { (_, value) -> value }.toMap()
        notifyDataSetChanged()
    }
}