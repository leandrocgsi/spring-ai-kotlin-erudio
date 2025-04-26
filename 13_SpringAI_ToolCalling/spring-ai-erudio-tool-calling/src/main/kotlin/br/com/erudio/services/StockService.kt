package br.com.erudio.services

import br.com.erudio.api.StockData
import br.com.erudio.api.StockRequest
import br.com.erudio.api.StockResponse
import br.com.erudio.settings.APISettings
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.web.client.RestTemplate
import java.util.function.Function

class StockService(
    private val restTemplate: RestTemplate
) : Function<StockRequest, StockResponse> {

    private val logger: Logger = LoggerFactory.getLogger(StockService::class.java)

    @Value("\${TWELVE_DATA_API_KEY:none}")
    lateinit var apiKey: String

    override fun apply(stockRequest: StockRequest): StockResponse {
        val data = restTemplate.getForObject(
            "${APISettings.TWELVE_DATA_BASE_URL}?symbol={0}&interval=1min&outputsize=1&apikey={1}",
            StockData::class.java,
            stockRequest.company,
            apiKey
        )
        val latestData = data?.values?.firstOrNull() ?: throw IllegalArgumentException("No data found for company")
        logger.info("Get stock prices: {} -> {}", stockRequest.company, latestData.close)
        return StockResponse(latestData.close.toFloat())
    }
}
