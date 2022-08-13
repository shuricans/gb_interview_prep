package no.war.lesson_2.task_1;

public interface LinkedList<E> {

    void insertFirst(E value);

    void insertLast(E value);

    E getFirst();

    E getLast();

    E removeFirst();

    E removeLast();

    boolean remove(E value);

    boolean contains(E value);

    int size();

    boolean isEmpty();

    class Node<E> {
        E item;
        Node<E> next;
        Node<E> prev;

        public Node(E item, Node<E> next) {
            this.item = item;
            this.next = next;
        }

        public Node(E item, Node<E> next, Node<E> prev) {
            this.item = item;
            this.next = next;
            this.prev = prev;
        }
    }
}
