package com.example.crypt_droid.utility

fun getCoinName(symbol: String): String {
    return when (symbol) {
        // The Big Players
        "BTCUSDT" -> "Bitcoin"
        "ETHUSDT" -> "Ethereum"
        "BNBUSDT" -> "Binance Coin"
        "SOLUSDT" -> "Solana"
        "XRPUSDT" -> "Ripple"

        // Popular Altcoins
        "ADAUSDT" -> "Cardano"
        "DOGEUSDT" -> "Dogecoin"
        "AVAXUSDT" -> "Avalanche"
        "TRXUSDT" -> "Tron"
        "DOTUSDT" -> "Polkadot"

        // DeFi & Infrastructure
        "LINKUSDT" -> "Chainlink"
        "LTCUSDT" -> "Litecoin"
        "BCHUSDT" -> "Bitcoin Cash"
        "UNIUSDT" -> "Uniswap"

        // High Volatility / Trending
        "ATOMUSDT" -> "Cosmos"
        "ETCUSDT" -> "Ethereum Classic"
        "FILUSDT" -> "Filecoin"
        "NEARUSDT" -> "Near Protocol"
        "ARBUSDT" -> "Arbitrum"

        // Meme & AI
        "PEPEUSDT" -> "Pepe"
        "SHIBUSDT" -> "Shiba Inu"
        "GRTUSDT" -> "The Graph"
        "OPUSDT" -> "Optimism"

        else -> "Crypto Asset" // Fallback
    }
}