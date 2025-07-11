package org.example;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
public class DaoTest {

    private Dao dao;

    @Container
    private static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15")
            .withDatabaseName("homeworkdb")
            .withUsername("postgres")
            .withPassword("123321");

    @BeforeAll
    static void beforeAll() {
        postgres.start();
        System.setProperty("hibernate.connection.url", postgres.getJdbcUrl());
        System.setProperty("hibernate.connection.username", postgres.getUsername());
        System.setProperty("hibernate.connection.password", postgres.getPassword());
    }

    @AfterAll
    static void afterAll() {
        postgres.stop();
    }

    @BeforeEach
     void refresh(){
        dao = new Dao();
    }

    @Test
    void saveUser(){
        User user = new User();
        user.setName("ТестДокер");
        dao.save(user);

        assertNotNull(user.getId());
    }

    @Test
    void findAllUsers(){
        User user1 = new User();
        user1.setName("Тест1");
        dao.save(user1);

        User user2 = new User();
        user2.setName("Тест2");
        dao.save(user2);

        List<User> users = dao.findAll();

        assertTrue(users.size() >= 2); //перед применением должна быть таблица должна быть пустой
    }

    @Test
    void updateUser(){
        User user = new User();
        user.setName("ТестНеИзмененный");
        dao.save(user);
        user.setName("ТестИзмененный");
        dao.update(user);

        User updatedUser = dao.findById(user.getId());
        assertEquals("ТестИзмененный",updatedUser.getName());
    }

    @Test
    void deleteUser(){
        User user = new User();
        user.setName("ТестУдаления");
        dao.save(user);

        dao.delete(user.getId());
        assertNull(dao.findById(user.getId()));
    }

}
