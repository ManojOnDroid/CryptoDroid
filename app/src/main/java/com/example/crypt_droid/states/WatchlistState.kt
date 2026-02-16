package com.example.crypt_droid.states

data class WatchlistState(
    val connectionState: ConnectionState = ConnectionState.DISCONNECTED,
    val tickerMap: Map<String, CryptoItemState> = emptyMap(),
    val historyMap: Map<String, List<Float>> = emptyMap()
)