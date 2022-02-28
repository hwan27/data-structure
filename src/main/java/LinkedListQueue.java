import interface_form.IQueue;

import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.NoSuchElementException;

public class LinkedListQueue<E> implements IQueue<E> {

    private Node<E> head;
    private Node<E> tail;
    private int size;

    public LinkedListQueue() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    @Override
    public boolean offer(E value) {

        Node<E> newNode = new Node<E>(value);

        if (size == 0) {
            head = newNode;
        } else {
            tail.next = newNode;
        }
        tail = newNode;
        size++;
        return true;
    }

    @Override
    public E poll() {
        if (size == 0) {
            return null;
        }

        E element = head.data;
        Node<E> nextNode = head.next;

        head.data = null;
        head.next = null;

        head = nextNode;
        size--;

        return element;
    }

    public E remove() {

        E element = poll();

        if (element == null) {
            throw new NoSuchElementException();
        }

        return element;
    }

    @Override
    public E peek() {

        if (size == 0) {
            return null;
        }
        return head.data;
    }

    public E element() {

        E element = peek();
        if (element == null) {
            throw new NoSuchElementException();
        }
        return element;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean contains(Object value) {

        for (Node<E> x = head; x != null; x = x.next) {
            if (value.equals(x.data)) {
                return true;
            }
        }
        return false;
    }

    public void clear() {

        for (Node<E> x = head; x != null;) {

            Node<E> next = x.next;
            x.data = null;
            x.next = null;
            x = next;
        }
        size = 0;
        head = tail = null;
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

        int i = 0;
        Object[] res = a;
        for (Node<E> x = head; x != null; x = x.next) {
            res[i++] = x.data;
        }
        return a;
    }

    @Override
    public Object clone() {
        try {
            @SuppressWarnings("unchecked")
            LinkedListQueue<E> clone = (LinkedListQueue<E>) super.clone();
            clone.head = null;
            clone.tail = null;
            clone.size = 0;

            for (Node<E> x = head; x != null; x = x.next) {
                clone.offer(x.data);
            }
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new Error(e);
        }
    }

    public void sort() {
        sort(null);
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    public void sort(Comparator<? super E> c) {
        Object[] a = this.toArray();
        Arrays.sort(a, (Comparator) c);

        int i = 0;
        for (Node<E> x = head; x != null; x = x.next) {
            x.data = (E) a[i];
        }
    }

}
