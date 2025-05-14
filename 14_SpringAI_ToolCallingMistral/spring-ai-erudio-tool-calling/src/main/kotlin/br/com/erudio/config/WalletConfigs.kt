package br.com.erudio.config

import br.com.erudio.api.StockRequest
import br.com.erudio.api.StockResponse
import br.com.erudio.api.WalletResponse
import br.com.erudio.repositories.WalletRepository
import br.com.erudio.services.StockService
import br.com.erudio.services.WalletService
import br.com.erudio.tools.StockTools
import br.com.erudio.tools.WalletTools
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Description
import org.springframework.web.client.RestTemplate
import java.util.function.Function
import java.util.function.Supplier

@Configuration
class WalletConfigs {

    @Bean
    fun restTemplate(): RestTemplate = RestTemplate()

    @Bean
    @Description("Number of shares for each company in my portfolio")
    fun numberOfShares(walletRepository: WalletRepository): Supplier<WalletResponse> = WalletService(walletRepository)

    @Bean
    @Description("Latest Stock Prices")
    fun latestStockPrices(): Function<StockRequest, StockResponse> = StockService(restTemplate())

    @Bean
    fun walletTools(repository: WalletRepository): WalletTools = WalletTools(repository)

    @Bean
    fun stockTools(): StockTools = StockTools(restTemplate())
}