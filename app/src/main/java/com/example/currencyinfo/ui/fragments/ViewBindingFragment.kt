package com.example.currencyinfo.ui.fragments

import android.os.Bundle
import android.view.*
import android.view.inputmethod.EditorInfo
import androidx.annotation.CallSuper
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.viewbinding.ViewBinding
import com.example.currencyinfo.R
import com.example.currencyinfo.ui.RatesListAdapter
import com.example.currencyinfo.ui.sort.SortBottomSheet
import com.example.currencyinfo.ui.viewmodels.MainActivityViewmodel

/**
 * Superclass to fragments, which displays rates list ([FavoriteFragment] or [PopularFragment])
 *
 * Describes, how to toolbar's menu items, and also setups [RecyclerView].
 *
 *
 */

abstract class ViewBindingFragment<VB : ViewBinding>  : Fragment() {

    lateinit var ratesAdapter: RatesListAdapter
    val viewModel: MainActivityViewmodel by activityViewModels()

    private var _binding: ViewBinding? = null
    abstract val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> VB

    protected val binding: VB
        get() = _binding as VB

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    @CallSuper
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = bindingInflater.invoke(layoutInflater, container, false)
        return requireNotNull(_binding).root
    }

    @CallSuper
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ratesAdapter = RatesListAdapter(viewModel)
    }

    abstract fun fetchRates()

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