package br.com.erudio.service

import org.springframework.ai.image.ImagePrompt
import org.springframework.ai.image.ImageResponse
import org.springframework.ai.openai.OpenAiImageModel
import org.springframework.ai.openai.OpenAiImageOptions
import org.springframework.stereotype.Service

@Service
class ImageService (private val imageModel: OpenAiImageModel) {

    fun generateImage(prompt: String,
          quality: String?,
          n: Int?,
          height: Int?,
          width: Int?
        ): ImageResponse? {

        val response: ImageResponse? = imageModel.call(
            ImagePrompt(prompt,
                OpenAiImageOptions.builder()
                    .model("janus-pro")
                    .quality(quality)
                    .N(n)
                    .height(height)
                    .width(width).build()
            )
        )
        return response;
    }
}