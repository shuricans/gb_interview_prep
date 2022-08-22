package no.war.lesson_5.task_1.dao;

import java.util.List;
import java.util.Optional;

public interface Dao<T> {

    Optional<T> findById(long id);

    List<T> findAll();

    void save(T t);

    void saveAll(List<T> students);

    void update(T t);

    void delete(T t);

    void deleteAll();
}