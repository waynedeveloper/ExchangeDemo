package com.waynelau.exchangedemo.ui.exchange

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.waynelau.exchangedemo.data.model.CurrencyInfo
import com.waynelau.exchangedemo.data.model.EnableSortButtonEvent
import com.waynelau.exchangedemo.data.source.repository.CurrencyRepository
import kotlinx.coroutines.launch
import org.greenrobot.eventbus.EventBus


class CurrencyListViewModel(private val repo: CurrencyRepository): ViewModel() {
    var rvList = MutableLiveData<List<CurrencyInfo>>()

    fun onLoadList() {
        viewModelScope.launch {
            rvList.value = repo.getCurrencyList(true)
        }
    }

    fun onSortList(isAsc: Boolean) {
        viewModelScope.launch {
            rvList.value = repo.getCurrencyList(isAsc)

            // notify HomeActivity to enable button
            EventBus.getDefault().post(EnableSortButtonEvent())

        }
    }


    fun showCurrencyListByKeyword(keyword: String? = "") {
        viewModelScope.launch {
            rvList.value = repo.getCurrencyListByKeyword(keyword)
        }
    }
}