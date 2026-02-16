package com.example.crypt_droid.navigation

import kotlinx.serialization.Serializable

sealed class AppDestinations{
    @Serializable
    object CryptoListRoute : AppDestinations()

    @Serializable
    data class CryptoDetailRoute(
        val symbol: String
    ) : AppDestinations()
}
