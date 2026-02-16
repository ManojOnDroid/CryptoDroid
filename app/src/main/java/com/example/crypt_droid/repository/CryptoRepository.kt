package com.example.crypt_droid.repository

import com.example.crypt_droid.service.CryptoSocketService
import com.example.crypt_droid.service.SocketEvent
import com.example.crypt_droid.states.ConnectionState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.retryWhen
import java.io.IOException

class CryptoRepository {
    private val service = CryptoSocketService()

    fun observeTicker(symbols: List<String>): Flow<SocketEvent> {
        return service.observeTicker(symbols)
            .retryWhen { cause, attempt ->
                if (cause is IOException || cause is Exception) {
                    val delayTime = if (attempt < 3) 2000L else 5000L
                    emit(SocketEvent.ConnectionChange(ConnectionState.DISCONNECTED))
                    delay(delayTime)
                    true
                } else {
                    false
                }
            }
    }
}