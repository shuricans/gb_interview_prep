package no.war.lesson_7.service.dto;

import no.war.lesson_7.persist.model.Student;
import org.springframework.stereotype.Component;


@Component
public class StudentMapper {

    public StudentDto fromStudent(Student student) {
        return StudentDto.builder()
                .id(student.getId())
                .name(student.getName())
                .age(student.getAge())
                .build();
    }
}
