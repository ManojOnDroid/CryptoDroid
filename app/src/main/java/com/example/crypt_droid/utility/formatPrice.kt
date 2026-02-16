package com.example.crypt_droid.utility

import java.math.BigDecimal
import java.text.DecimalFormat

fun formatPrice(price: String): String {
    return try {
        val bigDecimal = BigDecimal(price)
        if (bigDecimal < BigDecimal.ONE) {
            bigDecimal.toPlainString()
        } else {
            val df = DecimalFormat("#,##0.00")
            df.format(bigDecimal)
        }
    } catch (e: Exception) {
        price
    }
}