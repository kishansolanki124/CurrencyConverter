package com.app.currencyconverter.view.helper

object CurrencyUtil {

    private val currencyList = listOf("USD", "INR")

    /**
     * The test cases will pass if..
     * ...amount is not null
     * ...amount is > 0
     * ...currency is not empty
     * ...currency is not null
     * ...currency is from the given currency list
     */
    fun validateCurrencySelectionAndInputValue(
        inputAmount: Double?,
        selectedCurrency: String?
    ): Boolean {
        if (inputAmount == null || inputAmount <= 0.0 || selectedCurrency == null || selectedCurrency.isEmpty()) {
            return false
        }

        if (!currencyList.contains(selectedCurrency)) {
            return false
        }

        return true
    }
}