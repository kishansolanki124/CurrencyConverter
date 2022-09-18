package com.app.currencyconverter.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.currencyconverter.databinding.CurrencyListItemBinding
import com.app.currencyconverter.pojo.LatestCurrencyRatesModel

class ProductCategoryAdapter(
    private val itemClickWeb: (LatestCurrencyRatesModel.CurrencyAndTypeList) -> Unit
) :
    RecyclerView.Adapter<ProductCategoryAdapter.HomeOffersViewHolder>() {

    private var list: ArrayList<LatestCurrencyRatesModel.CurrencyAndTypeList> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeOffersViewHolder {
        val binding =
            CurrencyListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return HomeOffersViewHolder(binding, itemClickWeb)
    }

    override fun onBindViewHolder(holder: HomeOffersViewHolder, position: Int) {
        holder.bindForecast(list[position])
    }

    fun setItem(list: ArrayList<LatestCurrencyRatesModel.CurrencyAndTypeList>) {
        val currentSize = this.list.size
        this.list.addAll(list)
        notifyItemRangeInserted(currentSize, this.list.size)
        println("set Item called: ${this.list.size}")

    }

    fun reset() {
        val currentSize = this.list.size
        this.list.clear()
        notifyItemRangeRemoved(0, currentSize)
    }

    override fun getItemCount(): Int = list.size

    class HomeOffersViewHolder(
        private val binding: CurrencyListItemBinding,
        private val itemClickCall: (LatestCurrencyRatesModel.CurrencyAndTypeList) -> Unit,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bindForecast(
            newsPortal: LatestCurrencyRatesModel.CurrencyAndTypeList
        ) {
            with(newsPortal) {
                binding.tvCurrencyAmount.text = String.format("%.2f", newsPortal.currencyAmount)
                binding.tvCurrencyType.text = newsPortal.currencyType

                binding.root.setOnClickListener {
                    itemClickCall(this)
                }
            }
        }
    }
}