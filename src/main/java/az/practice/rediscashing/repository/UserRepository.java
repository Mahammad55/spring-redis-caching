package az.practice.rediscashing.repository;

import az.practice.rediscashing.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findUserBySurname(String surname);

    User findUserByAddress(String address);

    User findUserByName(String name);
}

