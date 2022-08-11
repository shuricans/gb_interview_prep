package no.war.lesson_1.task_1;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PersonBuilderTest {

    @Test
    public void builderTest() {
        // given
        String firstName = "FirstName";
        String lastName = "LastName";
        int age = 100;

        // when
        Person personByConstructor = new Person(
                firstName,
                lastName,
                null,
                null,
                null,
                null,
                age,
                null);

        Person personByBuilder = Person.builder()
                .firstName(firstName)
                .lastName(lastName)
                .age(age)
                .build();

        // then
        assertThat(personByConstructor).isEqualTo(personByBuilder);
    }
}