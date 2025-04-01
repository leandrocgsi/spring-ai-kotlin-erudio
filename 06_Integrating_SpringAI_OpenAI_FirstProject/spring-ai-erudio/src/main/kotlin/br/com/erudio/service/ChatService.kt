package br.com.erudio.service

import org.springframework.ai.chat.model.ChatModel
import org.springframework.ai.chat.prompt.Prompt
import org.springframework.ai.openai.OpenAiChatOptions
import org.springframework.stereotype.Service


@Service
class ChatService (private val chatModel: ChatModel) {

    fun getResponse(prompt: String?): String? {
        return chatModel.call(prompt)
    }
}