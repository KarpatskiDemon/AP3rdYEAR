import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

public class UserRegistry {
    private final Set<User> users = new HashSet<>();
    private int nextId = 1;

    public void registerUser(String login, String password) {
        User probe = new User(0, login, password);
        if (users.contains(probe)) {
            System.out.println("Користувач [" + login + "] вже є у списку");
            return;
        }
        users.add(new User(nextId++, login, password));
    }

    public void loginUser(String login, String password) {
        User user = findByLogin(login);
        if (user == null || !user.getPassword().equals(password)) {
            System.out.println("Неможливо ідентифікувати або аутентифікувати користувача");
            return;
        }
        user.setLoggedIn(true);
        user.setLastLoginDate(LocalDateTime.now());
    }

    public void logoutUser(int userId) {
        User user = findById(userId);
        if (user != null) {
            user.setLoggedIn(false);
        }
    }

    public boolean isUserRegistered(String login) {
        return findByLogin(login) != null;
    }

    public void removeUser(int id) {
        User user = findById(id);
        if (user != null) {
            users.remove(user);
        }
    }

    public void printTotalUniqueUsers() {
        System.out.println("Унікальних користувачів: " + users.size());
    }

    public void displayAllUsers() {
        for (User user : users) {
            System.out.println(user.getName());
        }
    }

    private User findByLogin(String login) {
        for (User user : users) {
            if (user.getName().equals(login)) {
                return user;
            }
        }
        return null;
    }

    private User findById(int id) {
        for (User user : users) {
            if (user.getId() == id) {
                return user;
            }
        }
        return null;
    }
}
