package com.myapp.jetsnack.ui.utils

import java.math.BigDecimal
import java.text.NumberFormat

/**
 * Helper to format a price as a currency string.
 */
fun formatPrice(price: Long): String {
    return NumberFormat.getCurrencyInstance().format(
        BigDecimal(price).movePointLeft(2),
    )
}
