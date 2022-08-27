package no.war.lesson_7.api;

import lombok.SneakyThrows;
import no.war.lesson_7.persist.model.Student;
import no.war.lesson_7.persist.repository.StudentRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(locations = "classpath:application-tc.properties")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class StudentResourceTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private StudentRepository studentRepository;

    private static ArrayList<Student> students;

    @BeforeAll
    static void init(@Autowired StudentRepository studentRepository) {
        students = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            students.add(Student.builder()
                    .name("name_" + i)
                    .age(30 + i)
                    .build());
        }
        studentRepository.saveAll(students);
    }

    @SneakyThrows
    @Test
    void shouldFetchAllStudents() {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/api/v1/student")
                .contentType(MediaType.APPLICATION_JSON);

        int amountStudents = (int) studentRepository.count();

        int studentId = 5;
        Student student = students.get(studentId);


        mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(amountStudents)))
                .andExpect(jsonPath("$["+ studentId +"].name", is(student.getName())));
    }

    @SneakyThrows
    @Test
    void shouldFindStudentById() {
        int studentId = 3;

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get("/api/v1/student/" + studentId)
                .contentType(MediaType.APPLICATION_JSON);

        Student student = students.get(studentId - 1);
        mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(student.getName())));
    }

    @SneakyThrows
    @Test
    void shouldGetNotFoundWhenWeTryGetByDoesNotExistId() {
        int nonExistentId = 45;
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get("/api/v1/student/" + nonExistentId)
                .contentType(MediaType.APPLICATION_JSON);

        mvc.perform(request)
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error", is("error-0100")))
                .andExpect(jsonPath("$.message",
                        is("Student with id = " + nonExistentId +  " does not exist.")));
    }

    @SneakyThrows
    @Test
    void shouldDeleteStudentById() {
        int studentId = 6;

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .delete("/api/v1/student/" + studentId)
                .contentType(MediaType.APPLICATION_JSON);

        mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",
                        is("Student with id = " + studentId + " was deleted successfully")));
    }

    @SneakyThrows
    @Test
    void shouldGetBadRequestWhenWeTryDeleteByInvalidId() {
        int invalidId = -26;
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .delete("/api/v1/student/" + invalidId)
                .contentType(MediaType.APPLICATION_JSON);

        mvc.perform(request)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", is("Invalid student id = " + invalidId)));
    }

    @SneakyThrows
    @Test
    void shouldGetNotFoundWhenWeTryDeleteByDoesNotExistId() {
        int nonExistentId = 13;
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .delete("/api/v1/student/" + nonExistentId)
                .contentType(MediaType.APPLICATION_JSON);

        mvc.perform(request)
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error", is("error-0100")))
                .andExpect(jsonPath("$.message",
                        is("Student with id = " + nonExistentId +  " does not exist.")));
    }

    @SneakyThrows
    @Test
    void shouldSaveNewStudent() {
        String name = "New Student";
        int age = 20;

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post("/api/v1/student/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(getMessageBody(name, age));

        // new generated id
        int expectedNewId = 11;
        mvc.perform(request)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(expectedNewId)))
                .andExpect(jsonPath("$.name", is(name)))
                .andExpect(jsonPath("$.age", is(age)));
    }

    @SneakyThrows
    @Test
    void shouldGetBadRequestWhenWeTrySaveWithoutRequestBody() {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post("/api/v1/student/")
                .contentType(MediaType.APPLICATION_JSON);

        mvc.perform(request)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error", is("error-0200")))
                .andExpect(jsonPath("$.message", is("Bad request. Empty body.")));
    }

    private String getMessageBody(String name, int age) {
        return "{\"name\":\"" + name + "\",\"age\":\"" + age + "\"}";
    }
}