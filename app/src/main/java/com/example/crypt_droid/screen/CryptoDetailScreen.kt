package com.example.crypt_droid.screen

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.asAndroidPath
import androidx.compose.ui.graphics.asComposePath
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.crypt_droid.viewmodel.CryptoViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CryptoDetailScreen(
    symbol: String,
    viewModel: CryptoViewModel,
    onBack: () -> Unit
) {
    val state by viewModel.watchlistState.collectAsStateWithLifecycle()
    val item = state.tickerMap[symbol]
    val history = state.historyMap[symbol] ?: emptyList()
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(symbol) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "$${item?.price ?: "Loading..."}",
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold,
                color = item?.color ?: Color.Black
            )

            Spacer(modifier = Modifier.height(32.dp))

            Text("Live Market Trend", fontSize = 14.sp, color = Color.Gray)

            if (history.size > 2) {
                LineChart(
                    data = history,
                    color = item?.color ?: Color.Green,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                        .padding(16.dp)
                )
            } else {
                Box(
                    modifier = Modifier.fillMaxWidth().height(300.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Gathering market data...", color = Color.Gray)
                }
            }
        }
    }
}

@Composable
fun LineChart(
    data: List<Float>,
    color: Color,
    modifier: Modifier = Modifier
) {
    if (data.isEmpty()) return

    Canvas(modifier = modifier) {
        val width = size.width
        val height = size.height

        val maxVal = data.maxOrNull() ?: 0f
        val minVal = data.minOrNull() ?: 0f
        val range = if (maxVal - minVal == 0f) 1f else maxVal - minVal

        val path = Path()

        fun getY(price: Float): Float {
            val ratio = (price - minVal) / range
            return height - (ratio * height)
        }

        val spacing = width / (data.size - 1).coerceAtLeast(1)

        path.moveTo(0f, getY(data.first()))

        for (i in 0 until data.size - 1) {
            val x1 = i * spacing
            val y1 = getY(data[i])
            val x2 = (i + 1) * spacing
            val y2 = getY(data[i+1])

            val controlPoint1 = Offset(x1 + (spacing / 2), y1)
            val controlPoint2 = Offset(x1 + (spacing / 2), y2)

            path.cubicTo(
                controlPoint1.x, controlPoint1.y,
                controlPoint2.x, controlPoint2.y,
                x2, y2
            )
        }

        drawPath(
            path = path,
            color = color,
            style = Stroke(
                width = 2.dp.toPx(),
                cap = androidx.compose.ui.graphics.StrokeCap.Round
            )
        )

        val fillPath = android.graphics.Path(path.asAndroidPath()).asComposePath().apply {
            lineTo(width, height)
            lineTo(0f, height)
            close()
        }

        drawPath(
            path = fillPath,
            brush = androidx.compose.ui.graphics.Brush.verticalGradient(
                colors = listOf(color.copy(alpha = 0.3f), Color.Transparent),
                endY = height
            )
        )
    }
}