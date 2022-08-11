package no.war.lesson_1.task_1;


import java.util.Objects;

public class Person {

    private String firstName;
    private String lastName;
    private String middleName;
    private String country;
    private String address;
    private String phone;
    private int age;
    private String gender;

    Person(final String firstName,
           final String lastName,
           final String middleName,
           final String country,
           final String address,
           final String phone,
           final int age,
           final String gender) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.country = country;
        this.address = address;
        this.phone = phone;
        this.age = age;
        this.gender = gender;
    }

    public static Person.PersonBuilder builder() {
        return new Person.PersonBuilder();
    }

    public static class PersonBuilder {
        private String firstName;
        private String lastName;
        private String middleName;
        private String country;
        private String address;
        private String phone;
        private int age;
        private String gender;


        PersonBuilder() {
        }

        public Person.PersonBuilder firstName(final String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Person.PersonBuilder lastName(final String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Person.PersonBuilder middleName(final String middleName) {
            this.middleName = middleName;
            return this;
        }

        public Person.PersonBuilder country(final String country) {
            this.country = country;
            return this;
        }

        public Person.PersonBuilder address(final String address) {
            this.address = address;
            return this;
        }

        public Person.PersonBuilder phone(final String phone) {
            this.phone = phone;
            return this;
        }

        public Person.PersonBuilder age(final int age) {
            this.age = age;
            return this;
        }

        public Person.PersonBuilder gender(final String gender) {
            this.gender = gender;
            return this;
        }

        public Person build() {
            return new Person(this.firstName, this.lastName, this.middleName, this.country, this.address, this.phone, this.age, this.gender);
        }
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return age == person.age && Objects.equals(firstName, person.firstName) && Objects.equals(lastName, person.lastName) && Objects.equals(middleName, person.middleName) && Objects.equals(country, person.country) && Objects.equals(address, person.address) && Objects.equals(phone, person.phone) && Objects.equals(gender, person.gender);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, middleName, country, address, phone, age, gender);
    }
}
