package com.example.application.data.service;



import com.example.application.data.entity.School;
import com.example.application.data.entity.Teacher;
import com.example.application.data.entity.Type;
import com.example.application.data.repository.SchoolRepository;

import com.example.application.data.repository.TeacherRepository;
import com.example.application.data.repository.TypeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CrmService {

    private final TeacherRepository teacherRepository;
    private final SchoolRepository schoolRepository;
    private final TypeRepository typeRepository;

    public CrmService(TeacherRepository teacherRepository,
                      SchoolRepository schoolRepository,
                      TypeRepository typeRepository) {
        this.teacherRepository = teacherRepository;
        this.schoolRepository = schoolRepository;
        this.typeRepository = typeRepository;
    }

    public List<Teacher> findAllTeachers(String stringFilter) {
        if (stringFilter == null || stringFilter.isEmpty()) {
            return teacherRepository.findAll();
        } else {
            return teacherRepository.search(stringFilter);
        }
    }

    public long countTeachers() {
        return teacherRepository.count();
    }

    public void deleteTeacher(Teacher teacher) {
        teacherRepository.delete(teacher);
    }

    public void saveTeacher(Teacher teacher) {
        if (teacher == null) {
            System.err.println("Teacher is null. Are you sure you have connected your form to the application?");
            return;
        }
        teacherRepository.save(teacher);
    }

    public List<School> findAllSchools() {
        return schoolRepository.findAll();
    }

    public List<Type> findAllTypes(){
        return typeRepository.findAll();
    }
}
