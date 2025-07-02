package com.hackathon.book.service.impl;

import com.hackathon.book.dto.BookDTO;
import com.hackathon.book.entity.Book;
import com.hackathon.book.repository.BookRepository;
import com.hackathon.book.service.BookService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import jakarta.persistence.EntityNotFoundException;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository, ModelMapper modelMapper) {
        this.bookRepository = bookRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional
    public BookDTO createBook(BookDTO bookDTO) {
        // Check if book with same ISBN already exists
        if (bookDTO.getIsbn() != null && bookRepository.existsByIsbn(bookDTO.getIsbn())) {
            throw new IllegalArgumentException("Book with ISBN " + bookDTO.getIsbn() + " already exists");
        }
        
        // Convert DTO to entity
        Book book = modelMapper.map(bookDTO, Book.class);
        
        // Save the book
        Book savedBook = bookRepository.save(book);
        
        // Convert entity back to DTO and return
        return modelMapper.map(savedBook, BookDTO.class);
    }

    @Override
    @Transactional(readOnly = true)
    public BookDTO getBookById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Book not found with id: " + id));
        
        return modelMapper.map(book, BookDTO.class);
    }

    @Override
    @Transactional(readOnly = true)
    public BookDTO getBookByIsbn(String isbn) {
        Book book = bookRepository.findByIsbn(isbn)
                .orElseThrow(() -> new EntityNotFoundException("Book not found with ISBN: " + isbn));
        
        return modelMapper.map(book, BookDTO.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookDTO> getAllBooks() {
        List<Book> books = bookRepository.findAll();
        
        return books.stream()
                .map(book -> modelMapper.map(book, BookDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public BookDTO updateBook(Long id, BookDTO bookDTO) {
        // Check if book exists
        Book existingBook = bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Book not found with id: " + id));
        
        // Check if ISBN is being changed and if new ISBN already exists
        if (bookDTO.getIsbn() != null && !bookDTO.getIsbn().equals(existingBook.getIsbn()) 
                && bookRepository.existsByIsbn(bookDTO.getIsbn())) {
            throw new IllegalArgumentException("Book with ISBN " + bookDTO.getIsbn() + " already exists");
        }
        
        // Update the book properties
        if (bookDTO.getTitle() != null) {
            existingBook.setTitle(bookDTO.getTitle());
        }
        if (bookDTO.getAuthor() != null) {
            existingBook.setAuthor(bookDTO.getAuthor());
        }
        if (bookDTO.getIsbn() != null) {
            existingBook.setIsbn(bookDTO.getIsbn());
        }
        if (bookDTO.getDescription() != null) {
            existingBook.setDescription(bookDTO.getDescription());
        }
        if (bookDTO.getPublishedYear() != null) {
            existingBook.setPublishedYear(bookDTO.getPublishedYear());
        }
        
        // Save the updated book
        Book updatedBook = bookRepository.save(existingBook);
        
        // Convert entity back to DTO and return
        return modelMapper.map(updatedBook, BookDTO.class);
    }

    @Override
    @Transactional
    public void deleteBook(Long id) {
        // Check if book exists
        if (!bookRepository.existsById(id)) {
            throw new EntityNotFoundException("Book not found with id: " + id);
        }
        
        bookRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookDTO> searchBooksByTitle(String title) {
        List<Book> books = bookRepository.findByTitleContainingIgnoreCase(title);
        
        return books.stream()
                .map(book -> modelMapper.map(book, BookDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookDTO> searchBooksByAuthor(String author) {
        List<Book> books = bookRepository.findByAuthorContainingIgnoreCase(author);
        
        return books.stream()
                .map(book -> modelMapper.map(book, BookDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookDTO> getBooksByPublishedYear(Integer publishedYear) {
        List<Book> books = bookRepository.findByPublishedYear(publishedYear);
        
        return books.stream()
                .map(book -> modelMapper.map(book, BookDTO.class))
                .collect(Collectors.toList());
    }
}