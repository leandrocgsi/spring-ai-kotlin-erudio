package br.com.erudio.api

data class DailyShareQuote(
    val company: String,
    val price: Float,
    val datetime: String
)