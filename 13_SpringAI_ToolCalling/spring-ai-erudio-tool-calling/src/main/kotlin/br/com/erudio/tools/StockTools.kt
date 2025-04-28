package br.com.erudio.tools

import br.com.erudio.api.DailyShareQuote
import br.com.erudio.api.StockRequest
import br.com.erudio.api.StockResponse
import br.com.erudio.api.twelvedata.DailyStockData
import br.com.erudio.api.twelvedata.StockData
import br.com.erudio.settings.APISettings
import org.slf4j.LoggerFactory
import org.springframework.ai.tool.annotation.Tool
import org.springframework.ai.tool.annotation.ToolParam
import org.springframework.beans.factory.annotation.Value
import org.springframework.web.client.RestTemplate

class StockTools(private val restTemplate: RestTemplate) {

    private val logger = LoggerFactory.getLogger(this::class.java);

    @Value("\${TWELVE_DATA_API_KEY:none}")
    lateinit var apiKey: String

    @Tool(description = "Latest stock prices")
    fun getLatestStockPrices(@ToolParam(description = "Name of Company") company: String): StockResponse {
        val data = restTemplate.getForObject(
        "${APISettings.TWELVE_DATA_BASE_URL}?symbol=$company&interval=1day&outputsize=1&apikey=$apiKey",
            StockData::class.java
        )
        val latestData = data?.values?.firstOrNull() ?: throw IllegalArgumentException("No data found for company")
        logger.info("Get stock prices: $company -> ${latestData.close}")
        return StockResponse(latestData.close.toFloat())
    }

    @Tool(description = "Historical daily stock prices")
    fun getHistoricalStockPrices(
            @ToolParam(description = "Search period in days") days: Int,
            @ToolParam(description = "Name of Company") company: String
        ): MutableList<DailyShareQuote?> {

        logger.info("Get historical stock prices: $company for $days days")

        val data = restTemplate.getForObject(
        "${APISettings.TWELVE_DATA_BASE_URL}?symbol=$company&interval=1day&outputsize=${days}&apikey=$apiKey",
            StockData::class.java
        )
        return data!!.values.stream()
            .map { d: DailyStockData? ->
                DailyShareQuote(company, d!!.close.toFloat(), d.datetime)
            }.toList()
    }
}