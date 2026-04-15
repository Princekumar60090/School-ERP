package com.schoolerp.SchoolERP.controller;

import com.schoolerp.SchoolERP.entity.LibraryTransaction;
import com.schoolerp.SchoolERP.service.LibraryTransactionService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/library")
@SecurityRequirement(name = "basicAuth")
public class LibraryTransactionController {

    @Autowired
    private LibraryTransactionService transactionService;


    @PostMapping("/issue/{bookId}/{personId}/{personType}")
    public ResponseEntity<?> issueBook(
            @PathVariable ObjectId bookId,
            @PathVariable ObjectId personId,
            @PathVariable String personType) {

        try {

            LibraryTransaction transaction = transactionService.issueBook(bookId, personId, personType);
            return new ResponseEntity<>(transaction, HttpStatus.CREATED);

        } catch (IllegalArgumentException e) {

            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);

        } catch (IllegalStateException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);

        } catch (Exception e) {

            return new ResponseEntity<>("An error occurred during issue: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PutMapping("/return/{transactionId}")
    public ResponseEntity<?> returnBook(@PathVariable ObjectId transactionId) {
        try {

            LibraryTransaction transaction = transactionService.returnBook(transactionId);
            return new ResponseEntity<>(transaction, HttpStatus.OK);

        } catch (IllegalArgumentException e) {

            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);

        } catch (IllegalStateException e) {

            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);

        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred during return: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/issued/{personId}")
    public ResponseEntity<?> getIssuedBooksForPerson(@PathVariable ObjectId personId) {
        try {
            List<LibraryTransaction> transactions = transactionService.getIssuedBooksForPerson(personId);
            return new ResponseEntity<>(transactions, HttpStatus.OK);
        } catch (IllegalArgumentException e) {

            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND); // 404 Not Found
        }
    }


    @GetMapping("/transactions")
    public ResponseEntity<List<LibraryTransaction>> getAllOpenTransactions(
            @RequestParam("status") String status) {


        if (!"ISSUED".equalsIgnoreCase(status) && !"OVERDUE".equalsIgnoreCase(status)) {

            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        List<LibraryTransaction> transactions = transactionService.getAllOpenTransactions(status.toUpperCase());
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }
}

