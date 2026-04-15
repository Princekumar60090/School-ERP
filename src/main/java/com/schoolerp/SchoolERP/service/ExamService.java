package com.schoolerp.SchoolERP.service;

import com.schoolerp.SchoolERP.entity.Exam;
import com.schoolerp.SchoolERP.repository.ExamRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ExamService {

    @Autowired
    private ExamRepository examRepository;

    @Transactional
    public Exam createExam(Exam exam){
        if(exam==null){
            throw new IllegalArgumentException("Exam cannot be null");
        }

        if(exam.getName()==null){
            throw new IllegalArgumentException("Exam name is required");
        }

        if(exam.getClassLevel()==null){
            throw new IllegalArgumentException("Class level is required");
        }

        if(exam.getSection()==null){
            throw new IllegalArgumentException("Section is required");
        }

        boolean exists = examRepository.existsByNameAndClassLevelAndSection(
                exam.getName().trim(),exam.getClassLevel(),exam.getSection().trim().toUpperCase()
        );

        if(exists){
            throw new IllegalStateException("Exam with the same name already exists for this class and section");
        }

        exam.setPublished(false);

        return examRepository.save(exam);

    }

    @Transactional(readOnly = true)
    public Exam getExamById(ObjectId examId){
        if(examId==null){
            throw new IllegalArgumentException("Exam ID cannot be null");
        }
        return examRepository.findById(examId)
                .orElseThrow(()->new IllegalStateException("No Exam found for ID: "+examId));
    }

    @Transactional(readOnly = true)
    public List<Exam> getExamsForClass(Integer classLevel, String section){
        if(classLevel == null){
            throw new IllegalArgumentException("Class level is required");
        }
        if(section == null || section.trim().isEmpty()){
            throw new IllegalArgumentException("Section is required");
        }

        return examRepository.findByClassLevelAndSection(classLevel,section.trim().toUpperCase());

    }

    @Transactional
    public Exam updateExam(ObjectId examId, Exam updatedExam){

        Exam existing = getExamById(examId);


        if(updatedExam.getName()!=null){
            existing.setName(updatedExam.getName());
        }

        if(updatedExam.getExamDate()!=null){
            existing.setExamDate(updatedExam.getExamDate());
        }

        if(updatedExam.getExamType()!=null){
            existing.setExamType(updatedExam.getExamType());
        }

        return examRepository.save(existing);
    }


    @Transactional
    public void deleteExam(ObjectId examId){
        if(examId==null){
            throw new IllegalArgumentException("Exam ID cannot be null");
        }
        Exam existing = getExamById(examId);

        examRepository.delete(existing);
    }

    @Transactional
    public Exam publishExam(ObjectId examId){
        Exam exam = getExamById(examId);


        if(exam.isPublished()){
            throw new IllegalStateException("Exam with ID: "+examId+" is already published");
        }

        exam.setPublished(true);

        return examRepository.save(exam);
    }

    @Transactional(readOnly = true)
    public List<Exam> getAllExams(){
        return examRepository.findAll();
    }

}