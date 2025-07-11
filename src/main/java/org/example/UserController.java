package org.example;

import org.example.notification.UserEvent;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final UserRepository userRepository;

    public UserController(UserRepository userRepository, KafkaTemplate<String ,String> kafkaTemplate) {
        this.userRepository = userRepository;
        this.kafkaTemplate = kafkaTemplate;
    }

    // Получить одного пользователя
    @GetMapping("/{id}")
    public User getUser(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Не найден пользователь с id: " + id
        ));
    }
    // Получить всех пользователей
    @GetMapping
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Создать пользователя
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User savedUser = userRepository.save(user);
        kafkaTemplate.send("user-events",
                savedUser.getMail(),
                new UserEvent(savedUser.getMail(), UserEvent.EventType.CREATED).toMessageString()
        );
        return ResponseEntity.ok(savedUser);
    }

    // Обновить пользователя
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(
            @PathVariable Long id,
            @RequestBody User userDetails
    ) {
        return userRepository.findById(id)
                .map(user -> {
                    user.setName(userDetails.getName());
                    return ResponseEntity.ok(userRepository.save(user));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // Удалить пользователя
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Long id) {
        userRepository.deleteById(id);
    }

}
