package com.example.currencyinfo.domain.model

data class Rates(
    val success: Boolean,
    val timestamp: Long,
    val base: String,
    val date: String,
    val rates: Map<String, Double>
)
