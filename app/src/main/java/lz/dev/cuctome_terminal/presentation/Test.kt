package lz.dev.cuctome_terminal.presentation

import android.os.Parcelable
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import kotlinx.parcelize.Parcelize

@Composable
fun Test() {
    var testData by rememberSaveable(saver = TestData.Saver){
        mutableStateOf(TestData(number = 0, text = "Duman chort ebany"))
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .clickable { testData = testData.copy(number = testData.number + 1) },
        contentAlignment = Alignment.Center
    ) {
        Text(text = "count: $testData")
    }


}

data class TestData(val number: Int, val text: String) {

   companion object {

       val Saver: Saver<MutableState<TestData>, Any> = listSaver(
           save =  {
                   val testData = it.value
               listOf(testData.number, testData.text)
           },
           restore = {
                val testData = TestData(
                    number = it[0] as Int,
                    text = it[1] as String
                )
               mutableStateOf(testData)
           }
       )

       /*данный споосб плох тем, что рабоатет только с  одним параметром*/
//       val Saver: Saver<MutableState<TestData>, Int> = Saver(
//           save = {
//                it.value.number
//           },
//           restore = {
//               mutableStateOf(TestData(it))
//           }
//       )

   }

}

/*
Бывают ситуации когда нет возможности use Parcelize, чтобы сохр данные в  bundle
 поэтому необходимо использовать собственный объект saver для данных
* */

