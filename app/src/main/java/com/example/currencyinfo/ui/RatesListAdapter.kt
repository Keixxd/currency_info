package com.example.currencyinfo.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.example.currencyinfo.databinding.RateItemBinding
import com.example.currencyinfo.domain.model.roomentity.DbRates
import com.example.currencyinfo.ui.sort.SortTypes
import com.example.currencyinfo.ui.viewmodels.MainActivityViewmodel
import java.lang.StringBuilder
import java.math.BigDecimal
import java.util.*

class RatesListAdapter(private val viewmodel: MainActivityViewmodel) : RecyclerView.Adapter<RatesListAdapter.ItemViewHolder>(), Filterable {

    private var filteredList = emptyList<DbRates>()
    private var mainList = emptyList<DbRates>()

    inner class ItemViewHolder(private val binding: RateItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            with(binding){
                rateName.text = filteredList[adapterPosition].name
                rateValue.text = BigDecimal(filteredList[adapterPosition].value.toString() ).toPlainString()
                favoriteButton.isChecked = filteredList[adapterPosition].favorite

                favoriteButton.setOnCheckedChangeListener { _, isChecked ->
                    if (isChecked) {
                        viewmodel.updateSingleRate(DbRates(
                            filteredList[adapterPosition].id,
                            filteredList[adapterPosition].name,
                            filteredList[adapterPosition].value,
                            true
                        ))
                    } else {
                        viewmodel.updateSingleRate(DbRates(
                            filteredList[adapterPosition].id,
                            filteredList[adapterPosition].name,
                            filteredList[adapterPosition].value,
                            false
                        ))
                    }

                }
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

    override fun getItemCount() = filteredList.size

    fun setData(list: List<DbRates>){
        mainList = list
        filteredList = mainList
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
        filteredList = filteredList.sortedByDescending { it.name }
        notifyDataSetChanged()
    }

    private fun sortAZ() {
        filteredList = filteredList.sortedBy { it.name }
        notifyDataSetChanged()
    }

    private fun sortAscending(){
        filteredList = filteredList.sortedBy { it.value }
        notifyDataSetChanged()
    }

    private fun sortDescending(){
        filteredList = filteredList.sortedByDescending { it.value }
        notifyDataSetChanged()
    }

    override fun getFilter(): Filter {
        return object: Filter(){
            override fun performFiltering(key: CharSequence?): FilterResults {
                val searchKey = key.toString()
                filteredList = if(searchKey.isEmpty()){
                    mainList
                }else{
                    val resultList = mutableListOf<DbRates>()
                    for (index in mainList.indices) {
                        val sb = StringBuilder()
                        sb.append(mainList[index].name)
                        if (sb.toString().lowercase(Locale.ROOT).contains(searchKey.lowercase(Locale.ROOT))) {
                            resultList.add(mainList[index])
                        }
                    }
                    resultList
                }
                val filterResults = FilterResults()
                filterResults.values = filteredList
                return filterResults
            }

            override fun publishResults(key: CharSequence?, results: FilterResults?) {
                filteredList = results?.values as List<DbRates>
                notifyDataSetChanged()
            }
        }
    }
}