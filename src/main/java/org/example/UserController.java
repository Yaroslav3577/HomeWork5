package org.example;

import org.example.notification.UserEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final UserRepository userRepository;
    private static final String USER_TOPIC = "user-events";

    @Autowired
    public UserController(UserRepository userRepository, KafkaTemplate<String ,String> kafkaTemplate) {
        this.userRepository = userRepository;
        this.kafkaTemplate = kafkaTemplate;
    }

    // Получить одного пользователя
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<User>> getUserById(@PathVariable Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        EntityModel<User> model = EntityModel.of(userOptional.get());
        model.add(createSelfLink(id));
        model.add(WebMvcLinkBuilder.linkTo(UserController.class).withRel("all-users"));
        return ResponseEntity.ok(model);
    }

    // Получить всех пользователей
    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<User>>> getAllUsers() {
        List<User> users = userRepository.findAll();
        List<EntityModel<User>> userModels = new ArrayList<>();

        for (User user : users) {
            EntityModel<User> model = EntityModel.of(user);
            model.add(createSelfLink(user.getId()));
            userModels.add(model);
        }

        Link allUsersLink = WebMvcLinkBuilder.linkTo(UserController.class).withSelfRel();
        return ResponseEntity.ok(CollectionModel.of(userModels, allUsersLink));
    }

    // Создать пользователя
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ResponseEntity<EntityModel<User>> createUser(@RequestBody User user) {
        User savedUser = userRepository.save(user);
        kafkaTemplate.send(USER_TOPIC, "CREATE", new UserEvent(savedUser, UserEvent.EventType.CREATED).toString());

        EntityModel<User> model = EntityModel.of(savedUser);
        model.add(createSelfLink(savedUser.getId()));
        return ResponseEntity.ok(model);
    }

    // Обновить пользователя
    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<User>> updateUser(@PathVariable Long id,
                                                        @RequestBody User userDetails) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        User user = userOptional.get();
        user.setName(userDetails.getName());
        user.setMail(userDetails.getMail());
        // другие поля по необходимости

        User updatedUser = userRepository.save(user);
        kafkaTemplate.send(USER_TOPIC, "UPDATE", new UserEvent(user, UserEvent.EventType.UPDATED).toString());

        EntityModel<User> model = EntityModel.of(updatedUser);
        model.add(createSelfLink(id));
        return ResponseEntity.ok(model);
    }

    // Удалить пользователя
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        kafkaTemplate.send(USER_TOPIC, "DELETE", new UserEvent(userOptional.get(),UserEvent.EventType.DELETED).toString());
        userRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    private Link createSelfLink(Long id) {
        return WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(UserController.class)
                        .getUserById(id)
        ).withSelfRel();
    }

}
