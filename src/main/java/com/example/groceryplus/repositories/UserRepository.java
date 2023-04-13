package com.example.groceryplus.repositories;
import com.example.groceryplus.model.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UserRepository {
    List<User> users = new ArrayList<>(List.of(
            new User("Asger", "1234")

    ));

    public User getUser(String uid) {
        for (User user:users) {
            if (user.getUid().equals(uid))
                return user;
        }
        return null;
    }
}
