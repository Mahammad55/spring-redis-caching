package az.practice.rediscashing.service;

import az.practice.rediscashing.dto.UserDto;
import az.practice.rediscashing.entity.User;

import java.util.List;

public interface UserService {
    User getUserById(Long id);

    User getUserBySurname(String surname);

    User getUserByAddress(String address);

    User getUserByName(String name);

    User saveUser(UserDto userDto);

    List<User> getAllUsers();

    User updateUserById(Long id, UserDto userDto);

    User deleteUserById(Long id);
}
