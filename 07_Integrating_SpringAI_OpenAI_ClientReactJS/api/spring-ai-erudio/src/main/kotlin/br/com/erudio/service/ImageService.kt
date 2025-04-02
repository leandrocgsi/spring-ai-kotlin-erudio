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
                    .quality(quality)
                    .N(n)
                    .height(height)
                    .width(width).build()
            )
        )
        return response;
    }

    /**
    fun generateImage(prompt: String): ImageResponse? {

    val response: ImageResponse? = imageModel.call(
    ImagePrompt(prompt,
    OpenAiImageOptions.builder()
    .quality("hd")
    .N(4)
    .height(1024)
    .width(1024).build()
    )

    )
    return response;
    }

    fun generateImage(prompt: String): ImageResponse {
        val imageResponse = imageModel.call(ImagePrompt(prompt))
        return imageResponse;
    }
    */
}