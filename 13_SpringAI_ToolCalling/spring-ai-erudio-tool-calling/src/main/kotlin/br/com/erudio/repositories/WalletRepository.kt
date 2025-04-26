package br.com.erudio.repositories

import br.com.erudio.entities.Share
import org.springframework.data.jpa.repository.JpaRepository

interface WalletRepository : JpaRepository<Share, Long>
