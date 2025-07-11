package org.example;


public class Dto {

    private Long id;
    private String name;
    private String mail;

    public Dto() {}

    public Dto(String name, Long id ) {
        this.id = id;
        this.name = name;
    }
    public Dto(String name, Long id, String mail ) {
        this.id = id;
        this.name = name;
        this.mail = mail;
    }

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

    @Override
    public String toString() {
        return "User [id = " + id + ", name = " + name +", mail = " + mail +"]";
    }
}
