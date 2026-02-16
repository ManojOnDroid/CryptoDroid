package com.example.crypt_droid.states

import androidx.compose.ui.graphics.Color

data class CryptoItemState(
    val symbol: String,
    val name: String,
    val price: String = "Loading...",
    val color: Color = Color.White
)