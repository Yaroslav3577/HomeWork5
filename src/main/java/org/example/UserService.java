package org.example;

import java.util.List;

public class UserService {
    private Dao dao;

    public UserService (Dao dao){
        this.dao = dao;
    }

    public void CreateUser(User user){
        dao.save(user);
    }

    public List<User> FindAll(){
        return dao.findAll();
    }

    public void update(User user){
        dao.update(user);
    }

    public void delete(Long id){
        dao.delete(id);
    }
}
