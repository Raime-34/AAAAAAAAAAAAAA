package com.example.currency.presentation.main_screen

import com.patrykandpatrick.vico.core.entry.FloatEntry

data class MainScreenState(
    val currencyList: List<MainScreenItemState> = emptyList(),
    val currentSource: String = "RUB"
)

class MainScreenItemState(
    val code: String,
    val value: Double,
    val values: MutableList<FloatEntry> = mutableListOf(FloatEntry(11f, value.toFloat())),
){
    init {
        values.addAll((0 until 10).map { FloatEntry(it.toFloat(), ((-1..1).random() * 1 / value + value).toFloat()) })
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if(javaClass != other?.javaClass) return false

        other as MainScreenItemState

        if(code == other.code)
            return true

        return false
    }
}