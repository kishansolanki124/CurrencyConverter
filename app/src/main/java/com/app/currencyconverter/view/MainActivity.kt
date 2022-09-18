package com.app.currencyconverter.view

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.currencyconverter.R
import com.app.currencyconverter.apputils.MySpinnerItemSelectionListener
import com.app.currencyconverter.apputils.showToast
import com.app.currencyconverter.databinding.ActivityMainBinding
import com.app.currencyconverter.pojo.LatestCurrencyRatesModel
import com.app.currencyconverter.view.adapter.ProductCategoryAdapter
import com.app.currencyconverter.viewmodel.ProductViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var govtWorkNewsAdapter: ProductCategoryAdapter
    private var currencyType = ""
    private lateinit var layoutManager: LinearLayoutManager
    private var currencyAndTypeList = ArrayList<LatestCurrencyRatesModel.CurrencyAndTypeList>()
    private lateinit var categoryViewModel: ProductViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initRecyclerView()

        //categoryViewModel = ViewModelProvider(this)[ProductViewModel::class.java]
        categoryViewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        )[ProductViewModel::class.java]

//        categoryViewModel.currencyRates().observe(this) {
//            //handleResponse(it)
//        }

        categoryViewModel.getUpdatedDataFromDB().observe(this) { currencyList ->
            currencyAndTypeList.clear()
            currencyList.forEach {
                currencyAndTypeList.add(
                    LatestCurrencyRatesModel.CurrencyAndTypeList
                        (it.currencyAmount, it.currencyType)
                )
            }

            govtWorkNewsAdapter.reset()
            govtWorkNewsAdapter.setItem(currencyAndTypeList)
            setupSpinner()
        }

//        //get latest currencies
//        categoryViewModel.latest()

        binding.btConvert.setOnClickListener {
            if (binding.etCurrencyInput.text.toString().isNotEmpty() &&
                binding.etCurrencyInput.text.toString().toDouble() > 0.0
                && currencyType.isNotEmpty()
//                binding.etCurrencyType.text.toString().isNotEmpty() &&
//                binding.etCurrencyType.text.toString().trim().length == 3
            ) {
                //val currencyType = binding.etCurrencyType.text.toString()
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
                    govtWorkNewsAdapter.notifyItemRangeChanged(0, currencyAndTypeList.size)
                } else {
                    showToast("Currency Not Found")
                }
            } else {
                showToast("enter valid fields")
            }
        }
    }

//    private fun handleResponse(latestCurrencyRatesModel: LatestCurrencyRatesModel?) {
//        if (null != latestCurrencyRatesModel) {
//            val jsonObject: JsonObject =
//                JsonParser().parse(Gson().toJson(latestCurrencyRatesModel.rates)).asJsonObject
//            val keys = jsonObject.keySet()
//            currencyAndTypeList = ArrayList()
//            for (language in keys) {
//                currencyAndTypeList.add(
//                    LatestCurrencyRatesModel.CurrencyAndTypeList(
//                        jsonObject.get(language).toString().toDouble(), language
//                    )
//                )
//            }
//            govtWorkNewsAdapter.reset()
//            govtWorkNewsAdapter.setItem(currencyAndTypeList)
//        }
//    }

    private fun initRecyclerView() {
        layoutManager = GridLayoutManager(this, 3)
        binding.rvCurrency.layoutManager = layoutManager

        govtWorkNewsAdapter = ProductCategoryAdapter {

        }
        binding.rvCurrency.adapter = govtWorkNewsAdapter
    }

    private fun setupSpinner() {
        var currencyTypeList : ArrayList<String> = ArrayList()
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