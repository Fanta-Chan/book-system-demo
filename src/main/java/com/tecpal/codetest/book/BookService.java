package com.tecpal.codetest.book;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public List<BookEntity> list(){
        return (List<BookEntity>) bookRepository.findAll();
    }

    public BookEntity get(Long id){
        Optional<BookEntity> book = bookRepository.findById(id);
        return book.orElse(null);
    }

    public List<BookEntity> get(String title){
        List<BookEntity> book = bookRepository.findByTitle(title);
        return book;
    }

    public void create(BookEntity book){
        bookRepository.save(book);
    }

    public void delete(Long id){
        bookRepository.deleteById(id);
    }

    public void delete(String title){
        bookRepository.deleteByTitle(title);
    }
}
