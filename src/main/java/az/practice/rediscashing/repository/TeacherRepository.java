package az.practice.rediscashing.repository;

import az.practice.rediscashing.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher,Long> {
    Teacher findByName(String name);
}
