package br.com.erudio.service

import org.springframework.ai.chat.model.ChatModel
import org.springframework.ai.chat.prompt.PromptTemplate
import org.springframework.stereotype.Service

@Service
class RecipeService (private val chatModel: ChatModel) {

    fun createRecipe(ingredients: String,
                 cuisine: String,
                 dietaryRestrictions: String) : String {

        val template = """
           I want to create a recipe using the following ingredients: ${ingredients}.
           The cuisine type I prefer is ${cuisine}.
           Please consider the following dietary restrictions: ${dietaryRestrictions}.
           Please provide me with a detailed recipe including title, list of ingredients, and cooking instructions
        """.trimIndent()

        val prompt = PromptTemplate(template).create()
        return chatModel.call(prompt).result.output.text
    }
}