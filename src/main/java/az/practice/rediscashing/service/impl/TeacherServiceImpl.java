package az.practice.rediscashing.service.impl;

import az.practice.rediscashing.dto.TeacherDto;
import az.practice.rediscashing.entity.Teacher;
import az.practice.rediscashing.repository.TeacherRepository;
import az.practice.rediscashing.service.TeacherService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class TeacherServiceImpl implements TeacherService {
    private final TeacherRepository teacherRepository;

    private final RedisTemplate<String, Object> redisTemplate;

    private final ModelMapper mapper;

    private final static String HASH_KEY = "teacher";

    @Override
    public void saveTeacher(TeacherDto teacherDto) {
        Teacher savedTeacher = teacherRepository.save(mapper.map(teacherDto, Teacher.class));
        redisTemplate.opsForHash().put(HASH_KEY, savedTeacher.getId().toString(), savedTeacher);
    }

    @Override
    public Teacher getTeacherById(Long id) {
        Teacher cacheTeacher = (Teacher) redisTemplate.opsForHash().get(HASH_KEY, id.toString());
        if (cacheTeacher == null) {
            Teacher teacher = teacherRepository.findById(id).orElseThrow();
            redisTemplate.opsForHash().put(HASH_KEY, id.toString(), teacher);
            return teacher;
        }
        return cacheTeacher;
    }

    @Override
    public void deleteTeacherById(Long id) {
        Teacher cacheTeacher = (Teacher) redisTemplate.opsForHash().get(HASH_KEY, id.toString());
        teacherRepository.deleteById(id);
        if (cacheTeacher != null) redisTemplate.opsForHash().delete(HASH_KEY, id.toString());
    }

    @Override
    public void updateTeacherById(Long id, TeacherDto teacherDto) {
        Teacher cacheTeacher = (Teacher) redisTemplate.opsForHash().get(HASH_KEY, id.toString());
        Teacher teacher = mapper.map(teacherDto, Teacher.class);
        teacher.setId(id);
        Teacher savedTeacher = teacherRepository.save(teacher);
        if (cacheTeacher != null) {
            redisTemplate.opsForHash().delete(HASH_KEY, id.toString());
            redisTemplate.opsForHash().put(HASH_KEY, id.toString(), savedTeacher);
        }
    }

    @Override
    public Teacher getTeacherByName(String name) {
        ValueOperations<String, Object> value = redisTemplate.opsForValue();
        Teacher cacheTeacher = (Teacher) value.get(HASH_KEY + name);
        if (cacheTeacher == null) {
            Teacher teacher = teacherRepository.findByName(name);
            value.set(HASH_KEY + name, teacher, 5, TimeUnit.MINUTES);
            return teacher;
        }
        return cacheTeacher;
    }
}
