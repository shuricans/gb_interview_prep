package no.war.lesson_5.task_1.dao;

import no.war.lesson_5.task_1.HibernateUtil;
import no.war.lesson_5.task_1.model.Student;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

import static org.assertj.core.api.Assertions.assertThat;

class StudentDaoImplTest {

    private StudentDaoImpl underTest;

    @BeforeEach
    void setUp() {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        underTest = new StudentDaoImpl(sessionFactory);
    }

    @AfterEach
    void tearDown() {
        underTest.deleteAll();
    }

    @AfterAll
    static void afterAll() {
        HibernateUtil.closeSessionFactory();
    }

    @Test
    void shouldFindStudentById() {
        // given
        long expectedId = 1;
        String name = "Student #1";
        int mark = 5;
        Student student = Student.builder().name(name).mark(mark).build();
        underTest.save(student);

        // when
        Optional<Student> optionalStudent = underTest.findById(expectedId);

        // then
        assertThat(optionalStudent).isPresent().hasValue(student);
        Student studentFromDb = optionalStudent.get();
        assertThat(studentFromDb.getId()).isEqualTo(expectedId);
        assertThat(studentFromDb.getName()).isEqualTo(name);
        assertThat(studentFromDb.getMark()).isEqualTo(mark);
    }

    @Test
    void shouldFindAllStudents() {
        // given
        ArrayList<Student> students = new ArrayList<>();
        int amountStudents = 10;
        for (int i = 1; i <= amountStudents; i++) {
            students.add(
                    Student.builder()
                            .name("Student #" + i)
                            .mark(ThreadLocalRandom.current().nextInt(5) + 1)
                            .build()
            );
        }
        underTest.saveAll(students);

        // when
        List<Student> studentsFromDb = underTest.findAll();

        // then
        assertThat(studentsFromDb).hasSize(amountStudents);
        int randomIndex = ThreadLocalRandom.current().nextInt(amountStudents) + 1;
        Student randomStudent = studentsFromDb.get(randomIndex);
        assertThat(students.get(randomIndex)).isEqualTo(randomStudent);
        assertThat(students.get(randomIndex).getName()).isEqualTo(randomStudent.getName());
    }

    @Test
    void shouldSaveStudent() {
        // given
        String name = "Student";
        Student student = Student.builder().name(name).build();

        // when
        underTest.save(student);

        // then
        List<Student> students = underTest.findAll();
        assertThat(students).hasSize(1);
        Student studentFromDb = students.get(0);
        assertThat(studentFromDb).isEqualTo(student);
        assertThat(studentFromDb.getName()).isEqualTo(name);
    }

    @Test
    void shouldSaveOneThousandStudents() {
        // given
        ArrayList<Student> students = new ArrayList<>();
        int amountStudents = 1000;
        for (int i = 1; i <= amountStudents; i++) {
            students.add(
                    Student.builder()
                            .name("Student #" + i)
                            .mark(ThreadLocalRandom.current().nextInt(5) + 1)
                            .build()
            );
        }

        // when
        underTest.saveAll(students);

        // then
        List<Student> studentsFromDb = underTest.findAll();
        assertThat(studentsFromDb).hasSize(amountStudents);
        int randomIndex = ThreadLocalRandom.current().nextInt(amountStudents) + 1;
        Student randomStudent = studentsFromDb.get(randomIndex);
        assertThat(students.get(randomIndex)).isEqualTo(randomStudent);
        assertThat(students.get(randomIndex).getName()).isEqualTo(randomStudent.getName());
    }

    @Test
    void shouldDeleteAllStudents() {
        // given
        Student student_1 = Student.builder().name("Student #1").build();
        Student student_2 = Student.builder().name("Student #2").build();
        underTest.save(student_1);
        underTest.save(student_2);

        // when
        underTest.deleteAll();

        // then
        List<Student> students = underTest.findAll();
        assertThat(students).isEmpty();
    }

    @Test
    void shouldUpdateExistingStudent() {
        // given
        Student student = Student.builder().name("Student").build();
        underTest.save(student);

        // when
        int expectedMark = 5;
        student.setMark(expectedMark);
        underTest.update(student);

        // then
        List<Student> students = underTest.findAll();
        assertThat(students).hasSize(1);
        Student studentFromDb = students.get(0);
        assertThat(studentFromDb).isEqualTo(student);
        assertThat(studentFromDb.getMark()).isEqualTo(expectedMark);
    }

    @Test
    void shouldDeleteStudent() {
        // given
        Student student_1 = Student.builder().name("Student #1").build();
        underTest.save(student_1);

        // when
        underTest.delete(student_1);

        // then
        List<Student> students = underTest.findAll();
        assertThat(students).isEmpty();
    }
}