package com.systemnegro.liter_alura;



import com.systemnegro.liter_alura.repository.AuthorRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LiterAluraApplication implements CommandLineRunner {

    private final AuthorRepository repository;

    public LiterAluraApplication(AuthorRepository repository) {
        this.repository = repository;
    }

    public static void main(String[] args) {
        SpringApplication.run(LiterAluraApplication.class, args);
    }

    @Override
    public void run(String... args){
        Main main = new Main(repository);
        main.menu();
    }
}
