CREATE DATABASE cinema;

-- Create sequences for ids

DROP SEQUENCE IF EXISTS movies_id_movie_seq;
CREATE SEQUENCE IF NOT EXISTS movies_id_movie_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;

DROP SEQUENCE IF EXISTS tikets_id_tiket_seq;
CREATE SEQUENCE IF NOT EXISTS tikets_id_tiket_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;

DROP SEQUENCE IF EXISTS sessions_id_session_seq;
CREATE SEQUENCE IF NOT EXISTS sessions_id_session_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;

-- Create tables

DROP TABLE IF EXISTS movies;
CREATE TABLE IF NOT EXISTS movies
(
    id_movie bigint NOT NULL DEFAULT nextval('movies_id_movie_seq'::regclass),
    name character varying NOT NULL,
    running_time integer NOT NULL,
    CONSTRAINT movies_pkey PRIMARY KEY (id_movie)
);

DROP TABLE IF EXISTS sessions;
CREATE TABLE IF NOT EXISTS sessions
(
    id_session bigint NOT NULL DEFAULT nextval('sessions_id_session_seq'::regclass),
    time_start timestamp without time zone NOT NULL,
    id_movie bigint NOT NULL,
    tiket_price integer NOT NULL,
    CONSTRAINT sessions_pkey PRIMARY KEY (id_session),
    CONSTRAINT fk_sessions_movies FOREIGN KEY (id_movie)
        REFERENCES movies (id_movie) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

DROP TABLE IF EXISTS tikets;
CREATE TABLE IF NOT EXISTS tikets
(
    id_tiket bigint NOT NULL DEFAULT nextval('tikets_id_tiket_seq'::regclass),
    id_session bigint NOT NULL,
    CONSTRAINT tikets_pkey PRIMARY KEY (id_tiket),
    CONSTRAINT fk_tikets_session FOREIGN KEY (id_session)
        REFERENCES sessions (id_session) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);