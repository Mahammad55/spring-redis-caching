package az.practice.rediscashing.controller;

import az.practice.rediscashing.dto.UserDto;
import az.practice.rediscashing.entity.User;
import az.practice.rediscashing.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/id/{id}")
    public User getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @GetMapping("/surname/{surname}")
    public User getUserBySurname(@PathVariable String surname) {
        return userService.getUserBySurname(surname);
    }

    @GetMapping("/address/{address}")
    public User getUserByAddress(@PathVariable String address) {
        return userService.getUserByAddress(address);
    }

    @GetMapping("/name/{name}")
    public User getUserByName(@PathVariable String name) {
        return userService.getUserByName(name);
    }

    @PostMapping
    public User saveUser(@RequestBody UserDto userDto) {
        return userService.saveUser(userDto);
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @PutMapping("/{id}")
    public User updateUserById(@PathVariable Long id, @RequestBody UserDto userDto) {
        return userService.updateUserById(id, userDto);
    }

    @DeleteMapping("/{id}")
    public User deleteUserById(@PathVariable Long id) {
        return userService.deleteUserById(id);
    }
}
