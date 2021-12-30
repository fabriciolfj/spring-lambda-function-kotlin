package com.github.fabriciolfj.lambda.config

import io.netty.channel.ChannelOption
import io.netty.handler.timeout.ReadTimeoutHandler
import io.netty.handler.timeout.WriteTimeoutHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.client.reactive.ReactorClientHttpConnector
import org.springframework.web.reactive.function.client.WebClient
import reactor.netty.http.client.HttpClient

@Configuration
class WebClientConfig {

    @Bean
    fun quotesWebClient(webClientBuilder: WebClient.Builder) : WebClient {
        val httpClent = HttpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 4_000)
                .doOnConnected {
                    it.addHandlerLast(ReadTimeoutHandler(4))
                    it.addHandlerLast(WriteTimeoutHandler(4))
                }

        return webClientBuilder
                .baseUrl("https://quotes.rest/")
                .clientConnector(ReactorClientHttpConnector(httpClent))
                .build()
    }
}