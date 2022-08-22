package no.war.lesson_5.task_1.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@Builder
@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "students")
public class Student {

    @Id
    @SequenceGenerator(
            name = "student_id_seq",
            sequenceName = "student_id_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            generator = "student_id_seq",
            strategy = GenerationType.SEQUENCE)
    private Long id;

    private String name;

    private Integer mark;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return Objects.equals(id, student.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
