import interface_form.List;

import java.util.Arrays;
import java.util.Comparator;
import java.util.NoSuchElementException;

public class DoubleLinkedList<E> implements List<E>, Cloneable {

    private Node<E> head;
    private Node<E> tail;
    private int size;

    public DoubleLinkedList() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    private Node<E> search(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        if (index > size / 2) {
            Node<E> x = tail;
            for (int i = size - 1; i > index; i--) {
                x = x.prev;
            }
            return x;
        }
        else {
            Node<E> x = head;
            for (int i = 0; i < index; i++) {
                x = x.next;
            }
            return x;
        }
    }

    public void addFirst(E value) {
        Node<E> newNode = new Node<E>(value);
        newNode.next = head;

        if (head != null) {
            head.prev = newNode;
        }

        head = newNode;
        size++;

        if (head.next == null) {
            tail = head;
        }
    }

    @Override
    public boolean add(E value) {
        addLast(value);
        return true;
    }

    public void addLast(E value) {
        Node<E> newNode = new Node<E>(value);

        if (size == 0) {
            addFirst(value);
            return;
        }

        tail.next = newNode;
        newNode.prev = tail;
        tail = newNode;
        size++;
    }

    public void add(int index, E value) {

        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException();
        }

        if (index == 0) {
            addFirst(value);
            return;
        }

        if (index == size) {
            addLast(value);
            return;
        }

        Node<E> prev_Node = search(index - 1);
        Node<E> next_Node = prev_Node.next;

        Node<E> newNode = new Node<E>(value);

        prev_Node.next = null;
        next_Node.prev = null;

        prev_Node.next = newNode;
        newNode.prev = prev_Node;
        newNode.next = next_Node;
        next_Node.prev = newNode;

        size++;
    }

    public E remove() {
        Node<E> headNode = head;
        if (headNode == null) {
            throw new NoSuchElementException();
        }

        E element = headNode.data;
        Node<E> nextNode = head.next;

        head.data = null;
        head.next = null;

        if (nextNode != null) {
            nextNode.prev = null;
        }

        head = nextNode;
        size--;

        if (size == 0) {
            tail = null;;
        }

        return element;
    }

    @Override
    public E remove(int index) {

        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException();
        }

        if (index == 0) {
            E element = head.data;
            remove();
            return element;
        }

        Node<E> prevNode = search(index - 1);
        Node<E> removeNode = prevNode.next;
        Node<E> nextNode = removeNode.next;

        E element = removeNode.data;

        prevNode.next = null;
        removeNode.next = null;
        removeNode.data = null;
        removeNode.prev = null;

        if (nextNode != null) {
            nextNode.prev = null;
            nextNode.prev = prevNode;
            prevNode.next = nextNode;
        }

        else {
            tail = prevNode;
        }

        size--;

        return element;
    }

    @Override
    public boolean remove(Object value) {

        Node<E> prevNode = head;
        Node<E> x = head;

        for (; x != null; x = x.next) {
            if (value.equals(x.data)) {
                break;
            }
            prevNode = x;
        }

        if (x == null) {
            return false;
        }

        if (x.equals(head)) {
            remove();
            return true;
        }

        else {
            Node<E> nextNode = x.next;

            prevNode.next = null;
            x.data = null;
            x.next = null;
            x.prev = null;

            if (nextNode != null) {
                nextNode.prev = null;

                nextNode.prev = prevNode;
                prevNode.next = nextNode;
            } else {
                tail = prevNode;
            }
            size--;
            return true;
        }
    }

    @Override
    public E get(int index) {
        return search(index).data;
    }

    @Override
    public void set(int index, E value) {

        Node<E> replaceNode = search(index);
        replaceNode.data = null;
        replaceNode.data = value;
    }

    @Override
    public int indexOf(Object o) {
        int index = 0;

        for (Node<E> x = head; x != null; x = x.next) {
            if (o.equals(x.data)) {
                return index;
            }
            index++;
        }
        return -1;
    }

    public int lastIndexOf(Object o) {
        int index = 0;
        for (Node<E> x = tail; x != null; x = x.prev) {
            if (o.equals(x.data)) {
                return index;
            }
            index++;
        }
        return -1;
    }

    public boolean contains(Object item) {
        return indexOf(item) >= 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public void clear() {
        for (Node<E> x = head; x != null;) {
            Node<E> nextNode = x.next;
            x.data = null;
            x.next = null;
            x.prev = null;
            x = nextNode;
        }
        head = tail = null;
        size = 0;
    }

    public Object clone() throws CloneNotSupportedException {

        @SuppressWarnings("unchecked")
        DoubleLinkedList<? super E> clone = (DoubleLinkedList<? super E>) super.clone();

        clone.head = null;
        clone.tail = null;
        clone.size = 0;

        for (Node<E> x  = head; x != null; x = x.next) {
            clone.addLast(x.data);
        }

        return clone;
    }

    public Object[] toArray() {
        Object[] array = new Object[size];
        int idx = 0;
        for (Node<E> x = head; x != null; x = x.next) {
            array[idx++] = (E) x.data;
        }
        return array;
    }

    @SuppressWarnings("unchecked")
    public <T> T[] toArray(T[] a) {
        if (a.length < size) {
            a = (T[]) java.lang.reflect.Array.newInstance(a.getClass().getComponentType(), size);
        }
        int idx = 0;
        Object[] result = a;
        for (Node<E> x = head; x != null; x = x.next) {
            result[idx++] = x.data;
        }
        return a;
    }

    public void sort() {
        sort(null);
    }

    @SuppressWarnings("unchecked")
    public void sort(Comparator<? super E> c) {
        Object[] a = this.toArray();
        Arrays.sort(a, (Comparator) c);

        int idx = 0;
        for (Node<E> x = head; x != null; x = x.next) {
            x.data = (E) a[idx];
        }
    }
}
