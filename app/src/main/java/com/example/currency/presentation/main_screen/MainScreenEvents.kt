package com.example.currency.presentation.main_screen

sealed class MainScreenEvent{
    data class ScreenUpdate(val state: MainScreenState): MainScreenEvent()
    data class SourceUpdate(val newSource: String): MainScreenEvent()
}