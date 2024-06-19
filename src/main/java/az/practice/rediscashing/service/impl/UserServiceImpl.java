package az.practice.rediscashing.service.impl;

import az.practice.rediscashing.dto.UserDto;
import az.practice.rediscashing.entity.User;
import az.practice.rediscashing.repository.UserRepository;
import az.practice.rediscashing.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    private final ModelMapper mapper;

    @Override
    @Cacheable(cacheNames = "user", key = "#id")
    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow();
    }

    @Override
    @Cacheable(cacheNames = "user", key = "#surname")
    public User getUserBySurname(String surname) {
        return userRepository.findUserBySurname(surname);
    }

    @Override
    @Cacheable(cacheNames = "user", key = "#address.length()")
    public User getUserByAddress(String address) {
        return userRepository.findUserByAddress(address);
    }

    @Override
    @Cacheable(cacheNames = "user", key = "#name", unless = "#name.lastIndexOf('d')==#name.length()-1")
    public User getUserByName(String name) {
        return userRepository.findUserByName(name);
    }

    @Override
    @Cacheable(cacheNames = "user", key = "#userDto.name")
    public User saveUser(UserDto userDto) {
        return userRepository.save(mapper.map(userDto, User.class));
    }

    @Override
    @Cacheable(cacheNames = "user", key = "@userRepository.findAll()")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    @CachePut(cacheNames = "user", key = "#id")
    public User updateUserById(Long id, UserDto userDto) {
        User user = mapper.map(userDto, User.class);
        user.setId(id);
        return userRepository.save(user);
    }

    @Override
    @CacheEvict(cacheNames = "user", key = "#id")
    public User deleteUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow();
        userRepository.delete(user);
        return user;
    }
}

