package com.brij;

import java.util.HashMap;

public class UserHelper {
    static HashMap<String, User> users = new HashMap<>();

    static {
        addUser("1", "user 1", "user1@gmail.com");
        addUser("2", "user 2", "user2@gmail.com");
        addUser("3", "user 3", "user3@gmail.com");
    }

    public static void addUser(String id, String name, String email) {
        User user = new User();
        user.id = id;
        user.name = name;
        user.email = email;
        users.put(id, user);
    }

    static public User getUser(String id) {
        return users.get(id);
    }
    public static void addUser(User user) {
        users.put(user.id, user);
    }
}
