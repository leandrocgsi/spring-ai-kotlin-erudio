package br.com.erudio.config

import br.com.erudio.service.CourseService
import org.springframework.ai.support.ToolCallbacks
import org.springframework.ai.tool.ToolCallback
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ToolConfig {

    @Bean
    fun erudioTools(service: CourseService): List<ToolCallback> {
        return ToolCallbacks.from(service).toList()
    }
}