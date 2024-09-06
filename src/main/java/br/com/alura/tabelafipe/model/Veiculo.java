package br.com.alura.tabelafipe.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Veiculo(String Valor,
                      String Marca,
                      String Modelo,
                      @JsonAlias("MesReferencia") String Ano,
                      String Combustivel) {
}
