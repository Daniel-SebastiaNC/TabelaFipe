package br.com.alura.tabelafipe;

import br.com.alura.tabelafipe.main.Main;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TabelafipeApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(TabelafipeApplication.class, args);
	}


	@Override
	public void run(String... args) throws Exception {
		Main main = new Main();
		main.exibirMenu();
	}
}
