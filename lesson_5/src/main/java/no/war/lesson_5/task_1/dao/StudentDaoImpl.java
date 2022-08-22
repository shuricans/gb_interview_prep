package no.war.lesson_5.task_1.dao;

import lombok.RequiredArgsConstructor;
import no.war.lesson_5.task_1.model.Student;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

@RequiredArgsConstructor
public class StudentDaoImpl implements Dao<Student> {

    private final SessionFactory sessionFactory;

    @Override
    public Optional<Student> findById(long id) {
        return execute(
                sessionFactory,
                session -> Optional.ofNullable(session.find(Student.class, id))
        );
    }

    @Override
    public List<Student> findAll() {
        return execute(
                sessionFactory,
                session -> session.createQuery("from Student s", Student.class)
                        .getResultList()
        );
    }

    @Override
    public void save(Student student) {
        executeInTransaction(
                sessionFactory,
                session -> {
                    if (student.getId() == null) {
                        session.persist(student);
                    } else {
                        session.merge(student);
                    }
                });
    }

    @Override
    public void update(Student student) {
        executeInTransaction(
                sessionFactory,
                session -> {
                    if (student.getId() != null) {
                        session.merge(student);
                    }
                });
    }

    @Override
    public void delete(Student student) {
        executeInTransaction(
                sessionFactory,
                session -> session.remove(student));
    }

    @Override
    public void saveAll(List<Student> students) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();

            int batchSize = 25;

            for (int i = 0; i < students.size(); i++) {
                if (i > 0 && i % batchSize == 0) {
                    // flush a batch of inserts and release memory
                    session.flush();
                    session.clear();
                }
                session.persist(students.get(i));
            }

            session.getTransaction().commit();
        } catch (RuntimeException e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        }
    }

    @Override
    public void deleteAll() {
        executeInTransaction(
                sessionFactory,
                session -> {
                    session.createNativeQuery("delete from students", Student.class)
                            .executeUpdate();
                    session.flush();
                }
        );
    }

    private <R> R execute(SessionFactory sessionFactory, Function<Session, R> function) {
        try (Session session = sessionFactory.openSession()) {
            return function.apply(session);
        }
    }

    private void executeInTransaction(SessionFactory sessionFactory, Consumer<Session> consumer) {
        final Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            consumer.accept(session);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
