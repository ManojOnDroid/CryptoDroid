package com.example.crypt_droid.data.response

import com.google.gson.annotations.SerializedName

data class CryptoModel(
    @SerializedName("s") val symbol: String?,
    @SerializedName("p") val price: String?,
    @SerializedName("E") val timestamp: Long?
)

data class SocketResponse(
    val stream: String,
    val data: CryptoModel
)