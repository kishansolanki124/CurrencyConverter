package com.app.currencyconverter.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.currencyconverter.databinding.CurrencyListItemBinding
import com.app.currencyconverter.db.CurrencyEntity

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

    fun setItems(list: ArrayList<CurrencyEntity>) {
        val currentSize = this.list.size
        this.list.addAll(list)
        notifyItemRangeInserted(currentSize, this.list.size)
    }

    //todo work here, change from rest to diff utils for showing skills
    fun reset() {
        val currentSize = this.list.size
        this.list.clear()
        notifyItemRangeRemoved(0, currentSize)
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