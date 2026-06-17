public class ArrayBlock {

    static final int BLOCK_SIZE = 4;

    Object[] data;
    int count;
    ArrayBlock next;
    ArrayBlock prev;

    ArrayBlock() {
        data = new Object[BLOCK_SIZE];
        count = 0;
        next = null;
        prev = null;
    }

    boolean isFull() {
        return count >= BLOCK_SIZE;
    }

    boolean isEmpty() {
        return count == 0;
    }
}
