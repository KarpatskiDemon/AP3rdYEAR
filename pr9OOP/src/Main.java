public class Main {

    public static void main(String[] args) {
        MyArrayList list1 = new MyArrayList();
        list1.add(new Student("Ivan", 20));
        list1.add(new Student("Anna", 18));
        list1.add(new Student("Borys", 22));
        
        MyArrayList list2 = new MyArrayList();
        list2.add(new Student("Ivan", 20));
        list2.add(new Student("Anna", 18));
        list2.add(new Student("Borys", 22));

        System.out.println("list1.compareTo(list2) (однакові): " + list1.compareTo(list2));
        
        list2.add(new Student("Zoe", 25));
        System.out.println("list1.compareTo(list2) (list2 довший): " + list1.compareTo(list2));
        
        System.out.println("До sort():");
        printList(list1);

        list1.sort();
        System.out.println("Після sort() (Comparable):");
        printList(list1);

        MyArrayList list3 = new MyArrayList();
        list3.add(new Student("Maria", 19));
        list3.add(new Student("Oleg", 17));
        list3.add(new Student("Petro", 21));

        System.out.println("До sort(Comparator):");
        printList(list3);

        list3.sort(new StudentAgeComparator());
        System.out.println("Після sort(Comparator):");
        printList(list3);
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
    }
}
