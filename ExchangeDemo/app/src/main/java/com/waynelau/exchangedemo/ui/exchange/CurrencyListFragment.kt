package com.waynelau.exchangedemo.ui.exchange

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.waynelau.exchangedemo.R
import com.waynelau.exchangedemo.data.model.SortingEvent
import com.waynelau.exchangedemo.databinding.FragmentCurrencyListBinding
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.koin.androidx.viewmodel.ext.android.viewModel

class CurrencyListFragment: Fragment(){
    private val currencyViewModel: CurrencyListViewModel by viewModel()
    private lateinit var binding: FragmentCurrencyListBinding

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_currency_list, container, false)
        binding.vm = currencyViewModel
        binding.lifecycleOwner = this.viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        initController()

        // display the list
        currencyViewModel.onLoadList()
    }

    private fun initAdapter() {
        val currencyAdapter = CurrencyListAdapter(currencyViewModel)
        binding.rv.apply{
            layoutManager = LinearLayoutManager(context)
            adapter = currencyAdapter
            addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
        }
    }

    private fun initController() {
        binding.etSearch.addTextChangedListener( object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if(p0.isNullOrEmpty()) {
                    currencyViewModel.onLoadList()
                }else {
                    currencyViewModel.showCurrencyListByKeyword(p0.toString())
                }
            }

            override fun afterTextChanged(p0: Editable?) {}
        })
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }

    @Subscribe
    fun onEvent(event: SortingEvent) {
        currencyViewModel.onSortList(event.isAsc)
    }
}