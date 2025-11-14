package server;

import java.util.HashMap;

public class UserDB {
    private static final HashMap<String, String> USERS = new HashMap<>();

    static {
        USERS.put("user1", "pass1");
        USERS.put("admin", "1234");
    }

    public static boolean authenticate(String login, String password) {
        return USERS.containsKey(login) && USERS.get(login).equals(password);
    }
}
