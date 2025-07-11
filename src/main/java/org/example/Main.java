package org.example;

import java.util.List;
import java.util.Scanner;

public class Main {
        private static Scanner scanner = new Scanner(System.in);
    private static Dao dao = new Dao();
    public static void main(String[] args) {
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
        System.out.print("ведите id пользователя для удаления: ");
        Long id = scanner.nextLong();
        scanner.nextLine();
        User user = dao.findById(id);
        if (user != null) {
            dao.delete(id);
            System.out.println("Пользователь успешно удалён!");
        } else {
            System.out.println("Пользователь с ID " + id + " не найден.");
        }
    }

    private static void updateUsers() {
        System.out.print("Введите id пользователя для обновления данных: ");
        Long id = scanner.nextLong();
        scanner.nextLine();
        User user = dao.findById(id);
        if (user != null) {
            System.out.println("Текущие данные: " + user);
            System.out.println("Введите новое имя: ");
            String name = scanner.nextLine();
            user.setName(name);
            dao.update(user);
            System.out.println("Пользователь успешно обновлён");
        } else {
            System.out.println("Пользователь с ID " + id + " не найден.");
        }
    }

    private static void readUsers() {
        List<User> users = dao.findAll();
        if (users != null && !users.isEmpty()) {
            System.out.println("\nСписок пользователей:");
            for (User user : users) {
                System.out.println(user);
            }
        } else {
            System.out.println("Пользователь не найден.");
        }
    }

    private static void createUser() {
        System.out.print("Введите имя: ");
        String name = scanner.nextLine();
        User user = new User();
        user.setName(name);
        dao.save(user);
        System.out.println("пользователь успешно создан с id: " + user.getId());
    }
}
