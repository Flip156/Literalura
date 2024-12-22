package com.systemnegro.liter_alura.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "livros")
@Getter
@Setter
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Author author;

    private String title;
    private Integer downloadCount;
    private String language;

    public Book(BookApiResponse book) {
        this.title = book.title();
        this.downloadCount = book.downloadCount();
        this.language = book.languages().getFirst();
    }
    public Book(){

    }

    @Override
    public String toString() {
        return String.format("""
                Livro
                Título: %s
                Autor: %s
                Idioma: %s
                Número de downloads: %d
                """,
                title,
                author.getName(),
                language,
                downloadCount);
    }

}
