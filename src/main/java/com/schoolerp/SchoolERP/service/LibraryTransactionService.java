package com.schoolerp.SchoolERP.service;

import com.schoolerp.SchoolERP.entity.Book;
import com.schoolerp.SchoolERP.entity.FeePayment;
import com.schoolerp.SchoolERP.entity.LibraryTransaction;
import com.schoolerp.SchoolERP.entity.SalaryPayment;
import com.schoolerp.SchoolERP.repository.*;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class LibraryTransactionService {


    @Autowired
    private LibraryTransactionRepository transactionRepository;


    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private FeePaymentRepository feePaymentRepository;

    @Autowired
    private SalaryPaymentRepository salaryPaymentRepository;


    @Autowired
    private EmailService emailService;


    @Transactional
    public LibraryTransaction issueBook(ObjectId bookId, ObjectId personId, String personType) {


        validatePersonExists(personId, personType);

        Optional<Book> bookOptional = bookRepository.findById(bookId);
        if (!bookOptional.isPresent()) {
            throw new IllegalArgumentException("Book not found with id: " + bookId);
        }
        Book book = bookOptional.get();

        if (book.getAvailableCopies() <= 0) {
            throw new IllegalStateException("Book is currently not available. All copies are issued.");
        }

        List<LibraryTransaction> personTransactions = transactionRepository.findByPersonIdAndStatus(personId, "ISSUED");
        for (LibraryTransaction trans : personTransactions) {
            if (trans.getBookId().equals(bookId)) {
                throw new IllegalStateException("This person (" + personId + ") has already issued this book (" + bookId + ").");
            }
        }

        LibraryTransaction transaction = new LibraryTransaction();
        transaction.setBookId(bookId);
        transaction.setPersonId(personId);
        transaction.setPersonType(personType);
        transaction.setIssueDate(LocalDate.now());
        transaction.setDueDate(LocalDate.now().plusDays(14));
        transaction.setStatus("ISSUED");
        transaction.setFineAmount(0.0);
        transaction.setReminderSent(false);

        book.setAvailableCopies(book.getAvailableCopies() - 1);
        bookRepository.save(book);

        LibraryTransaction savedTransaction = transactionRepository.save(transaction);

        // ---------------- SEND EMAIL ----------------
        String email = getPersonEmail(personId, personType);
        String name = personType.equalsIgnoreCase("STUDENT")
                ? studentRepository.findById(personId).get().getName()
                : teacherRepository.findById(personId).get().getName();

        String subject = "Book Issued: " + book.getTitle();
        String body = "Hello " + name + ",\n\n" +
                "You have successfully issued a book:\n" +
                "📘 Title: " + book.getTitle() + "\n" +
                "✍ Author: " + book.getAuthor() + "\n" +
                "📅 Due Date: " + savedTransaction.getDueDate() + "\n\n" +
                "Please return the book on time.\n\n" +
                "Regards,\nSchool Library Team";

        emailService.sendEmail(email, subject, body);
        // --------------------------------------------------

        return savedTransaction;
    }


    @Transactional
    public LibraryTransaction returnBook(ObjectId transactionId) {

        Optional<LibraryTransaction> transactionOptional = transactionRepository.findById(transactionId);
        if (!transactionOptional.isPresent()) {
            throw new IllegalArgumentException("No transaction found with id: " + transactionId);
        }
        LibraryTransaction transaction = transactionOptional.get();


        if ("RETURNED".equalsIgnoreCase(transaction.getStatus())) {
            throw new IllegalStateException("This book has already been returned.");
        }


        Optional<Book> bookOptional = bookRepository.findById(transaction.getBookId());
        Book book;
        if (bookOptional.isPresent()) {
            book = bookOptional.get();
            book.setAvailableCopies(book.getAvailableCopies() + 1);
            bookRepository.save(book);
        } else {

            throw new IllegalStateException("Associated book data not found. Cannot update available copies.");
        }


        transaction.setReturnDate(LocalDate.now());
        transaction.setStatus("RETURNED");


        double fine = 0.0;
        if (LocalDate.now().isAfter(transaction.getDueDate())) {
            long lateDays = java.time.temporal.ChronoUnit.DAYS.between(transaction.getDueDate(), LocalDate.now());
            double finePerDay = 10.0;
            fine = lateDays * finePerDay;
        }
        transaction.setFineAmount(fine);

        LibraryTransaction savedTransaction = transactionRepository.save(transaction);

        String personType = transaction.getPersonType();
        ObjectId personId = transaction.getPersonId();

        String email = getPersonEmail(personId, personType);

        String name = personType.equalsIgnoreCase("STUDENT")
                ? studentRepository.findById(personId).get().getName()
                : teacherRepository.findById(personId).get().getName();


        String subject = "Book Returned: " + book.getTitle();

        String body =
                "Hello " + name + ",\n\n" +
                        "You have successfully returned the book:\n" +
                        "📘 Title: " + book.getTitle() + "\n" +
                        "📅 Returned On: " + LocalDate.now() + "\n\n" +
                        (fine > 0
                                ? "⚠ A fine of ₹" + fine + " has been applied for late return.\n\n"
                                : "") +
                        "Thank you!\n" +
                        "Library Team";

        emailService.sendEmail(email, subject, body);

        // 2) HANDLE FINE FOR STUDENTS (Add to Fee Payments)
        // -----------------------------------------
        if (fine > 0 && personType.equalsIgnoreCase("STUDENT")) {

            FeePayment finePayment = new FeePayment();
            finePayment.setId(null);
            finePayment.setStudentId(personId);
            finePayment.setAmountPaid(fine);
            finePayment.setPaymentDate(LocalDate.now());
            finePayment.setFeeType("LIBRARY_FINE");
            finePayment.setPaymentMonth(LocalDate.now().getMonth());
            finePayment.setPaymentYear(LocalDate.now().getYear());
            finePayment.setNotes("Library fine for book: " + book.getTitle());

            feePaymentRepository.save(finePayment);
        }


        // 3) HANDLE FINE FOR TEACHERS (Deduct from salary)
        // -----------------------------------------
        if (fine > 0 && personType.equalsIgnoreCase("TEACHER")) {

            // Salary Payment Logic
            SalaryPayment fineDeduction = new SalaryPayment();
            fineDeduction.setId(null);
            fineDeduction.setPersonId(personId);
            fineDeduction.setPersonType("TEACHER");
            fineDeduction.setPaymentDate(LocalDate.now());
            fineDeduction.setSalaryMonth(LocalDate.now().getMonth());
            fineDeduction.setSalaryYear(LocalDate.now().getYear());
            fineDeduction.setAmount(-fine); // negative salary
            fineDeduction.setNotes("Library fine deducted for: " + book.getTitle());

            salaryPaymentRepository.save(fineDeduction);
        }

        return savedTransaction;

    }


    public List<LibraryTransaction> getIssuedBooksForPerson(ObjectId personId) {
        validatePersonExists(personId, "STUDENT_OR_TEACHER"); // Check karein ki person valid hai
        return transactionRepository.findByPersonIdAndStatus(personId, "ISSUED");
    }


    public List<LibraryTransaction> getAllOpenTransactions(String status) {
        return transactionRepository.findAllByStatus(status);
    }



    private void validatePersonExists(ObjectId personId, String personType) {

        if ("STUDENT_OR_TEACHER".equalsIgnoreCase(personType)) {
            if (studentRepository.existsById(personId) || teacherRepository.existsById(personId)) {
                return;
            } else {
                throw new IllegalArgumentException("No Student or Teacher found with id: " + personId);
            }
        }


        boolean exists = false;
        if ("STUDENT".equalsIgnoreCase(personType)) {
            exists = studentRepository.existsById(personId);
        } else if ("TEACHER".equalsIgnoreCase(personType)) {
            exists = teacherRepository.existsById(personId);
        } else {
            throw new IllegalArgumentException("Invalid person type: " + personType + ". Must be STUDENT or TEACHER.");
        }

        if (!exists) {
            throw new IllegalArgumentException("No " + personType + " found with id: " + personId);
        }


    }

    private String getPersonEmail(ObjectId personId, String personType) {
        if(personType.equalsIgnoreCase("STUDENT")) {
            return studentRepository.findById(personId)
                    .map(student -> student.getEmail())
                    .orElseThrow(()-> new IllegalArgumentException("Student not found"));
        } else if (personType.equalsIgnoreCase("TEACHER")) {
             return teacherRepository.findById(personId)
                     .map(teacher->teacher.getEmail())
                     .orElseThrow(()-> new IllegalArgumentException("Teacher not found"));
        }
        throw new IllegalArgumentException("Invalid person type: " + personType);
    }



}

