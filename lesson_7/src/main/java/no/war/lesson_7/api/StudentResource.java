package no.war.lesson_7.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import no.war.lesson_7.exception.BadRequestException;
import no.war.lesson_7.exception.StudentNotFoundException;
import no.war.lesson_7.service.StudentService;
import no.war.lesson_7.service.dto.StudentDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/student")
public class StudentResource {

    private final StudentService studentService;

    @GetMapping
    public List<StudentDto> findAll() {
        return studentService.findAll();
    }

    @GetMapping("/{id}")
    public StudentDto findById(@PathVariable("id") Long id) {
        return studentService.findById(id)
                .orElseThrow(() ->
                        new StudentNotFoundException("Student with id = " + id +  " does not exist."));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id) {
        if (id == null || id < 1) {
            return ResponseEntity.badRequest().body("Invalid student id = " + id);
        }

        if (!studentService.existById(id)) {
            throw new StudentNotFoundException("Student with id = " + id +  " does not exist.");
        }

        studentService.deleteById(id);
        return ResponseEntity.ok().body("Student with id = " + id + " was deleted successfully");
    }

    @PostMapping
    public ResponseEntity<StudentDto> save(@RequestBody Optional<StudentDto> studentDto) {
        if (studentDto.isEmpty()) {
            String msg = "Bad request. Empty body.";
            log.warn(msg);
            throw new BadRequestException(msg);
        }

        StudentDto student = studentDto.get();
        String name = student.getName();
        Integer age = student.getAge();

        if (name == null || name.isBlank()) {
            String msg = "Bad request. Invalid name [" + name + "]";
            log.warn(msg);
            throw new BadRequestException(msg);
        }

        if (age == null || age < 1) {
            String msg = "Bad request. Invalid age [" + age + "]";
            log.warn(msg);
            throw new BadRequestException(msg);
        }

        student = studentService.save(student);
        log.info("Student: {} saved successfully", student);
        URI uri = URI.create(ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/api/v1/student")
                .toUriString());
        return ResponseEntity.created(uri).body(student);
    }
}
