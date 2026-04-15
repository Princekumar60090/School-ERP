package com.schoolerp.SchoolERP.repository;

import com.schoolerp.SchoolERP.entity.LibraryTransaction;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface LibraryTransactionRepository extends MongoRepository<LibraryTransaction, ObjectId> {


    Optional<LibraryTransaction> findByBookIdAndStatus(ObjectId bookId, String status);


    List<LibraryTransaction> findByPersonIdAndStatus(ObjectId personId, String status);


    List<LibraryTransaction> findAllByStatus(String status);

    List<LibraryTransaction> findByStatusAndDueDateBeforeAndReminderSentFalse(String status, LocalDate date);
}
