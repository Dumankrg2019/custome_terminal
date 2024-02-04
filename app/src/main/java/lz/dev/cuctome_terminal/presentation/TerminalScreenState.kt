package lz.dev.cuctome_terminal.presentation

import lz.dev.cuctome_terminal.data.Bar

sealed class TerminalScreenState {

    object Initial: TerminalScreenState()
    object Loading: TerminalScreenState()

    data class Content(val barList: List<Bar>, val timeFrame: TimeFrame): TerminalScreenState()
}