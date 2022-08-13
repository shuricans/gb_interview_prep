package no.war.lesson_2.task_2;

import java.util.Arrays;

public class ArrayListImpl<E> implements ArrayList<E> {

    private static final int DEFAULT_CAPACITY = 10;

    private int currentCapacity = DEFAULT_CAPACITY;

    private E[] values;

    private int size;

    public ArrayListImpl() {
        values = (E[]) new Object[DEFAULT_CAPACITY];
    }

    @Override
    public boolean add(E e) {
        if (size == currentCapacity) {
            ensureCapacity();
        }
        values[size] = e;
        size++;
        return true;
    }

    @Override
    public void add(int index, E e) {
        if (index < 0 || index > size - 1) {
            throw new IndexOutOfBoundsException(index);
        }

        if (size == currentCapacity) {
            ensureCapacity();
        }

        System.arraycopy(values, index, values, index + 1, size - index);
        values[index] = e;
        size++;
    }

    @Override
    public boolean remove(E e) {
        int index = indexOf(e);

        if (index == -1) {
            return false;
        }

        if (index == size - 1) { // last element
            values[index] = null;
            size--;
            return true;
        }

        System.arraycopy(values, index + 1, values, index, size - index + 1);
        size--;
        return true;
    }

    @Override
    public E remove(int index) {
        if (index < 0 || index > size - 1) {
            throw new IndexOutOfBoundsException(index);
        }

        E removerValue = values[index];

        if (index == size - 1) { // last element
            values[index] = null;
            size--;
            return removerValue;
        }

        System.arraycopy(values, index + 1, values, index, size - index + 1);
        size--;
        return removerValue;
    }

    @Override
    public E get(int index) {
        if (index < 0 || index > size - 1) {
            throw new IndexOutOfBoundsException(index);
        }
        return values[index];
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public int indexOf(E e) {
        for (int i = 0; i < size; i++) {
            if (values[i].equals(e)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public boolean contains(E e) {
        for (int i = 0; i < size; i++) {
            if (values[i].equals(e)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private void ensureCapacity() {
        currentCapacity = size * 2;
        values = Arrays.copyOf(values, currentCapacity);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");

        for (int i = 0; i < size; i++) {
            sb.append(values[i]);
            if (i < size - 1) {
                sb.append(" -> ");
            }
        }

        sb.append("]");

        return sb.toString();
    }
}
