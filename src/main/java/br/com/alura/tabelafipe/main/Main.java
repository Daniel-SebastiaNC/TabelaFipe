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
        var opcao = leitura.next();

        String pesquisa;

        if (opcao.toLowerCase().contains("carr")){
            pesquisa = "carros";
        } else if (opcao.toLowerCase().contains("mot")) {
            pesquisa = "motos";
        } else {
            pesquisa = "caminhoes";
        }

        String endereco = BASE_LINK + pesquisa + "/marcas/";
        String json = api.obterDados(endereco);
        var marcas = conversor.obterLista(json, Dados.class);

        marcas.stream()
                .sorted(Comparator.comparing(Dados::codigo))
                .forEach(System.out::println);

        System.out.println("Digite o código da marca: ");
        var codigoMarca = leitura.nextInt();

        endereco +=  codigoMarca + "/modelos/";
        json = api.obterDados(endereco);
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

        endereco = endereco + codigoModelo + "/anos/";
        json = api.obterDados(endereco);
        var anos = conversor.obterLista(json, Dados.class);

        List<Veiculo> veiculos = new ArrayList<>();
        for (int i = 0; i < anos.size(); i++) {
            json = api.obterDados(endereco + anos.get(i).codigo());
            veiculos.add(conversor.obterDados(json, Veiculo.class));
        }

        //System.out.println(ultimos5anos);

        veiculos.forEach(System.out::println);


    }
}
