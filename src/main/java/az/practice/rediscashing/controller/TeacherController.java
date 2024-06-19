package az.practice.rediscashing.controller;

import az.practice.rediscashing.dto.TeacherDto;
import az.practice.rediscashing.entity.Teacher;
import az.practice.rediscashing.service.TeacherService;
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
@RequestMapping("/api/v1/teachers")
@RequiredArgsConstructor
public class TeacherController {
    private final TeacherService teacherService;

    @PostMapping
    public void saveTeacher(@RequestBody TeacherDto teacherDto) {
        teacherService.saveTeacher(teacherDto);
    }

    @GetMapping("/id/{id}")
    public Teacher getTeacherById(@PathVariable Long id) {
        return teacherService.getTeacherById(id);
    }

    @GetMapping("/name/{name}")
    public Teacher getTeacherByName(@PathVariable String name) {
        return teacherService.getTeacherByName(name);
    }

    @DeleteMapping("/{id}")
    public void deleteTeacherById(@PathVariable Long id) {
        teacherService.deleteTeacherById(id);
    }

    @PutMapping("/{id}")
    public void updateTeacherById(@PathVariable Long id, @RequestBody TeacherDto teacherDto) {
        teacherService.updateTeacherById(id, teacherDto);
    }
}
