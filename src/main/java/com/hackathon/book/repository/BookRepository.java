package com.hackathon.book.repository;

import com.hackathon.book.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    
    // Find book by ISBN
    Optional<Book> findByIsbn(String isbn);
    
    // Find books by author
    List<Book> findByAuthorContainingIgnoreCase(String author);
    
    // Find books by title
    List<Book> findByTitleContainingIgnoreCase(String title);
    
    // Find books by published year
    List<Book> findByPublishedYear(Integer publishedYear);
    
    // Check if a book with the given ISBN exists
    boolean existsByIsbn(String isbn);
}