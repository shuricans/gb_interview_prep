package no.war.lesson_7.service;

import no.war.lesson_7.exception.StudentNotFoundException;
import no.war.lesson_7.persist.model.Student;
import no.war.lesson_7.persist.repository.StudentRepository;
import no.war.lesson_7.service.dto.StudentDto;
import no.war.lesson_7.service.dto.StudentMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class StudentServiceImplTest {

    @Mock
    private StudentRepository studentRepository;
    @Mock
    private StudentMapper studentMapper;

    private StudentService underTest;

    @BeforeEach
    void setUp() {
        underTest = new StudentServiceImpl(studentRepository, studentMapper);
    }

    @Test
    void canGetAllStudents() {
        // when
        underTest.findAll();

        // then
        verify(studentRepository).findAll();
    }

    @Test
    void canGetStudentById() {
        // given
        long studentId = 1;

        // when
        underTest.findById(studentId);

        // then
        ArgumentCaptor<Long> idArgumentCaptor =
                ArgumentCaptor.forClass(Long.class);

        verify(studentRepository).findById(idArgumentCaptor.capture());

        Long capturedStudentId = idArgumentCaptor.getValue();

        assertThat(capturedStudentId).isEqualTo(studentId);
    }

    @Test
    void canDeleteStudentById() {
        // given
        long studentId = 1;

        // when
        underTest.deleteById(studentId);

        // then
        ArgumentCaptor<Long> idArgumentCaptor =
                ArgumentCaptor.forClass(Long.class);

        verify(studentRepository).deleteById(idArgumentCaptor.capture());

        Long capturedStudentId = idArgumentCaptor.getValue();

        assertThat(capturedStudentId).isEqualTo(studentId);
    }

    @Test
    void canCheckStudentExistById() {
        // given
        long studentId = 1;

        // when
        underTest.existById(studentId);

        // then
        ArgumentCaptor<Long> idArgumentCaptor =
                ArgumentCaptor.forClass(Long.class);

        verify(studentRepository).existsById(idArgumentCaptor.capture());

        Long capturedStudentId = idArgumentCaptor.getValue();

        assertThat(capturedStudentId).isEqualTo(studentId);
    }

    @Test
    void canSaveStudent() {
        // given
        String name = "name";
        int age = 30;

        StudentDto studentDto = StudentDto.builder()
                .name(name)
                .age(age)
                .build();

        Student student = Student.builder()
                .name(name)
                .age(age)
                .build();

        // when
        underTest.save(studentDto);

        // then
        ArgumentCaptor<Student> studentArgumentCaptor =
                ArgumentCaptor.forClass(Student.class);

        verify(studentRepository).save(studentArgumentCaptor.capture());

        Student capturedStudent = studentArgumentCaptor.getValue();

        assertThat(capturedStudent).isEqualTo(student);
    }

    @Test
    void shouldThrowStudentNotFoundExceptionWhenStudentDoesNotExist() {
        // given
        StudentDto studentDto = StudentDto.builder()
                .id(1L)
                .name("name")
                .age(30)
                .build();

        given(studentRepository.findById(studentDto.getId()))
                .willReturn(Optional.empty());

        // when
        // then
        assertThatThrownBy(() -> underTest.save(studentDto))
                .isInstanceOf(StudentNotFoundException.class)
                .hasMessage("Student with id = " + studentDto.getId() +  " does not exist.");
    }
}