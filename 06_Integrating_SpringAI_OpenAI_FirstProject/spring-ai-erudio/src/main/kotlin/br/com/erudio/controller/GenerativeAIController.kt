package br.com.erudio.controller

import br.com.erudio.service.ChatService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class GenerativeAIController (private val chatService: ChatService) {

    @GetMapping("ask-ai")
    fun askAi(prompt: String?): String? {
        return chatService.getResponse(prompt);
    }
}