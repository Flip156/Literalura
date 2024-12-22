package com.systemnegro.liter_alura;


import com.systemnegro.liter_alura.model.ApiResponse;
import com.systemnegro.liter_alura.model.Author;
import com.systemnegro.liter_alura.model.Book;
import com.systemnegro.liter_alura.repository.AuthorRepository;
import com.systemnegro.liter_alura.service.ApiConsumption;
import com.systemnegro.liter_alura.service.JsonSerializationService;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Main {
    private final ApiConsumption consumer = new ApiConsumption();
    private final JsonSerializationService serialization = new JsonSerializationService();
    private final AuthorRepository repository;
    private final Scanner scanner = new Scanner(System.in);

    public Main(AuthorRepository repository) {
        this.repository = repository;
    }

    public void menu() {
        var option = -1;
        while (option != 0) {
            System.out.println("""
                    1 - Buscar livro pelo título
                    2 - Listar livros registrados
                    3 - Listar autores registrados
                    4 - Listar autores vivos em um determinado ano
                    5 - Listar livros em um determinado idioma
                    0 - Sair
                    """);
            option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1 -> searchBookByTitle();
                case 2 -> searchRegisteredBooks();
                case 3 -> searchForRegisteredAuthors();
                case 4 -> searchForLivingAuthorsByYear();
                case 5 -> searchBooksByLanguage();
                case 0 -> System.out.println("Saindo...");
                default -> System.out.println("Opção inválida");
            }
        }
    }


    private void searchBookByTitle() {
        System.out.println("Insira o nome do livro que deseja procurar: ");
        var desiredBook = scanner.nextLine();
        String apiAddress = "https://gutendex.com/books/?search=";
        var json = consumer.consumption(apiAddress + desiredBook.replace(" ", "%20").trim());
        var obj = serialization.fromJson(json, ApiResponse.class);

        Book book = new Book(obj.results().getFirst());
        Author author = new Author(obj.results().getFirst().authors().getFirst());
        save(book, author);

    }

    private void searchRegisteredBooks() {
        List<Book> books = repository.findAllBook();
        if (books.isEmpty()) {
            System.out.println("Nenhum livro cadastrado");
        }
        books.forEach(System.out::println);
    }

    private void save(Book book, Author author) {
        Optional<Book> bookFound = repository.findByTitle(book.getTitle());
        bookFound.ifPresentOrElse(
                b -> System.out.println("Livro já cadastrado tente outro"),
                () -> {
                    Author authorToSave = repository.findByNameContainingIgnoreCase(author.getName())
                            .orElse(author);
                    authorToSave.addBook(book);
                    repository.save(authorToSave);
                }
        );
    }

    private void searchForRegisteredAuthors() {
        List<Author> authors = repository.findAll();

        if (authors.isEmpty()) {
            System.out.println("Nenhum autor registrado ");
        }
        authors.forEach(System.out::println);
    }

    private void searchForLivingAuthorsByYear() {
        System.out.println("Insira o ano que deseja pesquisar ");

        int year;
        while (true) {
            if (scanner.hasNextInt()) {
                year = scanner.nextInt();
                break;
            } else if (scanner.hasNextDouble()) {
                System.out.println("Por favor, insira apenas um número inteiro.");
                scanner.next();
            } else {
                System.out.println("Entrada inválida! Por favor, insira um número.");
                scanner.next();
            }
        }
        List<Author> authors = repository.findLivingAuthorsByYear(year);
        if (authors.isEmpty()) {
            System.out.println("Nenhum autor encontrado ");
        }

        authors.forEach(System.out::println);
    }

    private void searchBooksByLanguage(){
        System.out.print("""
                Insira o idioma para realizar a busca:
                es - espanhol
                en - inglês
                fr - francês
                pt - português
                """);
        var language = scanner.nextLine();
        List<Book> books = repository.findBooksByLanguage(language);
        if (books.isEmpty()){
            System.out.println("Não temos livros com esse idioma no banco de dados");
        }
        books.forEach(System.out::println);
    }


}
