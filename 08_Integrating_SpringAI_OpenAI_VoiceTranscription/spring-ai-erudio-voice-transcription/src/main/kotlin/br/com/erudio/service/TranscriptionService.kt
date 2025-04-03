package br.com.erudio.service

import org.springframework.ai.audio.transcription.AudioTranscriptionPrompt
import org.springframework.ai.openai.OpenAiAudioTranscriptionModel
import org.springframework.ai.openai.OpenAiAudioTranscriptionOptions
import org.springframework.ai.openai.api.OpenAiAudioApi
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.FileSystemResource
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.File

@Service
class TranscriptionService (
    @Value("\${spring.ai.openai.api-key}") apiKey: String) {

    private val transcriptionModel: OpenAiAudioTranscriptionModel

    init {
        val openAiAudioApi = OpenAiAudioApi.builder()
            .apiKey(apiKey)
            .build()
        transcriptionModel = OpenAiAudioTranscriptionModel(openAiAudioApi)
    }

    fun transcribeAudio(file: MultipartFile): String? {
        val tempFile = File.createTempFile("audio", "mp3")
        file.transferTo(tempFile)

        val options = OpenAiAudioTranscriptionOptions.builder()
            .responseFormat(OpenAiAudioApi.TranscriptResponseFormat.TEXT)
            .language("pt")
            .temperature(0f)
            .build();

        val audioFile = FileSystemResource(tempFile)
        val transcriptionRequest = AudioTranscriptionPrompt(audioFile, options)
        val response = transcriptionModel.call(transcriptionRequest)

        tempFile.delete()

        return response.result.output
    }
}