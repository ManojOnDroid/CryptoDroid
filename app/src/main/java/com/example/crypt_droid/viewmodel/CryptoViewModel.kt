package com.example.crypt_droid.viewmodel

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.crypt_droid.data.constants.mySymbols
import com.example.crypt_droid.repository.CryptoRepository
import com.example.crypt_droid.service.SocketEvent
import com.example.crypt_droid.states.ConnectionState
import com.example.crypt_droid.states.CryptoItemState
import com.example.crypt_droid.states.WatchlistState
import com.example.crypt_droid.utility.formatPrice
import com.example.crypt_droid.utility.getCoinName
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import okhttp3.Dispatcher

class CryptoViewModel : ViewModel() {
    private val repository = CryptoRepository()
    private val _watchlistState = MutableStateFlow(WatchlistState())
    val watchlistState = _watchlistState.asStateFlow()
    private val currentPrices = mutableMapOf<String, Float>()

    init {
        val initialMap = mySymbols.associateWith { symbol ->
            CryptoItemState(
                symbol = symbol,
                name = getCoinName(symbol),
                price = "Loading...",
                color = Color.White
            )
        }
        _watchlistState.value = _watchlistState.value.copy(tickerMap = initialMap)
        connectSocket()
        startChartSampler()
    }

    private fun connectSocket() {
        viewModelScope.launch {
            repository.observeTicker(mySymbols).conflate().flowOn(Dispatchers.Default).collect { event ->
                when (event) {
                    is SocketEvent.ConnectionChange -> {
                        _watchlistState.value = _watchlistState.value.copy(connectionState = event.state)
                    }
                    is SocketEvent.DataReceived -> {
                        val data = event.data
                        val symbol = data.symbol ?: return@collect
                        val newPrice = data.price?.toDoubleOrNull() ?: 0.0
                        currentPrices[symbol] = newPrice.toFloat()
                        updateTickerUI(symbol, newPrice)
                    }
                }
            }
        }
    }

    private fun startChartSampler() {
        viewModelScope.launch {
            while (true) {
                delay(1000)
                val currentHistory = _watchlistState.value.historyMap.toMutableMap()
                mySymbols.forEach { symbol ->
                    val priceSnapshot = currentPrices[symbol] ?: 0f
                    if (priceSnapshot > 0) {
                        val oldList = currentHistory[symbol] ?: emptyList()
                        val newList = (oldList + priceSnapshot).takeLast(300)
                        currentHistory[symbol] = newList
                    }
                }
                _watchlistState.value = _watchlistState.value.copy(historyMap = currentHistory)
            }
        }
    }

    private fun updateTickerUI(symbol: String, newPrice: Double) {
        val currentMap = _watchlistState.value.tickerMap.toMutableMap()
        val oldItem = currentMap[symbol]
        val oldPriceVal = oldItem?.price?.replace(",", "")?.toDoubleOrNull() ?: 0.0

        val color = when {
            newPrice > oldPriceVal -> Color.Green
            newPrice < oldPriceVal -> Color.Red
            else -> oldItem?.color ?: Color.Black
        }
        val formattedPrice = formatPrice(newPrice.toString())
        currentMap[symbol] = CryptoItemState(
            symbol = symbol,
            name = oldItem?.name ?: getCoinName(symbol),
            price = formattedPrice,
            color = color
        )
        _watchlistState.value = _watchlistState.value.copy(
            tickerMap = currentMap,
            connectionState = ConnectionState.CONNECTED
        )
    }
}