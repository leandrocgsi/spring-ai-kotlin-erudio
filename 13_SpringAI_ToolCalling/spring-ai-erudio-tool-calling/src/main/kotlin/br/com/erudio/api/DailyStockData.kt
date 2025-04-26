package br.com.erudio.api

import com.fasterxml.jackson.annotation.JsonProperty

data class DailyStockData(
    @JsonProperty("datetime")
    val datetime: String,

    @JsonProperty("open")
    val open: String,

    @JsonProperty("high")
    val high: String,

    @JsonProperty("low")
    val low: String,

    @JsonProperty("close")
    val close: String,

    @JsonProperty("volume")
    val volume: String?  // Torne o campo volume opcional
)