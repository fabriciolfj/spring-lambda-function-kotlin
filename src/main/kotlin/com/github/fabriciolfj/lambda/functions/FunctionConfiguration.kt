package com.github.fabriciolfj.lambda.functions

import com.amazonaws.services.lambda.runtime.Context
import com.fasterxml.jackson.databind.JsonNode
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.messaging.Message
import org.springframework.web.reactive.function.client.WebClient

@Configuration
class FunctionConfiguration(private val quotesWebClient: WebClient) {

    @Bean
    fun fetchRandomQuotes() : (Message<Any>) -> Unit {
        return {
            val awsContext = it.headers["aws-context"] as Context
            val logger = awsContext.logger

            logger.log("Buscando uma quota qualquer")

            val response = quotesWebClient.get()
                    .uri("/qod?language={language}", "en")
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToMono(JsonNode::class.java)
                    .block()


            val quote = response?.get("contents")?.get("quotes")?.get(0)?.get("quote")
            val author = response?.get("contents")?.get("quotes")?.get(0)?.get("author")

            logger.log("quote  $quote")
            logger.log("author $author")
        }
    }
}