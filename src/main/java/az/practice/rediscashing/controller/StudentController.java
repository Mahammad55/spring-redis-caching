package az.practice.rediscashing.controller;

import az.practice.rediscashing.dto.StudentDto;
import az.practice.rediscashing.entity.Student;
import az.practice.rediscashing.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/students")
@RequiredArgsConstructor
public class StudentController {
    private final StudentService studentService;

    @PostMapping
    public void saveStudent(@RequestBody StudentDto studentDto) {
        studentService.saveStudent(studentDto);
    }

    @GetMapping("/id/{id}")
    public Student getStudentById(@PathVariable Long id) {
        return studentService.getStudentById(id);
    }

    @GetMapping("/name/{name}")
    public StudentDto getStudentByName(@PathVariable String name) {
        return studentService.getStudentByName(name);
    }

    @DeleteMapping("/{id}")
    public void deleteStudentById(@PathVariable Long id) {
        studentService.deleteStudentById(id);
    }

    @PutMapping("/{id}")
    public void updateStudentById(@PathVariable Long id, @RequestBody StudentDto studentDto) {
        studentService.updateStudentById(id, studentDto);
    }
}
