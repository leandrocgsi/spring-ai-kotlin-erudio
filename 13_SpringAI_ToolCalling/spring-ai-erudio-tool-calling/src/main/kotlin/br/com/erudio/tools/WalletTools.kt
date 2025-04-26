package br.com.erudio.tools

import br.com.erudio.entities.Share
import br.com.erudio.repositories.WalletRepository
import org.springframework.ai.tool.annotation.Tool

class WalletTools(private val walletRepository: WalletRepository) {

    @Tool(description = "Number of shares for each company in my wallet")
    fun getNumberOfShares(): List<Share> {
        return walletRepository.findAll()
    }
}