package com.app.currencyconverter.view

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.app.currencyconverter.R
import com.app.currencyconverter.apputils.MySpinnerItemSelectionListener
import com.app.currencyconverter.apputils.hideKeyboard
import com.app.currencyconverter.apputils.showSnackBar
import com.app.currencyconverter.databinding.ActivityMainBinding
import com.app.currencyconverter.db.CurrencyEntity
import com.app.currencyconverter.service.CurrencyRatesWorker
import com.app.currencyconverter.view.adapter.CurrencyGridAdapter
import com.app.currencyconverter.viewmodel.CurrencyViewModel

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private val layoutManager: GridLayoutManager by lazy {
        GridLayoutManager(this, 3)
    }
    private val currencyViewModel: CurrencyViewModel by lazy {
        ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        )[CurrencyViewModel::class.java]
    }
    private val currencyGridAdapter: CurrencyGridAdapter by lazy {
        CurrencyGridAdapter()
    }
    private var currencyType = ""
    private var currencyAndTypeList: ArrayList<CurrencyEntity> = ArrayList()
    private val currencyTypeList: ArrayList<String> = ArrayList()

    private lateinit var spinnerAdapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)

        initViews()
        grabLatestCurrencyRates()
        initViewModel()
    }

    private fun initViews() {
        initSpinner()
        initRecyclerView()
        setupClickListeners()
    }

    /**
     * Work Manager to grab latest currencies from server and update it to DB,
     * API will be called only if last updated before 30 minutes
     */
    private fun grabLatestCurrencyRates() {
        val currencyRatesWorker = OneTimeWorkRequestBuilder<CurrencyRatesWorker>().build()
        WorkManager.getInstance(applicationContext).enqueue(currencyRatesWorker)
    }

    private fun initRecyclerView() {
        binding.rvCurrency.layoutManager = layoutManager
        binding.rvCurrency.adapter = currencyGridAdapter
    }

    private fun initViewModel() {
        currencyViewModel.currencyList().observe(this) { currencyList ->
            currencyAndTypeList.clear()
            currencyAndTypeList.addAll(currencyList)
            currencyGridAdapter.setItems(currencyAndTypeList)
            updateSpinnerList()
        }
    }

    private fun setupClickListeners() {
        binding.btConvert.setOnClickListener {
            hideKeyboard(this)
            if (areFieldsValid()) {
                updateListWithNewValues()
            }
        }
    }

    /**
     * 1. getting the current amount of user selected drop down currency
     * 2. convert USD value to match with the amount. USD value will be used by other currencies for conversion
     * 3. convert all other currencies according to the new USD value.
     */
    private fun updateListWithNewValues() {
        var currentAmountOfSelectedCurrency = 0.0
        for (item in currencyAndTypeList) {
            if (item.currencyType == currencyType) {
                currentAmountOfSelectedCurrency = item.currencyAmount
                break
            }
        }

        if (currentAmountOfSelectedCurrency > 0.0) {
            val usdValueOfSelectedCurrency =
                (binding.etCurrencyInput.text.toString().trim()
                    .toDouble() / currentAmountOfSelectedCurrency)
            for (item in currencyAndTypeList) {
                item.currencyAmount = usdValueOfSelectedCurrency * item.currencyAmount
            }
            //updating list
            currencyGridAdapter.notifyItemRangeChanged(0, currencyAndTypeList.size)
        }
    }

    private fun areFieldsValid(): Boolean {
        if (TextUtils.isEmpty(binding.etCurrencyInput.text.toString())) {
            showSnackBar(getString(R.string.hint_enter_amount))
            return false
        } else if (binding.etCurrencyInput.text.toString().toDouble() <= 0.0) {
            showSnackBar(getString(R.string.valid_amount))
            return false
        } else if (currencyType.isEmpty() || currencyType == getString(R.string.select_currency)) {
            showSnackBar(getString(R.string.valid_currency_type))
            return false
        }
        return true
    }

    private fun updateSpinnerList() {
        currencyTypeList.clear()
        currencyTypeList.add(getString(R.string.select_currency))
        for (item in currencyAndTypeList) {
            currencyTypeList.add(item.currencyType)
        }
        spinnerAdapter.notifyDataSetChanged()
    }

    private fun initSpinner() {
        spinnerAdapter = ArrayAdapter(
            binding.spCurrencyType.context,
            R.layout.spinner_display_item,
            currencyTypeList
        )
        spinnerAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item)
        binding.spCurrencyType.adapter = spinnerAdapter

        binding.spCurrencyType.onItemSelectedListener =
            object : MySpinnerItemSelectionListener() {
                override fun onUserItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    dropdownPosition: Int,
                    id: Long
                ) {
                    currencyType = currencyTypeList[dropdownPosition]
                }
            }
    }
}