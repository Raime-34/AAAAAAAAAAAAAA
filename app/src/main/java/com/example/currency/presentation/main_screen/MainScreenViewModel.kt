package com.example.currency.presentation.main_screen

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currency.domain.use_cases.CurrencyUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import okhttp3.Dispatcher
import java.security.CodeSource
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val currencyUseCases: CurrencyUseCases
): ViewModel(){

    private val _state = mutableStateOf(MainScreenState())
    val state: State<MainScreenState> = _state

    var currencyJob: Job? = null

    init {
        getCurrencyList("RUB")
    }

    fun onEvent(event: MainScreenEvent){
        when(event){
            is MainScreenEvent.ScreenUpdate -> {
                getCurrencyList(event.state.currentSource)
            }
            is MainScreenEvent.SourceUpdate -> {
                _state.value = state.value.copy( currentSource = event.newSource )
                getCurrencyList(event.newSource)
            }
        }
    }

    private fun getCurrencyList(source: String){
        currencyJob?.cancel()
        currencyJob = viewModelScope.launch(Dispatchers.IO) {
            try {
                currencyUseCases.getCurrencyList(source).onEach {
                    _state.value = state.value.copy(
                        currencyList = it
                    )
                }
                    .collect()
            }
            catch (e: Exception){
                e.message?.let { Log.e("E", it) }
            }
        }
    }
}