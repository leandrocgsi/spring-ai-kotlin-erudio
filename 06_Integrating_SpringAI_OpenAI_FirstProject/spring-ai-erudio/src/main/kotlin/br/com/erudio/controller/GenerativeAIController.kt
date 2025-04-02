package br.com.erudio.controller

import br.com.erudio.service.ChatService
import br.com.erudio.service.ImageService
import br.com.erudio.service.RecipeService
import org.springframework.ai.image.ImageGeneration
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class GenerativeAIController (
        private val chatService: ChatService,
        private val recipeService: RecipeService,
        private val imageService: ImageService
    ) {

    @GetMapping("ask-ai")
    fun askAi(prompt: String?): String? {
        return chatService.getResponse(prompt);
    }

    @GetMapping("ask-ai-options")
    fun askAiWithOptions(prompt: String?): String? {
        return chatService.getResponseWithOptions(prompt);
    }

    @GetMapping("recipe-creator")
    fun generateRecipe(
        @RequestParam ingredients: String,
        @RequestParam(defaultValue = "any") cuisine: String,
        @RequestParam(defaultValue = "none") dietaryRestrictions: String): String? {

        if (ingredients.isNullOrBlank()) {
            throw IllegalArgumentException("The 'ingredients' parameter is required and cannot be empty!")
        }

        return recipeService.createRecipe(ingredients, cuisine, dietaryRestrictions);
    }

    @GetMapping("generate-image")
    fun generateImage(
            @RequestParam prompt: String,
            @RequestParam(defaultValue = "hd") quality: String?,
            @RequestParam(defaultValue = "1") n: Int?,
            @RequestParam(defaultValue = "1024") height: Int?,
            @RequestParam(defaultValue = "1024") width: Int?
    ): MutableList<String?> {
        val response = imageService.generateImage(prompt, quality, n, height, width)!!
        val imageUrls = response.results.stream()
            .map {result: ImageGeneration -> result.output.url}
            .toList()
        return imageUrls;
    }
}