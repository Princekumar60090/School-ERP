package com.schoolerp.SchoolERP.service;

import com.schoolerp.SchoolERP.entity.Subject;
import com.schoolerp.SchoolERP.repository.SubjectRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SubjectService {

    @Autowired
    private SubjectRepository subjectRepository;

    public Subject saveSubject(Subject subject){
        Optional<Subject> existingSubject=subjectRepository.findBySubjectCode(subject.getSubjectCode());
        if(existingSubject.isPresent()){
            throw new IllegalStateException("Subject with code '" + subject.getSubjectCode() + "' already exists.");

        }

       return  subjectRepository.save(subject);
    }

    public List<Subject> getAllSubjects(){
        return subjectRepository.findAll();
    }

    public Optional<Subject> getSubjectById(ObjectId id){
        return subjectRepository.findById(id);
    }

    public Subject updateSubject(ObjectId id,Subject subjectDetails){
        Optional<Subject> subjectOptional = subjectRepository.findById(id);
        if(subjectOptional.isPresent()){
            Subject existingSubject = subjectOptional.get();
            existingSubject.setSubjectName(subjectDetails.getSubjectName());
            existingSubject.setSubjectCode(subjectDetails.getSubjectCode());
            return subjectRepository.save(existingSubject);
        }
        return null;
    }

    public void deleteSubjectById(ObjectId id){
        subjectRepository.deleteById(id);
    }
}
