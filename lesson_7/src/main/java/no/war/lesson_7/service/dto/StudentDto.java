package no.war.lesson_7.service.dto;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class StudentDto {

    private Long id;
    private String name;
    private Integer age;
}
