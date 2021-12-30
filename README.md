# Lambda aws com spring function em kotlin
- Spring cloud function podemos executar qualquer código dentro de uma função que se encaixe na especificação functionalInterface.
- As funções mais utilizadas são: Function, Supplier e Consumer
  - Function: indicada para api, onde ela consome algum e retorna algum (recebe um parametro e possui retorno)
  - Consumer: apenas consome, indicada para listener de eventos (não possui parâmetro)
  - Supplier: recebe um valor mas não possui retorno (indicado para crons).
