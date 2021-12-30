# Lambda aws com spring function em kotlin
- Spring cloud function podemos executar qualquer código dentro de uma função que se encaixe na especificação functionalInterface.
- As funções mais utilizadas são: Function, Supplier e Consumer
  - Function: indicada para api, onde ela consome algum e retorna algum (recebe um parametro e possui retorno)
  - Consumer: apenas consome, indicada para listener de eventos (não possui parâmetro)
  - Supplier: recebe um valor mas não possui retorno (indicado para crons).

- Spring Cloud Function envolve a carga útil de entrada e cabeçalhos como um org.springframework.messaging.Message. Se quisermos acessar o contexto do AWS Lambda, por exemplo, podemos extrair esse objeto dos cabeçalhos e obter acesso ao registrador do Lambda:
```
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

```
