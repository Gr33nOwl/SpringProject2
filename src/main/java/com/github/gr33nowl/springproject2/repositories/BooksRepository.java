package com.github.gr33nowl.springproject2.repositories;

import com.github.gr33nowl.springproject2.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BooksRepository extends JpaRepository<Book, Integer> {
}
