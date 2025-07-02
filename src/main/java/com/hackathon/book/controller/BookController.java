package com.hackathon.book.controller;

import com.hackathon.book.dto.BookDTO;
import com.hackathon.book.service.BookService;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Hidden
@RestController
@RequestMapping("/api/books")
@Tag(name = "Book Controller", description = "CRUD operations for books")
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @Operation(summary = "Create a new book", description = "Creates a new book with the provided information")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Book created successfully",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = BookDTO.class))),
        @ApiResponse(responseCode = "400", description = "Invalid input or book with same ISBN already exists")
    })
    @PostMapping
    public ResponseEntity<BookDTO> createBook(
            @Parameter(description = "Book information for creation", required = true) @RequestBody BookDTO bookDTO) {
        BookDTO createdBook = bookService.createBook(bookDTO);
        return new ResponseEntity<>(createdBook, HttpStatus.CREATED);
    }

    @Operation(summary = "Get a book by ID", description = "Returns a book based on the provided ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Book found",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = BookDTO.class))),
        @ApiResponse(responseCode = "404", description = "Book not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<BookDTO> getBookById(
            @Parameter(description = "ID of the book to retrieve", required = true) @PathVariable Long id) {
        BookDTO book = bookService.getBookById(id);
        return ResponseEntity.ok(book);
    }

    @Operation(summary = "Get a book by ISBN", description = "Returns a book based on the provided ISBN")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Book found",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = BookDTO.class))),
        @ApiResponse(responseCode = "404", description = "Book not found")
    })
    @GetMapping("/isbn/{isbn}")
    public ResponseEntity<BookDTO> getBookByIsbn(
            @Parameter(description = "ISBN of the book to retrieve", required = true) @PathVariable String isbn) {
        BookDTO book = bookService.getBookByIsbn(isbn);
        return ResponseEntity.ok(book);
    }

    @Operation(summary = "Get all books", description = "Returns a list of all books in the system")
    @ApiResponse(responseCode = "200", description = "List of books retrieved successfully",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = BookDTO.class)))
    @GetMapping
    public ResponseEntity<List<BookDTO>> getAllBooks() {
        List<BookDTO> books = bookService.getAllBooks();
        return ResponseEntity.ok(books);
    }

    @Operation(summary = "Update a book", description = "Updates an existing book with the provided information")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Book updated successfully",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = BookDTO.class))),
        @ApiResponse(responseCode = "400", description = "Invalid input or book with same ISBN already exists"),
        @ApiResponse(responseCode = "404", description = "Book not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<BookDTO> updateBook(
            @Parameter(description = "ID of the book to update", required = true) @PathVariable Long id,
            @Parameter(description = "Updated book information", required = true) @RequestBody BookDTO bookDTO) {
        BookDTO updatedBook = bookService.updateBook(id, bookDTO);
        return ResponseEntity.ok(updatedBook);
    }

    @Operation(summary = "Delete a book", description = "Deletes a book with the specified ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Book deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Book not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(
            @Parameter(description = "ID of the book to delete", required = true) @PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Search books by title", description = "Returns books that contain the specified title (case-insensitive)")
    @ApiResponse(responseCode = "200", description = "Search completed successfully",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = BookDTO.class)))
    @GetMapping("/search/title")
    public ResponseEntity<List<BookDTO>> searchBooksByTitle(
            @Parameter(description = "Title to search for", required = true) @RequestParam String title) {
        List<BookDTO> books = bookService.searchBooksByTitle(title);
        return ResponseEntity.ok(books);
    }

    @Operation(summary = "Search books by author", description = "Returns books that contain the specified author name (case-insensitive)")
    @ApiResponse(responseCode = "200", description = "Search completed successfully",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = BookDTO.class)))
    @GetMapping("/search/author")
    public ResponseEntity<List<BookDTO>> searchBooksByAuthor(
            @Parameter(description = "Author name to search for", required = true) @RequestParam String author) {
        List<BookDTO> books = bookService.searchBooksByAuthor(author);
        return ResponseEntity.ok(books);
    }

    @Operation(summary = "Get books by published year", description = "Returns books published in the specified year")
    @ApiResponse(responseCode = "200", description = "Search completed successfully",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = BookDTO.class)))
    @GetMapping("/search/year/{year}")
    public ResponseEntity<List<BookDTO>> getBooksByPublishedYear(
            @Parameter(description = "Published year to search for", required = true) @PathVariable Integer year) {
        List<BookDTO> books = bookService.getBooksByPublishedYear(year);
        return ResponseEntity.ok(books);
    }
}
