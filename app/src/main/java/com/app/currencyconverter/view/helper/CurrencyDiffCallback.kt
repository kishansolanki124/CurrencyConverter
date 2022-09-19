package com.app.currencyconverter.view.helper

import androidx.annotation.Nullable
import androidx.recyclerview.widget.DiffUtil
import com.app.currencyconverter.db.CurrencyEntity

class CurrencyDiffCallback(
    private val oldList: List<CurrencyEntity>,
    private val newList: List<CurrencyEntity>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].currencyAmount == newList[newItemPosition].currencyAmount
    }

    override fun areContentsTheSame(oldPosition: Int, newPosition: Int): Boolean {
        val oldItem = oldList[oldPosition]
        val newItem = newList[newPosition]
        return oldItem.currencyType == newItem.currencyType && oldItem.currencyAmount == newItem.currencyAmount
    }

    @Nullable
    override fun getChangePayload(oldPosition: Int, newPosition: Int): Any? {
        return super.getChangePayload(oldPosition, newPosition)
    }
}