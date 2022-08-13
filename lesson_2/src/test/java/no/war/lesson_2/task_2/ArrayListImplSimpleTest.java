package no.war.lesson_2.task_2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ArrayListImplSimpleTest {

    private ArrayListImpl<String> arrayList;

    @BeforeEach
    void setUp() {
        arrayList = new ArrayListImpl<>();
        for (int i = 0; i < 15; i++) {
            arrayList.add(String.valueOf(i + 1));
        }
    }

    @Test
    void add() {
        // when
        boolean result = arrayList.add("16");
        // then
        assertThat(result).isTrue();
        assertThat(arrayList.size()).isEqualTo(16);
    }

    @Test
    void shouldAddObjectAtSpecifiedPositionByIndex() {
        // given
        String newObject = "new";
        int index = 5;
        // Before
        // arrayList = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15] - size 15

        // when
        arrayList.add(index, newObject);
        // After
        // arrayList = [1, 2, 3, 4, 5, new, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15] - size 15

        // then
        assertThat(arrayList.size()).isEqualTo(16);
        assertThat(arrayList.get(index)).isEqualTo(newObject);
    }


    @Test
    void shouldThrowIndexOutOfBoundExceptionWhenSpecifiedPositionByIndexIsInvalid() {
        // given
        int invalidIndex = 15;

        // then
        assertThatThrownBy(() -> arrayList.add(invalidIndex, "new"))
                .isInstanceOf(IndexOutOfBoundsException.class)
                .hasMessageContaining("Index out of range: " + invalidIndex);
    }

    @Test
    void shouldRemoveObjectFromList() {
        // given
        String removerObject = "9";
        // arrayList = [1, 2, 3, 4, 5, 6, 7, 8, |9|, 10, 11, 12, 13, 14, 15] - size 15

        // when
        boolean result = arrayList.remove(removerObject);
        // arrayList = [1, 2, 3, 4, 5, 6, 7, 8, 10, 11, 12, 13, 14, 15] - size 14

        // then
        assertThat(result).isTrue();
        assertThat(arrayList.size()).isEqualTo(14);
        assertThat(arrayList.get(8)).isEqualTo("10");
    }

    @Test
    void shouldLastObjectFromList() {
        // given
        String removerObject = "15";
        // arrayList = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, |15|] - size 15

        // when
        boolean result = arrayList.remove(removerObject);
        // arrayList = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14] - size 14

        // then
        assertThat(result).isTrue();
        assertThat(arrayList.size()).isEqualTo(14);
        assertThat(arrayList.get(13)).isEqualTo("14");
    }

    @Test
    void shouldReturnFalseWhenRemoverObjectNotExistInList() {
        // given
        String removerObject = "23";

        // when
        boolean result = arrayList.remove(removerObject);

        // then
        assertThat(result).isFalse();
        assertThat(arrayList.size()).isEqualTo(15);
    }

    @Test
    void shouldRemoveObjectByIndexAndReturnIt() {
        // given
        int removerIndex = 3;
        String expectedRemoverObject = arrayList.get(removerIndex); // |4|
        // arrayList = [1, 2, 3, |4|, 5, 6, 7, 8, |9|, 10, 11, 12, 13, 14, 15] - size 15

        // when
        String removerObject = arrayList.remove(removerIndex);
        // arrayList = [1, 2, 3, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15] - size 14

        // then
        assertThat(removerObject).isEqualTo(expectedRemoverObject);
        assertThat(arrayList.size()).isEqualTo(14);
        assertThat(arrayList.get(removerIndex)).isEqualTo("5");
    }

    @Test
    void shouldRemoveObjectByLastIndexAndReturnIt() {
        // given
        int removerIndex = 14;
        String expectedRemoverObject = arrayList.get(removerIndex); // |15|
        // arrayList = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, |15|] - size 15

        // when
        String removerObject = arrayList.remove(removerIndex);
        // arrayList = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14] - size 14

        // then
        assertThat(removerObject).isEqualTo(expectedRemoverObject);
        assertThat(arrayList.size()).isEqualTo(14);
    }

    @Test
    void shouldThrowIndexOutOfBoundExceptionWhenIndexInvalid() {
        // given
        int invalidIndex = 16;

        // then
        assertThatThrownBy(() -> arrayList.remove(invalidIndex))
                .isInstanceOf(IndexOutOfBoundsException.class)
                .hasMessageContaining("Index out of range: " + invalidIndex);
    }

    @Test
    void shouldGetObjectByIndex() {
        // when
        String s = arrayList.get(4);

        // then
        assertThat(s).isEqualTo("5");
    }

    @Test
    void shouldThrowIndexOutOfBoundExceptionWhenTryGetByInvalidIndex() {
        // given
        int invalidIndex = 16;

        // then
        assertThatThrownBy(() -> arrayList.get(invalidIndex))
                .isInstanceOf(IndexOutOfBoundsException.class)
                .hasMessageContaining("Index out of range: " + invalidIndex);
    }

    @Test
    void size() {
        // when
        // then
        assertThat(arrayList.size()).isEqualTo(15);
    }

    @Test
    void shouldBeZeroWhenListIsEmpty() {
        // given
        arrayList = new ArrayListImpl<>();
        // when
        // then
        assertThat(arrayList.size()).isEqualTo(0);
    }


    @Test
    void shouldGetIndexByObject() {
        // when
        // then
        assertThat(arrayList.indexOf("5")).isEqualTo(4);
    }

    @Test
    void shouldReturnMinusOneThenObjectNotExistInList() {
        // when
        // then
        assertThat(arrayList.indexOf("17")).isEqualTo(-1);
    }

    @Test
    void shouldReturnTrueWhenObjectContainsInList() {
        // when
        // then
        assertThat(arrayList.contains("10")).isTrue();
    }

    @Test
    void shouldReturnFalseWhenObjectNotExistInList() {
        // when
        // then
        assertThat(arrayList.contains("a")).isFalse();
    }

    @Test
    void shouldBeFalseWhenListNotEmpty() {
        assertThat(arrayList.isEmpty()).isFalse();
    }

    @Test
    void shouldBeTrueWhenListIsEmpty() {
        arrayList = new ArrayListImpl<>();
        assertThat(arrayList.isEmpty()).isTrue();
    }


    @Test
    void testToString() {
        // given
        String expectedValue = "[1 -> 2 -> 3 -> 4 -> 5 -> 6 -> 7 -> 8 -> 9 -> 10 -> 11 -> 12 -> 13 -> 14 -> 15]";

        // when
        String result = arrayList.toString();

        // then
        assertThat(result).isEqualTo(expectedValue);
    }
}