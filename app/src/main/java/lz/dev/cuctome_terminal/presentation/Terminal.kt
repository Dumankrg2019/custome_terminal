package lz.dev.cuctome_terminal.presentation

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.TransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.translate
import lz.dev.cuctome_terminal.data.Bar
import kotlin.math.roundToInt
import kotlin.math.sign

private const val MIN_VISIBLE_BARS_COUNT = 20

@Composable
fun Terminal(bars: List<Bar>) {

    var visibleBarsCount by remember {
        mutableStateOf(100)
    }

    var scrollBy by remember {
        mutableStateOf(0f)
    }

    var terminalWidth by remember {
        mutableStateOf(0f)
    }

    //ширина одного bar
    val barWidth by remember {
       // mutableStateOf(0f)

        //если один стэйт зависит от другого, то can be use
        derivedStateOf {
            terminalWidth / visibleBarsCount
        }
    }

    //стэйт для определения bars, которые сейчас на экране
    val visibleBars by remember {
        derivedStateOf {
            val startIndex = (scrollBy / barWidth).roundToInt().coerceAtLeast(0)
            val endIndex = (startIndex + visibleBarsCount).coerceAtMost(bars.size)
            bars.subList(startIndex, endIndex)
        }
    }

    val transformableState = TransformableState{zoomChange, panChange, _ ->
        visibleBarsCount = (visibleBarsCount/zoomChange).roundToInt()
            .coerceIn(MIN_VISIBLE_BARS_COUNT, bars.size)

        scrollBy = (scrollBy + panChange.x)
            .coerceAtLeast(0f)
            .coerceAtMost(bars.size * barWidth - terminalWidth)
    }
    Canvas(modifier = Modifier
        .fillMaxSize()
        .background(Color.Black)
        .transformable(transformableState)
    ) {
        terminalWidth = size.width

        //получаем максимальную цену из списка
        val max = visibleBars.maxOf {it.high}

        //получаем минимальную  цену из списка
        val min = visibleBars.minOf {it.low}

        //количесвто пикселей на 1 пункт(нужно чтобы график занимал весь экран -от мин до макс значения)
        val pxPerPoint = size.height / (max - min)

        translate(left = scrollBy) {
            bars.forEachIndexed {index, bar->
                //делаем чтобы рисовалось справа на лево
                val offsetX = size.width - index * barWidth
                drawLine(
                    color = Color.White,
                    start = Offset(offsetX, size.height - ((bar.low - min) * pxPerPoint)),
                    end = Offset(offsetX, size.height - ((bar.high - min)* pxPerPoint)),
                    strokeWidth = 1f
                )
                drawLine(
                    color = if(bar.open < bar.close) Color.Green else Color.Red,
                    start = Offset(offsetX, size.height - ((bar.open - min) * pxPerPoint)),
                    end = Offset(offsetX, size.height - ((bar.close - min) * pxPerPoint)),
                    strokeWidth =  barWidth / 2
                )
            }
        }
    }
}