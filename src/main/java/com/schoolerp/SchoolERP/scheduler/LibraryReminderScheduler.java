package com.schoolerp.SchoolERP.scheduler;

import com.schoolerp.SchoolERP.entity.Book;
import com.schoolerp.SchoolERP.entity.LibraryTransaction;
import com.schoolerp.SchoolERP.repository.BookRepository;
import com.schoolerp.SchoolERP.repository.LibraryTransactionRepository;
import com.schoolerp.SchoolERP.repository.StudentRepository;
import com.schoolerp.SchoolERP.repository.TeacherRepository;
import com.schoolerp.SchoolERP.service.EmailService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Component
public class LibraryReminderScheduler {

    @Autowired
    private LibraryTransactionRepository libraryTransactionRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private EmailService emailService;


    @Scheduled(cron = "0 0 9 * * ?")  // Runs every day at 9 AM
    @Transactional
    public void sendOverdueReminders() {

        LocalDate today = LocalDate.now();

        // find all overdue transactions
        List<LibraryTransaction> overdueList =
                libraryTransactionRepository.findByStatusAndDueDateBeforeAndReminderSentFalse(
                        "ISSUED", today);

        if (overdueList.isEmpty()) return;

        for (LibraryTransaction tx : overdueList) {
            try {
                tx.setReminderSent(true);
                tx.setStatus("OVERDUE");

                String personType = tx.getPersonType();
                ObjectId personId = tx.getPersonId();

                String name = "User";
                String email = null;

                // CLEAN, optimized logic:
                if (personType.equalsIgnoreCase("STUDENT")) {

                    var s = studentRepository.findById(personId).orElse(null);
                    if (s != null) {
                        name = s.getName();
                        email = s.getEmail();
                    }

                } else if (personType.equalsIgnoreCase("TEACHER")) {

                    var t = teacherRepository.findById(personId).orElse(null);
                    if (t != null) {
                        name = t.getName();
                        email = t.getEmail();
                    }
                }

                // fetch book title
                Optional<Book> bookOpt = bookRepository.findById(tx.getBookId());
                String bookTitle = bookOpt.map(Book::getTitle).orElse("the book");

                // send email if a valid email exists
                if (email != null) {
                    String subject = "Overdue Book Reminder: " + bookTitle;

                    String body =
                            "Hello " + name + ",\n\n" +
                                    "This is a reminder that the following book is overdue:\n" +
                                    "📘 Title: " + bookTitle + "\n" +
                                    "📅 Due Date: " + tx.getDueDate() + "\n\n" +
                                    "Please return it as soon as possible. A fine may be applied for late returns.\n\n" +
                                    "If you have already returned the book, please ignore this message.\n\n" +
                                    "Regards,\nSchool Library Team";

                    emailService.sendEmail(email, subject, body);
                }

                libraryTransactionRepository.save(tx);

            } catch (Exception e) {

                System.err.println("Failed to send overdue reminder for transaction "
                        + tx.getId() + " : " + e.getMessage());
            }
        }
    }
}
