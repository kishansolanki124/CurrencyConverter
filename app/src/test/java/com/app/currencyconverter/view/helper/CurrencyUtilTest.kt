package com.app.currencyconverter.view.helper

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class CurrencyUtilTest {

    @Test
    fun `amount null return false`() {
        val result = CurrencyUtil.validateCurrencySelectionAndInputValue(
            null,
            "USD"
        )
        assertThat(result).isFalse()
    }

    @Test
    fun `currency null return false`() {
        val result = CurrencyUtil.validateCurrencySelectionAndInputValue(
            123.0,
            null
        )
        assertThat(result).isFalse()
    }

    @Test
    fun `empty currency will returns false`() {
        val result = CurrencyUtil.validateCurrencySelectionAndInputValue(
            123.00,
            ""
        )
        assertThat(result).isFalse()
    }

    @Test
    fun `zero amount will returns false`() {
        val result = CurrencyUtil.validateCurrencySelectionAndInputValue(
            0.0,
            "USD"
        )
        assertThat(result).isFalse()
    }

    @Test
    fun `unknown currency will returns false`() {
        val result = CurrencyUtil.validateCurrencySelectionAndInputValue(
            20.0,
            "XYZ"
        )
        assertThat(result).isFalse()
    }

    @Test
    fun `valid currency and amount will return true`() {
        val result = CurrencyUtil.validateCurrencySelectionAndInputValue(
            20.0,
            "INR"
        )
        assertThat(result).isTrue()
    }
}