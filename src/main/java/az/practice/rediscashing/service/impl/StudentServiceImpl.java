package az.practice.rediscashing.service.impl;

import az.practice.rediscashing.dto.StudentDto;
import az.practice.rediscashing.entity.Student;
import az.practice.rediscashing.repository.StudentRepository;
import az.practice.rediscashing.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "student")
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;

    private final ModelMapper mapper;

    private final CacheManager cacheManager;

    @Override
    public void saveStudent(StudentDto studentDto) {
        Cache cache = cacheManager.getCache("student");
        Student savedStudent = studentRepository.save(mapper.map(studentDto, Student.class));
        cache.put(savedStudent.getId(), savedStudent);
    }

    @Override
    @Cacheable(key = "#id")
    public Student getStudentById(Long id) {
        return studentRepository.findById(id).orElseThrow();
    }

    @Override
    public StudentDto getStudentByName(String name) {
        Cache cache = cacheManager.getCache("student");
        Student cacheStudent = cache.get(name, Student.class);
        if (cacheStudent == null) {
            Student student = studentRepository.findByName(name);
            cache.put(name, student);
            return mapper.map(student, StudentDto.class);
        }
        return mapper.map(cacheStudent, StudentDto.class);
    }

    @Override
    public void deleteStudentById(Long id) {
        Cache cache = cacheManager.getCache("student");
        studentRepository.deleteById(id);
        if (cache.get(id, Student.class) != null) cache.evict(id);
    }

    @Override
    public void updateStudentById(Long id, StudentDto studentDto) {
        Cache cache = cacheManager.getCache("student");
        Student cacheStudent = cache.get(id, Student.class);
        Student student = mapper.map(studentDto, Student.class);
        student.setId(id);
        Student savedStudent = studentRepository.save(student);
        if (cacheStudent != null) {
            cache.evict(id);
            cache.put(id, savedStudent);
        }
    }
}