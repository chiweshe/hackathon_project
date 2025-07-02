package com.hackathon.book.service;

import com.hackathon.book.dto.BookDTO;
import java.util.List;

public interface BookService {
    
    // Create a new book
    BookDTO createBook(BookDTO bookDTO);
    
    // Get a book by ID
    BookDTO getBookById(Long id);
    
    // Get a book by ISBN
    BookDTO getBookByIsbn(String isbn);
    
    // Get all books
    List<BookDTO> getAllBooks();
    
    // Update a book
    BookDTO updateBook(Long id, BookDTO bookDTO);
    
    // Delete a book
    void deleteBook(Long id);
    
    // Search books by title
    List<BookDTO> searchBooksByTitle(String title);
    
    // Search books by author
    List<BookDTO> searchBooksByAuthor(String author);
    
    // Get books by published year
    List<BookDTO> getBooksByPublishedYear(Integer publishedYear);
}