package no.war.lesson_7.service;

import lombok.RequiredArgsConstructor;
import no.war.lesson_7.exception.StudentNotFoundException;
import no.war.lesson_7.persist.model.Student;
import no.war.lesson_7.persist.repository.StudentRepository;
import no.war.lesson_7.service.dto.StudentDto;
import no.war.lesson_7.service.dto.StudentMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;

    @Override
    public List<StudentDto> findAll() {
        return studentRepository.findAll()
                .stream()
                .map(studentMapper::fromStudent)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<StudentDto> findById(long id) {
        return studentRepository.findById(id)
                .map(studentMapper::fromStudent);
    }

    @Transactional
    @Override
    public void deleteById(long id) {
        studentRepository.deleteById(id);
    }

    @Override
    public boolean existById(long id) {
        return studentRepository.existsById(id);
    }

    @Transactional
    @Override
    public StudentDto save(StudentDto studentDto) {
        Long studentId = studentDto.getId();
        Student student = (studentId != null) ?
                (studentRepository.findById(studentId)
                        .orElseThrow(() ->
                        new StudentNotFoundException("Student with id = " + studentId +  " does not exist.")))
                : new Student();

        student.setName(studentDto.getName());
        student.setAge(studentDto.getAge());

        student = studentRepository.save(student);
        return studentMapper.fromStudent(student);
    }
}
