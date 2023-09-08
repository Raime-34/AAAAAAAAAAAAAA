package com.example.currency.data

val countryFlags = mapOf<String, String>(
    "RUB" to "RU",
    "USD" to "US",
    "EUR" to "FR",
    "GBP" to "GB",
    "BYN" to "BY",
    "AUD" to "AU",
    "UAH" to "UA",
    "JPY" to "JP",
    "KRW" to "KR",
    "KZT" to "KZ",
    "EGP" to "EG",
    "BRL" to "BR"
)

fun mergeCodes(currentCode: String) = countryFlags.keys.filter { it != currentCode }.joinToString(",")