package no.war.lesson_2.task_1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class LinkedListImplSimpleTest {

    private LinkedList<String> linkedList;

    @BeforeEach
    void setUp() {
        linkedList = new LinkedListImpl<>();
    }

    @Test
    void shouldInsertFirst() {
        // when
        String firstValue = "value";
        linkedList.insertFirst(firstValue);

        // then
        assertThat(linkedList.size()).isEqualTo(1);
        assertThat(linkedList.getFirst()).isEqualTo(firstValue);
    }

    @Test
    void shouldInsertLast() {
        // when
        String lastValue = "value";
        linkedList.insertLast(lastValue);

        // then
        assertThat(linkedList.size()).isEqualTo(1);
        assertThat(linkedList.getLast()).isEqualTo(lastValue);
    }

    @Test
    void shouldGetFirst() {
        // when
        String firstValue = "first value";
        linkedList.insertLast("last value");
        linkedList.insertFirst("value");
        linkedList.insertFirst(firstValue);

        // then
        assertThat(linkedList.size()).isEqualTo(3);
        assertThat(linkedList.getFirst()).isEqualTo(firstValue);
    }

    @Test
    void shouldGetLast() {
        // when
        String lastValue = "last value";
        linkedList.insertFirst("first value");
        linkedList.insertLast("value");
        linkedList.insertLast(lastValue);

        // then
        assertThat(linkedList.size()).isEqualTo(3);
        assertThat(linkedList.getLast()).isEqualTo(lastValue);
    }

    @Test
    void shouldRemoveFirst() {
        // given
        String firstValue = "first value";
        linkedList.insertLast("last value");
        linkedList.insertFirst("value");
        linkedList.insertFirst(firstValue);

        // when
        String removerItem = linkedList.removeFirst();

        // then
        assertThat(linkedList.size()).isEqualTo(2);
        assertThat(removerItem).isEqualTo(firstValue);
    }

    @Test
    void shouldReturnNullThenListIsEmptyOnRemoveFirst() {
        // when
        // then
        assertThat(linkedList.removeFirst()).isNull();
    }

    @Test
    void shouldRemoveLast() {
        // given
        String lastValue = "last value";
        linkedList.insertFirst("first value");
        linkedList.insertLast("value");
        linkedList.insertLast(lastValue);

        // when
        String removerItem = linkedList.removeLast();

        // then
        assertThat(linkedList.size()).isEqualTo(2);
        assertThat(removerItem).isEqualTo(lastValue);
    }

    @Test
    void shouldReturnNullThenListIsEmptyOnRemoveLast() {
        // when
        // then
        assertThat(linkedList.removeLast()).isNull();
    }

    @ParameterizedTest
    @MethodSource("data")
    <E> void remove(boolean result, LinkedListImpl<E> linkedList, E value) {
        assertThat(linkedList.remove(value)).isEqualTo(result);
    }

    @ParameterizedTest
    @MethodSource("data")
    <E> void contains(boolean result, LinkedListImpl<E> linkedList, E value) {
        assertThat(linkedList.contains(value)).isEqualTo(result);
    }

    @Test
    void size() {
        assertThat(linkedList.isEmpty()).isTrue();
    }

    @Test
    void isEmpty() {
        assertThat(linkedList.isEmpty()).isTrue();
    }

    @Test
    void testToString() {
        // given
        String expectedValue = "[a -> b -> c -> d -> 1 -> 2 -> 3]";

        linkedList.insertLast("a");
        linkedList.insertLast("b");
        linkedList.insertLast("c");
        linkedList.insertLast("d");
        linkedList.insertLast("1");
        linkedList.insertLast("2");
        linkedList.insertLast("3");

        // when
        String s = linkedList.toString();

        // then
        assertThat(s).isEqualTo(expectedValue);
    }

    private static Stream<Arguments> data() {
        LinkedListImpl<String> linkedList_1 = new LinkedListImpl<>();
        linkedList_1.insertLast("a");
        linkedList_1.insertLast("b");
        linkedList_1.insertLast("c");
        linkedList_1.insertLast("d");
        linkedList_1.insertLast("1");
        linkedList_1.insertLast("2");
        linkedList_1.insertLast("3");
        LinkedListImpl<String> emptyList = new LinkedListImpl<>();
        return Stream.of(
                Arguments.of(false, linkedList_1, "e"),
                Arguments.of(true, linkedList_1, "a"),
                Arguments.of(true, linkedList_1, "2"),
                Arguments.of(false, emptyList, "2")
        );
    }
}