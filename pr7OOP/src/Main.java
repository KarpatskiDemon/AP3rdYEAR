public class Main {

    public static void main(String[] args) {
        MyArrayList list = new MyArrayList();

        System.out.println("=== Додавання в кінець ===");
        list.add("A");
        list.add("B");
        list.add("C");
        printList(list);

        System.out.println("\n=== Додавання на початок ===");
        list.addFirst("START");
        printList(list);

        System.out.println("\n=== Додавання в середину (індекс 2) ===");
        list.add(2, "MIDDLE");
        printList(list);

        System.out.println("\n=== Отримання елемента get(2) ===");
        System.out.println("Елемент: " + list.get(2));

        System.out.println("\n=== Метрики ===");
        System.out.println("size: " + list.size());
        System.out.println("capacity: " + list.capacity());

        System.out.println("\n=== Видалення за індексом 1 ===");
        list.remove(1);
        printList(list);

        System.out.println("\n=== Очищення ===");
        list.clear();
        System.out.println("size після clear: " + list.size());
        System.out.println("capacity після clear: " + list.capacity());
    }

    private static void printList(MyArrayList list) {
        System.out.print("Елементи: ");
        for (int i = 0; i < list.size(); i++) {
            System.out.print(list.get(i));
            if (i < list.size() - 1) {
                System.out.print(", ");
            }
        }
        System.out.println();
        System.out.println("size=" + list.size() + ", capacity=" + list.capacity());
    }
}
