package com.schoolerp.SchoolERP.controller;

import com.mongodb.DuplicateKeyException;
import com.schoolerp.SchoolERP.entity.Book;
import com.schoolerp.SchoolERP.service.BookService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/books")
@SecurityRequirement(name = "basicAuth")
public class BookController {

    @Autowired
    private BookService bookService;

    @PostMapping
    public ResponseEntity<?> addBook(@Valid @RequestBody Book book) {
        try {
            Book savedBook = bookService.addBook(book);
            return new ResponseEntity<>(savedBook, HttpStatus.CREATED);
        } catch (IllegalStateException e) {
            // Service se duplicate ISBN error
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT); // 409 Conflict
        } catch (DuplicateKeyException e) {
            // Database se duplicate ISBN error (safety net)
            return new ResponseEntity<>("Book with this ISBN already exists.", HttpStatus.CONFLICT);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    // 2. Saari Books ki List Get Karein
    @GetMapping
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    // 3. Ek Book ko uski ID se Get Karein
    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable ObjectId id) {
        Optional<Book> book = bookService.getBookById(id);
        if (book.isPresent()) {
            return new ResponseEntity<>(book.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> updateBook(@PathVariable ObjectId id, @Valid @RequestBody Book bookDetails) {
        try {
            Book updatedBook = bookService.updateBook(id, bookDetails);
            if (updatedBook != null) {
                return new ResponseEntity<>(updatedBook, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Book not found with id: " + id, HttpStatus.NOT_FOUND);
            }
        } catch (IllegalStateException e) {
            // Service se duplicate ISBN error
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        } catch (DuplicateKeyException e) {
            // Database se duplicate ISBN error
            return new ResponseEntity<>("Another book with this ISBN already exists.", HttpStatus.CONFLICT);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable ObjectId id) {
        try {
            bookService.deleteBook(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 204 No Content
        } catch (IllegalStateException e) {
            // Service se "not found" error
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/search/title")
    public List<Book> searchByTitle(@RequestParam("q") String title) {
        return bookService.searchBooksByTitle(title);
    }


    @GetMapping("/search/author")
    public List<Book> searchByAuthor(@RequestParam("q") String author) {
        return bookService.searchBooksByAuthor(author);
    }

}
