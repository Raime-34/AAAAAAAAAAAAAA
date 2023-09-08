package com.example.currency.domain.model

class Response1 (
    val success: Boolean,
    val timestamp: Long,
    val source: String,
    val quotes: Map<String, Double>
)

class Response2(
    val meta: Meta,
    val data: Map<String, Data>
)

class Meta(
    val last_updated_at: String
)

class Data(
    val code: String,
    val value: Double
)