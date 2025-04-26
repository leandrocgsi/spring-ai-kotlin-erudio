package br.com.erudio.service

import org.springframework.ai.image.Image
import org.springframework.ai.image.ImageGeneration
import org.springframework.ai.image.ImagePrompt
import org.springframework.ai.image.ImageResponse
import org.springframework.stereotype.Service


@Service
class ImageService (/*private val imageModel: OpenAiImageModel*/) {

    fun generateImage(prompt: String,
          quality: String?,
          n: Int?,
          height: Int?,
          width: Int?
        ): ImageResponse? {

        val imagePrompt = ImagePrompt(prompt) // Mantendo padrão parecido

        val imageGenerations: MutableList<ImageGeneration?> = ArrayList<ImageGeneration?>()

        for (i in 0..<n!!) {
            val url = "https://placehold.co/" + width + "x" + height + "?text=Mocked+Image"
            val b64Json: String? = null

            val image: Image = Image(url, b64Json)
            val imageGeneration = ImageGeneration(image)
            imageGenerations.add(imageGeneration)
        }

        val imageResponse = ImageResponse(imageGenerations)
        return imageResponse

    }
}