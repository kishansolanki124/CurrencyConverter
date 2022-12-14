package com.app.currencyconverter.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.app.currencyconverter.databinding.CurrencyListItemBinding
import com.app.currencyconverter.db.CurrencyEntity
import com.app.currencyconverter.view.helper.CurrencyDiffCallback

class CurrencyGridAdapter :
    RecyclerView.Adapter<CurrencyGridAdapter.HomeOffersViewHolder>() {

    private var list: ArrayList<CurrencyEntity> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeOffersViewHolder {
        val binding = CurrencyListItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return HomeOffersViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HomeOffersViewHolder, position: Int) {
        holder.bindForecast(list[position])
    }

    fun setItems(newList: ArrayList<CurrencyEntity>) {
        val diffCallback = CurrencyDiffCallback(this.list, newList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.list.clear()
        this.list.addAll(newList)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun getItemCount(): Int = list.size

    class HomeOffersViewHolder(
        private val binding: CurrencyListItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bindForecast(
            currencyEntity: CurrencyEntity
        ) {
            binding.tvCurrencyAmount.text = String.format("%.4f", currencyEntity.currencyAmount)
            binding.tvCurrencyType.text = currencyEntity.currencyType
        }
    }
}