package com.example.crypt_droid.service

import android.util.Log
import com.example.crypt_droid.data.response.CryptoModel
import com.example.crypt_droid.data.response.SocketResponse
import com.example.crypt_droid.states.ConnectionState
import com.google.gson.Gson
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener

class CryptoSocketService {
    private  var webSocket: WebSocket? = null
    private val  client : OkHttpClient by lazy {
        OkHttpClient()
    }
    private val gson = Gson()
    fun observeTicker(symbols: List<String>): Flow<SocketEvent> = callbackFlow {
        val streamParams = symbols.joinToString("/") { "${it.lowercase()}@trade" }
        val url = "wss://stream.binance.com:9443/stream?streams=$streamParams"
        val request = Request.Builder().url(url).build()
        val listener = object : WebSocketListener() {
            override fun onOpen(webSocket: WebSocket, response: Response) {
                trySend(SocketEvent.ConnectionChange(ConnectionState.CONNECTED))
            }

            override fun onMessage(webSocket: WebSocket, text: String) {
                try {
                    val model = gson.fromJson(text, SocketResponse::class.java)
                    trySend(SocketEvent.DataReceived(model.data))
                } catch (e: Exception) {
                    Log.e("CryptoSocket", "Parsing error", e)
                }
            }

            override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
                trySend(SocketEvent.ConnectionChange(ConnectionState.DISCONNECTED))
            }

            override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
                trySend(SocketEvent.ConnectionChange(ConnectionState.ERROR))
            }
        }

        trySend(SocketEvent.ConnectionChange(ConnectionState.CONNECTING))
        webSocket = client.newWebSocket(request, listener)

        awaitClose {
            webSocket?.close(1000, "Closed by App")
        }
    }
}

sealed class SocketEvent {
    data class DataReceived(val data: CryptoModel) : SocketEvent()
    data class ConnectionChange(val state: ConnectionState) : SocketEvent()
}