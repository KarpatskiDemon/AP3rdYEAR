public class Main {

    public static void main(String[] args) {
        System.out.println("=== Перевірені виключення (checked) ===");

        try {
            MyArrayList list1 = new MyArrayList(-5);
        } catch (InvalidCapacityException e) {
            System.out.println("InvalidCapacityException: " + e.getMessage());
        } catch (InvalidSizeException e) {
            System.out.println("InvalidSizeException: " + e.getMessage());
        }

        try {
            MyArrayList list2 = new MyArrayList(200);
        } catch (InvalidCapacityException e) {
            System.out.println("InvalidCapacityException: " + e.getMessage());
        } catch (InvalidSizeException e) {
            System.out.println("InvalidSizeException: " + e.getMessage());
        }

        System.out.println("\n=== Неперевірені виключення (unchecked) ===");

        MyArrayList list = new MyArrayList();

        try {
            list.add("A");
            list.add("B");
            list.get(10);
        } catch (InvalidIndexException e) {
            System.out.println("InvalidIndexException: " + e.getMessage());
        }

        try {
            list.add(null);
        } catch (NullValueException e) {
            System.out.println("NullValueException: " + e.getMessage());
        }

        try {
            MyArrayList empty = new MyArrayList();
            empty.getFirst();
        } catch (EmptyListException e) {
            System.out.println("EmptyListException: " + e.getMessage());
        }

        System.out.println("\n=== Нормальна робота ===");
        list.add("C");
        printList(list);
        System.out.println("Перший елемент: " + list.getFirst());
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
