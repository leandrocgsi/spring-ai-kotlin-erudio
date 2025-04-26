package br.com.erudio.controllers

import br.com.erudio.tools.StockTools
import br.com.erudio.tools.WalletTools
import org.springframework.ai.chat.client.ChatClient
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor
import org.springframework.ai.chat.prompt.PromptTemplate
import org.springframework.ai.model.tool.ToolCallingChatOptions
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.Map

@RestController
@RequestMapping("/ai")
class WalletController(
    chatClientBuilder: ChatClient.Builder,
    private val stockTools: StockTools,
    private val walletTools: WalletTools?
) {
    private val chatClient: ChatClient

    init {
        this.chatClient = chatClientBuilder
            .defaultAdvisors(SimpleLoggerAdvisor())
            .build()
    }

    @GetMapping("/wallet")
    fun calculateWalletValue(): String? {
        val template = PromptTemplate(
            """
        What’s the current value in dollars of my wallet based on the latest stock daily prices?
        To improve readability, add tables and line breaks when deemed necessary.
        
        """.trimIndent()
        )

        return this.chatClient.prompt(
            template.create(
                ToolCallingChatOptions.builder()
                    .toolNames("numberOfShares", "latestStockPrices")
                    .build()
            )
        )
            .call()
            .content()
    }

    @GetMapping("/with-tools")
    fun calculateWalletValueWithTools(): String? {
        val template = PromptTemplate(
            """
        What’s the current value in dollars of my wallet based on the latest stock daily prices?
        To improve readability, add tables and line breaks when deemed necessary.
        
        """.trimIndent()
        )

        return this.chatClient.prompt(template.create())
            .tools(stockTools, walletTools)
            .call()
            .content()
    }

    @GetMapping("/highest-day/{days}")
    fun calculateHighestWalletValue(@PathVariable days: Int): String? {
        val template = PromptTemplate(
            """
        On which day during last $days days my wallet had the highest value in dollars based on the historical daily stock prices?
        To improve readability, add tables and line breaks when deemed necessary.
        
        """.trimIndent()
        )

        return this.chatClient.prompt(template.create())
            .tools(stockTools, walletTools)
            .call()
            .content()
    }
}
