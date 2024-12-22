# Projeto de Gerenciamento de Livros e Autores

Este projeto administra livros e autores em um banco de dados, possibilitando o registro de novos livros. Para obter informações sobre as obras, utilize a API Gutendex.


## Funcionalidades

- Cadastro de livros e autores
- Verificação de existência de livros antes do cadastro
- Relacionamento bidirecional entre livros e autores
- Listar os autores vivos em um determinado ano
- Listar todos autores
- Pesquisa de livros por título
- Listar os livros de um determinado idioma



## Método `save(Book book, Author author)`
Este é o método principal para vincular livros e autores no banco de dados. Ele verifica duplicações antes de salvar um livro e garantir a associação bidirecional entre livros e autores. As operações realizadas são:

- **Verificação do livro existente**: Antes de salvar, o método verifica se o livro já está cadastrado no banco.
- **Verificação do autor existente**: Se o autor não estiver cadastrado, o autor é salvo junto com o livro, estabelecendo a relação entre eles.
- **Relacionamento bidirecional**: O método adiciona o livro à lista de livros do autor, garantindo a associação bidirecional.
- **Associação do livro a autor já existente**: Caso o autor já esteja cadastrado no banco, o livro é simplesmente associado a esse autor, sem duplicar o autor no banco.
### Código do Método `save`:

```java
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
```
### Explicação

- **`findByTitle()`**: Verifica se o livro já está registrado no banco.
- **`findByNameContainingIgnoreCase()`**: Verifica se o autor já está no banco, ignorando diferenças de maiúsculas/minúsculas no nome.
- **`orElse(author)`**: Se o autor não for encontrado, o próprio autor passado como parâmetro é usado.
- **`addBook()`**: O livro é adicionado à lista de livros do autor, estabelecendo o relacionamento bidirecional.
- **`repository.save()`**: O autor é salvo (ou atualizado) no banco, com o livro já associado.

