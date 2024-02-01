package lz.dev.cuctome_terminal.presentation

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import lz.dev.cuctome_terminal.ui.theme.Cuctome_terminalTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Cuctome_terminalTheme {
                val viewModel: TerminalVM = viewModel()
                val screenState = viewModel.state.collectAsState()

                when(val currentState = screenState.value) {
                    is TerminalScreenState.Content -> {
                        Log.d("MainActivity", currentState.barList.toString())
                        Terminal(bars = currentState.barList)
                    }

                    is TerminalScreenState.Initial -> {

                    }

                }
            }
        }
    }
}

