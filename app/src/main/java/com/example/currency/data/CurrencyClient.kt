package com.example.currency.data

import com.example.currency.domain.model.Response1
import com.example.currency.domain.model.Response2
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.await
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

object CurrencyClient {

    private val URLs = listOf(
        "https://api.apilayer.com/currency_data/",
        "https://api.currencyapi.com/v3/"
    )

    private val clients: MutableMap<String, Retrofit> = mutableMapOf()
    private val services: MutableList<BaseCurrencyService> = mutableListOf()

    init {
        initClients()
        initServices()
    }

    private fun getClient(baseUrl: String): Retrofit {

        lateinit var client: Retrofit

        client = Retrofit
            .Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return client
    }

    private fun initClients() {
        URLs.forEach { url ->
            clients.put(
                url,
                getClient(url)
            )
        }
    }

    private fun initServices() {
        clients.forEach {
            services.add(
                when (it.key) {
                    URLs[0] -> {
                        it.value.create(CurrencyService1::class.java)
                    }

                    URLs[1] -> {
                        it.value.create(CurrencyService2::class.java)
                    }

                    else -> {
                        throw NotFoundURLException()
                    }
                }
            )
        }
    }

    fun getResponse(source: String): Map<String, Double> {

        val responses: MutableList<Any> = mutableListOf()

        runBlocking(Dispatchers.IO) {
            services.forEach { service ->
                launch(Dispatchers.IO) {
                    when(service){
                        is CurrencyService1 -> {
                            responses.add(service.getCurrencyList(source = source, currencies = mergeCodes(source)).await())
                        }

                        is CurrencyService2 -> {
                            responses.add(service.getCurrencyList(source = source, currencies = mergeCodes(source)).await())
                        }
                    }
                }
            }
        }

        responses.forEach {
            when (it) {
                is Response1 -> {
                    if(it.quotes.isNotEmpty())
                        return it.quotes
                }

                is Response2 -> {
                    if(it.data.isNotEmpty())
                        return it.data.values.toList().associate { it.code to it.value }
                }
            }
        }

        return emptyMap()
    }
}

class NotFoundURLException: Exception()

interface BaseCurrencyService{}

interface CurrencyService1: BaseCurrencyService{

    @GET("live?")
    fun getCurrencyList(
        @Query("base") base: String = "USD", @Query("symbols") symbols: String = "EUR,GBP", @Query("apikey") apikey: String ="o5vCzeGGF5umOmLW92ltaYUS1nnbO3B7", @Query("source") source: String ="RUB", @Query("currencies") currencies: String
    ): Call<Response1>

}

interface CurrencyService2: BaseCurrencyService{

    @GET("latest?")
    fun getCurrencyList(
        @Query("apikey") apikey: String = "cur_live_n7iv9iYkHwjkeILFvTrUA6RUGWGFgJy1QVqmXYEQ", @Query("base_currency") source: String = "RUB", @Query("currencies") currencies: String
    ): Call<Response2>

}