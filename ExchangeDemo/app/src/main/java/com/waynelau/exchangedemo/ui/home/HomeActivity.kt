package com.waynelau.exchangedemo.ui.home

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.waynelau.exchangedemo.R
import com.waynelau.exchangedemo.data.model.EnableSortButtonEvent
import com.waynelau.exchangedemo.data.model.SortingEvent
import com.waynelau.exchangedemo.databinding.ActivityMainBinding
import com.waynelau.exchangedemo.ui.exchange.CurrencyListFragment
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

class HomeActivity : AppCompatActivity() {
    private var _isAsc = true
    private val currencyListFragment = CurrencyListFragment()
    private lateinit var binding: ActivityMainBinding

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_main
        )

        initController()
        enableSortButton(false)
    }

    private fun initController() {
        binding.btnLoad.setOnClickListener {
            addFragment(currencyListFragment)
            _isAsc = false
            enableSortButton(true)
        }

        binding.btnSort.setOnClickListener {
            // handle multi threading operation
            enableSortButton(false)
            EventBus.getDefault().post(SortingEvent(_isAsc))
            _isAsc = !_isAsc
        }
    }

    private fun addFragment(f: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fl_container, f)
            commit()
        }
    }

    private fun enableSortButton(isEnable: Boolean) {
        binding.btnSort.isEnabled = isEnable
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }

    @Subscribe
    fun onEvent(event: EnableSortButtonEvent) {
        enableSortButton(true)
    }
}