package com.schoolerp.SchoolERP.service;

import com.schoolerp.SchoolERP.entity.Book;
import com.schoolerp.SchoolERP.repository.BookRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;



    public Book addBook(Book book) {
        Optional<Book> existingBook = bookRepository.findByIsbn(book.getIsbn());

        if (existingBook.isPresent()) {
            throw new IllegalStateException("Book with ISBN '" + book.getIsbn() + "' already exists.");
        }


        book.setAvailableCopies(book.getTotalQuantity());

        return bookRepository.save(book);
    }


    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }


    public Optional<Book> getBookById(ObjectId id) {
        return bookRepository.findById(id);
    }


    public List<Book> searchBooksByTitle(String title) {
        return bookRepository.findByTitleContainingIgnoreCase(title);
    }


    public List<Book> searchBooksByAuthor(String author) {
        return bookRepository.findByAuthorContainingIgnoreCase(author);
    }


    public Book updateBook(ObjectId id, Book bookDetails) {
        Optional<Book> bookOptional = bookRepository.findById(id);
        if (bookOptional.isPresent()) {
            Book existingBook = bookOptional.get();

            Optional<Book> duplicateIsbnCheck = bookRepository.findByIsbn(bookDetails.getIsbn());
            if (duplicateIsbnCheck.isPresent() && !duplicateIsbnCheck.get().getId().equals(id)) {
                throw new IllegalStateException("Another book with this ISBN already exists.");
            }

            existingBook.setTitle(bookDetails.getTitle());
            existingBook.setAuthor(bookDetails.getAuthor());
            existingBook.setIsbn(bookDetails.getIsbn());
            existingBook.setPublisher(bookDetails.getPublisher());
            existingBook.setTotalQuantity(bookDetails.getTotalQuantity());


            existingBook.setAvailableCopies(bookDetails.getAvailableCopies());

            return bookRepository.save(existingBook);
        }
        return null;
    }


    public void deleteBook(ObjectId id) {
        if (!bookRepository.existsById(id)) {
            throw new IllegalStateException("Book not found with id: " + id);
        }
        bookRepository.deleteById(id);
    }
}
