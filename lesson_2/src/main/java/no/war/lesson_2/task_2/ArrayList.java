package no.war.lesson_2.task_2;

public interface ArrayList<E> {

    boolean add(E e);

    void add(int index, E e);

    boolean remove(E e);

    E remove(int index);

    E get(int index);

    int size();

    int indexOf(E e);

    boolean contains(E e);

    boolean isEmpty();
}
