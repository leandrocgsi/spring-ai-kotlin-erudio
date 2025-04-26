package br.com.erudio.tools

import br.com.erudio.api.DailyShareQuote
import br.com.erudio.api.DailyStockData
import br.com.erudio.api.StockData
import br.com.erudio.api.StockResponse
import br.com.erudio.settings.APISettings
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.ai.tool.annotation.Tool
import org.springframework.ai.tool.annotation.ToolParam
import org.springframework.beans.factory.annotation.Value
import org.springframework.web.client.RestTemplate

class StockTools(private val restTemplate: RestTemplate) {

    @Value("\${TWELVE_DATA_API_KEY:none}")
    var apiKey: String? = null

    @Tool(description = "Latest stock prices")
    fun getLatestStockPrices(@ToolParam(description = "Name of company") company: String): StockResponse {
        LOG.info("Get stock prices for: {}", company)
        val data = restTemplate.getForObject<StockData?>(
            APISettings.TWELVE_DATA_BASE_URL + "?symbol={0}&interval=1min&outputsize=1&apikey={1}",
            StockData::class.java,
            company,
            apiKey
        )
        val latestData = data!!.values!!.get(0)
        LOG.info("Get stock prices ({}) -> {}", company, latestData!!.close)
        return StockResponse(latestData.close!!.toFloat())
    }

    @Tool(description = "Historical daily stock prices")
    fun getHistoricalStockPrices(
        @ToolParam(description = "Search period in days") days: Int,
        @ToolParam(description = "Name of company") company: String
    ): MutableList<DailyShareQuote?> {
        LOG.info("Get historical stock prices: {} for {} days", company, days)
        val data = restTemplate.getForObject<StockData?>(
            APISettings.TWELVE_DATA_BASE_URL + "?symbol={0}&interval=1day&outputsize={1}&apikey={2}",
            StockData::class.java,
            company,
            days,
            apiKey
        )
        return data!!.values!!.stream()
            .map<DailyShareQuote?> { d: DailyStockData? ->
                DailyShareQuote(
                    company,
                    d!!.close!!.toFloat(),
                    d.datetime
                )
            }
            .toList()
    }

    companion object {
        private val LOG: Logger = LoggerFactory.getLogger(StockTools::class.java)
    }
}
