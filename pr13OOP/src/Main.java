import java.util.LinkedList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        UserRegistry registry = new UserRegistry();

        System.out.print("Відновити базу? (y/n): ");
        if (scanner.nextLine().equalsIgnoreCase("y")) {
            System.out.print("Шлях до файлу: ");
            try {
                registry = UserRegistry.load(scanner.nextLine());
                System.out.println("Базу відновлено");
            } catch (Exception e) {
                System.out.println("Помилка відновлення");
            }
        }

        while (true) {
            System.out.println("\n1. Реєстрація");
            System.out.println("2. Вхід");
            System.out.println("3. Вихід з системи");
            System.out.println("4. Перевірити реєстрацію");
            System.out.println("5. Видалити користувача");
            System.out.println("6. Кількість унікальних користувачів");
            System.out.println("7. Показати всіх користувачів");
            System.out.println("8. Список за іменем (A-Z)");
            System.out.println("9. Тільки увійшли в систему");
            System.out.println("0. Завершити");
            System.out.print("Вибір: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1" -> {
                    System.out.print("Логін: ");
                    String login = scanner.nextLine();
                    System.out.print("Пароль: ");
                    String password = scanner.nextLine();
                    registry.registerUser(login, password);
                }
                case "2" -> {
                    System.out.print("Логін: ");
                    String login = scanner.nextLine();
                    System.out.print("Пароль: ");
                    String password = scanner.nextLine();
                    registry.loginUser(login, password);
                }
                case "3" -> {
                    System.out.print("ID користувача: ");
                    int id = Integer.parseInt(scanner.nextLine());
                    registry.logoutUser(id);
                }
                case "4" -> {
                    System.out.print("Логін: ");
                    String login = scanner.nextLine();
                    System.out.println(registry.isUserRegistered(login) ? "Зареєстрований" : "Не зареєстрований");
                }
                case "5" -> {
                    System.out.print("ID користувача: ");
                    int id = Integer.parseInt(scanner.nextLine());
                    registry.removeUser(id);
                }
                case "6" -> registry.printTotalUniqueUsers();
                case "7" -> printUsers(registry.getUserList());
                case "8" -> printUsers(registry.getInOrder(
                        (a, b) -> a.getIdentifier().getName().compareTo(b.getIdentifier().getName())));
                case "9" -> printUsers(registry.getFiltered(User::isLoggedIn));
                case "0" -> {
                    System.out.print("Зберегти базу? (y/n): ");
                    if (scanner.nextLine().equalsIgnoreCase("y")) {
                        System.out.print("Шлях до файлу: ");
                        try {
                            registry.save(scanner.nextLine());
                            System.out.println("Базу збережено");
                        } catch (Exception e) {
                            System.out.println("Помилка збереження");
                        }
                    }
                    scanner.close();
                    return;
                }
                default -> System.out.println("Невірний вибір");
            }
        }
    }

    private static void printUsers(LinkedList<User> users) {
        for (User user : users) {
            System.out.println(user.getIdentifier().getId() + " - " + user.getIdentifier().getName());
        }
    }
}
