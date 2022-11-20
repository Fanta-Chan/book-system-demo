package com.tecpal.codetest.book;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends CrudRepository<BookEntity, Long> {

    public List<BookEntity> findByTitle(String title);

    public void deleteByTitle(String title);
}
