package br.com.erudio.services

import br.com.erudio.api.WalletResponse
import br.com.erudio.repositories.WalletRepository
import java.util.function.Supplier

class WalletService(private val walletRepository: WalletRepository) : Supplier<WalletResponse> {

    override fun get(): WalletResponse {
        return WalletResponse(walletRepository.findAll())
    }
}