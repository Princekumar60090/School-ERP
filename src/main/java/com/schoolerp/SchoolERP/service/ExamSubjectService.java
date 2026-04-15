package com.schoolerp.SchoolERP.service;

import com.schoolerp.SchoolERP.entity.Exam;
import com.schoolerp.SchoolERP.entity.ExamSubject;
import com.schoolerp.SchoolERP.repository.ExamSubjectRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ExamSubjectService {

    @Autowired
    private ExamSubjectRepository examSubjectRepository;

    @Transactional
    public ExamSubject addSubjectToExam(ExamSubject examSubject) {
        if (examSubject == null) {
            throw new IllegalArgumentException("ExamSubject cannot be null");
        }

        if (examSubject.getExamId() == null) {
            throw new IllegalArgumentException("Exam id is required");
        }
        if (examSubject.getSubjectId() == null) {
            throw new IllegalArgumentException("Subject Id is required");
        }
        if (examSubject.getTeacherId() == null) {
            throw new IllegalArgumentException("Teacher Id is required");
        }

        boolean exists = examSubjectRepository.existsByExamIdAndSubjectId(examSubject.getExamId(), examSubject.getSubjectId());

        if (exists) {
            throw new IllegalArgumentException("Subject already exists");
        }

        examSubject.setEditAllowed(false);

        return examSubjectRepository.save(examSubject);

    }

    @Transactional(readOnly = true)
    public List<ExamSubject> getExamSubjectsForExam(ObjectId examId) {
        if(examId == null) {
            throw new IllegalArgumentException("Exam Id cannot be null");
        }
        List<ExamSubject> examSubjects = examSubjectRepository.findByExamId(examId);
        return examSubjects;
    }

    @Transactional
    public ExamSubject updateSubjectSettings(ObjectId examSubjectId ,  ExamSubject updatedData) {
        if(examSubjectId == null) {
            throw new IllegalArgumentException("Exam Id cannot be null");
        }
        ExamSubject existing = examSubjectRepository.findById(examSubjectId)
                .orElseThrow(() -> new IllegalStateException("No ExamSubject found for ID: " + examSubjectId));

        if(updatedData.getTotalMarks()!=null && updatedData.getTotalMarks()>0){
            existing.setTotalMarks(updatedData.getTotalMarks());
        }

        if(updatedData.getPassingMarks()!=null && updatedData.getPassingMarks()>=0){
            existing.setPassingMarks(updatedData.getPassingMarks());
        }

        existing.setEditAllowed(updatedData.isEditAllowed());
        return examSubjectRepository.save(existing);
    }

    @Transactional
    public void deleteSubjectFromExam(ObjectId examSubjectId) {
        if(examSubjectId == null) {
            throw new IllegalArgumentException("ExamSubject Id cannot be null");
        }

        boolean exist = examSubjectRepository.existsById(examSubjectId);

        if(!exist){
            throw new IllegalArgumentException("No ExamSubject found for ID: " + examSubjectId);
        }

        examSubjectRepository.deleteById(examSubjectId);

    }

    @Transactional(readOnly = true)
    public ExamSubject getExamSubject(ObjectId examId, ObjectId subjectId) {
        if (examId == null || subjectId == null) {
            throw new IllegalArgumentException("Exam ID and Subject ID cannot be null");
        }

        ExamSubject examSubject = examSubjectRepository
                .findByExamIdAndSubjectId(examId, subjectId);

        if (examSubject == null) {
            throw new IllegalArgumentException("This subject is not assigned to the exam");
        }

        return examSubject;
    }


}
