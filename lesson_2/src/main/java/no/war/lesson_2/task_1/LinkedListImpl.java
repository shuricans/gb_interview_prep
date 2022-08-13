package no.war.lesson_2.task_1;

public class LinkedListImpl<E> implements LinkedList<E> {

    protected Node<E> first;
    protected Node<E> last;
    protected int size;

    @Override
    public void insertFirst(E value) {
        first = new Node<>(value, first);
        size++;
        if (size == 1) {
            last = first;
        }
    }

    @Override
    public void insertLast(E value) {
        Node<E> newNode = new Node<>(value, null, last);

        if (isEmpty()) {
            insertFirst(value);
            return;
        }

        last.next = newNode;
        last = newNode;
        size++;
    }

    @Override
    public E getFirst() {
        return first.item;
    }

    @Override
    public E getLast() {
        return last.item;
    }

    @Override
    public E removeFirst() {
        if (isEmpty()) {
            return null;
        }

        Node<E> removerNode = first;
        first = removerNode.next;
        removerNode.next = null;
        size--;

        if (isEmpty()) {
            last = null;
        }

        return removerNode.item;
    }

    @Override
    public E removeLast() {
        if (isEmpty()) {
            return null;
        }

        if (size == 1) {
            return removeFirst();
        }

        Node<E> removerNode = last;
        last = removerNode.prev;
        last.next = null;
        removerNode.prev = null;
        size--;

        return removerNode.item;
    }

    @Override
    public boolean remove(E value) {
        Node<E> current = first;
        Node<E> prev = null;

        while (current != null) {
            if (current.item.equals(value)) {
                break;
            }
            prev = current;
            current = current.next;
        }

        if (current == null) {
            return false;
        } else if (current == first) {
            removeFirst();
            return true;
        } else if (current == last) {
            last = prev;
            last.next = null;
        }
        prev.next = current.next;

        current.next = null;
        size--;

        return true;
    }

    @Override
    public boolean contains(E value) {
        Node<E> current = first;
        while (current != null) {
            if (current.item.equals(value)) {
                return true;
            }
            current = current.next;
        }

        return false;
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
    public String toString() {

        StringBuilder sb = new StringBuilder("[");
        Node<E> current = first;
        while (current != null) {
            sb.append(current.item);
            if (current.next != null) {
                sb.append(" -> ");
            }
            current = current.next;
        }
        sb.append("]");

        return sb.toString();
    }
}
