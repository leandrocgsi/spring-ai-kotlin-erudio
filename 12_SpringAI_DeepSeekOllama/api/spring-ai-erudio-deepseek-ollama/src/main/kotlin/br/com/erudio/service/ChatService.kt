package br.com.erudio.service

import org.springframework.ai.chat.model.ChatModel
import org.springframework.ai.chat.prompt.ChatOptions
import org.springframework.ai.chat.prompt.Prompt
import org.springframework.stereotype.Service


@Service
class ChatService (private val chatModel: ChatModel) {

    fun getResponse(prompt: String?): String? {
        return chatModel.call(prompt)
    }

    fun getResponseWithOptions(prompt: String?): String? {
        val response = chatModel.call(
            Prompt(
                prompt,
                ChatOptions.builder()
                    //.model("deepseek-chat")
                    .temperature(0.4)
                    .build()
            )
        )
        return response.result.output.text
    }
}