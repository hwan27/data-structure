import interface_form.List;

public class DoubleLinkedList<E> implements List<E> {

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
                x = x.prev
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
}
