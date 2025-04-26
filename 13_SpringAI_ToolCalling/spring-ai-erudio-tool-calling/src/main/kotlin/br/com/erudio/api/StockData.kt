package br.com.erudio.api

import com.fasterxml.jackson.annotation.JsonProperty

data class StockData(

    @JsonProperty("values")
    val values: List<DailyStockData>
)