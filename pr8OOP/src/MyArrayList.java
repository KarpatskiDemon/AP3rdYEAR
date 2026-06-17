public class MyArrayList {

    private ArrayBlock head;
    private int size;

    public MyArrayList() {
        head = new ArrayBlock();
        size = 0;
    }

    public MyArrayList(int capacity) throws InvalidCapacityException, InvalidSizeException {
        if (capacity < 0) {
            throw new InvalidCapacityException("Capacity не може бути від'ємним: " + capacity);
        }
        if (capacity > 100) {
            throw new InvalidSizeException("Capacity занадто великий: " + capacity);
        }
        head = new ArrayBlock();
        size = 0;
    }

    public void add(Object value) {
        checkNull(value);
        ArrayBlock block = head;
        while (block.next != null) {
            block = block.next;
        }

        if (block.isFull()) {
            ArrayBlock newBlock = new ArrayBlock();
            newBlock.data[0] = value;
            newBlock.count = 1;
            newBlock.prev = block;
            block.next = newBlock;
        } else {
            block.data[block.count] = value;
            block.count++;
        }
        size++;
    }

    public void addFirst(Object value) {
        checkNull(value);
        if (head.isFull()) {
            ArrayBlock newHead = new ArrayBlock();
            newHead.data[0] = value;
            newHead.count = 1;
            newHead.next = head;
            head.prev = newHead;
            head = newHead;
        } else {
            for (int i = head.count; i > 0; i--) {
                head.data[i] = head.data[i - 1];
            }
            head.data[0] = value;
            head.count++;
        }
        size++;
    }

    public void add(int index, Object value) {
        checkNull(value);
        checkIndexForAdd(index);
        int pos = 0;
        ArrayBlock block = head;

        while (block != null) {
            if (index <= pos + block.count) {
                int localIndex = index - pos;
                insertIntoBlock(block, localIndex, value);
                size++;
                return;
            }
            pos += block.count;
            block = block.next;
        }
    }

    private void insertIntoBlock(ArrayBlock block, int localIndex, Object value) {
        if (!block.isFull()) {
            for (int i = block.count; i > localIndex; i--) {
                block.data[i] = block.data[i - 1];
            }
            block.data[localIndex] = value;
            block.count++;
            return;
        }

        Object overflow = block.data[ArrayBlock.BLOCK_SIZE - 1];
        for (int i = block.count - 1; i > localIndex; i--) {
            block.data[i] = block.data[i - 1];
        }
        block.data[localIndex] = value;

        if (block.next == null) {
            ArrayBlock newBlock = new ArrayBlock();
            newBlock.data[0] = overflow;
            newBlock.count = 1;
            newBlock.prev = block;
            block.next = newBlock;
        } else {
            insertIntoBlock(block.next, 0, overflow);
        }
    }

    public Object get(int index) {
        checkIndex(index);
        int pos = 0;
        ArrayBlock block = head;

        while (block != null) {
            if (index < pos + block.count) {
                return block.data[index - pos];
            }
            pos += block.count;
            block = block.next;
        }
        return null;
    }

    public Object getFirst() {
        if (size == 0) {
            throw new EmptyListException("Список порожній");
        }
        return get(0);
    }

    public int size() {
        return size;
    }

    public int capacity() {
        int blocks = 0;
        ArrayBlock block = head;
        while (block != null) {
            blocks++;
            block = block.next;
        }
        return blocks * ArrayBlock.BLOCK_SIZE;
    }

    public void remove(int index) {
        checkIndex(index);
        int pos = 0;
        ArrayBlock block = head;

        while (block != null) {
            if (index < pos + block.count) {
                int localIndex = index - pos;
                for (int i = localIndex; i < block.count - 1; i++) {
                    block.data[i] = block.data[i + 1];
                }
                block.data[block.count - 1] = null;
                block.count--;

                if (block.isEmpty() && block != head) {
                    removeBlock(block);
                }
                size--;
                return;
            }
            pos += block.count;
            block = block.next;
        }
    }

    private void removeBlock(ArrayBlock block) {
        if (block.prev != null) {
            block.prev.next = block.next;
        }
        if (block.next != null) {
            block.next.prev = block.prev;
        }
    }

    public void clear() {
        head = new ArrayBlock();
        size = 0;
    }

    private void checkNull(Object value) {
        if (value == null) {
            throw new NullValueException("Значення не може бути null");
        }
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new InvalidIndexException("Індекс: " + index + ", розмір: " + size);
        }
    }

    private void checkIndexForAdd(int index) {
        if (index < 0 || index > size) {
            throw new InvalidIndexException("Індекс: " + index + ", розмір: " + size);
        }
    }
}
