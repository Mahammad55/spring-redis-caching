package az.practice.rediscashing.service.impl;

import az.practice.rediscashing.dto.BookDto;
import az.practice.rediscashing.entity.Book;
import az.practice.rediscashing.repository.BookRepository;
import az.practice.rediscashing.service.BookService;
import az.practice.rediscashing.util.CacheUtil;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;

    private final CacheUtil cacheUtil;

    private static final String CACHE_KEY = "book";

    private final ModelMapper mapper;

    @Override
    public Book getBookById(Long id) {
        Book cacheBook = cacheUtil.getBucket(CACHE_KEY + id);
        if (cacheBook == null) {
            Book book = bookRepository.findById(id).orElseThrow();
            cacheUtil.saveToCache(CACHE_KEY + id, book, 1L, ChronoUnit.HOURS);
            return book;
        }
        return cacheBook;
    }

    @Override
    public void deleteBookById(Long id) {
        Book cacheBook = cacheUtil.getBucket(CACHE_KEY + id);
        bookRepository.deleteById(id);
        if (cacheBook != null) cacheUtil.deleteCache(CACHE_KEY + id);
    }

    @Override
    public void saveBook(BookDto bookDto) {
        Book savedBook = bookRepository.save(mapper.map(bookDto, Book.class));
        cacheUtil.saveToCache(CACHE_KEY + savedBook.getId(), savedBook, 1L, ChronoUnit.HOURS);
    }
}
