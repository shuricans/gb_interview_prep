package no.war.lesson_7.persist.repository;

import no.war.lesson_7.persist.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
}
