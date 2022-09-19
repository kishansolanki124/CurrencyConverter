package com.app.currencyconverter.view

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.app.currencyconverter.R
import com.app.currencyconverter.apputils.MySpinnerItemSelectionListener
import com.app.currencyconverter.apputils.showSnackBar
import com.app.currencyconverter.databinding.ActivityMainBinding
import com.app.currencyconverter.db.CurrencyEntity
import com.app.currencyconverter.service.CurrencyRatesWorker
import com.app.currencyconverter.view.adapter.CurrencyGridAdapter
import com.app.currencyconverter.viewmodel.CurrencyViewModel

class MainActivity : AppCompatActivity() {

    //todo change lateinit to lazy wherever possible
    private lateinit var binding: ActivityMainBinding
    private lateinit var currencyGridAdapter: CurrencyGridAdapter
    private var currencyType = ""
    private lateinit var layoutManager: LinearLayoutManager
    private var currencyAndTypeList = ArrayList<CurrencyEntity>()
    private lateinit var categoryViewModel: CurrencyViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //todo work here, remove this
        //startService(Intent(this, CurrencyAPIService::class.java))
        val uploadWorkRequest = OneTimeWorkRequestBuilder<CurrencyRatesWorker>().build()
        val workManager = WorkManager.getInstance(applicationContext)
        workManager.enqueue(uploadWorkRequest)

        initRecyclerView()
        initViewModel()

        binding.btConvert.setOnClickListener {
            if (binding.etCurrencyInput.text.toString().isNotEmpty() &&
                binding.etCurrencyInput.text.toString().toDouble() > 0.0
                && currencyType.isNotEmpty()
            ) {
                var amount = 0.0
                for (item in currencyAndTypeList) {
                    if (item.currencyType == currencyType) {
                        amount = item.currencyAmount
                        break
                    }
                }
                if (amount > 0.0) {
                    val convertedUSD =
                        (binding.etCurrencyInput.text.toString().trim().toDouble() / amount)
                    for (item in currencyAndTypeList) {
                        item.currencyAmount = convertedUSD * item.currencyAmount
                    }
                    currencyGridAdapter.notifyItemRangeChanged(0, currencyAndTypeList.size)
                } else {
                    showSnackBar("Currency Not Found")
                }
            } else {
                showSnackBar("enter valid fields")
            }
        }
    }

    private fun initViewModel() {
        categoryViewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        )[CurrencyViewModel::class.java]

        categoryViewModel.currencyList().observe(this) { currencyList ->
            currencyAndTypeList.clear()
            currencyAndTypeList.addAll(currencyList)

            currencyGridAdapter.reset()
            currencyGridAdapter.setItems(currencyAndTypeList)

            setupSpinner()
        }
    }

    private fun initRecyclerView() {
        layoutManager = GridLayoutManager(this, 3)
        binding.rvCurrency.layoutManager = layoutManager

        currencyGridAdapter = CurrencyGridAdapter()
        binding.rvCurrency.adapter = currencyGridAdapter
    }

    private fun setupSpinner() {
        //todo work here, update this code
        val currencyTypeList: ArrayList<String> = ArrayList()
        for (item in currencyAndTypeList) {
            currencyTypeList.add(item.currencyType)
        }
        val adapter: ArrayAdapter<String> = ArrayAdapter(
            binding.spCurrencyType.context,
            R.layout.spinner_display_item,
            currencyTypeList
        )

        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item)

        binding.spCurrencyType.adapter = adapter

        binding.spCurrencyType.onItemSelectedListener =
            object : MySpinnerItemSelectionListener() {
                override fun onUserItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    dropdownPosition: Int,
                    id: Long
                ) {
                    currencyType = currencyAndTypeList[dropdownPosition].currencyType
                }
            }
    }
}