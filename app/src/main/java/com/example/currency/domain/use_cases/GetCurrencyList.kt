package com.example.currency.domain.use_cases

import com.example.currency.data.CurrencyClient
import com.example.currency.data.mergeCodes
import com.example.currency.presentation.main_screen.MainScreenItemState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import retrofit2.await

class GetCurrencyList{

    operator fun invoke(source: String): Flow<List<MainScreenItemState>>{

        val response: Map<String, Double>

        runBlocking(
            Dispatchers.IO
        ) {
            response = CurrencyClient.getResponse(source)
        }

        return flowOf(
            response.map {
                MainScreenItemState(
                    code = it.key,
                    value = it.value
                )
            }
        )
    }

}