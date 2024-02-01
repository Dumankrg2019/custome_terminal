package lz.dev.cuctome_terminal.presentation

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import lz.dev.cuctome_terminal.data.Bar

@Composable
fun Terminal(bars: List<Bar>) {

    Canvas(modifier = Modifier
        .fillMaxSize()
        .background(Color.Black)
    ) {
        //получаем максимальную цену из списка
        val max = bars.maxOf {it.high}

        //получаем минимальную  цену из списка
        val min = bars.minOf {it.low}

        //ширина одного bar
        val barWidth = size.width / bars.size

        //количесвто пикселей на 1 пункт(нужно чтобы график занимал весь экран -от мин до макс значения)
        val pxPerPoint = size.height / (max - min)

        bars.forEachIndexed {index, bar->
            val offsetX = index * barWidth
            drawLine(
                color = Color.White,
                start = Offset(offsetX, size.height - ((bar.low - min) * pxPerPoint)),
                end = Offset(offsetX, size.height - ((bar.high - min)* pxPerPoint)),
                strokeWidth = 1f
            )
        }
    }
}