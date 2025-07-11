package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Scanner;


@SpringBootApplication
public class Main {
    private static final String API_URL = "http://localhost:8080/api/users";
    private static Scanner scanner = new Scanner(System.in);
    private static RestTemplate restTemplate;
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(Main.class, args);
        restTemplate = context.getBean(RestTemplateBuilder.class).build();
        boolean running = true;
        while(running){
            System.out.println("\n=== Меню управления пользователями ===");
            System.out.println("1. Создать нового пользователя");
            System.out.println("2. Список пользователей");
            System.out.println("3. Обновить данные пользователя");
            System.out.println("4. Удалить пользователя");
            System.out.println("5. Выход");
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice){
                case 1:
                    createUser();
                    continue;
                case 2:
                    readUsers();
                    continue;
                case 3:
                    updateUsers();
                    continue;
                case 4:
                    deleteUser();
                    continue;
                case 5:
                    running = false;
                    continue;
                default:
                    System.out.println("Ошибка ввода данных");
            }
        }
    }
    private static void deleteUser() {
        System.out.print("Введите ID пользователя для удаления: ");
        Long id = scanner.nextLong();
        try {
            restTemplate.delete(API_URL + "/" + id);
            System.out.println("Пользователь удален.");
        } catch (Exception e) {
            System.out.println("Пользователь не найден");
        }
    }

    private static void updateUsers() {
        System.out.print("Введите ID пользователя для обновления: ");
        Long id = scanner.nextLong();
        scanner.nextLine();
        Dto oldUser = restTemplate.getForObject(API_URL + "/" + id, Dto.class);
        System.out.println("Введите новое имя");
        String newName = scanner.nextLine();
        if(newName.isEmpty()){
            System.out.println("не введено имя, сохраняем старое: "+oldUser.getName());
            newName = oldUser.getName();
        }
        System.out.println("Введите новую почту");
        String newMail = scanner.nextLine();
        if(newName.isEmpty()){
            System.out.println("не введена почта, сохраняем старую: "+oldUser.getMail());
            newMail = oldUser.getMail();
        }
        Dto update = new Dto();
        update.setId(id);
        update.setName(newName);
        update.setMail(newMail);
            restTemplate.put(API_URL + "/" + id, update);
            System.out.println("Cохранено");
    }

    private static void readUsers() {
            ResponseEntity<Dto[]> response = restTemplate.getForEntity(API_URL, Dto[].class);
            System.out.println("\n=== Список пользователей ===");
            Arrays.stream(response.getBody())
                    .forEach(user -> System.out.printf("ID: %d | Имя: %s%n", user.getId(), user.getName()));
    }

    private static void createUser() {
        boolean restart = true;
        String name;
        String mail;
        do {
            System.out.print("Введите имя пользователя: ");
            name = scanner.nextLine();
            if(name.isEmpty()){
                System.out.println("не введено имя, попробуйте еще раз");
            }
            else {restart= false;}
        }while (restart);
        restart = true;
        do {
            System.out.print("Введите электронную почту пользователя: ");
            mail = scanner.nextLine();
            if(mail.isEmpty()){
                System.out.println("не введена почта, попробуйте еще раз");
            }
            else {restart= false;}
        }while (restart);
        Dto dto = new Dto();
        dto.setName(name);
        dto.setMail(mail);
        ResponseEntity<Dto> response = restTemplate.postForEntity(API_URL, dto, Dto.class);
        System.out.println("Создан пользователь: " + response.getBody());
    }
}
