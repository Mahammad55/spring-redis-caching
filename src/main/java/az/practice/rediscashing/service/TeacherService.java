package az.practice.rediscashing.service;

import az.practice.rediscashing.dto.TeacherDto;
import az.practice.rediscashing.entity.Teacher;

public interface TeacherService {
    void saveTeacher(TeacherDto teacherDto);

    Teacher getTeacherById(Long id);

    void deleteTeacherById(Long id);

    void updateTeacherById(Long id, TeacherDto teacherDto);

    Teacher getTeacherByName(String name);
}
