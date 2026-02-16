package com.example.crypt_droid.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.crypt_droid.viewmodel.CryptoViewModel
import com.example.crypt_droid.states.ConnectionState
import com.example.crypt_droid.states.CryptoItemState

@Composable
fun CryptoScreen(
    viewModel: CryptoViewModel,
    onCryptoClick: (String) -> Unit
) {
    val state by viewModel.watchlistState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            ConnectionStatusBar(state.connectionState)
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier.padding(padding)
        ) {
            items(
                state.tickerMap.values.toList(),
                key = { it.symbol }
            ) { item ->
                CryptoRow(
                    item = item,
                    onClick = { onCryptoClick(item.symbol) }
                )
                HorizontalDivider(color = Color.LightGray.copy(alpha = 0.5f))
            }
        }
    }
}

@Composable
fun CryptoRow(
    item: CryptoItemState,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(text = item.symbol, fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Text(text = item.name, style = MaterialTheme.typography.bodySmall, color = Color.Gray)
        }

        Surface(
            color = item.color.copy(alpha = 0.1f),
            shape = MaterialTheme.shapes.small
        ) {
            Text(
                text = "$${item.price}",
                color = item.color,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
            )
        }
    }
}

@Composable
fun ConnectionStatusBar(state: ConnectionState) {
    val (color, text) = when (state) {
        ConnectionState.CONNECTED -> Color(0xFF4CAF50) to "Connected"
        ConnectionState.CONNECTING -> Color(0xFFFFC107) to "Connecting..."
        ConnectionState.DISCONNECTED -> Color(0xFFF44336) to "Disconnected"
        ConnectionState.ERROR -> Color(0xFFB00020) to "Connection Error"
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(color)
            .padding(4.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(text = text, color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Medium)
    }
}