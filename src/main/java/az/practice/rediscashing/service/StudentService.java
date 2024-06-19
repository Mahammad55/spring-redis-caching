package az.practice.rediscashing.service;

import az.practice.rediscashing.dto.StudentDto;
import az.practice.rediscashing.entity.Student;

public interface StudentService {
    void saveStudent(StudentDto studentDto);

    Student getStudentById(Long id);

    StudentDto getStudentByName(String name);

    void deleteStudentById(Long id);

    void updateStudentById(Long id, StudentDto studentDto);
}
