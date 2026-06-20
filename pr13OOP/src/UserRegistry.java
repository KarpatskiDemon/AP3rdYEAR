import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.function.Predicate;

public class UserRegistry implements Serializable {
    private final Map<UserIdentifier, User> users = new HashMap<>();
    private int nextId = 1;

    public void registerUser(String login, String password) {
        UserIdentifier key = new UserIdentifier(0, login);
        if (users.containsKey(key)) {
            System.out.println("Користувач [" + login + "] вже є у списку");
            return;
        }
        UserIdentifier id = new UserIdentifier(nextId++, login);
        users.put(id, new User(id, password));
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
            users.remove(user.getIdentifier());
        }
    }

    public void printTotalUniqueUsers() {
        System.out.println("Унікальних користувачів: " + users.size());
    }

    public void displayAllUsers() {
        for (User user : getUserList()) {
            System.out.println(user.getIdentifier().getName());
        }
    }

    public LinkedList<User> getUserList() {
        LinkedList<User> list = new LinkedList<>(users.values());
        list.sort(Comparator.comparingInt(u -> u.getIdentifier().getId()));
        return list;
    }

    public LinkedList<User> getInOrder(Comparator<User> comparator) {
        LinkedList<User> list = new LinkedList<>(users.values());
        list.sort(comparator);
        return list;
    }

    public LinkedList<User> getFiltered(Predicate<User> predicate) {
        LinkedList<User> list = new LinkedList<>();
        for (User user : users.values()) {
            if (predicate.test(user)) {
                list.add(user);
            }
        }
        return list;
    }

    public void save(String path) throws IOException {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(path))) {
            out.writeObject(this);
        }
    }

    public static UserRegistry load(String path) throws IOException, ClassNotFoundException {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(path))) {
            UserRegistry registry = (UserRegistry) in.readObject();
            for (User user : registry.users.values()) {
                user.setLoggedIn(false);
            }
            return registry;
        }
    }

    private User findByLogin(String login) {
        return users.get(new UserIdentifier(0, login));
    }

    private User findById(int id) {
        for (User user : users.values()) {
            if (user.getIdentifier().getId() == id) {
                return user;
            }
        }
        return null;
    }
}
