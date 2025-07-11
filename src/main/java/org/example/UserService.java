//package org.example;
//
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Service
//public class UserService {
//    private final UserRepository userRepository;
//
//    public UserService(UserRepository userRepository) {
//        this.userRepository = userRepository;
//    }
//
//    @Transactional
//    public Dto createUser(Dto userDto) {
//        User user = new User(userDto.getName());
//        user = userRepository.save(user);
//        return new Dto(user.getId(), user.getName());
//    }
//
//    @Transactional(readOnly = true)
//    public Dto getUserById(Long id) {
//        return userRepository.findById(id)
//                .map(user -> new Dto(user.getId(), user.getName()))
//                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));
//    }
//
//    @Transactional(readOnly = true)
//    public List<Dto> getAllUsers() {
//        return userRepository.findAll().stream()
//                .map(user -> new Dto(user.getId(), user.getName()))
//                .collect(Collectors.toList());
//    }
//
//    @Transactional
//    public Dto updateUser(Long id, Dto userDto) {
//        User user = userRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));
//        user.setName(userDto.getName());
//        user = userRepository.save(user);
//        return new Dto(user.getId(), user.getName());
//    }
//
//    @Transactional
//    public void deleteUser(Long id) {
//        userRepository.deleteById(id);
//    }
//}