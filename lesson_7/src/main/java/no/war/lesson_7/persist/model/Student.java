package no.war.lesson_7.persist.model;

import lombok.*;

import javax.persistence.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "students")
public class Student {

    @Id
    @SequenceGenerator(
            name = "students_student_id_seq",
            sequenceName = "students_student_id_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            generator = "students_student_id_seq",
            strategy = GenerationType.SEQUENCE
    )
    @Column(name = "student_id")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "age", nullable = false)
    private Integer age;
}
