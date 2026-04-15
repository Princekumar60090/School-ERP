package com.schoolerp.SchoolERP.repository;

import com.schoolerp.SchoolERP.entity.Book;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends MongoRepository<Book, ObjectId> {

    Optional<Book> findByIsbn(String isbn);

    List<Book> findByTitleContainingIgnoreCase(String title);
    List<Book> findByAuthorContainingIgnoreCase(String author);
}
