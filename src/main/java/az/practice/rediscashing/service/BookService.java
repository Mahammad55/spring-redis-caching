package az.practice.rediscashing.service;

import az.practice.rediscashing.dto.BookDto;
import az.practice.rediscashing.entity.Book;

public interface BookService {
    Book getBookById(Long id);

    void deleteBookById(Long id);

    void saveBook(BookDto bookDto);
}
