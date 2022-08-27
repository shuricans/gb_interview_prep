package no.war.lesson_7.service;

import no.war.lesson_7.service.dto.StudentDto;

import java.util.List;
import java.util.Optional;

public interface StudentService {

    List<StudentDto> findAll();

    Optional<StudentDto> findById(long id);

    void deleteById(long id);

    boolean existById(long id);

    StudentDto save(StudentDto studentDto);
}
