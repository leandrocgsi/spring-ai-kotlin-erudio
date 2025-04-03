package br.com.erudio.controller

import br.com.erudio.service.TranscriptionService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.multipart.MultipartFile

@Controller
@RequestMapping("ai")
class TranscriptionController(val service: TranscriptionService) {

    @PostMapping("transcribe")
    fun transcribeAudio(@RequestParam("file") file: MultipartFile) : ResponseEntity<String>{
        try {
            val transcription = service.transcribeAudio(file)
            return ResponseEntity.ok<String>(transcription)
        } catch (e: Exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error processing the audio file: " + e.message)
        }
    }
}