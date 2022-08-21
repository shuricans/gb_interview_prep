CREATE DATABASE students;

-- Create sequences for ids

DROP SEQUENCE IF EXISTS students_id_seq;
CREATE SEQUENCE IF NOT EXISTS students_id_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;


-- Create tables
DROP TABLE IF EXISTS students;
CREATE TABLE IF NOT EXISTS students
(
    id bigint NOT NULL DEFAULT nextval('students_id_seq'::regclass),
    name character varying NOT NULL,
    mark integer,
    CONSTRAINT student_pkey PRIMARY KEY (id)
);
