package org.example;

import jakarta.persistence.*;


@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column
    private String mail;

    // Конструкторы, геттеры и сеттеры
    public User() {
    }

    public User(String name) {
        this.name = name;
    }
    public User(String name, Long id) {
        this.name = name;
        this.id = id;
    }

    public User(String name, Long id, String mail) {
        this.name = name;
        this.id = id;
        this.mail = mail;
    }

    // Геттеры и сеттеры для всех полей
    public String getMail(){return  mail;}

    public void  setMail(String mail){this.mail = mail;}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
