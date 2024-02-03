package lz.dev.cuctome_terminal.presentation

import android.os.Parcelable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import kotlinx.parcelize.Parcelize
import lz.dev.cuctome_terminal.data.Bar
import kotlin.math.roundToInt

@Parcelize
data class TerminalState(
    val barList: List<Bar>,
    val visibleBarsCount: Int = 100,
    val terminalWidth: Float = 1f,
    val terminalHeight: Float = 1f,
    val scrolledBy: Float = 0f
): Parcelable {
    //ширина одного bar
    val barWidth: Float
        get() = terminalWidth / visibleBarsCount


    //стэйт для определения bars, которые сейчас на экране
   private val visibleBars: List<Bar>
        get() {
            val startIndex = (scrolledBy / barWidth).roundToInt().coerceAtLeast(0)
            val endIndex = (startIndex + visibleBarsCount).coerceAtMost(barList.size)
            return barList.subList(startIndex, endIndex)
        }

    //получаем максимальную цену из списка
    val max: Float
        get() = visibleBars.maxOf {it.high}

    //получаем минимальную  цену из списка
    val min: Float
        get()= visibleBars.minOf {it.low}

    //количесвто пикселей на 1 пункт(нужно чтобы график занимал весь экран -от мин до макс значения)
    val pxPerPoint: Float
        get() = terminalHeight / (max - min)

}

@Composable
fun rememberTerminalState(bars: List<Bar>): MutableState<TerminalState> {
    return rememberSaveable {
        mutableStateOf(TerminalState(bars))
    }
}