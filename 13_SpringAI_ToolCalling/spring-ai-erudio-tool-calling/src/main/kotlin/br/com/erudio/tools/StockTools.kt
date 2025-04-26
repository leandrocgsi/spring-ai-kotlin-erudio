package br.com.erudio.tools

import br.com.erudio.api.DailyShareQuote
import br.com.erudio.api.DailyStockData
import br.com.erudio.api.StockData
import br.com.erudio.api.StockResponse
import br.com.erudio.settings.APISettings
import org.slf4j.LoggerFactory
import org.springframework.ai.tool.annotation.Tool
import org.springframework.ai.tool.annotation.ToolParam
import org.springframework.beans.factory.annotation.Value
import org.springframework.web.client.RestTemplate

class StockTools(private val restTemplate: RestTemplate) {

    private val logger = LoggerFactory.getLogger(this::class.java)

    @Value("\${TWELVE_DATA_API_KEY:none}")
    private val apiKey: String? = null

    @Tool(description = "Latest stock prices")
    fun getLatestStockPrices(@ToolParam(description = "Name of company") company: String): StockResponse {
        logger.info("Get stock prices for: {}", company)
        val data = restTemplate.getForObject<StockData?>(
            "${APISettings.TWELVE_DATA_BASE_URL}?symbol=$company&interval=1day&outputsize=1&apikey=$apiKey",
            StockData::class.java
        )
        val latestData = data!!.values.get(0)
        logger.info("Get stock prices ({}) -> {}", company, latestData.close)
        return StockResponse(latestData.close.toFloat())
    }

    @Tool(description = "Historical daily stock prices")
    fun getHistoricalStockPrices(
        @ToolParam(description = "Search period in days") days: Int,
        @ToolParam(description = "Name of company") company: String
    ): MutableList<DailyShareQuote?> {
        logger.info("Get historical stock prices: {} for {} days", company, days)
        val data = restTemplate.getForObject<StockData?>(
            "${APISettings.TWELVE_DATA_BASE_URL}?symbol=$company&interval=1day&outputsize=$days&apikey=$apiKey",
            StockData::class.java
        )
        return data!!.values.stream()
            .map<DailyShareQuote?> { d: DailyStockData? -> DailyShareQuote(company, d!!.close.toFloat(), d.datetime) }
            .toList()
    }
}