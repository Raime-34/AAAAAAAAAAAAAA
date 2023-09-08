package com.example.currency.presentation.main_screen.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.currency.presentation.main_screen.MainScreenEvent
import com.example.currency.presentation.main_screen.MainScreenItemState
import com.example.currency.presentation.main_screen.MainScreenState
import com.example.currency.presentation.main_screen.MainScreenViewModel

@Composable
fun MainScreen(state: MainScreenState, viewModel: MainScreenViewModel, onItemClicked: (Int) -> Unit) {

    Column {

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(end = 20.dp, top = 20.dp)
        ) {
            Spacer(modifier = Modifier.weight(1f))
            Text(text = "выбранная валюта", fontStyle = FontStyle.Italic, color = Color.Gray, fontSize = 15.sp)
            Spacer(modifier = Modifier.width(5.dp))
            CurrentSourcePicker{
                viewModel.onEvent(MainScreenEvent.SourceUpdate(it))
            }
        }

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(7.dp) ,
            modifier = Modifier
                .padding(20.dp)
                .fillMaxSize()
        ){
            state.currencyList.forEach {
                item {
                    MainScreenItem(
                        code = it.code,
                        value = it.value,
                        values = it.values,
                        onItemClicked = { onItemClicked(viewModel.state.value.currencyList.indexOf(it)) }
                    )
                }
            }
        }
    }
}