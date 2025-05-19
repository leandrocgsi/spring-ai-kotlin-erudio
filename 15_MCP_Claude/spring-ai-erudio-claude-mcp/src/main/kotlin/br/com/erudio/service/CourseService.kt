package br.com.erudio.service

import br.com.erudio.data.Course
import jakarta.annotation.PostConstruct
import org.slf4j.LoggerFactory
import org.springframework.ai.tool.annotation.Tool
import org.springframework.stereotype.Service

@Service
class CourseService {

    private val logger = LoggerFactory.getLogger(this::class.java)
    private val courses: MutableList<Course> = mutableListOf()

    @Tool(name = "get_erudio_courses", description = "Get a list of courses from Leandro Costa!")
    fun getCourses(): List<Course> {
        return courses
    }

    @Tool(name = "get_erudio_course", description = "Get a single course from Leandro Costa by title!")
    fun getCourse(title: String): Course?{
        return courses.firstOrNull { it.title == title }
    }

    @PostConstruct
    fun init() {
        courses.addAll(
            listOf(
                Course("Spring Boot 2025: REST API's do 0 à AWS e GCP com Java e Docker", "https://pub.erudio.com.br/kr/blog_rest_spring_java", "NON_FREE"),
                Course("Spring AI com Spring Boot, Ollama, DeepSeek, Open AI e ChatGPT", "https://pub.erudio.com.br/kr/blog_ai_jspring", "NON_FREE"),
                Course("Spring AI com Kotlin, Spring Boot, ChatGPT, Ollama e DeepSeek", "https://pub.erudio.com.br/kr/blog_ai_kspring", "NON_FREE"),
                Course("Inteligência Artificial com .NET AI DeepSeek OpenAI e ChatGPT", "https://pub.erudio.com.br/kr/blog_ai_dotnet", "NON_FREE"),
                Course("Docker do 0 à Maestria: Contêineres Desmistificados com K8s", "https://pub.erudio.com.br/kr/blog_docker", "NON_FREE"),
                Course("Java Unit Testing com Spring Boot 3, TDD, Junit 5 e Mockito", "https://pub.erudio.com.br/kr/blog_tests_java", "NON_FREE"),
                Course("Kotlin para DEVs Java: Aprenda a Linguagem Padrão do Android", "https://pub.erudio.com.br/kr/blog_kotlin", "NON_FREE"),
                Course("REST API's RESTFul do 0 à AWS c. Spring Boot Kotlin e Docker", "https://pub.erudio.com.br/kr/blog_rest_spring_kotlin", "NON_FREE"),
                Course("REST API's RESTFul do 0 à Azure com ASP.NET 8 e 5 e Docker", "https://pub.erudio.com.br/kr/blog_rest_asp_net", "NON_FREE"),

                Course("Inteligência Artificial com Spring AI e Kotlin: Minicurso Gratuito Completo", "https://youtu.be/B4PGut5lrQ4", "FREE"),
                Course("Testes Unitários com JUnit 5: Minicurso Gratuito Completo!!!", "https://youtu.be/F0EFN0LXuQg", "FREE"),
                Course("GIT e GitHub: Minicurso Gratuito Completo!!!", "https://youtu.be/QUHIVPAIbRw", "FREE"),
                Course("Kotlin do Zero: Minicurso Gratuito Completo!!!", "https://youtu.be/1f7-9gLF6AE", "FREE"),
                Course("Upload e Download de Arquivos com Spring Boot e Java: Minicurso Gratuito Completo!!!", "https://youtu.be/_Kx5p_bwbwE", "FREE"),
                Course("REST com Spring Boot 3 e Java 19: Minicurso Gratuito Completo", "https://youtu.be/fQAOaMqu0ho", "FREE"),
                Course("REST com Spring Boot 3 e Kotlin: Minicurso Gratuito Completo!!!", "https://youtu.be/0EipduE1gok", "FREE"),
                Course("Docker e Docker Compose para Iniciantes: Minicurso Gratuito Completo!", "https://youtu.be/dZ0xgSNlecQ", "FREE")
            )
        )
    }
}