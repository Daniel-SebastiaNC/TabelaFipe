package br.com.alura.tabelafipe.main;

import br.com.alura.tabelafipe.model.Dados;
import br.com.alura.tabelafipe.model.Modelos;
import br.com.alura.tabelafipe.model.Veiculo;
import br.com.alura.tabelafipe.service.ApiRequest;
import br.com.alura.tabelafipe.service.ConverteDados;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class Main {
    Scanner leitura = new Scanner(System.in);
    ApiRequest api = new ApiRequest();
    ConverteDados conversor = new ConverteDados();

    final String BASE_LINK = "https://parallelum.com.br/fipe/api/v1/";

    public void exibirMenu(){
        System.out.println("""
                        ***Opcoes***
                        motos
                        carros
                        caminhoes
                        
                        Digite uma das opcoes: 
                        """);
        var opcao = leitura.next().toLowerCase();

        String json = api.obterDados(BASE_LINK + opcao +"/marcas");
        var marcas = conversor.obterLista(json, Dados.class);

        marcas.stream()
                .sorted(Comparator.comparing(Dados::codigo))
                .forEach(System.out::println);

        System.out.println("Digite o código da marca: ");
        var codigoMarca = leitura.nextInt();

        json = api.obterDados(BASE_LINK + opcao +"/marcas/" + codigoMarca + "/modelos");
        var Listamodelos = conversor.obterDados(json, Modelos.class);

        Listamodelos.modelos().stream()
               .sorted(Comparator.comparing(Dados::codigo))
               .forEach(System.out::println);

        System.out.println("Digite um trecho do nome do veículo para consulta:");
        String filtroModelo = leitura.next();

        List<Dados> modelosFiltrados = Listamodelos.modelos().stream()
                .filter(m -> m.nome().toLowerCase().contains(filtroModelo.toLowerCase()))
                .toList();

        modelosFiltrados.forEach(System.out::println);

        System.out.println("Digite o código do modelo para consultar valores: ");
        var codigoModelo = leitura.nextInt();

        json = api.obterDados(BASE_LINK + opcao +"/marcas/" + codigoMarca + "/modelos/" + codigoModelo + "/anos");
        var anos = conversor.obterLista(json, Dados.class);

        List<String> ultimos5anos = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            ultimos5anos.add(anos.get(i).codigo());
        }

        //System.out.println(ultimos5anos);

        List<Veiculo> veiculos = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            json = api.obterDados(BASE_LINK + opcao +"/marcas/" + codigoMarca + "/modelos/" + codigoModelo + "/anos/" + ultimos5anos.get(i));
            veiculos.add(conversor.obterDados(json, Veiculo.class));
        }

        veiculos.forEach(System.out::println);


    }
}
